package com.myproject.manager;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 * Authentication manager used to authenticate user credentials against Active directory
 * users and accordinlgy return response.
 *  
 * @author anand
 *
 */
public class ADAuthenticatorManager {
	public static String serverName = "104.197.91.131";

	private static String domain;
	private static String ldapHost;
	private static String searchBase;
	   
	public ADAuthenticatorManager() {
	    this.domain = "ids.com";  //"<your domain>";
	    this.ldapHost = "ldap://104.197.91.131:389"; //"ldap://<your AD controller>";
	    this.searchBase =  "dc=ids,dc=com" ; // "your AD root e.g. dc=xyz,dc=org";
	}
	 
	public ADAuthenticatorManager(String domain, String host, String dn) {
	    this.domain = domain;
	    this.ldapHost = host;
	    this.searchBase = dn;
	}
	 
	public static Map<String, Object> authenticate(String user, String pass) {
		System.out.println("Authenticating " + user + "@" + domain + " through " + serverName + "." + domain);

		//Specify the attributes to return
	      String returnedAtts[]={"sn","givenName", "samAccountName"};
	    // String returnedAtts[] ={ "sn", "givenName", "mail" };
	    //specify the LDAP search filter
	    //  String searchFilter = "(&(objectClass=user))";
	    String searchFilter = "(&(objectClass=user)(sAMAccountName=" + user + "))";
	     
	    //Create the search controls
	    SearchControls searchCtls = new SearchControls();
	    searchCtls.setReturningAttributes(returnedAtts);
	     
	    //Specify the search scope
	    searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	    
	     
	    Hashtable<String, String> env = new Hashtable<String, String>();
	    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	    env.put(Context.PROVIDER_URL, ldapHost);
	    env.put(Context.SECURITY_AUTHENTICATION, "simple");
	    env.put(Context.SECURITY_PRINCIPAL, user + "@" + domain); 
	   // ldapEnv.put(Context.SECURITY_PRINCIPAL, "cn=jean paul blanc,ou=MonOu,dc=dom,dc=fr");
	    env.put(Context.SECURITY_CREDENTIALS, pass);
	     
	    LdapContext ctxGC = null;
	     
	    try
	    {
	      ctxGC = new InitialLdapContext(env, null);
	      //Search objects in GC using filters
	      NamingEnumeration<?> answer = ctxGC.search(searchBase, searchFilter, searchCtls);
	      while (answer.hasMoreElements())
	      {
	        SearchResult sr = (SearchResult) answer.next();
	        System.out.println("Search result name: " + sr.getName());
	        
	        Attributes attrs = sr.getAttributes();
	        System.out.println(">>>>>>" + attrs.get("samAccountName"));
	        
	        Map<String, Object> amap = null;
	        int totalResults = 0;
	        
	        if (attrs != null)
	        {
	          amap = new HashMap<String, Object>();
	          NamingEnumeration<?> ne = attrs.getAll();
	          while (ne.hasMore())
	          {
	        	totalResults++;
	        	  
	            Attribute attr = (Attribute) ne.next();
	            System.out.println("Attributes are:"+attr.getID() +" and "+attr.get());
	            amap.put(attr.getID(), attr.get());
	          }
	          
	          System.out.println("Total results: " + totalResults);
	          
	          ne.close();
	         // ctxGC.close();
	        }
	          return amap;
	      }
	    } catch (AuthenticationException a) {
            System.out.println("Authentication failed: " + a);
            System.exit(1);
        }
	    catch (NamingException ex)
	    {
	    	 System.out.println("Failed to bind to LDAP / get account information: " + ex);
	    	 ex.printStackTrace();
	    	 System.exit(1);
	    }
	     
	    return null;
	 }
	  
	 public static void main(String[] args) {
		  String user = "saapisvc"; //saapisvc
		  String password = "demo@123";
		 
		  
		  ADAuthenticatorManager adm = new ADAuthenticatorManager("ids.com","ldap://"+serverName+":389","dc=ids,dc=com");
		  Map<String, Object> userMap = adm.authenticate(user,password);
		  if (userMap == null){
			  System.out.println("login failed");
		  }
		  else {
			  System.out.println("login successfull");
			  System.out.println("Server return\n");
			  for(String values :userMap.keySet()){
				 
				  System.out.println(values+"-"+userMap.get(values));
				  
			  }
		  // userMap has three attributes: sn, givenName, mail
		  }
	  }
}
