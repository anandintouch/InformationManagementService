package com.myproject.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.myproject.DBConnection;
import com.myproject.PreviewReport;
import com.myproject.PreviewReport.PreviewReportItem;

public class ReportManager {

	private static PreparedStatement preparedStatement = null;
	private static ResultSet resultSet = null;

	public static PreviewReport getReportPreview(String objectTitleShort) {
		String query = "select objectpreview1,objectdescriptionlong,runs,subscribers,kits,favorites,comment"
				+ " from portaldb01.Object"
				+ " where ObjectTitleShort=?";

		Connection conn = null;
		try {
			System.out.println("SQL Statement:\n\t" + query);

			conn = DBConnection.getConnection();
			preparedStatement = conn.prepareStatement(query);
	
			System.out.println("Prepared Statement before bind variables set:\n\t" + preparedStatement.toString());
			preparedStatement.setString(1,objectTitleShort );
			System.out.println("Prepared Statement after bind variables set:\n\t" + preparedStatement.toString());
			
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			
			PreviewReport workbook = new PreviewReport(resultSet.getString("runs"), resultSet.getString("subscribers"), resultSet.getString("kits"),
					resultSet.getString("favorites"),resultSet.getInt("comment"));
			PreviewReportItem view = new PreviewReport.PreviewReportItem(resultSet.getString("ObjectPreview1"), resultSet.getString("ObjectDescriptionLong"));
			workbook.views.add(view);
			
			return workbook;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return null;
	}
	
	public static int increaseLikes(String objectTitleShort, String likes) {
		String query = "update portaldb01.Object set Likes = ? where ObjectTitleShort = ?";

		Connection conn = null;
		int currentLikes = 0;
		
		try {
			System.out.println("SQL Statement:\n\t" + query);

			conn = DBConnection.getConnection();
			preparedStatement = conn.prepareStatement(query);
	
			System.out.println("Prepared Statement before bind variables set:\n\t" + preparedStatement.toString());
			preparedStatement.setInt(1, Integer.valueOf(likes) + 1);
			preparedStatement.setString(2, objectTitleShort );
			System.out.println("Prepared Statement after bind variables set:\n\t" + preparedStatement.toString());
			
			preparedStatement.executeUpdate();
			//this line must be at the end
			currentLikes = Integer.valueOf(likes) + 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return currentLikes;
		}
	}
}
