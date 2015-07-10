package com.myproject.rest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;













import com.myproject.Image;
import com.myproject.Workbook;
/*import com.myproject.resource.GranteeCapabilitiesType;
import com.myproject.resource.GroupType;
import com.myproject.resource.ProjectListType;
import com.myproject.resource.ProjectType;
import com.myproject.resource.TableauCredentialsType;
import com.myproject.resource.WorkbookType;*/
import com.tableausoftware.documentation.api.rest.bindings.GranteeCapabilitiesType;
import com.tableausoftware.documentation.api.rest.bindings.GroupType;
import com.tableausoftware.documentation.api.rest.bindings.ProjectListType;
import com.tableausoftware.documentation.api.rest.bindings.ProjectType;
import com.tableausoftware.documentation.api.rest.bindings.TableauCredentialsType;
import com.tableausoftware.documentation.api.rest.bindings.ViewListType;
import com.tableausoftware.documentation.api.rest.bindings.ViewType;
import com.tableausoftware.documentation.api.rest.bindings.WorkbookType;




/**
 * This class demonstrates how to make Tableau REST API calls. It first gets
 * some required values from the configuration file, including the credentials
 * for a system administrator to authenticate as, and the name or IP address of the
 * server to sign in to. The code then makes REST API calls to perform these
 * tasks:
 *
 * 1. Sign in to the server ("Sign In" method).
 * 2. Get a list of projects ("Query Projects" method).
 * 3. Get the ID of the current project, which is the default project.
 * 4. Publish a workbook ("Publish Workbook").
 * 5. Create a group on the server ("Create Group" method).
 * 6. Set permissions for the group on the published workbook ("Add Workbook Permissions" method).
 * 7. Get a list of workbooks that the current user can read ("Query Workbooks For User" method).
 * 8. Sign out ("Sign Out" method).
 */
public class Demo {

    private static Logger s_logger = Logger.getLogger(Demo.class);

    private static Properties s_properties = new Properties();

    private static RestApiUtils s_restApiUtils = null ;//RestApiUtils.getInstance();
/*
    static {
        // Configures the logger to log to stdout
        BasicConfigurator.configure();

        // Loads the values from configuration file into the Properties instance
        try {
            s_properties.load(new FileInputStream("src/main/resources/config.properties"));
        } catch (IOException e) {
            s_logger.error("Failed to load configuration files.");
        }
    }*/
    
    public Demo(){
    	//Properties s_properties = new Properties();
    	 // Configures the logger to log to stdout
        BasicConfigurator.configure();

        // Loads the values from configuration file into the Properties instance
        try {
            s_properties.load(new FileInputStream("/Users/anand/Documents/RESTfulIMSService/src/main/resources/config.properties"));
        } catch (IOException e) {
            s_logger.error("Failed to load configuration files.");
        }
        
    	s_restApiUtils = RestApiUtils.getInstance();
    }
    
    public Workbook  getWorkbookNames(){
    	List<String> workbookNames= new ArrayList<String>();
    	Workbook wb = new Workbook();

    	String currentSiteId = null;

        // Sets the username, password, and content URL, which are all required
        // in the payload of a Sign In request
        String username = s_properties.getProperty("user.admin.name");
        String password = s_properties.getProperty("user.admin.password");
        String contentUrl = s_properties.getProperty("site.default.contentUrl");

        // Signs in to server and saves the authentication token, site ID, and current user ID
        TableauCredentialsType credential = s_restApiUtils.invokeSignIn(username, password, contentUrl);
         currentSiteId = credential.getSite().getId();
        String currentUserId = credential.getUser().getId();

        s_logger.info(String.format("Authentication token: %s", credential.getToken()));
        s_logger.info(String.format("Site ID: %s", currentSiteId));


        // Gets the list of workbooks the current user can read
        List<WorkbookType> currentUserWorkbooks = s_restApiUtils.invokeQueryWorkbooks(credential, currentSiteId,
                currentUserId).getWorkbook();

        // Checks whether the workbook published previously is in the list, then
        // checks whether the workbook's owner is the current user
        for (WorkbookType workbook : currentUserWorkbooks) {
            /*if (workbook.getId().equals(publishedWorkbook.getId())) {
                s_logger.debug(String.format("Published workbook found: %s", workbook.getId()));

                if (workbook.getOwner().getId().equals(currentUserId)) {
                    s_logger.debug("Published workbook was published by current user");
                }
            }*/
        	
        	workbookNames.add(workbook.getName());
        	s_logger.info("SUCCESS, WORKBOOKID-"+workbook.getName());
        }

        
        wb.setSiteId(currentSiteId);
        wb.setWorkbookName(workbookNames);

        // Signs out of the server. This invalidates the authentication token so
        // that it cannot be used for more requests.
        s_restApiUtils.invokeSignOut(credential);
		return wb;
	
    }
    
    public Image  getWorkbookImage(){
    	String currentSiteId = null;
    	Image image = null;
    	String workbookImage=null;
    	//String imageName=null;

        // Sets the username, password, and content URL, which are all required
        // in the payload of a Sign In request
        String username = s_properties.getProperty("user.admin.name");
        String password = s_properties.getProperty("user.admin.password");
        String contentUrl = s_properties.getProperty("site.default.contentUrl");

        // Signs in to server and saves the authentication token, site ID, and current user ID
        TableauCredentialsType credential = s_restApiUtils.invokeSignIn(username, password, contentUrl);
         currentSiteId = credential.getSite().getId();
        String currentUserId = credential.getUser().getId();

        s_logger.info(String.format("Authentication token: %s", credential.getToken()));
        s_logger.info(String.format("Site ID: %s", currentSiteId));

        // Queries the projects on the current site and iterates over the list to
        // find the ID of the default project
        /* ProjectType defaultProject = null;
        ProjectListType projects = s_restApiUtils.invokeQueryProjects(credential, currentSiteId);
        for (ProjectType project : projects.getProject()) {
            if (project.getName().equals("default")) {
                defaultProject = project;

                s_logger.info(String.format("Default project found: %s", defaultProject.getId()));
            }
        }

        // Sets the name to assign to the workbook to be published
        String workbookName = s_properties.getProperty("workbook.sample.name");

        // Gets the workbook file to publish
        String workbookPath = s_properties.getProperty("workbook.sample.path");
        File workbookFile = new File(workbookPath);

        // Gets whether or not to publish the workbook using file uploads
        boolean chunkedPublish = Boolean.valueOf(s_properties.getProperty("workbook.publish.chunked"));

        // Publishes the workbook as a multipart request
        WorkbookType publishedWorkbook = s_restApiUtils.invokePublishWorkbook(credential, currentSiteId,
                defaultProject.getId(), workbookName, workbookFile, chunkedPublish);

        // Creates a non Active Directory group named "TableauExample"
        GroupType group = s_restApiUtils.invokeCreateGroup(credential, currentSiteId, "TableauExample");

        // Sets permission to allow the group to read the new workbook, but not
        // to modify its permissions
        Map<String, String> capabilities = new HashMap<String, String>();
        capabilities.put("Read", "Allow");
        capabilities.put("ChangePermissions", "Deny");

        // Creates the grantee capability element for the group
        GranteeCapabilitiesType groupCapabilities = s_restApiUtils.createGroupGranteeCapability(group, capabilities);

        // Adds the created group to the list of grantees
        List<GranteeCapabilitiesType> granteeCapabilities = new ArrayList<GranteeCapabilitiesType>();
        granteeCapabilities.add(groupCapabilities);

        // Makes the call to add the permissions
        s_restApiUtils.invokeAddPermissionsToWorkbook(credential, currentSiteId, publishedWorkbook.getId(),
                granteeCapabilities);*/

        // Gets the list of workbooks the current user can read
        List<WorkbookType> currentUserWorkbooks = s_restApiUtils.invokeQueryWorkbooks(credential, currentSiteId,
                currentUserId).getWorkbook();

        // Checks whether the workbook published previously is in the list, then
        // checks whether the workbook's owner is the current user
        for (WorkbookType workbook : currentUserWorkbooks) {
            /*if (workbook.getId().equals(publishedWorkbook.getId())) {
                s_logger.debug(String.format("Published workbook found: %s", workbook.getId()));

                if (workbook.getOwner().getId().equals(currentUserId)) {
                    s_logger.debug("Published workbook was published by current user");
                }
            }*/
        	if (workbook.getId().equals("78932e90-c60b-4a6f-8058-7fc19116df0c")){
        		 s_logger.info("Checking workbook Id-"+workbook.getId());
        		/* List<ViewType> currentWorkbookImage = s_restApiUtils.queryWorkbookPreviewImage(credential, currentSiteId,
        				 workbook.getId()).getView();
        		 for (ViewType view : currentWorkbookImage) {
        			 s_logger.info("View name is-"+view.getName()+ " and URL is-"+view.getContentUrl());
        		 }*/
        		 workbookImage = s_restApiUtils.queryWorkbookPreviewImage(credential, currentSiteId,
        				 workbook.getId());
        		 System.out.println("Image string is: "+ workbookImage);
        		 
/*        		 Writer writer;
				try {
					 writer = new BufferedWriter(new OutputStreamWriter(
				              new FileOutputStream("filename.txt"), "utf-8"));

						writer.write(workbookImage);

				} catch (FileNotFoundException e) {			
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}*/
				
        		 image = new Image();
          	     image.setImageName(workbook.getName());
          	     image.setImage(workbookImage);
          	   writeImagetoFile(workbookImage);
        	}
        	
        	s_logger.info("SUCCESS, WORKBOOKID-"+workbook.getName());
        }

        // Signs out of the server. This invalidates the authentication token so
        // that it cannot be used for more requests.
        s_restApiUtils.invokeSignOut(credential);
        
		//return workbookImage;
        return image;
	
    }
    
    public String  getDashboard(){
    	String currentSiteId = null;
    	Image image = null;
    	String workbookImage=null;
    	String url = null;
    	String token = null;
    	//String imageName=null;

        // Sets the username, password, and content URL, which are all required
        // in the payload of a Sign In request
        String username = s_properties.getProperty("user.admin.name");
        String password = s_properties.getProperty("user.admin.password");
        String contentUrl = s_properties.getProperty("site.default.contentUrl");

        // Signs in to server and saves the authentication token, site ID, and current user ID
        TableauCredentialsType credential = s_restApiUtils.invokeSignIn(username, password, contentUrl);
         currentSiteId = credential.getSite().getId();
        String currentUserId = credential.getUser().getId();

        s_logger.info(String.format("Authentication token: %s", credential.getToken()));
        s_logger.info(String.format("Site ID: %s", currentSiteId));

       token = credential.getToken();

        // Gets the list of workbooks the current user can read
        List<WorkbookType> currentUserWorkbooks = s_restApiUtils.invokeQueryWorkbooks(credential, currentSiteId,
                currentUserId).getWorkbook();

        // Checks whether the workbook published previously is in the list, then
        // checks whether the workbook's owner is the current user
        String viewName = null;
        for (WorkbookType workbook : currentUserWorkbooks) {
            /*if (workbook.getId().equals(publishedWorkbook.getId())) {
                s_logger.debug(String.format("Published workbook found: %s", workbook.getId()));

                if (workbook.getOwner().getId().equals(currentUserId)) {
                    s_logger.debug("Published workbook was published by current user");
                }
            }*/
        	if (workbook.getId().equals("78932e90-c60b-4a6f-8058-7fc19116df0c")){
        		 s_logger.info("Checking workbook Id-"+workbook.getId());
        		/* List<ViewType> currentWorkbookImage = s_restApiUtils.queryWorkbookPreviewImage(credential, currentSiteId,
        				 workbook.getId()).getView();
        		 for (ViewType view : currentWorkbookImage) {
        			 s_logger.info("View name is-"+view.getName()+ " and URL is-"+view.getContentUrl());
        		 }*/
        		 /*workbookImage = s_restApiUtils.queryWorkbookPreviewImage(credential, currentSiteId,
        				 workbook.getId());*/
        		 System.out.println("view name is: "+ workbook.getContentUrl());
        		 viewName =  workbook.getContentUrl();
        		 

        	}
        	
        	s_logger.info("SUCCESS, WORKBOOKID-"+workbook.getName());
        }
        
        url = "http://146.148.85.242:8081/"+ 
        		"views/" + viewName + "/SalesNew?:embed=yes";
/*        url = "http://146.148.85.242:8081/trusted/" + token + 
        		"/views/HPESSalesDashboard/SalesNew?:embed=yes";*/
        System.out.println("dashboard url is: "+ url);
        // Signs out of the server. This invalidates the authentication token so
        // that it cannot be used for more requests.
        s_restApiUtils.invokeSignOut(credential);
        
		//return workbookImage;
        return url;
	
    }
    
    public String  getToken(){
    	String currentSiteId = null;
    	Image image = null;
    	String workbookImage=null;
    	String url = null;
    	String token = null;
    	//String imageName=null;

        // Sets the username, password, and content URL, which are all required
        // in the payload of a Sign In request
        String username = s_properties.getProperty("user.admin.name");
        String password = s_properties.getProperty("user.admin.password");
        String contentUrl = s_properties.getProperty("site.default.contentUrl");

        // Signs in to server and saves the authentication token, site ID, and current user ID
        TableauCredentialsType credential = s_restApiUtils.invokeSignIn(username, password, contentUrl);
         currentSiteId = credential.getSite().getId();
        String currentUserId = credential.getUser().getId();

        s_logger.info(String.format("Authentication token: %s", credential.getToken()));
        s_logger.info(String.format("Site ID: %s", currentSiteId));

       token = credential.getToken();
       return token;
    }
    
    public void writeImagetoFile(String content){
    	try {
    		 
			//String content = "This is the content to write into file";
 
			File file = new File("/Users/anand/Documents/RESTfulIMSService/ImageFile.png");
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
 
			System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public static void main(String[] args) {
    	Demo d = new Demo();
    	System.out.println("Site Id is: "+d.getWorkbookImage());
    	/*
    	Demo d = new Demo();
        // Sets the username, password, and content URL, which are all required
        // in the payload of a Sign In request
        String username = s_properties.getProperty("user.admin.name");
        String password = s_properties.getProperty("user.admin.password");
        String contentUrl = s_properties.getProperty("site.default.contentUrl");

        // Signs in to server and saves the authentication token, site ID, and current user ID
        TableauCredentialsType credential = s_restApiUtils.invokeSignIn(username, password, contentUrl);
        String currentSiteId = credential.getSite().getId();
        String currentUserId = credential.getUser().getId();

        s_logger.info(String.format("Authentication token: %s", credential.getToken()));
        s_logger.info(String.format("Site ID: %s", currentSiteId));

        // Queries the projects on the current site and iterates over the list to
        // find the ID of the default project
        ProjectType defaultProject = null;
        ProjectListType projects = s_restApiUtils.invokeQueryProjects(credential, currentSiteId);
        for (ProjectType project : projects.getProject()) {
            if (project.getName().equals("default")) {
                defaultProject = project;

                s_logger.info(String.format("Default project found: %s", defaultProject.getId()));
            }
        }

        // Sets the name to assign to the workbook to be published
        String workbookName = s_properties.getProperty("workbook.sample.name");

        // Gets the workbook file to publish
        String workbookPath = s_properties.getProperty("workbook.sample.path");
        File workbookFile = new File(workbookPath);

        // Gets whether or not to publish the workbook using file uploads
        boolean chunkedPublish = Boolean.valueOf(s_properties.getProperty("workbook.publish.chunked"));

        // Publishes the workbook as a multipart request
        WorkbookType publishedWorkbook = s_restApiUtils.invokePublishWorkbook(credential, currentSiteId,
                defaultProject.getId(), workbookName, workbookFile, chunkedPublish);

        // Creates a non Active Directory group named "TableauExample"
        GroupType group = s_restApiUtils.invokeCreateGroup(credential, currentSiteId, "TableauExample");

        // Sets permission to allow the group to read the new workbook, but not
        // to modify its permissions
        Map<String, String> capabilities = new HashMap<String, String>();
        capabilities.put("Read", "Allow");
        capabilities.put("ChangePermissions", "Deny");

        // Creates the grantee capability element for the group
        GranteeCapabilitiesType groupCapabilities = s_restApiUtils.createGroupGranteeCapability(group, capabilities);

        // Adds the created group to the list of grantees
        List<GranteeCapabilitiesType> granteeCapabilities = new ArrayList<GranteeCapabilitiesType>();
        granteeCapabilities.add(groupCapabilities);

        // Makes the call to add the permissions
        s_restApiUtils.invokeAddPermissionsToWorkbook(credential, currentSiteId, publishedWorkbook.getId(),
                granteeCapabilities);

        // Gets the list of workbooks the current user can read
        List<WorkbookType> currentUserWorkbooks = s_restApiUtils.invokeQueryWorkbooks(credential, currentSiteId,
                currentUserId).getWorkbook();

        // Checks whether the workbook published previously is in the list, then
        // checks whether the workbook's owner is the current user
        for (WorkbookType workbook : currentUserWorkbooks) {
            if (workbook.getId().equals(publishedWorkbook.getId())) {
                s_logger.debug(String.format("Published workbook found: %s", workbook.getId()));

                if (workbook.getOwner().getId().equals(currentUserId)) {
                    s_logger.debug("Published workbook was published by current user");
                }
            }
        }

        // Signs out of the server. This invalidates the authentication token so
        // that it cannot be used for more requests.
        s_restApiUtils.invokeSignOut(credential);
    */}
}
