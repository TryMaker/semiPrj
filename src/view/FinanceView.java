package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import finance_func.FinanceGS;
import finance_func.TransactFunc;
import finance_func.WishFunc;

public class FinanceView extends Panel implements ActionListener {
	
	//**집계란
	//수입 or 지출
	JLabel moneyinLB;
	JComboBox moneyinCB;
	//남아 있는 돈
	JLabel yourmoney, showmoney;
	//일간 지출, 주간 지출 표시
	JLabel daypayLB, daypayamountLB, weekpayLB, weekpayamountLB;
	//주간 지출 최다 키워드
	JLabel weeklykeywordLB, keywordLB;
	//월간 지출, 월간 지출 표시
	JLabel monthpayLB, monthpayamountLB;
	
	//**거래 입력란
	//금액, 설명 입력란
	JTextField moneyamountTF, moneyinfoTF;
	//금액, 설명 표시
	JLabel moneyamountLB, moneyinfoLB;
	//입력, 수정, 삭제 버튼
	JButton transinsB, transmodB, transdelB;
	JLabel moneydateLB;
	
	//**위시리스트 표시란
	JLabel wl1_name, wl1_cost, wl1_describe;
	JLabel wl2_name, wl2_cost, wl2_describe;
	JLabel wl3_name, wl3_cost, wl3_describe;
	
	//**거래 검색란
	JComboBox transearchIoCB, transearchTypeCB;
	JTextField transearchTF;
	
	//**위시리스트 입력
	JComboBox wlinput_rankCB;
	JLabel wlinput_rank, wlinput_name, wlinput_cost, wlinput_describe;
	JTextField wlinput_rankTF, wlinput_nameTF, wlinput_costTF, wlinput_describeTF;
	JButton wishinB;
	
	//**테이블
	JTable transactTable;
	TransactTableModel ttabmod;
	
	//**함수
	WishFunc wFunc;
	TransactFunc tFunc;
	
	public FinanceView() {
		
		//DB 연결
		connectDB();
		
		//GUI 생성
		addLayout();
		
		//소원리스트 초기화 연동
		try {
			initializeWish();
			System.out.println("위시리스트 초기화 성공");
		} catch (Exception e) {
			System.out.println("위시리스트 초기화 실패");
			e.printStackTrace();
		}
		
		//집계란 초기화 연동
		try {
			initializeTotal();
			System.out.println("집계란 초기화 성공");
		} catch (Exception e) {
			System.out.println("집계란 초기화 실패");
			e.printStackTrace();
		}
		
		//리스너 연결
		eventProc();
		
	}
	
	public void connectDB() {
		try {
			wFunc=new WishFunc();
			tFunc=new TransactFunc();
			System.out.println("가계부 연결 성공");
		} catch (Exception e) {
			System.out.println("가계부 연결 실패");
			e.printStackTrace();
		}
		
	}

	private void addLayout() {
		
		//집계란 컴포
		yourmoney=new JLabel("총 보유 금액");
		showmoney=new JLabel("");
		daypayLB=new JLabel("일간 지출액");
		daypayamountLB=new JLabel("");
		weekpayLB=new JLabel("주간 지출액");
		weekpayamountLB=new JLabel("");
		weeklykeywordLB=new JLabel("주간 지출 최다 키워드");
		keywordLB=new JLabel("미구현"); //구현해야함
		monthpayLB=new JLabel("월간 지출액");
		monthpayamountLB=new JLabel("");
		
		//거래입력란 컴포
		moneyinLB=new JLabel("구분");
		String[] moneyintype= {"수입","지출"};
		moneyinCB=new JComboBox(moneyintype); //콤보박스
		moneyamountTF=new JTextField();
		moneyamountLB=new JLabel("금액(숫자)");
		moneyinfoLB=new JLabel("설명");
		moneyinfoTF=new JTextField();
		moneydateLB=new JLabel("거래 날짜");
		
		//거래버튼란 컴포
		transinsB=new JButton("입력");
		transmodB=new JButton("수정");
		transdelB=new JButton("삭제");
		
		//거래검색란 컴포
		String[] strIO= {"전체","수입","지출"};
		transearchIoCB=new JComboBox(strIO);
		String[] strType= {"설명","금액"};
		transearchTypeCB=new JComboBox(strType);
		transearchTF=new JTextField(20);
		
		//위시리스트 표시 컴포 (업데이트 해야함)
		wl1_name=new JLabel("");
		wl1_cost=new JLabel("");
		wl1_describe=new JLabel("");
		wl2_name=new JLabel("");
		wl2_cost=new JLabel("");
		wl2_describe=new JLabel("");
		wl3_name=new JLabel("");
		wl3_cost=new JLabel("");
		wl3_describe=new JLabel("");
		
		//위시리스트 입력 컴포
		wlinput_rank=new JLabel("우선 순위");
		String[] wlrank= {"1", "2", "3"};
		wlinput_rankCB=new JComboBox(wlrank);
		wlinput_name=new JLabel("이름");
		wlinput_nameTF=new JTextField();
		wlinput_cost=new JLabel("금액");
		wlinput_costTF=new JTextField();
		wlinput_describe=new JLabel("설명");
		wlinput_describeTF=new JTextField();
		wishinB=new JButton("입력"); //버튼
		
		//테이블 컴포
		ttabmod=new TransactTableModel();
		transactTable=new JTable(ttabmod);
//		transactTable.setModel(ttabmod);
		transactTable.getColumnModel().getColumn(0).setMaxWidth(30);
		transactTable.getColumnModel().getColumn(1).setMaxWidth(600);
		transactTable.getColumnModel().getColumn(2).setMaxWidth(250);
		
		//집계란
		JPanel p_stats=new JPanel(); 
		p_stats.setLayout(new GridLayout(0, 2));
		p_stats.setBorder(new TitledBorder("집계란"));
		p_stats.setBackground(Color.white);
		p_stats.add(yourmoney);
		p_stats.add(showmoney); //총 보유 금액
		p_stats.add(daypayLB);
		p_stats.add(daypayamountLB); //일간 지출액
		p_stats.add(weekpayLB);
		p_stats.add(weekpayamountLB); //주간 지출액
		p_stats.add(weeklykeywordLB);
		p_stats.add(keywordLB); //주간 지출 최다 키워드
		p_stats.add(monthpayLB);
		p_stats.add(monthpayamountLB); //월간 지출액
		
		//위시리스트 표시
		JPanel p_wishlist_show=new JPanel();
		p_wishlist_show.setLayout(new GridLayout(0, 1));
//		p_wishlist_show.setBorder(new TitledBorder("위시리스트"));
		p_wishlist_show.setBackground(Color.white);
		p_wishlist_show.add(wl1_name);
		p_wishlist_show.add(wl1_cost);
		p_wishlist_show.add(wl1_describe);
		p_wishlist_show.add(wl2_name);
		p_wishlist_show.add(wl2_cost);
		p_wishlist_show.add(wl2_describe);
		p_wishlist_show.add(wl3_name);
		p_wishlist_show.add(wl3_cost);
		p_wishlist_show.add(wl3_describe);
		
		//위시리스트 입력
		JPanel p_wishlist_input=new JPanel();
		p_wishlist_input.setLayout(new GridLayout(0, 1));
		p_wishlist_input.setBackground(Color.white);
		p_wishlist_input.add(wlinput_rank);
		p_wishlist_input.add(wlinput_rankCB);
		p_wishlist_input.add(wlinput_name);
		p_wishlist_input.add(wlinput_nameTF);
		p_wishlist_input.add(wlinput_cost);
		p_wishlist_input.add(wlinput_costTF);
		p_wishlist_input.add(wlinput_describe);
		p_wishlist_input.add(wlinput_describeTF);
		p_wishlist_input.add(wishinB);
		
		//위시리스트
		JPanel p_wishlist=new JPanel();
		p_wishlist.setLayout(new GridLayout(1, 2));
		p_wishlist.setBorder(new TitledBorder("위시리스트"));
		p_wishlist.setBackground(Color.white);
		p_wishlist.add(p_wishlist_show);
		p_wishlist.add(p_wishlist_input);
		
		//거래검색란
		JPanel p_transearch=new JPanel();
		p_transearch.add(transearchIoCB);
		p_transearch.add(transearchTypeCB);
		p_transearch.add(transearchTF);
		
		//거래-입력란
		JPanel p_transacts_input=new JPanel();
		p_transacts_input.setLayout(new GridLayout(0, 2));
		p_transacts_input.setBackground(Color.white);
		p_transacts_input.add(moneyinLB);
		p_transacts_input.add(moneyinCB); //io구분
		p_transacts_input.add(moneyamountLB);
		p_transacts_input.add(moneyamountTF); //금액
		p_transacts_input.add(moneyinfoLB);
		p_transacts_input.add(moneyinfoTF); //설명
		p_transacts_input.add(moneydateLB);
		
		//거래-버튼란
		JPanel p_transacts_btns=new JPanel();
		p_transacts_btns.setLayout(new GridLayout(0, 3));
		p_transacts_btns.add(transinsB);
		p_transacts_btns.add(transmodB);
		p_transacts_btns.add(transdelB);
		
		//거래란
		JPanel p_transacts=new JPanel();
		p_transacts.setLayout(new BorderLayout());
		p_transacts.setBorder(new TitledBorder("거래란"));
		p_transacts.setBackground(Color.white);
		p_transacts.add(p_transacts_input, BorderLayout.NORTH);
		p_transacts.add(p_transacts_btns, BorderLayout.SOUTH);
		
		//서쪽
		JPanel p_west=new JPanel();
		p_west.setLayout(new GridLayout(0, 1));
		p_west.add(p_stats);
		p_west.add(p_wishlist);
		
		//동쪽
		JPanel p_east=new JPanel();
//		p_east.setBorder(new TitledBorder("보기"));
//		p_east.setLayout(new GridLayout(0, 1));
		p_east.setLayout(new BorderLayout());
		p_east.add(p_transearch, BorderLayout.NORTH);
		p_east.add(new JScrollPane(transactTable), BorderLayout.CENTER);
		p_east.add(p_transacts, BorderLayout.SOUTH);
		
		setLayout(new GridLayout(1, 2));
		add(p_west);
		add(p_east);
	}

	class TransactTableModel extends AbstractTableModel {
		
		ArrayList data=new ArrayList();
		String[] columnNames= {"구분", "금액", "날짜"};
		
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
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object evt=e.getSource();
		if (evt==wishinB) { //위시리스트 입력(업데이트)
			try { //나중에 정리하자
				updateWish();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (evt==transearchTF) { //거래 검색
			searchTransact();
		} else if (evt==transinsB) { //거래 입력
			insertTransact();
			//집계란 업데이트
			try {
				initializeTotal();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "집계란 업데이트 실패");
				e1.printStackTrace();
			}
		} else if (evt==transmodB) { //거래 수정
			modifyTransacat();
			//집계란 업데이트
			try {
				initializeTotal();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "집계란 업데이트 실패");
				e1.printStackTrace();
			}
		} else if (evt==transdelB) { //거래 삭제
			deleteTransact();
			//집계란 업데이트
			try {
				initializeTotal();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "집계란 업데이트 실패");
				e1.printStackTrace();
			}
		}
	}
	
	private void eventProc() { //리스너 연결
		
		//액션리스너 추가
		//거래 검색
		transearchTF.addActionListener(this);
		//거래 입력, 수정, 삭제
		transinsB.addActionListener(this);
		transmodB.addActionListener(this);
		transdelB.addActionListener(this);
		//소원 입력(업데이트)
		wishinB.addActionListener(this);
		
		//테이블 선택 기능
		transactTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row=transactTable.getSelectedRow();
				int col=2;
				//날짜로 가져오기
				FinanceGS gs=new FinanceGS();
				gs.setTdate((String) transactTable.getValueAt(row, col));
				
				try {
					gs=tFunc.selectTransact(gs); //gs를 tFunc에 넣고 gs에 대입함.
					selectTransact(gs); //view의 함수로 결과를 실행함.
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
	}

	private void initializeTotal() throws Exception {
		
		FinanceGS gs=new FinanceGS();
		
		//총 보유 금액
		gs=tFunc.initTotal(gs);
		showmoney.setText(gs.getTotalmoney()+"원");
		
		//일간 지출액
		gs=tFunc.initDayPay(gs);
		daypayamountLB.setText(gs.getDailypay()+"원");
		
		//주간 지출액
		gs=tFunc.initWeekPay(gs);
		weekpayamountLB.setText(gs.getWeeklypay()+"원");
		
		//주간 지출 최다 키워드
		
		//월간 지출액
		gs=tFunc.initMonthPay(gs);
		monthpayamountLB.setText(gs.getMonthlypay()+"원");
		
	}

	private void initializeWish() throws Exception {
//		wFunc=new WishFunc();
		FinanceGS gs=new FinanceGS();
		
		//위시 초기화
		for (int i = 1; i <= 3; i++) {
			gs.setWishRank(i);
			if (gs.getWishRank()==1) { //위시1 초기화
				gs=wFunc.initializeWish(gs);
				wl1_name.setText("1. "+gs.getWishName());
				wl1_cost.setText(gs.getWishCost()+"원");
				wl1_describe.setText(gs.getWishDescribe());
			} else if (gs.getWishRank()==2) { //위시2 초기화
				gs=wFunc.initializeWish(gs);
				wl2_name.setText("2. "+gs.getWishName());
				wl2_cost.setText(gs.getWishCost()+"원");
				wl2_describe.setText(gs.getWishDescribe());
			} else if (gs.getWishRank()==3) { //위시3 초기화
				gs=wFunc.initializeWish(gs);
				wl3_name.setText("3. "+gs.getWishName());
				wl3_cost.setText(gs.getWishCost()+"원");
				wl3_describe.setText(gs.getWishDescribe());
			}
		}
	}
	
	private void updateWish() throws Exception {
		wFunc=new WishFunc();
		FinanceGS gs=new FinanceGS();
		//입력
		String temprank=((String)wlinput_rankCB.getSelectedItem());
		gs.setWishRank(Integer.parseInt(temprank));
		gs.setWishName(wlinput_nameTF.getText());
		gs.setWishCost(wlinput_costTF.getText());
		gs.setWishDescribe(wlinput_describeTF.getText());
		
		//연결
		try {
			gs=wFunc.updateWish(gs);
			JOptionPane.showMessageDialog(null, "위시 업데이트 완료");
			int rank=gs.getWishRank();
			if (rank==1) {
				wl1_name.setText("1. "+gs.getWishName());
				wl1_cost.setText(gs.getWishCost()+"원");
				wl1_describe.setText(gs.getWishDescribe());
				nullWish();
			} else if (rank==2) {
				wl2_name.setText("2. "+gs.getWishName());
				wl2_cost.setText(gs.getWishCost()+"원");
				wl2_describe.setText(gs.getWishDescribe());
				nullWish();
			} else if (rank==3) {
				wl3_name.setText("3. "+gs.getWishName());
				wl3_cost.setText(gs.getWishCost()+"원");
				wl3_describe.setText(gs.getWishDescribe());
				nullWish();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "위시 업데이트 실패");
			e.printStackTrace();
		}
	}
	
	private void nullWish() {
		wlinput_nameTF.setText(null);
		wlinput_costTF.setText(null);
		wlinput_describeTF.setText(null);
	}

	private void searchTransact() {
		int idx1=transearchIoCB.getSelectedIndex();
		int idx2=transearchTypeCB.getSelectedIndex();
		String str=transearchTF.getText();
		
		try {
			ArrayList data=tFunc.searchTransact(idx1, idx2, str);
			ttabmod.data=data;
			transactTable.setModel(ttabmod);
			ttabmod.fireTableDataChanged();
			//검색 결과 없을 때
			if (data.isEmpty()) {
				JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.");
			}
			nullTransact();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	void selectTransact(FinanceGS gs) { //작업중
		if (gs.getTio().equals("수입")) {	
			moneyinCB.setSelectedIndex(0);
		} else if (gs.getTio().equals("지출")) {	
			moneyinCB.setSelectedIndex(1);
		}
		moneyamountTF.setText(""+gs.getTamount());
		moneyinfoTF.setText(gs.getTdescribe());
		moneydateLB.setText(gs.getTdate());
	}
	
	private void insertTransact() { //좀있다 아래로 내려
		FinanceGS gs=new FinanceGS();
		//transearchTF
		
		//입력값들 가져와서 gs에 집어넣어야함
		gs.setTio((String)moneyinCB.getSelectedItem());
		gs.setTamount(Integer.parseInt(moneyamountTF.getText()));
		gs.setTdescribe(moneyinfoTF.getText());
		
		try {
			ArrayList data=tFunc.insertTransact(gs);
			JOptionPane.showMessageDialog(null, "저장 완료");
			//text창 지우기
			nullTransact();
			//테이블 업뎃
			ttabmod.data=data;
			transactTable.setModel(ttabmod);
			ttabmod.fireTableDataChanged();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "저장 실패");
			e.printStackTrace();
		}
	}

	private void modifyTransacat() {
		FinanceGS gs=new FinanceGS();
		
		gs.setTio((String)moneyinCB.getSelectedItem());
		gs.setTamount(Integer.parseInt(moneyamountTF.getText()));
		gs.setTdescribe(moneyinfoTF.getText());
		
		int row=transactTable.getSelectedRow();
		if (row==-1) {
			row=gs.getRow();
		}
		int col=2;
		String date=(String)transactTable.getValueAt(row, col);
		gs.setTdate(date);
		try {
			ArrayList data=tFunc.modifyTransact(gs, date);
			JOptionPane.showMessageDialog(null, "수정 완료");
			//테이블 업뎃
			ttabmod.data=data;
			transactTable.setModel(ttabmod);
			ttabmod.fireTableDataChanged();
			
			gs.setRow(row);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "수정 실패");
			e.printStackTrace();
		}
		
	}

	private void deleteTransact() {
		FinanceGS gs=new FinanceGS();
		
		int row=transactTable.getSelectedRow();
		int col=2;
//		gs.setTdate(""+transactTable.getValueAt(row, col)); //이것도 잘 됨
		gs.setTdate((String) transactTable.getValueAt(row, col));
		
		try {
			ArrayList data=tFunc.deleteTransact(gs);
			JOptionPane.showMessageDialog(null, "삭제 완료");
			nullTransact();
			//테이블
			ttabmod.data=data;
			transactTable.setModel(ttabmod);
			ttabmod.fireTableDataChanged();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "삭제 실패");
			e.printStackTrace();
		}
	}
	
	private void nullTransact() {
		moneyamountTF.setText(null);
		moneyinfoTF.setText(null);
		moneydateLB.setText("거래 날짜");
	}
	
}
