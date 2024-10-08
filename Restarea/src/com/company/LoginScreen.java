package com.company;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginScreen extends JFrame {
	private Connection con = null;
	private static PreparedStatement pstmt = null;
	private ResultSet rs;
	String url = "jdbc:mysql://localhost:3306/restarea";
	String user = "root";
	String pw = "dBTue03";
	private JFrame frame;

	public LoginScreen() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, pw);
			System.out.println("Connect Successful");

		} catch (Exception e1) {
			System.out.println("Connect Failed");
			e1.printStackTrace();

		}

		setTitle("�α��� ȭ��");
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		JLabel login = new JLabel("�ްԼ� ���ո���");
		login.setFont(new Font("�޸յձ�������", Font.PLAIN, 50));
		login.setHorizontalAlignment(SwingConstants.CENTER);
		login.setBounds(0, 52, 584, 114);
		frame.getContentPane().add(login);

		JLabel idPanel = new JLabel("ID");
		idPanel.setFont(new Font("����", Font.PLAIN, 20));
		idPanel.setBounds(56, 223, 32, 40);
		frame.getContentPane().add(idPanel);

		JTextField IdText = new JTextField();
		IdText.setBounds(119, 223, 429, 40);
		frame.getContentPane().add(IdText);

		JLabel PwPanel = new JLabel("PW");
		PwPanel.setFont(new Font("����", Font.PLAIN, 20));
		PwPanel.setBounds(56, 289, 36, 40);
		frame.getContentPane().add(PwPanel);

		JPasswordField PwField = new JPasswordField();
		PwField.setBounds(119, 290, 429, 40);
		frame.getContentPane().add(PwField);

		JButton LoginBtn = new JButton("�α���");
		LoginBtn.setBounds(56, 368, 492, 38);
		frame.getContentPane().add(LoginBtn);

		JButton SignupBtn = new JButton("ȸ������");
		SignupBtn.setBounds(56, 425, 492, 38);
		frame.getContentPane().add(SignupBtn);

		// �α��� ��ư Ŭ�� ��
		LoginBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					String sql = "SELECT user_PASSWORD FROM userlogin WHERE user_ID = ? ";
					PreparedStatement pstmt = con.prepareStatement(sql); // pstmt ������ sql�� �ֱ�
					pstmt.setString(1, IdText.getText()); // sql������ ?�� �ش��ϴ� ���� userID�� �������
					rs = pstmt.executeQuery(); // sql �������� rs�� ����
					if (rs.next()) {
						// ?�� ����ڿ��� ���� ID�� ��й�ȣ�� ��ġ�ϴ� ���
						if (rs.getString(1).contentEquals(PwField.getText())) {
							// �α��� ����
							JOptionPane.showMessageDialog(null, "�α����� �����߽��ϴ�");
							new Layout3(new RestareaDao().selectName(null));
							frame.dispose();
							pstmt.close();
							con.close();
						} else {
							JOptionPane.showMessageDialog(null, "��й�ȣ�� Ʋ���ϴ�");
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "�α����� �����Ͽ����ϴ�");
				}
			}

		});

		// ȸ������ ��ư Ŭ�� ��
		SignupBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				new JoinScreen();

			}
		});

	}

}
