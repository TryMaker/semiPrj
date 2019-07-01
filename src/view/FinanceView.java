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
	
	//**�����
	//���� or ����
	JLabel moneyinLB;
	JComboBox moneyinCB;
	//���� �ִ� ��
	JLabel yourmoney, showmoney;
	//�ϰ� ����, �ְ� ���� ǥ��
	JLabel daypayLB, daypayamountLB, weekpayLB, weekpayamountLB;
	//�ְ� ���� �ִ� Ű����
	JLabel weeklykeywordLB, keywordLB;
	//���� ����, ���� ���� ǥ��
	JLabel monthpayLB, monthpayamountLB;
	
	//**�ŷ� �Է¶�
	//�ݾ�, ���� �Է¶�
	JTextField moneyamountTF, moneyinfoTF;
	//�ݾ�, ���� ǥ��
	JLabel moneyamountLB, moneyinfoLB;
	//�Է�, ����, ���� ��ư
	JButton transinsB, transmodB, transdelB;
	JLabel moneydateLB;
	
	//**���ø���Ʈ ǥ�ö�
	JLabel wl1_name, wl1_cost, wl1_describe;
	JLabel wl2_name, wl2_cost, wl2_describe;
	JLabel wl3_name, wl3_cost, wl3_describe;
	
	//**�ŷ� �˻���
	JComboBox transearchIoCB, transearchTypeCB;
	JTextField transearchTF;
	
	//**���ø���Ʈ �Է�
	JComboBox wlinput_rankCB;
	JLabel wlinput_rank, wlinput_name, wlinput_cost, wlinput_describe;
	JTextField wlinput_rankTF, wlinput_nameTF, wlinput_costTF, wlinput_describeTF;
	JButton wishinB;
	
	//**���̺�
	JTable transactTable;
	TransactTableModel ttabmod;
	
	//**�Լ�
	WishFunc wFunc;
	TransactFunc tFunc;
	
	public FinanceView() {
		
		//DB ����
		connectDB();
		
		//GUI ����
		addLayout();
		
		//�ҿ�����Ʈ �ʱ�ȭ ����
		try {
			initializeWish();
			System.out.println("���ø���Ʈ �ʱ�ȭ ����");
		} catch (Exception e) {
			System.out.println("���ø���Ʈ �ʱ�ȭ ����");
			e.printStackTrace();
		}
		
		//����� �ʱ�ȭ ����
		try {
			initializeTotal();
			System.out.println("����� �ʱ�ȭ ����");
		} catch (Exception e) {
			System.out.println("����� �ʱ�ȭ ����");
			e.printStackTrace();
		}
		
		//������ ����
		eventProc();
		
	}
	
	public void connectDB() {
		try {
			wFunc=new WishFunc();
			tFunc=new TransactFunc();
			System.out.println("����� ���� ����");
		} catch (Exception e) {
			System.out.println("����� ���� ����");
			e.printStackTrace();
		}
		
	}

	private void addLayout() {
		
		//����� ����
		yourmoney=new JLabel("�� ���� �ݾ�");
		showmoney=new JLabel("");
		daypayLB=new JLabel("�ϰ� �����");
		daypayamountLB=new JLabel("");
		weekpayLB=new JLabel("�ְ� �����");
		weekpayamountLB=new JLabel("");
		weeklykeywordLB=new JLabel("�ְ� ���� �ִ� Ű����");
		keywordLB=new JLabel("�̱���"); //�����ؾ���
		monthpayLB=new JLabel("���� �����");
		monthpayamountLB=new JLabel("");
		
		//�ŷ��Է¶� ����
		moneyinLB=new JLabel("����");
		String[] moneyintype= {"����","����"};
		moneyinCB=new JComboBox(moneyintype); //�޺��ڽ�
		moneyamountTF=new JTextField();
		moneyamountLB=new JLabel("�ݾ�(����)");
		moneyinfoLB=new JLabel("����");
		moneyinfoTF=new JTextField();
		moneydateLB=new JLabel("�ŷ� ��¥");
		
		//�ŷ���ư�� ����
		transinsB=new JButton("�Է�");
		transmodB=new JButton("����");
		transdelB=new JButton("����");
		
		//�ŷ��˻��� ����
		String[] strIO= {"��ü","����","����"};
		transearchIoCB=new JComboBox(strIO);
		String[] strType= {"����","�ݾ�"};
		transearchTypeCB=new JComboBox(strType);
		transearchTF=new JTextField(20);
		
		//���ø���Ʈ ǥ�� ���� (������Ʈ �ؾ���)
		wl1_name=new JLabel("");
		wl1_cost=new JLabel("");
		wl1_describe=new JLabel("");
		wl2_name=new JLabel("");
		wl2_cost=new JLabel("");
		wl2_describe=new JLabel("");
		wl3_name=new JLabel("");
		wl3_cost=new JLabel("");
		wl3_describe=new JLabel("");
		
		//���ø���Ʈ �Է� ����
		wlinput_rank=new JLabel("�켱 ����");
		String[] wlrank= {"1", "2", "3"};
		wlinput_rankCB=new JComboBox(wlrank);
		wlinput_name=new JLabel("�̸�");
		wlinput_nameTF=new JTextField();
		wlinput_cost=new JLabel("�ݾ�");
		wlinput_costTF=new JTextField();
		wlinput_describe=new JLabel("����");
		wlinput_describeTF=new JTextField();
		wishinB=new JButton("�Է�"); //��ư
		
		//���̺� ����
		ttabmod=new TransactTableModel();
		transactTable=new JTable(ttabmod);
//		transactTable.setModel(ttabmod);
		transactTable.getColumnModel().getColumn(0).setMaxWidth(30);
		transactTable.getColumnModel().getColumn(1).setMaxWidth(600);
		transactTable.getColumnModel().getColumn(2).setMaxWidth(250);
		
		//�����
		JPanel p_stats=new JPanel(); 
		p_stats.setLayout(new GridLayout(0, 2));
		p_stats.setBorder(new TitledBorder("�����"));
		p_stats.setBackground(Color.white);
		p_stats.add(yourmoney);
		p_stats.add(showmoney); //�� ���� �ݾ�
		p_stats.add(daypayLB);
		p_stats.add(daypayamountLB); //�ϰ� �����
		p_stats.add(weekpayLB);
		p_stats.add(weekpayamountLB); //�ְ� �����
		p_stats.add(weeklykeywordLB);
		p_stats.add(keywordLB); //�ְ� ���� �ִ� Ű����
		p_stats.add(monthpayLB);
		p_stats.add(monthpayamountLB); //���� �����
		
		//���ø���Ʈ ǥ��
		JPanel p_wishlist_show=new JPanel();
		p_wishlist_show.setLayout(new GridLayout(0, 1));
//		p_wishlist_show.setBorder(new TitledBorder("���ø���Ʈ"));
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
		
		//���ø���Ʈ �Է�
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
		
		//���ø���Ʈ
		JPanel p_wishlist=new JPanel();
		p_wishlist.setLayout(new GridLayout(1, 2));
		p_wishlist.setBorder(new TitledBorder("���ø���Ʈ"));
		p_wishlist.setBackground(Color.white);
		p_wishlist.add(p_wishlist_show);
		p_wishlist.add(p_wishlist_input);
		
		//�ŷ��˻���
		JPanel p_transearch=new JPanel();
		p_transearch.add(transearchIoCB);
		p_transearch.add(transearchTypeCB);
		p_transearch.add(transearchTF);
		
		//�ŷ�-�Է¶�
		JPanel p_transacts_input=new JPanel();
		p_transacts_input.setLayout(new GridLayout(0, 2));
		p_transacts_input.setBackground(Color.white);
		p_transacts_input.add(moneyinLB);
		p_transacts_input.add(moneyinCB); //io����
		p_transacts_input.add(moneyamountLB);
		p_transacts_input.add(moneyamountTF); //�ݾ�
		p_transacts_input.add(moneyinfoLB);
		p_transacts_input.add(moneyinfoTF); //����
		p_transacts_input.add(moneydateLB);
		
		//�ŷ�-��ư��
		JPanel p_transacts_btns=new JPanel();
		p_transacts_btns.setLayout(new GridLayout(0, 3));
		p_transacts_btns.add(transinsB);
		p_transacts_btns.add(transmodB);
		p_transacts_btns.add(transdelB);
		
		//�ŷ���
		JPanel p_transacts=new JPanel();
		p_transacts.setLayout(new BorderLayout());
		p_transacts.setBorder(new TitledBorder("�ŷ���"));
		p_transacts.setBackground(Color.white);
		p_transacts.add(p_transacts_input, BorderLayout.NORTH);
		p_transacts.add(p_transacts_btns, BorderLayout.SOUTH);
		
		//����
		JPanel p_west=new JPanel();
		p_west.setLayout(new GridLayout(0, 1));
		p_west.add(p_stats);
		p_west.add(p_wishlist);
		
		//����
		JPanel p_east=new JPanel();
//		p_east.setBorder(new TitledBorder("����"));
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
		String[] columnNames= {"����", "�ݾ�", "��¥"};
		
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
		if (evt==wishinB) { //���ø���Ʈ �Է�(������Ʈ)
			try { //���߿� ��������
				updateWish();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (evt==transearchTF) { //�ŷ� �˻�
			searchTransact();
		} else if (evt==transinsB) { //�ŷ� �Է�
			insertTransact();
			//����� ������Ʈ
			try {
				initializeTotal();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "����� ������Ʈ ����");
				e1.printStackTrace();
			}
		} else if (evt==transmodB) { //�ŷ� ����
			modifyTransacat();
			//����� ������Ʈ
			try {
				initializeTotal();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "����� ������Ʈ ����");
				e1.printStackTrace();
			}
		} else if (evt==transdelB) { //�ŷ� ����
			deleteTransact();
			//����� ������Ʈ
			try {
				initializeTotal();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "����� ������Ʈ ����");
				e1.printStackTrace();
			}
		}
	}
	
	private void eventProc() { //������ ����
		
		//�׼Ǹ����� �߰�
		//�ŷ� �˻�
		transearchTF.addActionListener(this);
		//�ŷ� �Է�, ����, ����
		transinsB.addActionListener(this);
		transmodB.addActionListener(this);
		transdelB.addActionListener(this);
		//�ҿ� �Է�(������Ʈ)
		wishinB.addActionListener(this);
		
		//���̺� ���� ���
		transactTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row=transactTable.getSelectedRow();
				int col=2;
				//��¥�� ��������
				FinanceGS gs=new FinanceGS();
				gs.setTdate((String) transactTable.getValueAt(row, col));
				
				try {
					gs=tFunc.selectTransact(gs); //gs�� tFunc�� �ְ� gs�� ������.
					selectTransact(gs); //view�� �Լ��� ����� ������.
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
	}

	private void initializeTotal() throws Exception {
		
		FinanceGS gs=new FinanceGS();
		
		//�� ���� �ݾ�
		gs=tFunc.initTotal(gs);
		showmoney.setText(gs.getTotalmoney()+"��");
		
		//�ϰ� �����
		gs=tFunc.initDayPay(gs);
		daypayamountLB.setText(gs.getDailypay()+"��");
		
		//�ְ� �����
		gs=tFunc.initWeekPay(gs);
		weekpayamountLB.setText(gs.getWeeklypay()+"��");
		
		//�ְ� ���� �ִ� Ű����
		
		//���� �����
		gs=tFunc.initMonthPay(gs);
		monthpayamountLB.setText(gs.getMonthlypay()+"��");
		
	}

	private void initializeWish() throws Exception {
//		wFunc=new WishFunc();
		FinanceGS gs=new FinanceGS();
		
		//���� �ʱ�ȭ
		for (int i = 1; i <= 3; i++) {
			gs.setWishRank(i);
			if (gs.getWishRank()==1) { //����1 �ʱ�ȭ
				gs=wFunc.initializeWish(gs);
				wl1_name.setText("1. "+gs.getWishName());
				wl1_cost.setText(gs.getWishCost()+"��");
				wl1_describe.setText(gs.getWishDescribe());
			} else if (gs.getWishRank()==2) { //����2 �ʱ�ȭ
				gs=wFunc.initializeWish(gs);
				wl2_name.setText("2. "+gs.getWishName());
				wl2_cost.setText(gs.getWishCost()+"��");
				wl2_describe.setText(gs.getWishDescribe());
			} else if (gs.getWishRank()==3) { //����3 �ʱ�ȭ
				gs=wFunc.initializeWish(gs);
				wl3_name.setText("3. "+gs.getWishName());
				wl3_cost.setText(gs.getWishCost()+"��");
				wl3_describe.setText(gs.getWishDescribe());
			}
		}
	}
	
	private void updateWish() throws Exception {
		wFunc=new WishFunc();
		FinanceGS gs=new FinanceGS();
		//�Է�
		String temprank=((String)wlinput_rankCB.getSelectedItem());
		gs.setWishRank(Integer.parseInt(temprank));
		gs.setWishName(wlinput_nameTF.getText());
		gs.setWishCost(wlinput_costTF.getText());
		gs.setWishDescribe(wlinput_describeTF.getText());
		
		//����
		try {
			gs=wFunc.updateWish(gs);
			JOptionPane.showMessageDialog(null, "���� ������Ʈ �Ϸ�");
			int rank=gs.getWishRank();
			if (rank==1) {
				wl1_name.setText("1. "+gs.getWishName());
				wl1_cost.setText(gs.getWishCost()+"��");
				wl1_describe.setText(gs.getWishDescribe());
				nullWish();
			} else if (rank==2) {
				wl2_name.setText("2. "+gs.getWishName());
				wl2_cost.setText(gs.getWishCost()+"��");
				wl2_describe.setText(gs.getWishDescribe());
				nullWish();
			} else if (rank==3) {
				wl3_name.setText("3. "+gs.getWishName());
				wl3_cost.setText(gs.getWishCost()+"��");
				wl3_describe.setText(gs.getWishDescribe());
				nullWish();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "���� ������Ʈ ����");
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
			//�˻� ��� ���� ��
			if (data.isEmpty()) {
				JOptionPane.showMessageDialog(null, "�˻� ����� �����ϴ�.");
			}
			nullTransact();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	void selectTransact(FinanceGS gs) { //�۾���
		if (gs.getTio().equals("����")) {	
			moneyinCB.setSelectedIndex(0);
		} else if (gs.getTio().equals("����")) {	
			moneyinCB.setSelectedIndex(1);
		}
		moneyamountTF.setText(""+gs.getTamount());
		moneyinfoTF.setText(gs.getTdescribe());
		moneydateLB.setText(gs.getTdate());
	}
	
	private void insertTransact() { //���ִ� �Ʒ��� ����
		FinanceGS gs=new FinanceGS();
		//transearchTF
		
		//�Է°��� �����ͼ� gs�� ����־����
		gs.setTio((String)moneyinCB.getSelectedItem());
		gs.setTamount(Integer.parseInt(moneyamountTF.getText()));
		gs.setTdescribe(moneyinfoTF.getText());
		
		try {
			ArrayList data=tFunc.insertTransact(gs);
			JOptionPane.showMessageDialog(null, "���� �Ϸ�");
			//textâ �����
			nullTransact();
			//���̺� ����
			ttabmod.data=data;
			transactTable.setModel(ttabmod);
			ttabmod.fireTableDataChanged();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "���� ����");
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
			JOptionPane.showMessageDialog(null, "���� �Ϸ�");
			//���̺� ����
			ttabmod.data=data;
			transactTable.setModel(ttabmod);
			ttabmod.fireTableDataChanged();
			
			gs.setRow(row);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "���� ����");
			e.printStackTrace();
		}
		
	}

	private void deleteTransact() {
		FinanceGS gs=new FinanceGS();
		
		int row=transactTable.getSelectedRow();
		int col=2;
//		gs.setTdate(""+transactTable.getValueAt(row, col)); //�̰͵� �� ��
		gs.setTdate((String) transactTable.getValueAt(row, col));
		
		try {
			ArrayList data=tFunc.deleteTransact(gs);
			JOptionPane.showMessageDialog(null, "���� �Ϸ�");
			nullTransact();
			//���̺�
			ttabmod.data=data;
			transactTable.setModel(ttabmod);
			ttabmod.fireTableDataChanged();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "���� ����");
			e.printStackTrace();
		}
	}
	
	private void nullTransact() {
		moneyamountTF.setText(null);
		moneyinfoTF.setText(null);
		moneydateLB.setText("�ŷ� ��¥");
	}
	
}
