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
		
		//탭 추가
		JTabbedPane pane=new JTabbedPane();
		pane.add("메모장", memoV);
		pane.add("가계부", finV);
		pane.setSelectedIndex(0); //초기 열리는 탭
		add("Center",pane);
		
		//시작화면 추가
		
		//프레임 설정
		setTitle("예산 관리");
		setSize(800, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
