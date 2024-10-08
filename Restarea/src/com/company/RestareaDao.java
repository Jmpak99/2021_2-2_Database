package com.company;

import java.sql.*;
import java.util.*;

public class RestareaDao { // data access object클래스 연결/해제/레코드 가져오기 다양한 기능을 수행하는 클래스

	private static Connection con = null;
	private static String url = "jdbc:mysql://localhost:3306/restarea?characterEncoding=utf8&serverTimezone=UTC";
	private static String user = "root";
	private static String passwd = "dBTue03";
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	public RestareaDao() { // 생성자 db연결
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, passwd);

		} catch (Exception e) {
			System.out.println("Connect Failed!");
			e.printStackTrace();
		}
	}

	public void db_close() { // db를 닫는 메소드
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

	public ArrayList<String> selectName(String a) { // 휴게소종합정보 테이블에서 휴게소명 속성 레코드를 가져오는 메소드
		// 레코드를 저장하는 ArrayList배열
		ArrayList<String> ra_arr = new ArrayList<>();

		String[][] total = new String[199][1]; //마지막에 반환할 2차원 배열
		String sql;
		
		if(a==null) { //값이 널이면 전체 다 갖고오기
		sql = "select * from 휴게소종합정보";
		}
		else { //String 값이 있으면 휴게소명에 'a'가 있는 레코드만 가져오기
			sql="select * from 휴게소종합정보 where 휴게소명 like '%"+a+"%'";
		}
		
		pstmt = null;
		rs = null;

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				ra_arr.add(rs.getString("휴게소명")); // 배열에 가져온 레코드 추가
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
