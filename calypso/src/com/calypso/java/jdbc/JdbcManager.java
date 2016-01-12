package com.calypso.java.jdbc;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcManager {

	public Connection con;

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		JdbcManager jc = new JdbcManager();
		jc.createConnection();
		jc.insertBatch();
		jc.insertUser("ching", "28374");
		System.out.println("ROWS INSERTED");
		jc.fetchUser();

	}

	public void createConnection() {

		try {

			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
			// con = DriverManager.getConnection("True");
			//con.setAutoCommit(false);

		} catch (SQLException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		} catch (ClassNotFoundException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}

	public void insertUser(String username, String password) {

		PreparedStatement ps = null;

		try {

			ps = con.prepareStatement("Insert into User (username, password) values (?,?)");

			ps.setString(1, username);

			ps.setString(2, password);
			
			int count = ps.executeUpdate();
			
			System.out.println(count);

		} catch (SQLException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	public void updateuser(String name, String newPass) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("Update User set password=? where username=?");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ps.setString(1, newPass);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ps.setString(2, name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void insertBatch() {
		try {
			PreparedStatement ps = con.prepareStatement("Insert into Users(username,password) values(?,?)");

			ps.setString(1, "A");
			ps.setString(2, "B");
			ps.addBatch();
			ps.setString(1, "c");
			ps.setString(2, "D");
			ps.addBatch();
			ps.setString(1, "e");
			ps.setString(2, "f");
			ps.addBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void fetchUser() {

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("select * from user where userid > ? and userid<?");
			ps.setInt(1, 1);
			ps.setInt(2, 5);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				
				System.out.println("id:"+ rs.getInt(1));
				System.out.println("Username:"+rs.getString(2));
				System.out.println("pwd:"+rs.getString(3));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}