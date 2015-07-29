package com.myproject.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.as.response.OAuthASResponse.OAuthTokenResponseBuilder;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;

import com.myproject.manager.UserManager;

@Path("/authorize")
public class UserService extends IMSService {
	private static OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response signIn(@FormParam("username") String username, @FormParam("password") String pwd) throws OAuthSystemException {
		boolean userStatus = UserManager.isUserValid(username, pwd);
	     
        if (!userStatus) {
        	return buildInvalidUserResponse("Invalid username or password.");
        }

        return generateToken(userStatus,username);
	}
	
//	@POST
//	@Path("/token")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getToken(@HeaderParam("auth_code") String authCode) throws OAuthSystemException {
//	     
//        if (!SignInManager.isAuthCodeValid(authCode)) {
//        	return buildInvalidUserResponse("Invalid auth-code.");
//        }
//        
//        return generateToken();
//	}
//
	@POST
	@Path("/token")
	@Produces(MediaType.APPLICATION_JSON)
	public Response refresh(@HeaderParam("refresh_token") String refreshToken) throws OAuthSystemException {

    	if (!UserManager.isRefreshTokenValid(refreshToken)) {
    		return buildInvalidUserResponse("Invalid refresh token.");
    	}
    	
    	return generateToken(false,null);
	}

	@PUT
	@Path("/logout")
	public Response signOut(@HeaderParam("Authorization") String token) throws OAuthSystemException {

    	Response res = validateToken(token);
    	
    	if (null == res) {
	    	boolean status = UserManager.signOut(token);
	
			res = getResponse()
				.status(status ? Response.Status.OK : Response.Status.NOT_MODIFIED)
				.entity(status ? "success" : "failed")
				.build();
    	}
    	
    	return res;
	}

	private Response generateToken(boolean isRefresh,String username) throws OAuthSystemException {
		String accessToken = oauthIssuerImpl.accessToken();
		String refreshToken = null;
		if (isRefresh) {
			refreshToken = oauthIssuerImpl.refreshToken();
		}
		UserManager.addToken(accessToken, refreshToken, username);
		
		OAuthTokenResponseBuilder bilder = OAuthASResponse
										        .tokenResponse(200)
										        .setAccessToken(accessToken)
										        .setTokenType("bearer")
										        .setExpiresIn("3600");
        if (isRefresh) {
        	bilder.setRefreshToken(refreshToken);
        }
        OAuthResponse response = bilder.buildJSONMessage();
		
		return getResponse().status(response.getResponseStatus()).entity(response.getBody()).build();
	}

	private Response buildInvalidUserResponse(String msg) throws OAuthSystemException {
        OAuthResponse response = OAuthASResponse
                .errorResponse(401) //unauthorized
                .setErrorDescription(msg)
                .buildJSONMessage();
        return getResponse().status(response.getResponseStatus()).entity(response.getBody()).build();
    }
}