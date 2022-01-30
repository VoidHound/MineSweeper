import java.util.*;
import java.io.*;
import java.io.Serializable.*;
import java.io.Serializable;

public class MineLogic implements Serializable{
    
    // 9 is bomb
    
    int[][] minefield;
    boolean[][] flags;
	boolean[][] revealed;
    boolean gameOver = false;
	int size;
	int bombs;
    
    public MineLogic(int gameSize, int numBombs){
        size = gameSize;
		bombs = numBombs;
		setUp();
    }
	public int getSize(){
		return size;
	}
	public int getBombsNum(){
		return bombs;
	}
    public int[][] getMinefield(){
        return minefield;
    }
    
    public void setFlags(int x, int y){
        flags[x][y] = !flags[x][y];
    }
    
    public boolean[][] getFlags(){
        return flags;
    }
    
	public void setRevealed(int x, int y){
		revealed[x][y] = true;
	}
	
	public boolean[][] getRevealed(){
		return revealed;
	}
	
    public boolean getGameOver(){
        return gameOver;
    }
    
    public void setUp(){
		minefield = new int[size][size];
		flags = new boolean[size][size];
		revealed = new boolean[size][size];
        for(int i = 0; i<bombs; i++){
            Random x = new Random();
            int xCoor = x.nextInt(size);
            Random y = new Random();
            int yCoor = y.nextInt(size);
            setUpMineCheck(xCoor, yCoor);
        }
        
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                setUpMinefieldNumber(i,j);
                flags[i][j] = false;
				revealed[i][j] = false;
            }
        }   
    }
    
    public void play(int x, int y){
        
        if(minefield[x][y] == 9){
            gameOver = true;
        }
        
    }
    
    public boolean winCheck(){
        
        int count = 0;
        
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if (flags[i][j] && minefield[i][j]==9){
                    count++;
                }
            }
        }
        
        if (count==bombs){
            return true;
        }
        else{
            return false;
        }
    }
    
    
    public void setUpMineCheck(int xCoor, int yCoor){
        if(minefield[xCoor][yCoor]==9){
            Random x = new Random();
            xCoor = x.nextInt(size);
            Random y = new Random();
            yCoor = y.nextInt(size);
            setUpMineCheck(xCoor,yCoor);
        }
        else{
            minefield[xCoor][yCoor]=9;
        }
    }
    
    public void setUpMinefieldNumber(int x, int y){
        int count = 0;
        for (int i=-1; i<=1; i++){
            for(int j=-1; j<=1; j++){
                if (((x+i)>=0) && ((x+i)<size) && ((y+j)>=0) && ((y+j)<size)){
                    if (minefield[x+i][y+j]==9){
                        count++;
                    }
                }
                else {
                    continue;
                }
            }
        }
        if (minefield[x][y]!=9){
            minefield[x][y] = count;
        }
    }
	
	public void save(File file){
		try{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(this);
		}
		catch(IOException e) {
				e.printStackTrace();
		}
	}
	
	public static MineLogic load(File file){
		try{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			return (MineLogic)in.readObject();
		}
		catch(IOException e) {
			System.out.println("Error reading file");
		}
		catch(ClassNotFoundException e) {
			System.out.println(file +" does not contain MineSweeper game");
		}
		
		return null;

	}
	
	public static void quit(){
		System.exit(0);
	}
}