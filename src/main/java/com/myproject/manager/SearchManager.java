package com.myproject.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.myproject.DBConnection;
import com.myproject.Image;
import com.myproject.SearchFilters;
import com.myproject.SearchResult;
import com.myproject.SearchResultItem;
import com.myproject.SearchSuggest;
import com.myproject.exception.IMApiServiceException;
import com.myproject.exception.IMApiServiceExceptionType;

public class SearchManager {
	
	private static PreparedStatement preparedStatement = null;
	private static ResultSet resultSet = null;

	public static List<SearchSuggest> getSuggestions(String searchString) throws IMApiServiceException {
		String post_match=searchString+"%";
		String pre_match="%"+searchString;
		String full="%"+searchString+"%";
		
		List<SearchSuggest> suggests = new ArrayList<SearchSuggest>();
		String query = "select ObjectTitleShort,ObjectDescriptionShort,ObjectIcon from portaldb01.Object"
				+ " where upper(ObjectTitle) like upper(?)";
		
		Connection conn = null;
		try {
			conn = DBConnection.getConnection();
			preparedStatement = conn.prepareStatement(query);
			System.out.println("Prepared Statement before bind variables set:\n\t" + preparedStatement.toString());
			if(searchString != null){
				preparedStatement.setString(1,full );
			}
			
			System.out.println("Prepared Statement after bind variables set:\n\t" + preparedStatement.toString());
			
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				suggests.add(new SearchSuggest(resultSet.getString("ObjectDescriptionShort"),
						resultSet.getString("ObjectTitleShort"), resultSet.getString("ObjectIcon")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IMApiServiceException(
					IMApiServiceExceptionType.INTERNAL_ERROR);
		}finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			close();
		}

		return suggests;
	}

	public static List<SearchResultItem> getSearchResult(String searchString) throws IMApiServiceException {
		String post_match=searchString+"%";
		String pre_match="%"+searchString;
		String full="%"+searchString+"%";
		
		//SearchResult result;
		List<SearchResultItem> result = new ArrayList<SearchResultItem>();
		String query = "select ObjectID,ObjectStatus,ObjectIcon,ObjectPreview1,ObjectURL,ObjectTitle,AuthorName,DateAuthored,"
				+ "Likes,Previews from portaldb01.Object"
				+ " where upper(ObjectTitle) like upper(?)";

		Connection conn = null;
		try {
			System.out.println("SQL Statement:\n\t" + query);

			conn = DBConnection.getConnection();
			preparedStatement = conn.prepareStatement(query);
			
			System.out.println("Prepared Statement before bind variables set:\n\t" + preparedStatement.toString());
			if(searchString != null){
				preparedStatement.setString(1,full );
			}
			
			System.out.println("Prepared Statement after bind variables set:\n\t" + preparedStatement.toString());
			
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {

				SearchResultItem item = new SearchResultItem(resultSet.getString("ObjectID"),resultSet.getString("ObjectURL"), resultSet.getString("ObjectStatus"), 
						resultSet.getString("ObjectIcon"), resultSet.getString("ObjectPreview1"), resultSet.getString("ObjectTitle"),
						resultSet.getString("AuthorName"), resultSet.getString("DateAuthored"),resultSet.getString("Previews"),
						resultSet.getString("Likes"));
				result.add(item);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IMApiServiceException(
					IMApiServiceExceptionType.INTERNAL_ERROR);
		}finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			close();
		}

		return result;
	}
	
/*	public static List<SearchResultItem> getSearchResult(String searchString) throws IMApiServiceException {
		String post_match=searchString+"%";
		String pre_match="%"+searchString;
		String full="%"+searchString+"%";
		
		List<SearchResultItem> result = new ArrayList<SearchResultItem>();
		String query = "select ObjectStatus,ObjectIcon,ObjectPreview1,ObjectURL,ObjectTitle,AuthorName,DateAuthored,"
				+ "Likes,Previews from portaldb01.Object"
				+ " where upper(ObjectTitle) like upper(?)";

		Connection conn = null;
		try {
			System.out.println("SQL Statement:\n\t" + query);

			conn = DBConnection.getConnection();
			preparedStatement = conn.prepareStatement(query);
			
			System.out.println("Prepared Statement before bind variables set:\n\t" + preparedStatement.toString());
			if(searchString != null){
				preparedStatement.setString(1,full );
			}
			
			System.out.println("Prepared Statement after bind variables set:\n\t" + preparedStatement.toString());
			
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {

				SearchResultItem item = new SearchResultItem(resultSet.getString("ObjectURL"), resultSet.getString("ObjectStatus"), 
						resultSet.getString("ObjectIcon"), resultSet.getString("ObjectPreview1"), resultSet.getString("ObjectTitle"),
						resultSet.getString("AuthorName"), resultSet.getString("DateAuthored"),resultSet.getString("Previews"),
						resultSet.getString("Likes"));
				result.add(item);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IMApiServiceException(
					IMApiServiceExceptionType.INTERNAL_ERROR);
		}finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			close();
		}

		return result;
	}*/
	
	
	private static void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
		    }
			if (preparedStatement != null) {
				preparedStatement.close();
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
