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
	 * #��1: �޸���
	 * - �޸� �Է�(�޺� �ڽ�)
	 * ����(�ؽ�Ʈ �Ʒ���)
	 * ��2000����Ʈ
	 * ����(��ư)
	 * ���ڵ����� ��¥ ����
	 * - �޸� �˻�(�޺� �ڽ�)
	 * ���� �˻�(�ؽ�Ʈ �ʵ�)
	 * �޸� ���
	 */
	JComboBox memoinCB; //�޸� �˻� ���, �˻��߿� �缱�� �� ��
	JTextArea memoTA; //�޸��� �Է�
	
	JTextField memotitleTF, memosearchTF; //�޸� ���� �Է�, �޸� �˻�
	
	JLabel showdateLb, showbyteLb; //��¥ ������, �뷮 ������
	
	JButton insB, modB, delB; //���� ��ư
	
	JTable memoTable;
	
	MemoFunc mFunc;
	MemoTableModel mtabmod;
	
	public MemoView() { //������
		addLayout(); //ȭ�� ����
		//�ʱ�ȭ �۾�
		initStyle(); //���� �㰡
		eventProc(); //�׼Ǹ�����
		//db����
		connectDB();
	}

	public void connectDB() { //DB ����
		try {
			mFunc=new MemoFunc();
			System.out.println("�޸��� ���� ����");
		} catch (Exception e) {
			System.out.println("�޸��� ���� ����");
			e.printStackTrace();
		}

	}

	private void addLayout() { //GUI ��ġ
		//������Ʈ
		//����
		memotitleTF=new JTextField(15); //�޸� ���� �Է�
		memoTA=new JTextArea(0,0); //�޸� �Է�
		memoTA.setLineWrap(true); //�ڵ� �ٹٲ�
		
		showdateLb=new JLabel("��¥");
		showbyteLb=new JLabel("�뷮");
		
		insB=new JButton("�Է�");
		modB=new JButton("����");
		delB=new JButton("����");
		
		//������
		String[] memoinStr= {"����", "����+����"};
		memoinCB=new JComboBox(memoinStr);
		memosearchTF=new JTextField(20);
		
		mtabmod=new MemoTableModel();
		memoTable=new JTable(mtabmod);
		memoTable.setModel(mtabmod);
		memoTable.getColumnModel().getColumn(0).setMaxWidth(1000); //Į�� ����
		memoTable.getColumnModel().getColumn(1).setMaxWidth(300); 

		
		//ȭ�� ����
		//���� ���
		JPanel p_west=new JPanel();
		p_west.setLayout(new BorderLayout());
		p_west.setBorder(new TitledBorder("�޸� ����"));
		
		
		JPanel p_west_top=new JPanel();
		p_west_top.setLayout(new BorderLayout());
		p_west_top.add(new JLabel("����"), BorderLayout.WEST);
		p_west_top.add(memotitleTF, BorderLayout.CENTER);
		
		
		JPanel p_west_cen=new JPanel();
		p_west_cen.setLayout(new BorderLayout());
		p_west_cen.add(showdateLb, BorderLayout.NORTH);
		p_west_cen.add(new JLabel("�޸�"), BorderLayout.WEST);
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
		
		//������ ���
		JPanel p_east=new JPanel();
		p_east.setLayout(new BorderLayout());
		
		//�������� ����
		JPanel p_east_top=new JPanel();
		p_east_top.setBorder(new TitledBorder("�޸� �˻�"));
		p_east_top.add(memoinCB);
		p_east_top.add(memosearchTF);
		
		
		p_east.add(p_east_top, BorderLayout.NORTH);
		p_east.add(new JScrollPane(memoTable), BorderLayout.CENTER);
		
		
		setLayout(new GridLayout(1, 2));
		add(p_west);
		add(p_east);
	}
	
	class MemoTableModel extends AbstractTableModel { //���� ���̺�
		
		ArrayList data=new ArrayList();
		String[] columnNames= {"�޸� ����", "�޸� ��¥"};
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
	
	
	void initStyle() { //��Ȱ��ȭ
	}
	
	public void eventProc() { //�׼Ǹ����� ����
		
		memosearchTF.addActionListener(this); //�˻�
		
		insB.addActionListener(this); //�Է� ��ư
		modB.addActionListener(this); //���� ��ư
		delB.addActionListener(this); //���� ��ư
		
		memoTable.addMouseListener(new MouseAdapter() { //Į�� ����
			@Override
			public void mouseClicked(MouseEvent e) {
				int row=memoTable.getSelectedRow();
				int col=1;
				//Ŭ�� ���ڵ� �޸� ��ȣ ���(��¥�� �غ���
				MemoGS gs=new MemoGS();
				gs.setDate((String) memoTable.getValueAt(row, col));
				try {
					gs=mFunc.selectMemo(gs); //gs�� mFunc�� �ְ� gs�� ������.
					selectMemo(gs); //view�� �Լ��� ����� ������.
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		memoTA.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				MemoGS gs=new MemoGS();
				memoTA.setText(memoTA.getText().replaceAll("\r\n", "<br>")); //�ڵ� �� �ٲ�
				
				gs.setMemo(memoTA.getText()); //�޸� ����
				gs.setMemory(gs.getMemo().getBytes().length); //�޸� �뷮 ����
				showbyteLb.setText(gs.getMemory()+"/2000Byte");
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) { //�׼� �ߵ�
		Object evt=e.getSource();
		
		if (evt==memosearchTF) { //�˻�
			searchMemo();
		} else if (evt==insB) { //�Է� ��ư
			insertMemo();
		} else if (evt==modB) { //���� ��ư
			modifyMemo();
		} else if (evt==delB) { //���� ��ư
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
				JOptionPane.showMessageDialog(null, "�˻� ����� �����ϴ�.");
			}
			memoTable.setModel(mtabmod);
			mtabmod.fireTableDataChanged();
			showdateLb.setText("��¥");
			showbyteLb.setText("�뷮");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//��׷� �ʱ�ȭ
		memotitleTF.setText(null);
		memoTA.setText(null);
	}
	
	void selectMemo(MemoGS gs) {
		memotitleTF.setText(gs.getTitle());
		memoTA.setText(gs.getMemo());
		showdateLb.setText(gs.getDate()+" ����");
		
		gs.setMemo(memoTA.getText()); //�޸� ����
		gs.setMemory(gs.getMemo().getBytes().length); //�޸� �뷮 ����
		showbyteLb.setText(gs.getMemory()+"/2000Byte");
	}
	
	private void insertMemo() {
		MemoGS gs=new MemoGS();
		gs.setTitle(memotitleTF.getText());
		gs.setMemo(memoTA.getText());
		
		gs.setMemory(gs.getMemo().getBytes().length); //����Ʈ ����
		
		try {
//			mFunc.insertMemo(gs);
			ArrayList data=mFunc.insertMemo(gs);
			JOptionPane.showMessageDialog(null, "���� �Ϸ�");
			//textâ �����
			memotitleTF.setText(null);
			memoTA.setText(null);
			showdateLb.setText("��¥");
			showbyteLb.setText("�뷮");
			//���̺� ����
			mtabmod.data=data;
			memoTable.setModel(mtabmod);
			mtabmod.fireTableDataChanged();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "���� ����");
			e.printStackTrace();
		}
	}
	
	private void modifyMemo() {
		MemoGS gs=new MemoGS();
		gs.setTitle(memotitleTF.getText());
		gs.setMemo(memoTA.getText());
		
		gs.setMemory(gs.getMemo().getBytes().length);
		
		int row=memoTable.getSelectedRow(); //�̰� ���� ������ �ȵ�
		//if���� ���� ���� ���� �� ������ �������� select���� �������°ɷ�?
		if (row==-1) { //���� ���ϸ� -1
			row=gs.getRow(); //gs�� ����� ���� ������
		}
		int col=1;
		String date=(String) memoTable.getValueAt(row, col);
		gs.setDate(date); //���� �����ؼ� �� �� �ְ�
		
		try {
//			mFunc.modifyMemo(gs, data);
			ArrayList data=mFunc.modifyMemo(gs, date);
			JOptionPane.showMessageDialog(null, "���� �Ϸ�");
//			memotitleTF.setText(null);
//			memoTA.setText(null);
			//���̺� ����
			mtabmod.data=data;
			memoTable.setModel(mtabmod);
			mtabmod.fireTableDataChanged();
			
			gs.setRow(row);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "���� ����");
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
			JOptionPane.showMessageDialog(null, "���� �Ϸ�");
			memotitleTF.setText(null);
			memoTA.setText(null);
			showdateLb.setText("��¥");
			showbyteLb.setText("�뷮");
			//���̺� ����
			mtabmod.data=data;
			memoTable.setModel(mtabmod);
			mtabmod.fireTableDataChanged();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "���� ����");
			e.printStackTrace();
		}
	}
	
}




