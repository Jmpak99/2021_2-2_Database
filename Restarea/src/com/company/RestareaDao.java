package com.company;

import java.sql.*;
import java.util.*;

public class RestareaDao { // data access objectŬ���� ����/����/���ڵ� �������� �پ��� ����� �����ϴ� Ŭ����

	private static Connection con = null;
	private static String url = "jdbc:mysql://localhost:3306/restarea?characterEncoding=utf8&serverTimezone=UTC";
	private static String user = "root";
	private static String passwd = "dBTue03";
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	public RestareaDao() { // ������ db����
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, passwd);

		} catch (Exception e) {
			System.out.println("Connect Failed!");
			e.printStackTrace();
		}
	}

	public void db_close() { // db�� �ݴ� �޼ҵ�
		try {
			if (con != null && !con.isClosed()) {
				con.close();
			}
			if (pstmt != null && !pstmt.isClosed()) {
				pstmt.close();
			}
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> selectName(String a) { // �ްԼ��������� ���̺��� �ްԼҸ� �Ӽ� ���ڵ带 �������� �޼ҵ�
		// ���ڵ带 �����ϴ� ArrayList�迭
		ArrayList<String> ra_arr = new ArrayList<>();

		String[][] total = new String[199][1]; //�������� ��ȯ�� 2���� �迭
		String sql;
		
		if(a==null) { //���� ���̸� ��ü �� �������
		sql = "select * from �ްԼ���������";
		}
		else { //String ���� ������ �ްԼҸ� 'a'�� �ִ� ���ڵ常 ��������
			sql="select * from �ްԼ��������� where �ްԼҸ� like '%"+a+"%'";
		}
		
		pstmt = null;
		rs = null;

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				ra_arr.add(rs.getString("�ްԼҸ�")); // �迭�� ������ ���ڵ� �߰�
			}
			for (int i = 0; i < ra_arr.size(); i++) {
				total[i][0] = ra_arr.get(i);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return ra_arr;
	}

}
