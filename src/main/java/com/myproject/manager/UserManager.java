package com.myproject.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myproject.DBConnection;


public class UserManager {
	
    private static final Logger log = LoggerFactory
    	    .getLogger(UserManager.class);
	
	public static boolean isUserValid(String uid, String password) {
		// Make a AD call to authenticate user credentials
		  ADAuthenticatorManager adm = new ADAuthenticatorManager("ids.com","ldap://"+ADAuthenticatorManager.serverName+":389","dc=ids,dc=com");
		  
		  if(uid != null && password !=null){
			  Map<String, Object> userMap = adm.authenticate(uid,password);
			  
			  if (userMap != null){
				  log.info("login successfull");
				  log.info("Server return\n");
				  for(String values :userMap.keySet()){
					 
					  if (values.equals("sAMAccountName")){
						  String username = userMap.get(values).toString();
						  log.info("User '"+username+"' is validated ! ");
						  
						  if(username.equals(uid)){
							  return true;
						  }else {
							  return false;
						  }
					  }
					  
				  }
			  // userMap has three attributes: sn, givenName, mail
			  } else {
				  log.info("login failed");
				  return false;
			  }
		  }

		  
		return true;
	}

	/**
	 * just check if code exists in db
	 * @param authCode
	 * @return
	 */
	public static boolean isAuthCodeValid(String authCode) {
		return true;
	}
	
	/**
	 * check if token exists in db and compare if expiry period has exceeded.
	 * @param token
	 * @return
	 */
	public static boolean isTokenValid(String token) {
		long now = Calendar.getInstance().getTimeInMillis();
		String query = "select tokenvalidity from portaldb01.session where accesstoken=?";
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		try {
			conn = DBConnection.getConnection();
			preparedStatement = conn.prepareStatement(query);
			
			System.out.println("Prepared Statement before bind variables set:\n\t" + preparedStatement.toString());
			preparedStatement.setString(1, token.split(" ")[1]);
			System.out.println("Prepared Statement after bind variables set:\n\t" + preparedStatement.toString());
			
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				long later = rs.getLong(1);
				if (now >= later) {
					return false;
				}
			} else {
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
			    }
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	/**
	 * just check if exists in db
	 * @param token
	 * @return
	 */
	public static boolean isRefreshTokenValid(String token) {
		long now = Calendar.getInstance().getTimeInMillis();
		String query = "select count(*) from portaldb01.session where refreshtoken=?";
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		try {
			conn = DBConnection.getConnection();
			preparedStatement = conn.prepareStatement(query);
			
			System.out.println("Prepared Statement before bind variables set:\n\t" + preparedStatement.toString());
			preparedStatement.setString(1, token);
			System.out.println("Prepared Statement after bind variables set:\n\t" + preparedStatement.toString());
			
			ResultSet rs = preparedStatement.executeQuery();
			
			return rs.next();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
			    }
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return false;
	}


	/** NO NEED TO IMPLEMENT NOW.
	 * TODO:
	 * @param token
	 * @param refreshToken
	 */
	public static void addAuthCode(String authCode) {

	}
	
	/**
	 * store token, refreshToken and current time
	 * @param token
	 * @param refreshToken
	 */
	public static void addToken(String token, String refreshToken, String username) {
		long now = Calendar.getInstance().getTimeInMillis();
		long oneHourLater = now + 3600 * 1000;
		
		String query = "update portaldb01.Session set AccessToken=?, RefreshToken=?, TokenValidity=? where userId=?";
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		 
		try {
			conn = DBConnection.getConnection();
			
			//Call to update token
			preparedStatement = conn.prepareStatement(query);
			
			System.out.println("Prepared Statement before bind variables set:\n\t" + preparedStatement.toString());
			preparedStatement.setString(1, token);
			preparedStatement.setString(2, refreshToken);
			preparedStatement.setLong(3, oneHourLater);
			preparedStatement.setString(4, getUserId(username));
			System.out.println("Prepared Statement after bind variables set:\n\t" + preparedStatement.toString());
			
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
			    }
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private static String getUserId(String username){
		ResultSet resultSet = null;
		String userId = null;
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		
		String userIdQuery = "select UserID from portaldb01.UserProfile where FirstName=?";
		
		try {
			conn = DBConnection.getConnection();
			//Call to get UserId
			preparedStatement = conn.prepareStatement(userIdQuery);
			System.out.println("Prepared Statement before bind variables set:\n\t" + preparedStatement.toString());
			preparedStatement.setString(1, username);
			System.out.println("Prepared Statement after bind variables set:\n\t" + preparedStatement.toString());
			
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				userId = resultSet.getString("UserID");
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
			    }
				if (resultSet != null) {
					resultSet.close();
			    }
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return userId;
		
		
	}
	
	public static boolean signOut(String token) {
		
		String query = "update portaldb01.session set AccessToken=null, TokenValidity=null where accesstoken=?";
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		try {
			conn = DBConnection.getConnection();
			preparedStatement = conn.prepareStatement(query);
			
			System.out.println("Prepared Statement before bind variables set:\n\t" + preparedStatement.toString());
			preparedStatement.setString(1, token.split(" ")[1]);
			System.out.println("Prepared Statement after bind variables set:\n\t" + preparedStatement.toString());
			
			return preparedStatement.executeUpdate() > 0;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
			    }
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return false;
	}
}
