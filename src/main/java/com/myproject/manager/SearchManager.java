package com.myproject.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myproject.DBConnection;
import com.myproject.SearchReport;
import com.myproject.SearchResult;
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
		String query = "select ObjectID,ObjectTitleShort,ObjectDescriptionShort,ObjectIcon from portaldb01.Object"
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
				suggests.add(new SearchSuggest(resultSet.getString("ObjectID"),resultSet.getString("ObjectDescriptionShort"),
						resultSet.getString("ObjectTitleShort"), resultSet.getString("ObjectIcon")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IMApiServiceException(
					IMApiServiceExceptionType.INTERNAL_ERROR);
		} finally {
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

	public static SearchResult getSearchResult(String searchString) throws IMApiServiceException {
		String post_match=searchString+"%";
		String pre_match="%"+searchString;
		String full="%"+searchString+"%";
		
		SearchResult result = new SearchResult();
		List<SearchReport> reports = new ArrayList<SearchReport>();
		String query = "select ObjectID,ObjectStatus,ObjectIcon,ObjectPreview1,ObjectURL,ObjectTitle,AuthorName,DateAuthored,"
				+ "Likes,Previews,Comment, Location from portaldb01.Object"
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
				SearchReport item = new SearchReport(resultSet.getString("ObjectID"),resultSet.getString("ObjectURL"), resultSet.getString("ObjectStatus"), 
						resultSet.getString("ObjectIcon"), resultSet.getString("ObjectPreview1"), resultSet.getString("ObjectTitle"),
						resultSet.getString("AuthorName"), resultSet.getString("DateAuthored"),resultSet.getString("Previews"), 
						resultSet.getString("Location"),resultSet.getInt("Comment"), resultSet.getString("Likes"));
			
				reports.add(item);
			}
			
			result.resultItems = reports;
			aggregateByFilters(reports, result);
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IMApiServiceException(IMApiServiceExceptionType.INTERNAL_ERROR);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			close();

			return result;
		}
	}
	
	private static void aggregateByFilters(List<SearchReport> reports, SearchResult result) {
		Map<String, Integer> authors = new HashMap<String, Integer>();
		Map<String, Integer> locations = new HashMap<String, Integer>();
		Map<String, Integer> timeFilter = new HashMap<String, Integer>();

		Calendar currentDate = Calendar.getInstance();
		long yesterday = currentDate.getTimeInMillis() - (24 * 3600 * 1000);
		long lastWeek = currentDate.getTimeInMillis() - (7 * 24 * 3600 * 1000);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String today = formatter.format(currentDate.getTime());
		
		for (SearchReport report : reports) {
			Integer count = authors.get(report.authorName);
			authors.put(report.authorName, count == null ? 1 : ++count);
			
			Integer locCount = locations.get(report.location);
			locations.put(report.location, locCount == null ? 1 : ++locCount);

			if (today.equals(report.dateAuthored)) {
				Integer dateCount = timeFilter.get("TODAY");
				timeFilter.put("TODAY",  dateCount == null ? 1 : ++dateCount);
			}
			
			//for this week
			try {
				Date reportDate = formatter.parse(report.dateAuthored);
				if (reportDate.getTime() >= lastWeek && reportDate.getTime() <= yesterday) {
					Integer dateCount = timeFilter.get("LAST 7 DAYS");
					timeFilter.put("LAST 7 DAYS",  dateCount == null ? 1 : ++dateCount);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		result.authors = authors;
		result.timeFilter = timeFilter;
		result.locations = locations;
	}
	
	
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
