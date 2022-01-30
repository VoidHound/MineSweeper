import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.ImageObserver;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class MineFrame extends JFrame{
		MinePanel panel;
        public MineFrame(){ 
            setTitle("MineSweeper"); 
            Toolkit kit = Toolkit.getDefaultToolkit();
            Dimension screenSize = kit.getScreenSize();
            setSize(screenSize.width/2,screenSize.height/2);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
			
			JMenuBar menuBar = new JMenuBar();

			JMenu menu = new JMenu("File");
			menu.setMnemonic(KeyEvent.VK_A);
			menu.getAccessibleContext().setAccessibleDescription("File Menu");
			menuBar.add(menu);
		
			JMenu startNew = new JMenu("New");
			startNew.setMnemonic(KeyEvent.VK_S);
		
			JMenuItem easy = new JMenuItem("Easy");
			easy.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent a){
					if(panel!=null){
						remove(panel);
					}
					panel = new MinePanel(10,10);
					add(panel);
					repaint();
					panel.setVisible(true);
					setVisible(true);
				}
			});
			startNew.add(easy);
		
			JMenuItem medium = new JMenuItem("Medium");
			medium.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent a){
					if(panel!=null){
						remove(panel);
					}
					panel = new MinePanel(15,20);
					add(panel);
					repaint();
					panel.setVisible(true);
					setVisible(true);
				}
			});
			startNew.add(medium);
		
			JMenuItem difficult = new JMenuItem("Difficult");
			difficult.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent a){
					if(panel!=null){
						remove(panel);
					}
					panel = new MinePanel(20,30);
					add(panel);
					repaint();
					panel.setVisible(true);
					setVisible(true);
				}
			});
			startNew.add(difficult);
			menu.add(startNew);
		
			JMenuItem save = new JMenuItem("Save");
			save.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent a){
					JFileChooser fileChooser = new JFileChooser();
					int choice = fileChooser.showOpenDialog(null);
					if (choice == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						panel.save(file);
					}
				}
			});
			menu.add(save);
		
			JMenuItem load = new JMenuItem("Load");
			load.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent a){
					if(panel!=null){
						remove(panel);
					}
					JFileChooser fileChooser = new JFileChooser();
					int choice = fileChooser.showOpenDialog(null);
					if (choice == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						panel = new MinePanel(file);
						add(panel);
						repaint();
						panel.setVisible(true);
						setVisible(true);
					}
				}
			});
			menu.add(load);
		
			JMenuItem quit = new JMenuItem("Quit");
			quit.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent a){
					MineLogic.quit();
				}
			});
			menu.add(quit);
		
			menuBar.add(menu);
			menuBar.setVisible(true);
            setJMenuBar(menuBar);
            setVisible(true);
            repaint();
    }
}