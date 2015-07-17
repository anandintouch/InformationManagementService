package com.myproject.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.myproject.DBConnection;
import com.myproject.PreviewReport;

public class ReportManager {

	private static PreparedStatement preparedStatement = null;
	private static ResultSet resultSet = null;

	public static PreviewReport getReportPreview(String objectTitleShort) {
		String query = "select ObjectPreview1, ObjectDescriptionLong, 10 as AuthorCredit, 20 as BuyerCredit, 30 as CreditsToRun from portaldb01.Object"
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

			return new PreviewReport(resultSet.getString("ObjectPreview1"), resultSet.getString("ObjectDescriptionLong"), resultSet.getInt("AuthorCredit"),
						resultSet.getInt("BuyerCredit"), resultSet.getInt("CreditsToRun"));
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
}
