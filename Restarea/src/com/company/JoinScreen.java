package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class JoinScreen extends JFrame {
	private JFrame frame;
	private Connection con = null;
	private static PreparedStatement pstmt = null;
	private static String url = "jdbc:mysql://localhost:3306/restarea";
	private static String user = "root";
	private static String pw = "dBTue03";
	String sql;

	public JoinScreen() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, pw);
			System.out.println("Connect Successful");
			
		} catch(Exception e1) {
			System.out.println("Connect Failed");
			e1.printStackTrace();
			
		}
		
		
		setTitle("회원가입");
		
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 550);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JLabel title = new JLabel("회원가입");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 50));
		title.setBounds(0, 10, 584, 114);
		frame.getContentPane().add(title);
		
		JLabel idPanel = new JLabel("      아이디 : ");
		idPanel.setFont(new Font("굴림", Font.PLAIN, 20));
		idPanel.setBounds(10, 120, 130, 40);
		frame.getContentPane().add(idPanel);
		
		JTextField IdText = new JTextField();
		IdText.setBounds(150, 120, 350, 40);
		frame.getContentPane().add(IdText);
		
		JLabel PwPanel = new JLabel("   비밀번호 : ");
		PwPanel.setFont(new Font("굴림", Font.PLAIN, 20));
		PwPanel.setBounds(10, 190, 130, 40);
		frame.getContentPane().add(PwPanel);
		
		JPasswordField PwText = new JPasswordField();
		PwText.setBounds(150, 190, 350, 40);
		frame.getContentPane().add(PwText);
		
		JLabel NamePanel = new JLabel("         이름 : ");
		NamePanel.setFont(new Font("굴림", Font.PLAIN, 20));
		NamePanel.setBounds(10, 260, 130, 40);
		frame.getContentPane().add(NamePanel);
		
		JTextField NameText = new JTextField();
		NameText.setBounds(150, 260, 350, 40);
		frame.getContentPane().add(NameText);
		
		JLabel MailPanel = new JLabel("      이메일 : ");
		MailPanel.setFont(new Font("굴림", Font.PLAIN, 20));
		MailPanel.setBounds(10, 330, 130, 40);
		frame.getContentPane().add(MailPanel);
		
		JTextField MailText = new JTextField();
		MailText.setBounds(150, 330, 350, 40);
		frame.getContentPane().add(MailText);

		
		JButton JoinBtn = new JButton("회원가입");
		JoinBtn.setBounds(60, 405, 200, 50);
		frame.getContentPane().add(JoinBtn);
		
		JButton CancelBtn = new JButton("취소");
		CancelBtn.setBounds(300, 405, 200, 50);
		frame.getContentPane().add(CancelBtn);
		
		
		
		// 회원가입 클릭 시
		JoinBtn.addActionListener(new ActionListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					String sql = "INSERT INTO userlogin VALUES (?,?,?,?)";
					PreparedStatement pstmt = con.prepareStatement(sql);
					pstmt.setString(1, IdText.getText());
					pstmt.setString(2, NameText.getText());
					pstmt.setString(3, PwText.getText());
					pstmt.setString(4, MailText.getText());
					pstmt.executeUpdate();
					JOptionPane.showMessageDialog
					(null, "회원가입이 완료되었습니다");
					frame.dispose();
					pstmt.close();
					con.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog
					(null, "회원가입을 실패하였습니다");
					frame.dispose();
				}
			}
		});
		
		
		
		// cancel 버튼 클릭 시
		CancelBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				
			}
		});
	}
}
