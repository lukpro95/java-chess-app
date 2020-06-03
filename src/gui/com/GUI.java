package gui.com;

import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.*;

import chess.com.Game;
import menu.com.Menu;
import server.com.ClientMouth;
import server.com.ServerHost;

public class GUI {
	
	private static String whiteSquares = Menu.settings.getWhiteSquares();
	private static String blackSquares = Menu.settings.getBlackSquares();
	
	public JFrame mainFrame = new JFrame();
	
	private static JPanel tempFrame = new JPanel();
	private static ImageIcon whiteIcon;
	private static ImageIcon blackIcon;
	private JPanel bgFrame = new JPanel();
	private JPanel blackFrame = new JPanel();
	private JPanel pieceFrame = new JPanel();
	private JLabel referee = new JLabel("", SwingConstants.CENTER);
	
	private ServerHost sH = null;
	private ClientMouth cM = null;
	
	public GUI(Game game, ServerHost sH, ClientMouth cM){
		this.sH = sH;
		this.cM = cM;

		initGUI();
		
	}
	
	private void initGUI() {
		
		// -------------------- Components ------------------------------------
		
		bgFrame.setLayout(null);
		bgFrame.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		//Referee
		JPanel refereePanel = new JPanel();
		refereePanel.setBounds(320, 780, 120, 20);
		refereePanel.setBackground(Color.BLACK);
		refereePanel.setLayout(null);
		refereePanel.setVisible(true);
		
		getReferee().setBounds(2, 2, 116, 16);
		getReferee().setBackground(Color.WHITE);
		getReferee().setText("Player turn: White!");
		getReferee().setOpaque(true);
		getReferee().setLayout(null);
		getReferee().setVisible(true);
		
		refereePanel.add(getReferee());
		mainFrame.add(refereePanel);
		
		//temporary, invisible frame for temporary possible-to-choose-squares
		mainFrame.add(getTempFrame());
		getTempFrame().setBounds(12, 12, 1000, 800);
		getTempFrame().setBackground(new Color(0,0,0,0));
		getTempFrame().setOpaque(true);
		getTempFrame().setLayout(null);
		
		mainFrame.add(getPieceFrame());
		getPieceFrame().setBounds(12, 12, 1000, 800);
		getPieceFrame().setBackground(new Color(0,0,0,0));
		getPieceFrame().setOpaque(true);
		getPieceFrame().setLayout(null);
		
		//board squares
		setChessBoard();
		
		//black frame behind board squares
		mainFrame.add(blackFrame);
		blackFrame.setBounds(12, 12, 740, 740);
		blackFrame.setBackground(Color.BLACK);
		blackFrame.setLayout(null);
		
		// -------------------- Main Frame Settings --------------------------
		
		if(sH == null && cM == null) {
			singleWindow();
		}
		else {
			new OnlineGUI(this);
			mutliWindow();
		}
		
		mainFrame.add(bgFrame);
		bgFrame.setSize(mainFrame.getSize().width-6, mainFrame.getSize().height-29);
		bgFrame.setBackground(Color.DARK_GRAY);

	}
	
	private void singleWindow() {
		mainFrame.setSize(775, 850);
		mainFrame.setTitle("Chess Game");
		mainFrame.setIconImage(new ImageIcon("Icons/small.png").getImage());
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		mainFrame.setLocationRelativeTo(null);
		mainFrame.setResizable(false);
		mainFrame.setLayout(null);
		mainFrame.setVisible(true);
	}
	
	private void mutliWindow() {
		mainFrame.setTitle("Chess Online! Playing as: ");
		mainFrame.setSize(1090, 850);
		mainFrame.setIconImage(new ImageIcon("Icons/small.png").getImage());
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		mainFrame.setLocationRelativeTo(null);
		mainFrame.setResizable(false);
		mainFrame.setLayout(null);
		mainFrame.setVisible(true);
	}
	
	public void setChessBoard() {
		
		int cellSize = getBasicSize();
		
		//Getting Grid
		int[] gridX = new int[8];
		int[] gridY = new int[8];
		
		for(int i = 0; i<8; i++) {
			gridX[i] = 3+i*cellSize;
		
			for(int j = 0; j<8; j++) {
				gridX[j] = 3+j*cellSize;
				}
		}
	
		for(int i = 0; i<8; i++) {
			gridY[i] = 3+i*cellSize;
		
			for(int j = 0; j<8; j++) {
				gridY[j] = 3+j*cellSize;
				}
		}
		
		int z0 = 3;
		int z1 = 3+cellSize;
		int z2 = 3+cellSize*2;
		int z3 = 3+cellSize*3;
		int z4 = 3+cellSize*4;
		int z5 = 3+cellSize*5;
		int z6 = 3+cellSize*6;
		int z7 = 3+cellSize*7;
		
		//Making graphic layout based on grids above
		for(int i=0; i<8;i++) {
			
			for(int j=0; j<8; j++) {
				if(
					(((gridX[i] == z0) && (gridY[j] == z0 || gridY[j] == z2 ||gridY[j] == z4 ||gridY[j] == z6))
					||
					((gridX[i] == z2) && (gridY[j] == z0 || gridY[j] == z2 ||gridY[j] == z4 ||gridY[j] == z6))
					||
					((gridX[i] == z4) && (gridY[j] == z0 || gridY[j] == z2 ||gridY[j] == z4 ||gridY[j] == z6))
					||
					((gridX[i] == z6) && (gridY[j] == z0 || gridY[j] == z2 ||gridY[j] == z4 ||gridY[j] == z6)))
					
					||
					
					//White vs Black
					
					(((gridX[i] == z1) && (gridY[j] == z1 || gridY[j] == z3 ||gridY[j] == z5 ||gridY[j] == z7))
					||
					((gridX[i] == z3) && (gridY[j] == z1 || gridY[j] == z3 ||gridY[j] == z5 ||gridY[j] == z7))
					||
					((gridX[i] == z5) && (gridY[j] == z1 || gridY[j] == z3 ||gridY[j] == z5 ||gridY[j] == z7))
					||
					((gridX[i] == z7) && (gridY[j] == z1 || gridY[j] == z3 ||gridY[j] == z5 ||gridY[j] == z7))
					))
				{
					
					JLabel whiteSquaresX = new JLabel();
					whiteSquaresX.setBounds(gridX[i], gridY[j], 90, 90);
					whiteSquaresX.setLayout(null);
					whiteSquaresX.setToolTipText("x: " + (i+1) + "y: " + (j+1));
					blackFrame.add(whiteSquaresX);
					
					if(whiteIcon == null && blackIcon == null) {
						whiteSquaresX.setBackground(Color.decode(whiteSquares));
						whiteSquaresX.setOpaque(true);
					} else {
						whiteSquaresX.setOpaque(false);
						whiteSquaresX.setIcon(whiteIcon);
					}
				
				}
				
				else {
					JLabel blackSquaresX = new JLabel();
					blackSquaresX.setBounds(gridX[i], gridY[j], 90, 90);
					blackSquaresX.setLayout(null);
					blackSquaresX.setToolTipText("x: " + (i+1) + "y: " + (j+1));
					blackFrame.add(blackSquaresX);
					
					if(whiteIcon == null && blackIcon == null) {
						blackSquaresX.setBackground(Color.decode(blackSquares));
						blackSquaresX.setOpaque(true);
					} else {
						blackSquaresX.setOpaque(false);
						blackSquaresX.setIcon(blackIcon);
					}
					
				}
			}
		}
		
		for(int i = 0; i<8; i++) {
			String n = String.valueOf(8-i);
			JLabel number = new JLabel(n);
			number.setForeground(Color.WHITE);
			number.setBounds(3, 3+49+i*92, 10, 10);
			bgFrame.add(number);
		}
	
		for(int i = 0; i<8; i++) {
			JLabel letter = new JLabel(KeyEvent.getKeyText(i+65).toLowerCase());
			letter.setForeground(Color.WHITE);
			letter.setBounds(3+52+i*92, 755, 15, 15);
			bgFrame.add(letter);
		}
		
	}
	
	public static void setSquares(String white, String black) {
		blackSquares = white;
		whiteSquares = black;
	}
	
	
	public void refreshAll() {
		tempFrame.removeAll();
		tempFrame.repaint();
		blackFrame.repaint();
		pieceFrame.repaint();
		mainFrame.repaint();
	}
	
	// ----------------------- Getters ---------------------------------
	
	public JPanel getPieceFrame() {
		return pieceFrame;
	}

	public static JPanel getTempFrame() {
		return tempFrame;
	}

	public JLabel getReferee() {
		return referee;
	}
	
	public ServerHost getsH() {
		return sH;
	}

	public ClientMouth getcM() {
		return cM;
	}
	
	public int getBasicSize() {
		return 92;
	}

	public static void setImages(ImageIcon wCell, ImageIcon bCell) {
		whiteIcon = wCell;
		blackIcon = bCell;
	}

}
