import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class MinePanel extends JPanel{
    
    JButton[][] board;
	int size;
	int bombs;
    
    MineLogic newGame;
	
	public MinePanel(File file){
		newGame = MineLogic.load(file);
		size = newGame.getSize();
		bombs = newGame.getBombsNum();
		boolean[][] revealed = newGame.getRevealed();
		board = new JButton[size][size];
		setLayout(new GridLayout(size,size));
        for ( int i = 0; i < size; i++ ){
            for(int j = 0; j < size; j++){
                board[i][j]= new JButton();
                add(board[i][j]);
                
                board[i][j].setOpaque(true);
                board[i][j].addMouseListener(new myAdapter());
            }
        }
		repaint();
		for(int i = 0; i<size ; i++){
			for(int j = 0; j<size; j++){
				if(revealed[i][j]){
					board[i][j].setVisible(false);
				}
			}
		}
	}

    public MinePanel(int gameSize, int numBombs){
		size = gameSize;
		bombs = numBombs;
		System.out.println("Panel size is: " +size);
        newGame = new MineLogic(size, bombs);
			
		board = new JButton[size][size];
		setLayout(new GridLayout(size,size));
        for ( int i = 0; i < size; i++ ){
            for(int j = 0; j < size; j++){
                board[i][j]= new JButton();
                add(board[i][j]);
                
                board[i][j].setOpaque(true);
                board[i][j].addMouseListener(new myAdapter());
            }
        }
    }
    
    private class myAdapter extends MouseAdapter{
        public void mouseClicked(MouseEvent a){
            int xCoor = 0;
            int yCoor = 0;
            
            for ( int i = 0; i < size; i++ ){
                for(int j = 0; j < size; j++){
                    if (board[i][j]==a.getSource()){
                        xCoor=i;
                        yCoor=j;
                    }
                }
            }
            
            if (SwingUtilities.isLeftMouseButton(a)){
                leftClick(xCoor, yCoor);
            }
            else if (SwingUtilities.isRightMouseButton(a)){
                rightClick(xCoor, yCoor);
            }
            
        }
    }
    
    public void rightClick(int xCoor, int yCoor){
    //for flags
        Icon flag = new ImageIcon("flag.jpg");
        if (!newGame.getFlags()[xCoor][yCoor]){
            newGame.setFlags(xCoor, yCoor);
            board[xCoor][yCoor].setIcon(flag);
            
        }
        else{
            newGame.setFlags(xCoor, yCoor);
            Icon clear = new ImageIcon("Clear");
            board[xCoor][yCoor].setIcon(clear);
        }
        
        if (newGame.winCheck()){
            JFrame frame = new JFrame();
            int choice = JOptionPane.showConfirmDialog(
            frame,
            "Yay! You won!\n"
            + "Do you want to play again?",
            "You win!!",
            JOptionPane.YES_NO_OPTION); 
            
            playAgain(choice);
        }
    }
    
    public void leftClick(int xCoor, int yCoor){
    //for play
        newGame.play(xCoor, yCoor);
        
        board[xCoor][yCoor].setVisible(false);
		newGame.setRevealed(xCoor, yCoor);
        
        /*if (newGame.getMinefield()[xCoor][yCoor]==0){
            clearZeros();
        }*/
        
        
        
        System.out.println("JButton "+xCoor+","+yCoor+" Value: " + newGame.getMinefield()[xCoor][yCoor]);
        
        
        if (newGame.getGameOver()){
                System.out.println("Bomb was pressed!");
            
                JFrame frame = new JFrame();
                int choice = JOptionPane.showConfirmDialog(
                frame,
                "Oops! You died!\n"
                + "Do you want to play again?",
                "Game Over!!",
                JOptionPane.YES_NO_OPTION);
                
                playAgain(choice);
            
            
        }
        
    }
    
    public void playAgain(int choice){
        if (choice == JOptionPane.YES_OPTION){
            newGame = new MineLogic(size, bombs);
            for ( int i = 0; i < size; i++ ){
                for(int j = 0; j < size; j++){
                    board[i][j].setVisible(true);
                    Icon clear = new ImageIcon("Clear");
                    board[i][j].setIcon(clear);
                }
            }
        }
        else{
            System.exit(0);
        }
    }
    
    
    public void save(File file){
		newGame.save(file);
	}
    public void paintComponent(Graphics g){
        
        super.paintComponent(g);
        
        int[][] minefield = newGame.getMinefield();
        int height = getHeight();
        int width = getWidth();
        
        double buttonWidth = width/size;
        double buttonHeight = height/size;
		
		
		if(size == 10){
			g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
			for (int i=0; i<size; i++){
				for (int j=0; j<size; j++){
					if(minefield[i][j]==9){
						g.drawString("Bomb!",(int)(j*buttonWidth+buttonWidth/5),(int)(i*buttonHeight+buttonHeight/2));
					}
					else{
						g.drawString( "" + minefield[i][j], (int)(j*buttonWidth+buttonWidth/2),(int)(i*buttonHeight+buttonHeight/2));
					}
				}
			}
		}
		if(size == 15){
			g.setFont(new Font("TimesRoman", Font.PLAIN, 11));
			for (int i=0; i<size; i++){
				for (int j=0; j<size; j++){
					if(minefield[i][j]==9){
						g.drawString("Bomb!",(int)(j*buttonWidth+buttonWidth/7.5),(int)(i*buttonHeight+buttonHeight));
					}
					else{
						g.drawString( "" + minefield[i][j], (int)(j*buttonWidth+buttonWidth/2),(int)(i*buttonHeight+buttonHeight));
					}
				}
			}
		}
		if(size == 20){
			g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
			for (int i=0; i<size; i++){
				for (int j=0; j<size; j++){
					if(minefield[i][j]==9){
						g.drawString("Bomb!",(int)(j*buttonWidth+buttonWidth/10),(int)(i*buttonHeight+buttonHeight/1.5));
					}
					else{
						g.drawString( "" + minefield[i][j], (int)(j*buttonWidth+buttonWidth/2),(int)(i*buttonHeight+buttonHeight/1.5));
					}
				}
			}
		}
        
        repaint();
    }
}