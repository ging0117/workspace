package com.calypso.training.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.ResultSetMetaData;

public class JdbcManager {

	// need to choose sql
	private static Connection conn;
	private static Connection conn1;

	public static void createConnection() throws ClassNotFoundException, SQLException {
		// Driver is class LOAD the driver class
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("Driver loaded");

		// Create Connection
		// username is root and password is blank or your password
		conn = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");

		conn.setAutoCommit(false);

		System.out.println("Connection has created");
		conn1 = DriverManager.getConnection("jdbc:mysql://localhost/user1", "root", "");
		System.out.println("Connection2 has created");

	}

	// CollableStatement only involve stored procedure
	// CRUD EXECUTED UPDATE
	public static void insertUser(String UserName, String Password) throws SQLException {
		try {
			String insertQuery = "Insert into User(Username, Password) values (?,?)";
			PreparedStatement ps = conn.prepareStatement(insertQuery);
			ps.setString(1, UserName);
			ps.setString(2, Password);
			int rowsIntserted = ps.executeUpdate();
			System.out.println("Rows has inserted::" + rowsIntserted);
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
		}

	}

	public static void insertUserUpdateBatch(String UserName, String Password) throws SQLException {
		try {
			String insertQuery = "Insert into User(Username, Password) values (?,?)";
			PreparedStatement ps = conn.prepareStatement(insertQuery);
			ps.setString(1, UserName);
			ps.setString(2, Password);
			ps.addBatch();
			ps.setString(1, "aaaaa");
			ps.setString(2, "ddddd");
			ps.addBatch();
			ps.setString(1, "eeeeee");
			ps.setString(2, "ffffff");
			ps.addBatch();
			int[] data = ps.executeBatch();
			// int rowsIntserted = ps.executeUpdate();
			System.out.println("Rows has inserted::" + data[0]);
			System.out.println("Rows has inserted::" + data[1]);
			System.out.println("Rows has inserted::" + data[2]);
			// conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}

	}

	public static void UpdateUser(int UserID, String UserName, String Password) throws SQLException {
		String updateQuery = "update user set username=?,password=?where userid=?";
		PreparedStatement ps = conn.prepareStatement(updateQuery);
		ps.setString(1, UserName);
		ps.setString(2, Password);
		ps.setInt(3, UserID);
		int rowsInserted = ps.executeUpdate();
		System.out.println("Rows has update::" + rowsInserted);

	}

	public static void addweight(int userid, int age) throws SQLException {
		String addQuery = "Insert into usertable(userid,age) values(?,?)";
		PreparedStatement ps = conn1.prepareStatement(addQuery);
		ps.setInt(1, userid);
		ps.setInt(2, age);
		int rowsInserted = ps.executeUpdate();
		System.out.println("Rows has added from table usertable" + rowsInserted);
	}

	public static void under20groupint() throws SQLException {
		String underQuery = "select userid,age from user1.usertable where age<20";
		Statement ps = conn1.createStatement();
		ResultSet select = ps.executeQuery(underQuery);
		while (select.next()) {
			int userid = select.getInt("userid");
			int age = select.getInt("age");

			System.out.println(
					"Select the data under 20 years" + "User id is ::" + userid + " Under 20 ages is ::" + age);
		}
	}

	public static void selectUser(int userId) throws SQLException {
		ResultSet rs1 = null;
		try {
			// Sorting with database getdata
			DatabaseMetaData dmr = conn.getMetaData();
			String selectQuery = "Select UserName, PassWord from User where UserID=?";
			PreparedStatement ps = conn.prepareStatement(selectQuery);
			ps.setInt(1, userId);
			rs1 = ps.executeQuery();
			// rorking with resultset
			java.sql.ResultSetMetaData rmd = rs1.getMetaData();
			System.out.println("Colum1 count :: Username:" + rmd.getColumnCount());
			System.out.println("colum2 Password:" + rmd.getColumnLabel(1));
			while (rs1.next()) {
				String userName = rs1.getString(1);
				String password = rs1.getString(2);

				System.out.println("Colum count :: Username:" + userName);
				System.out.println("Password:" + password);

			}
		} finally {
			if (rs1 != null) {
				rs1.close();
			}
		}

	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		createConnection();
		// insertUser("Ching Ting","Sun");
		insertUserUpdateBatch("Ching", "Ting");
		// UpdateUser(1, "Ching", "sun");
		// TODO Auto-generated method stub
		// addweight(1,20);
		// addweight(2,29);
		// addweight(3,18);
		// under20groupint();
		// selectUser(1);

	}

}
