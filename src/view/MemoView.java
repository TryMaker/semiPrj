package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import memo_func.MemoFunc;
import memo_func.MemoGS;

public class MemoView extends Panel implements ActionListener {
	/*
	 * #탭1: 메모장
	 * - 메모 입력(콤보 박스)
	 * 내용(텍스트 아레아)
	 * ㄴ2000바이트
	 * 저장(버튼)
	 * ㄴ자동으로 날짜 저장
	 * - 메모 검색(콤보 박스)
	 * 제목 검색(텍스트 필드)
	 * 메모 목록
	 */
	JComboBox memoinCB; //메모 검색 방법, 검색중에 재선택 안 됨
	JTextArea memoTA; //메모장 입력
	
	JTextField memotitleTF, memosearchTF; //메모 제목 입력, 메모 검색
	
	JLabel showdateLb, showbyteLb; //날짜 보여줌, 용량 보여줌
	
	JButton insB, modB, delB; //왼쪽 버튼
	
	JTable memoTable;
	
	MemoFunc mFunc;
	MemoTableModel mtabmod;
	
	public MemoView() { //생성자
		addLayout(); //화면 설계
		//초기화 작업
		initStyle(); //편집 허가
		eventProc(); //액션리스너
		//db연결
		connectDB();
	}

	public void connectDB() { //DB 연결
		try {
			mFunc=new MemoFunc();
			System.out.println("메모장 연결 성공");
		} catch (Exception e) {
			System.out.println("메모장 연결 실패");
			e.printStackTrace();
		}

	}

	private void addLayout() { //GUI 배치
		//컴포넌트
		//왼쪽
		memotitleTF=new JTextField(15); //메모 제목 입력
		memoTA=new JTextArea(0,0); //메모 입력
		memoTA.setLineWrap(true); //자동 줄바꿈
		
		showdateLb=new JLabel("날짜");
		showbyteLb=new JLabel("용량");
		
		insB=new JButton("입력");
		modB=new JButton("수정");
		delB=new JButton("삭제");
		
		//오른쪽
		String[] memoinStr= {"제목만", "제목+내용"};
		memoinCB=new JComboBox(memoinStr);
		memosearchTF=new JTextField(20);
		
		mtabmod=new MemoTableModel();
		memoTable=new JTable(mtabmod);
		memoTable.setModel(mtabmod);
		memoTable.getColumnModel().getColumn(0).setMaxWidth(1000); //칼럼 길이
		memoTable.getColumnModel().getColumn(1).setMaxWidth(300); 

		
		//화면 구성
		//왼쪽 페널
		JPanel p_west=new JPanel();
		p_west.setLayout(new BorderLayout());
		p_west.setBorder(new TitledBorder("메모 보기"));
		
		
		JPanel p_west_top=new JPanel();
		p_west_top.setLayout(new BorderLayout());
		p_west_top.add(new JLabel("제목"), BorderLayout.WEST);
		p_west_top.add(memotitleTF, BorderLayout.CENTER);
		
		
		JPanel p_west_cen=new JPanel();
		p_west_cen.setLayout(new BorderLayout());
		p_west_cen.add(showdateLb, BorderLayout.NORTH);
		p_west_cen.add(new JLabel("메모"), BorderLayout.WEST);
//		p_west_cen.add(memoTA, BorderLayout.CENTER);
		p_west_cen.add(new JScrollPane(memoTA), BorderLayout.CENTER);
		p_west_cen.add(showbyteLb, BorderLayout.SOUTH);
		
		
		JPanel p_west_bot=new JPanel();
		p_west_bot.setLayout(new GridLayout(1, 3));
		p_west_bot.add(insB);
		p_west_bot.add(modB);
		p_west_bot.add(delB);
		
		
		p_west.add(p_west_top, BorderLayout.NORTH);
		p_west.add(p_west_cen, BorderLayout.CENTER);
		p_west.add(p_west_bot, BorderLayout.SOUTH);
		
		//오른쪽 페널
		JPanel p_east=new JPanel();
		p_east.setLayout(new BorderLayout());
		
		//오른쪽의 위쪽
		JPanel p_east_top=new JPanel();
		p_east_top.setBorder(new TitledBorder("메모 검색"));
		p_east_top.add(memoinCB);
		p_east_top.add(memosearchTF);
		
		
		p_east.add(p_east_top, BorderLayout.NORTH);
		p_east.add(new JScrollPane(memoTable), BorderLayout.CENTER);
		
		
		setLayout(new GridLayout(1, 2));
		add(p_west);
		add(p_east);
	}
	
	class MemoTableModel extends AbstractTableModel { //우측 테이블
		
		ArrayList data=new ArrayList();
		String[] columnNames= {"메모 제목", "메모 날짜"};
		//MCODE,MTITLE,MMEMO,MDATE,MBYTE
		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public Object getValueAt(int row, int col) {
			ArrayList temp=(ArrayList) data.get(row);
			return temp.get(col);
		}
		
		public String getColumnName(int col) {
			return columnNames[col];
		}
		
//		public void resizeColumnWidth(JTable table) {
//		}
	}
	
	
	void initStyle() { //비활성화
	}
	
	public void eventProc() { //액션리스너 연결
		
		memosearchTF.addActionListener(this); //검색
		
		insB.addActionListener(this); //입력 버튼
		modB.addActionListener(this); //수정 버튼
		delB.addActionListener(this); //삭제 버튼
		
		memoTable.addMouseListener(new MouseAdapter() { //칼럼 선택
			@Override
			public void mouseClicked(MouseEvent e) {
				int row=memoTable.getSelectedRow();
				int col=1;
				//클릭 레코드 메모 번호 얻기(날짜로 해보자
				MemoGS gs=new MemoGS();
				gs.setDate((String) memoTable.getValueAt(row, col));
				try {
					gs=mFunc.selectMemo(gs); //gs를 mFunc에 넣고 gs에 대입함.
					selectMemo(gs); //view의 함수로 결과를 실행함.
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		memoTA.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				MemoGS gs=new MemoGS();
				memoTA.setText(memoTA.getText().replaceAll("\r\n", "<br>")); //자동 줄 바꿈
				
				gs.setMemo(memoTA.getText()); //메모 저장
				gs.setMemory(gs.getMemo().getBytes().length); //메모 용량 저장
				showbyteLb.setText(gs.getMemory()+"/2000Byte");
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) { //액션 발동
		Object evt=e.getSource();
		
		if (evt==memosearchTF) { //검색
			searchMemo();
		} else if (evt==insB) { //입력 버튼
			insertMemo();
		} else if (evt==modB) { //수정 버튼
			modifyMemo();
		} else if (evt==delB) { //삭제 버튼
			deleteMemo();
		}
	}

	private void searchMemo() {
		int idx=memoinCB.getSelectedIndex();
		String str=memosearchTF.getText();
		
		try {
			ArrayList data=mFunc.searchMemo(idx, str);
			mtabmod.data=data;
			if (data.isEmpty()) {
				JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.");
			}
			memoTable.setModel(mtabmod);
			mtabmod.fireTableDataChanged();
			showdateLb.setText("날짜");
			showbyteLb.setText("용량");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//어그로 초기화
		memotitleTF.setText(null);
		memoTA.setText(null);
	}
	
	void selectMemo(MemoGS gs) {
		memotitleTF.setText(gs.getTitle());
		memoTA.setText(gs.getMemo());
		showdateLb.setText(gs.getDate()+" 저장");
		
		gs.setMemo(memoTA.getText()); //메모 저장
		gs.setMemory(gs.getMemo().getBytes().length); //메모 용량 저장
		showbyteLb.setText(gs.getMemory()+"/2000Byte");
	}
	
	private void insertMemo() {
		MemoGS gs=new MemoGS();
		gs.setTitle(memotitleTF.getText());
		gs.setMemo(memoTA.getText());
		
		gs.setMemory(gs.getMemo().getBytes().length); //바이트 저장
		
		try {
//			mFunc.insertMemo(gs);
			ArrayList data=mFunc.insertMemo(gs);
			JOptionPane.showMessageDialog(null, "저장 완료");
			//text창 지우기
			memotitleTF.setText(null);
			memoTA.setText(null);
			showdateLb.setText("날짜");
			showbyteLb.setText("용량");
			//테이블 업뎃
			mtabmod.data=data;
			memoTable.setModel(mtabmod);
			mtabmod.fireTableDataChanged();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "저장 실패");
			e.printStackTrace();
		}
	}
	
	private void modifyMemo() {
		MemoGS gs=new MemoGS();
		gs.setTitle(memotitleTF.getText());
		gs.setMemo(memoTA.getText());
		
		gs.setMemory(gs.getMemo().getBytes().length);
		
		int row=memoTable.getSelectedRow(); //이거 때매 연속이 안됨
		//if문을 통해 값이 뭔가 안 맞으면 그제서야 select에서 가져오는걸로?
		if (row==-1) { //선택 안하면 -1
			row=gs.getRow(); //gs에 저장된 값을 가져옴
		}
		int col=1;
		String date=(String) memoTable.getValueAt(row, col);
		gs.setDate(date); //수정 연속해서 할 수 있게
		
		try {
//			mFunc.modifyMemo(gs, data);
			ArrayList data=mFunc.modifyMemo(gs, date);
			JOptionPane.showMessageDialog(null, "수정 완료");
//			memotitleTF.setText(null);
//			memoTA.setText(null);
			//테이블 업뎃
			mtabmod.data=data;
			memoTable.setModel(mtabmod);
			mtabmod.fireTableDataChanged();
			
			gs.setRow(row);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "수정 실패");
			e.printStackTrace();
		}
	}
	
	private void deleteMemo() {
		MemoGS gs=new MemoGS();
		
		int row=memoTable.getSelectedRow();
		int col=1;
		gs.setDate((String) memoTable.getValueAt(row, col));
		
		try {
			ArrayList data=mFunc.deleteMemo(gs);
			JOptionPane.showMessageDialog(null, "삭제 완료");
			memotitleTF.setText(null);
			memoTA.setText(null);
			showdateLb.setText("날짜");
			showbyteLb.setText("용량");
			//테이블 업뎃
			mtabmod.data=data;
			memoTable.setModel(mtabmod);
			mtabmod.fireTableDataChanged();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "삭제 실패");
			e.printStackTrace();
		}
	}
	
}




