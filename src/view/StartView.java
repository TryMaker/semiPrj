package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class StartView extends JPanel {
	
	JPanel startV;
	CardLayout card;
	
	public StartView() {
		startV.setLayout(card);
		
		startV.setBackground(Color.RED);
		
		startV.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
	}

}
