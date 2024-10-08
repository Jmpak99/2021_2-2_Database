package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Layout3 extends JFrame implements MouseListener {
	JTable table;
	String[][] total; // 휴게소명을 저장하는 테이블 RestareaDao의 selectName메소드로 생성
	JTextField text1; // 검색할 단어 입력창
	JPanel SearchPanel, Searchdown; // 검색 패널과 테이블 패널
	DefaultTableModel model; // 테이블 조작하기 위한 모델
	String[] colnames = { "휴게소 목록" }; // 테이블 속성
	

	public Layout3(ArrayList<String> sa) {
		super("휴게소 종합");

		total = new String[sa.size()][1];
		
		for (int i = 0; i < sa.size(); i++) {
			total[i][0] = sa.get(i);
		}

		setLocation(250, 120);
		setLayout(new BorderLayout());
		text1 = new JTextField(30);
		JButton btn = new JButton("검색");
		btn.addActionListener(new MyActionListener()); // 버튼 리스너 (검색 실행)

		// 상단 검색창
		JLabel label1 = new JLabel("휴게소명");
		setLayout(new FlowLayout());
		SearchPanel = new JPanel();
		Searchdown = new JPanel();
		SearchPanel.add(label1);
		SearchPanel.add(text1);
		SearchPanel.add(btn);
		add(SearchPanel, new BorderLayout().NORTH);

		// 테이블 설정
		setTable();

		Searchdown.add(new JScrollPane(table), BorderLayout.CENTER);
		add(Searchdown, BorderLayout.CENTER);

		table.setFillsViewportHeight(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		setSize(600, 550);
	}

	private void setTable() {// 테이블 설정
		// TODO Auto-generated method stub
		model = new DefaultTableModel(total, colnames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;// 모든 셀 수정 불가능
			}
		};

		table = new JTable(model);
		// 셀이 한개씩 선택
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(this);// 마우스리스너 추가

	}

	public void mouseClicked(MouseEvent me) { // 마우스 클릭 리스너
		int row = table.getSelectedRow();// 선택된 테이블 열 저장한 변수
		new DetailFlow((String) table.getValueAt(row, 0)); // 상세정보 레이아웃을 여는 코드
		
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	private class MyActionListener implements ActionListener { // 버튼 클릭 리스너
		@Override
		public void actionPerformed(ActionEvent e) {

			ArrayList<String> al = new ArrayList<>(); // 중간에 검색결과를 저장하는 배열
			String[][] sb; // model에 넣기 위해 위 배열을 2차원배열로 변환
			
			
			if (e.getActionCommand().equals("검색")) { // 버튼 텍스트가 검색이면 실행
				
				String word = text1.getText();
				
				if (!word.equals("")) { //텍스트필드에 값이 있으면 검색 실행/값이 없으면 원래 테이블로 복귀
					model.setNumRows(0); //테이블 초기화
					al = new RestareaDao().selectName(text1.getText()); //db연결해서 sql문 실행 
					sb = new String[al.size()][1];
					
					for (int i = 0; i < al.size(); i++) {
						sb[i][0] = al.get(i);
					}
					for (int i = 0; i < sb.length; i++) {
						model.addRow(sb[i]);
					}
				} else {
					model.setNumRows(0);
					for (int i = 0; i < total.length; i++) {
						model.addRow(total[i]);
					}
				}
			}

		}
	}
}
