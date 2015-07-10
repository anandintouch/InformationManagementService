package com.myproject.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.myproject.Image;
import com.myproject.SearchResultItem;
import com.myproject.SearchSuggest;

public class SearchManager {
	
	private static Connection conn = null;
	private static Statement statement = null;
	private static PreparedStatement preparedStatement = null;
	private static ResultSet resultSet = null;
	private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static String DB_URL = "jdbc:mysql://173.194.81.134/portaldb01";
	private static String USER = "aprakash";
	private static String PASS = "YpL7Hz5Q";

	static {

		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connection successful !!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<SearchSuggest> getSuggestions(String key) {
		
		List<SearchSuggest> suggests = new ArrayList<SearchSuggest>();
		String query = "select ObjectTitleShort,ObjectDescriptionShort,ObjectIcon from portaldb01.Object";

		try {
			preparedStatement = conn.prepareStatement(query);
			resultSet = preparedStatement.executeQuery(query);
			
			while (resultSet.next()) {
				suggests.add(new SearchSuggest(resultSet.getString("ObjectDescriptionShort"),
						resultSet.getString("ObjectTitleShort"), resultSet.getString("ObjectIcon")));
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			close();
		}

		return suggests;
	}

	public static List<SearchResultItem> getSearchResult(String objectTitleShort) {
		List<SearchResultItem> result = new ArrayList<SearchResultItem>();
		String query = "select ObjectStatus,ObjectIcon,ObjectPreview1,ObjectURL,ObjectTitle,AuthorName,DateAuthored,"
				+ "Likes,Previews from portaldb01.Object"
				+ " where ObjectTitleShort=?";

		try {
			System.out.println("SQL Statement:\n\t" + query);
			preparedStatement = conn.prepareStatement(query);
			
			System.out.println("Prepared Statement before bind variables set:\n\t" + preparedStatement.toString());
			preparedStatement.setString(1,objectTitleShort );
			System.out.println("Prepared Statement after bind variables set:\n\t" + preparedStatement.toString());
			
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {

				SearchResultItem item = new SearchResultItem(resultSet.getString("ObjectURL"), resultSet.getString("ObjectStatus"), 
						resultSet.getString("ObjectIcon"), resultSet.getString("ObjectPreview1"), resultSet.getString("ObjectTitle"),
						resultSet.getString("DateAuthored"),resultSet.getString("Previews"),
						resultSet.getString("Likes"));
				result.add(item);
			}
			
			//statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close();
		}

		return result;
	}
	
	private static void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
		    }
			if (preparedStatement != null) {
				preparedStatement.close();
		    }
			/*if (conn != null) {
		        conn.close();
		    }*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
