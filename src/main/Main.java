package main;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import view.FinanceView;
import view.MemoView;

public class Main extends JFrame {
	
	MemoView memoV;
	FinanceView finV;
	
	public Main() {
		memoV=new MemoView();
		finV=new FinanceView();
		
		//�� �߰�
		JTabbedPane pane=new JTabbedPane();
		pane.add("�޸���", memoV);
		pane.add("�����", finV);
		pane.setSelectedIndex(0); //�ʱ� ������ ��
		add("Center",pane);
		
		//����ȭ�� �߰�
		
		//������ ����
		setTitle("���� ����");
		setSize(800, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
