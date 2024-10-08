package com.company;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

public class DetailFlow extends JFrame {

	private JFrame frame;
	private String restAreaDataHost = "jdbc:mysql://localhost:3306/restarea";
	private String restAreaPreferenceHost = "jdbc:mysql://localhost:3306/restarea";
	private Connection restAreaDataConnection, restAreaPreferenceConnection;
	private String username ="root";
	private String pw = "dBTue03";
	
	private String restAreaName;
	
	private JButton UserLikeBtn, UserDislikeBtn;
	private JLabel RPfood, Highway, TruckStop, RestCallNum, UserLike, UserDislike;
	private JLabel RestAreaName, RPfoodName, HighwayName,TruckStopName, CallNum, LikeCount, DislikeCount;

	public DetailFlow(String restAreaName) {
		this.restAreaName = restAreaName;
		initializeForm();
		ResultSet restAreaData = loadRestAreaData();
		ResultSet restAreaPreferenceData = loadRestAreaPreference();
		displayData(restAreaData, restAreaPreferenceData);
		
	}
	
	private void initializeForm() {
		frame = new JFrame();
		frame.setBounds(800, 100, 600, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("상세정보");
		frame.setVisible(true);
		UserLikeBtn = new JButton("좋아요");
		UserLikeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int currentLikeCount = loadLikeCount();
				int updatedLikeCount = currentLikeCount + 1;
				
				boolean isSuccess = updateLikeCount(updatedLikeCount);
				if(isSuccess) {
					LikeCount.setText(String.valueOf(updatedLikeCount));
				} else {
					LikeCount.setText(String.valueOf(currentLikeCount));
				}
			}
		});
		
		UserLikeBtn.setBounds(14, 514, 105, 27);
		frame.getContentPane().add(UserLikeBtn);
		
		UserDislikeBtn = new JButton("싫어요");
		UserDislikeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int currentDislikeCount = loadDislikeCount();
				int updatedDislikeCount = currentDislikeCount + 1;
				
				boolean isSuccess = updateDislikeCount(updatedDislikeCount);
				if(isSuccess) {
					DislikeCount.setText(String.valueOf(updatedDislikeCount));
				} else {
					DislikeCount.setText(String.valueOf(currentDislikeCount));
				}
			}
		});
		
		UserDislikeBtn.setBounds(133, 514, 105, 27);
		frame.getContentPane().add(UserDislikeBtn);
		
		RPfood = DefaultComponentFactory.getInstance().createLabel("대표 음식 : ");
		RPfood.setBounds(14, 79, 105, 18);
		frame.getContentPane().add(RPfood);
		
		Highway = DefaultComponentFactory.getInstance().createLabel("고속도로 : ");
		Highway.setBounds(14, 158, 105, 18);
		frame.getContentPane().add(Highway);
		
		TruckStop = DefaultComponentFactory.getInstance().createLabel("화물 휴게소 : ");
		TruckStop.setBounds(14, 234, 105, 18);
		frame.getContentPane().add(TruckStop);
		
		RestCallNum = DefaultComponentFactory.getInstance().createLabel("전화번호 : ");
		RestCallNum.setBounds(14, 314, 105, 18);
		frame.getContentPane().add(RestCallNum);
		
		UserLike = DefaultComponentFactory.getInstance().createLabel("좋아요 : ");
		UserLike.setBounds(14, 399, 57, 18);
		frame.getContentPane().add(UserLike);
		
		UserDislike = DefaultComponentFactory.getInstance().createLabel("싫어요 : ");
		UserDislike.setBounds(151, 399, 57, 18);
		frame.getContentPane().add(UserDislike);
		
		RestAreaName = DefaultComponentFactory.getInstance().createLabel("");
		RestAreaName.setBounds(207, 12, 177, 27);
		frame.getContentPane().add(RestAreaName);
	
		RPfoodName = DefaultComponentFactory.getInstance().createLabel("");
		RPfoodName.setBounds(131, 79, 211, 18);
		frame.getContentPane().add(RPfoodName);
		
		HighwayName = DefaultComponentFactory.getInstance().createLabel("");
		HighwayName.setBounds(131, 158, 211, 18);
		frame.getContentPane().add(HighwayName);
		
		TruckStopName = DefaultComponentFactory.getInstance().createLabel("");
		TruckStopName.setBounds(131, 234, 211, 18);
		frame.getContentPane().add(TruckStopName);
		
		CallNum = DefaultComponentFactory.getInstance().createLabel("");
		CallNum.setBounds(133, 314, 209, 18);
		frame.getContentPane().add(CallNum);
		
		LikeCount = DefaultComponentFactory.getInstance().createLabel("");
		LikeCount.setBounds(71, 399, 57, 18);
		frame.getContentPane().add(LikeCount);
		
		DislikeCount = DefaultComponentFactory.getInstance().createLabel("");
		DislikeCount.setBounds(207, 399, 57, 18);
		frame.getContentPane().add(DislikeCount);
	}
	
	private ResultSet loadRestAreaData() {
		try {
			restAreaDataConnection = DriverManager.getConnection(restAreaDataHost, username, pw);
			Statement stmt = restAreaDataConnection.createStatement();
			String query = "SELECT * FROM 휴게소종합정보 WHERE 휴게소명='"+restAreaName+"'";
			ResultSet result = stmt.executeQuery(query);
			
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private ResultSet loadRestAreaPreference() {
		try {
			restAreaPreferenceConnection = DriverManager.getConnection(restAreaPreferenceHost, username, pw);
			Statement stmt = restAreaPreferenceConnection.createStatement();
			String query = "SELECT * FROM 휴게소만족도점수 WHERE 휴게소명='"+restAreaName+"'";
			ResultSet result = stmt.executeQuery(query);
			
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void displayData(ResultSet restAreaData, ResultSet restAreaPreferenceData) {
		try {
			while(restAreaData.next()) {
				RestAreaName.setText(restAreaData.getString("휴게소명"));
				RPfoodName.setText(restAreaData.getString("대표음식"));
				HighwayName.setText(restAreaData.getString("고속도로"));
				TruckStopName.setText(restAreaData.getString("화물 휴게소")); 
				CallNum.setText(restAreaData.getString("전화번호"));
			}
			
			while(restAreaPreferenceData.next()) {
				LikeCount.setText(String.valueOf(restAreaPreferenceData.getInt("user_like")));
				DislikeCount.setText(String.valueOf(restAreaPreferenceData.getInt("user_dislike")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private int loadLikeCount() {
		try {
			Statement stmt = restAreaPreferenceConnection.createStatement();
			String query = "SELECT user_like FROM 휴게소만족도점수 WHERE 휴게소명='"+restAreaName+"'";
			ResultSet result = stmt.executeQuery(query);
			
			int count = 0;
			while(result.next()) {
				count = result.getInt("user_like");
			}
			
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	private int loadDislikeCount() {
		try {
			Statement stmt = restAreaPreferenceConnection.createStatement();
			String query = "SELECT user_dislike FROM 휴게소만족도점수 WHERE 휴게소명='"+restAreaName+"'";
			ResultSet result = stmt.executeQuery(query);
			
			int count = 0;
			while(result.next()) {
				count = result.getInt("user_dislike");
			}
			
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	private boolean updateLikeCount(int updateCount) {
		try {
			Statement stmt = restAreaPreferenceConnection.createStatement();
			String query = "UPDATE 휴게소만족도점수 SET user_like ='"+updateCount+"' WHERE 휴게소명='"+restAreaName+"'";
			stmt.executeUpdate(query);
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean updateDislikeCount(int updateCount) {
		try {
			Statement stmt = restAreaPreferenceConnection.createStatement();
			String query = "UPDATE 휴게소만족도점수 SET user_dislike ='"+updateCount+"' WHERE 휴게소명='"+restAreaName+"'";
			stmt.executeUpdate(query);
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
