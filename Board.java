/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

/**
 * @author JeremyLaier
 * 2048 The game implemented into java using swing
 * The tiles are controlled via the arrow keys.
 * The goal is to combine the tiles until a 2048 tile is achieved.
 * @since 5/11/17
 */
public class Board extends JFrame
{
   private JTextField[] tiles = new JTextField[16];
   private JTextField scoreTF,loseTF,winTF;
   private JLabel instructionsL,scoreL;
   private JButton exitB, resetB;
   private JPanel centerBoardP, northP, southP;
   private ResetListener rbHandler;
   private ExitListener ebHandler;
   private Integer score=0;
   
   private Color two = new Color(0xeee4da);
   private Color four =new Color(0xede0c8);
   private Color eight = new Color(0xf2b179);
   private Color sixteen = new Color(0xf59563);
   private Color thirtyTwo = new Color(0xf67c5f);
   private Color sixtyFour = new Color(0xf65e3b);
   private Color oneHundredTwentyEight = new Color(0xedcf72);
   private Color twoHundredFiftySix = new Color(0xedcc61);
   private Color fiveHundredTwelve = new Color(0xedc850);
   private Color oneThousandTwentyFour = new Color(0xedc53f);
   private Color twoThousandTwentyEight = new Color(0xedc22e);
   private Color defaultTile = new Color(0xbbada0);
   private Color backgroundColor = (Color.lightGray);
   private Font tileFont = new Font("Arial", Font.BOLD,16);
    
    /**
     * Creates new form Board
     * this will be our board for the game
     */
   @SuppressWarnings("serial")
   public Board() 
   {
    	//setup jtextfields
      scoreTF = new JTextField("0",SwingConstants.CENTER);
      scoreTF.setPreferredSize(new Dimension(75,25));
      scoreTF.setEditable(false);
    	
      winTF = new JTextField("You win!",SwingConstants.CENTER);
      loseTF = new JTextField("You lost! Try again!",SwingConstants.CENTER);
      
      winTF.setEditable(false);
      loseTF.setEditable(false);
      
      //this creates the board
      //the board is made of an ONE DIMENSIONAL ARRAY of textfields
      for(int i = 0; i<16;i++)
      {
         tiles[i] = new JTextField("", SwingConstants.CENTER);
         tiles[i].setPreferredSize(new Dimension(15,115));
         tiles[i].setEditable(false);
         tiles[i].setBackground(defaultTile);
         tiles[i].setFont(tileFont);
      }
    	
      //setup labels
      
      instructionsL = new JLabel("Move the board by using the arrow keys.");
      scoreL = new JLabel("Score: ");
    	
      //setup buttons
    	
      exitB = new JButton("Exit");
      resetB = new JButton("Reset");
    	
      //marry buttons to listeners
    	
      ebHandler = new ExitListener();
      exitB.addActionListener(ebHandler);
      rbHandler = new ResetListener();
      resetB.addActionListener(rbHandler);
    	
      //setup panels
    		
      centerBoardP = new JPanel(new GridLayout(4,4));
      northP = new JPanel(new FlowLayout());
      southP = new JPanel(new FlowLayout());
    	 
      //add to panels
      
      for(int i = 0; i<16;i++)
      {
         centerBoardP.add(tiles[i]);
      }
    	
    	
    	//THIS IS THE BOARD BY TEXTFIELDS
    	 /*    0  1  2  3
    	 *     4  5  6  7
    	 *     8  9  10 11
    	 *     12 13 14 15
    	 */
    	
      //add components to panels
      
      northP.add(instructionsL);
      northP.add(exitB);
      northP.add(resetB);
      northP.add(scoreL);
      northP.add(scoreTF);
      northP.setBackground(backgroundColor);
    	
      //make pane
      Container pane = getContentPane();
      pane.setLayout(new BorderLayout());
    	
      //add panels to pane
    	
      pane.add(northP, BorderLayout.NORTH);
      pane.add(centerBoardP, BorderLayout.CENTER);
      pane.add(southP, BorderLayout.SOUTH);
   
      
      /*
       * NEXT SEGMENTS ARE KEY BINDINGS
       * essentially binds key to component, in this case the center board panel
       * always focused
       */
      
      
      centerBoardP.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,0), "up");
      centerBoardP.getActionMap().put("up", 
         new AbstractAction()
         {
            public void actionPerformed(ActionEvent e)
            {winCondition();
               loseCondition();
               stackTilesUp();
               moveTilesUp();
               createTileUp();
               colorTiles();
               scoreTF.setText(score.toString());
            }
         });
      centerBoardP.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0), "down");
      centerBoardP.getActionMap().put("down", 
         new AbstractAction()
         {
            public void actionPerformed(ActionEvent e)
            {winCondition();
               loseCondition();
               stackTilesDown();
               moveTilesDown();
               createTileDown();
               colorTiles();
               scoreTF.setText(score.toString());
            }
         });
    
      centerBoardP.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0), "right");
      centerBoardP.getActionMap().put("right", 
         new AbstractAction()
         {
            public void actionPerformed(ActionEvent e)
            {winCondition();
               loseCondition();
               stackTilesRight();
               moveTilesRight();
               createTileRight();
               colorTiles();
               scoreTF.setText(score.toString());
            }
         });
    	
      centerBoardP.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0), "left");
      centerBoardP.getActionMap().put("left", 
         new AbstractAction()
         {
            public void actionPerformed(ActionEvent e)
            {winCondition();
               loseCondition();
               stackTilesLeft();
               moveTilesLeft();
               createTileLeft();
               colorTiles();
               scoreTF.setText(score.toString());
            }
         });
    	
    	//make pane appear
    	
      setTitle("2048");
      setFocusable(true);
      setResizable(false);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setSize(600,600);
      setLocationRelativeTo(null);  //allows the box to be in the center of the users screen.
      setVisible(true);
   }

   //main mnethod runs the board 
   public static void main(String[] args)
   {
      Board p = new Board();
      p.setVisible(true);
      p.requestFocusInWindow();
   }
    
    //reset button listener, makes a new board when pressed
   private class ResetListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
      	//reset(); MAKE A METHOD TO RESET BOARD
         dispose();
         Board p = new Board();
      }
   }
   //exit button listener, closes prog when pressed
   private class ExitListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         System.exit(0);
      }
   }
	//method that returns a randum number, either 2 or 4 , 2 has a 90% chance of being submitted
   private Integer randomTile()
   {
      Random rand = new Random();
      int randomNum = rand.nextInt(4) + 1;
      return randomNum;		
   }
   private Integer randomNumber()
   {
      Random rand = new Random();
      int randomNum = rand.nextInt(10);
      if(randomNum==1)
         randomNum=4;
      else
         randomNum=2;
   	//gives 2 a 90% chance of occurring 
      return randomNum;
   } 
	//method that checks the board for tiles and colors them
   private void colorTiles()
   {
      for(int i=0; i<16; i++)
      {
         if(tiles[i].getText().equalsIgnoreCase("2048"))
            tiles[i].setBackground(twoThousandTwentyEight);
         else if(tiles[i].getText().equalsIgnoreCase("1024"))
            tiles[i].setBackground(oneThousandTwentyFour);
         else if(tiles[i].getText().equalsIgnoreCase("512"))
            tiles[i].setBackground(fiveHundredTwelve);
         else if(tiles[i].getText().equalsIgnoreCase("256"))
            tiles[i].setBackground(twoHundredFiftySix);
         else if(tiles[i].getText().equalsIgnoreCase("128"))
            tiles[i].setBackground(oneHundredTwentyEight);
         else if(tiles[i].getText().equalsIgnoreCase("64"))
            tiles[i].setBackground(sixtyFour);
         else if(tiles[i].getText().equalsIgnoreCase("32"))
            tiles[i].setBackground(thirtyTwo);
         else if(tiles[i].getText().equalsIgnoreCase("16"))
            tiles[i].setBackground(sixteen);
         else if(tiles[i].getText().equalsIgnoreCase("8"))
            tiles[i].setBackground(eight);
         else if(tiles[i].getText().equalsIgnoreCase("4"))
            tiles[i].setBackground(four);
         else if(tiles[i].getText().equalsIgnoreCase("2"))
            tiles[i].setBackground(two);
         else
            tiles[i].setBackground(defaultTile);
      }
   }
   
   //method that creates the tiles when the up key is pressed
   //creates tiles on the button row because when UP is pressed the board moves up leaving the last row to be made new
   private void createTileUp()
   {
      Integer randomNumber = randomTile();
   
      if(tiles[randomNumber+11].getText().equals(""))
      {			tiles[randomNumber + 11].setText(Integer.toString(randomNumber()));	
         score+=Integer.parseInt(tiles[randomNumber+11].getText());
      }
      else
         overFlowTile();
   		
      for(int i = 0; i<16;i++)
      {
         tiles[i].setHorizontalAlignment(JTextField.CENTER);
      }
   }
   //same concept as last method, makes the tiles on the first row
   private void createTileDown()
   {
      Integer randomNumber = randomTile();
   
      if(tiles[randomNumber-1].getText().equals(""))
      {	tiles[randomNumber-1].setText(Integer.toString(randomNumber()));	
         score+=Integer.parseInt(tiles[randomNumber-1].getText());
      }
      else
         overFlowTile();			
      for(int i = 0; i<16;i++)
      {
         tiles[i].setHorizontalAlignment(JTextField.CENTER);
      }
   }
   
   //makes the tiles on the right side column
   private void createTileLeft()
   {
      Integer randomNumber = randomTile();
      switch(randomNumber){
         case 1:
            randomNumber = 3;
            break;
         case 2:
            randomNumber = 7;
            break;
         case 3: 
            randomNumber = 11;
            break;
         case 4: randomNumber = 15;
            break;
      }
      if(tiles[randomNumber].getText().equals(""))
      {	tiles[randomNumber].setText(Integer.toString(randomNumber()));	
         score+=Integer.parseInt(tiles[randomNumber].getText());
      }
      else
         overFlowTile();
   		
      for(int i = 0; i<16;i++)
      {
         tiles[i].setHorizontalAlignment(JTextField.CENTER);
      }
   		
   	
   }
   //makes the tiles on the left side column
   private void createTileRight()
   {
      Integer randomNumber = randomTile();
   
      switch(randomNumber)
      {
      
         case 1:
            randomNumber = 0;
            break;
      
         case 2:
            randomNumber = 4;
            break;
      
         case 3: 
            randomNumber = 8;
            break;
      
         case 4: randomNumber = 12;
            break;
      
      }
      if(tiles[randomNumber].getText().equals(""))
      {	tiles[randomNumber].setText(Integer.toString(randomNumber()));	
         score+=Integer.parseInt(tiles[randomNumber].getText());
      }
      else 
         overFlowTile();
   		
      for(int i = 0; i<16;i++)
      {
         tiles[i].setHorizontalAlignment(JTextField.CENTER);
      }
   }
   //this class is for whenever the randomnumber gives a tile that is already filled
   //this way the tiles can be created in the center
   public void overFlowTile()
   {
      Integer randomNumber = randomTile();
   
      switch(randomNumber){
         case 1:
            randomNumber = 5;
            break;
         case 2:
            randomNumber = 6;
            break;
         case 3: 
            randomNumber = 9;
            break;
         case 4: randomNumber = 10;
            break;
      }
      if(tiles[randomNumber].getText().equals(""))
      {
         tiles[randomNumber].setText(Integer.toString(randomNumber()));	
         score+=Integer.parseInt(tiles[randomNumber].getText());
      }	
      for(int i = 0; i<16;i++)
      {
         tiles[i].setHorizontalAlignment(JTextField.CENTER);
      }
   }
	
	
	/*     0  1  2  3
	 *     4  5  6  7
	 *     8  9  10 11
	 *     12 13 14 15
	 */
   //stacks the tiles left
   //combines them into the next higher tier
   public void stackTilesLeft()
   {
      int i=0;
      for(int j=0; j<4;j++)
      {				
         if(j==0)
            i=0;
         else if(j==1)
            i=4;
         else if(j==2)
            i=8;
         else if(j==3)
            i=12;
      	
         if(!(tiles[i+0].getText().isEmpty()))
         {
            if(tiles[i+0].getText().equals(tiles[i+1].getText()))
            {
               tiles[i+0].setText(String.format("%d", (2* Integer.parseInt((tiles[i+1].getText())))));
               tiles[i+1].setText("");
               score+=Integer.parseInt(tiles[i].getText());
            }
            else if(tiles[i+0].getText().equals(tiles[i+2].getText()) &&tiles[i+1].getText().isEmpty())
            {	tiles[i+0].setText(String.format("%d", (2* Integer.parseInt((tiles[i+2].getText())))));
               tiles[i+2].setText("");
               score+=Integer.parseInt(tiles[i].getText());
            }
            else if(tiles[i+0].getText().equals(tiles[i+3].getText())&&tiles[i+1].getText().isEmpty() &&tiles[i+2].getText().isEmpty())
            {
               tiles[i+0].setText(String.format("%d", (2* Integer.parseInt((tiles[i+3].getText())))));
               tiles[i+3].setText("");
               score+=Integer.parseInt(tiles[i].getText());
            }
         }
         if(!(tiles[i+1].getText().isEmpty()))
         {
            if(tiles[i+1].getText().equals(tiles[i+2].getText()))
            {		tiles[i+1].setText(String.format("%d", (2* Integer.parseInt((tiles[i+2].getText())))));
               tiles[i+2].setText("");
               score+=Integer.parseInt(tiles[i+1].getText());
            	
            }
            else if(tiles[i+1].getText().equals(tiles[i+3].getText())&&tiles[i+2].getText().isEmpty())
            {	tiles[i+1].setText(String.format("%d", (2* Integer.parseInt((tiles[i+3].getText())))));
               tiles[i+3].setText("");	
               score+=Integer.parseInt(tiles[i+1].getText());
            }
         }
         if(!(tiles[i+2].getText().isEmpty()))
            if(tiles[i+2].getText().equals(tiles[i+3].getText()))
            {		tiles[i+2].setText(String.format("%d", (2* Integer.parseInt((tiles[i+3].getText())))));
               tiles[i+3].setText("");
               score+=Integer.parseInt(tiles[i+2].getText());
            }
      } 
   }
   //stacks tiles right by order
   public void stackTilesRight()
   {
      int i=0;
      for(int j=0; j<4;j++)
      {				
         if(j==0)
            i=3;
         else if(j==1)
            i=7;
         else if(j==2)
            i=11;
         else if(j==3)
            i=15;
      	
         if(!(tiles[i-0].getText().isEmpty()))
         {
            if(tiles[i-0].getText().equals(tiles[i-1].getText()))
            {
               tiles[i-0].setText(String.format("%d", (2* Integer.parseInt((tiles[i-1].getText())))));
               tiles[i-1].setText("");
               score+=Integer.parseInt(tiles[i].getText());
            }
            else if(tiles[i-0].getText().equals(tiles[i-2].getText()) &&tiles[i-1].getText().isEmpty())
            {	tiles[i-0].setText(String.format("%d", (2* Integer.parseInt((tiles[i-2].getText())))));
               tiles[i-2].setText("");
               score+=Integer.parseInt(tiles[i].getText());
            
            }
            else if(tiles[i-0].getText().equals(tiles[i-3].getText())&&tiles[i-1].getText().isEmpty() &&tiles[i-2].getText().isEmpty())
            {
               tiles[i-0].setText(String.format("%d", (2* Integer.parseInt((tiles[i-3].getText())))));
               tiles[i-3].setText("");
               score+=Integer.parseInt(tiles[i].getText());
            }
         }
         if(!(tiles[i-1].getText().isEmpty()))
         {
            if(tiles[i-1].getText().equals(tiles[i-2].getText()))
            {		tiles[i-1].setText(String.format("%d", (2* Integer.parseInt((tiles[i-2].getText())))));
               tiles[i-2].setText("");
               score+=Integer.parseInt(tiles[i-1].getText());
            }
            else if(tiles[i-1].getText().equals(tiles[i-3].getText())&&tiles[i-2].getText().isEmpty())
            {	tiles[i-1].setText(String.format("%d", (2* Integer.parseInt((tiles[i-3].getText())))));
               tiles[i-3].setText("");	
               score+=Integer.parseInt(tiles[i-1].getText());
            }
         }
         if(!(tiles[i-2].getText().isEmpty()))
            if(tiles[i-2].getText().equals(tiles[i-3].getText()))
            {		tiles[i-2].setText(String.format("%d", (2* Integer.parseInt((tiles[i-3].getText())))));
               tiles[i-3].setText("");
               score+=Integer.parseInt(tiles[i-2].getText());
            }
      } 
   }
   //stacks tiles down
   public void stackTilesDown()
   {
      for(int i=12; i<16; i++)
      {
         if(!(tiles[i-0].getText().isEmpty()))
         {
            if(tiles[i-0].getText().equals(tiles[i-4].getText()))
            {
               tiles[i-0].setText(String.format("%d", (2* Integer.parseInt((tiles[i-4].getText())))));
               tiles[i-4].setText("");
               score+=Integer.parseInt(tiles[i].getText());
            }
            else if(tiles[i-0].getText().equals(tiles[i-8].getText()) &&tiles[i-4].getText().isEmpty())
            {	tiles[i-0].setText(String.format("%d", (2* Integer.parseInt((tiles[i-8].getText())))));
               tiles[i-8].setText("");
               score+=Integer.parseInt(tiles[i].getText());
            }
            else if(tiles[i-0].getText().equals(tiles[i-12].getText())&&tiles[i-4].getText().isEmpty() &&tiles[i-8].getText().isEmpty())
            {
               tiles[i-0].setText(String.format("%d", (2* Integer.parseInt((tiles[i-12].getText())))));
               tiles[i-12].setText("");
               score+=Integer.parseInt(tiles[i].getText());
            }
         }
         if(!(tiles[i-4].getText().isEmpty()))
         {
            if(tiles[i-4].getText().equals(tiles[i-8].getText()))
            {		tiles[i-4].setText(String.format("%d", (2* Integer.parseInt((tiles[i-8].getText())))));
               tiles[i-8].setText("");
               score+=Integer.parseInt(tiles[i-4].getText());
            	
            }
            else if(tiles[i-4].getText().equals(tiles[i-12].getText())&&tiles[i-8].getText().isEmpty())
            {	tiles[i-4].setText(String.format("%d", (2* Integer.parseInt((tiles[i-12].getText())))));
               tiles[i-12].setText("");	
               score+=Integer.parseInt(tiles[i-4].getText());
            }
         }
         if(!(tiles[i-8].getText().isEmpty()))
            if(tiles[i-8].getText().equals(tiles[i-12].getText()))
            {		tiles[i-8].setText(String.format("%d", (2* Integer.parseInt((tiles[i-12].getText())))));
               tiles[i-12].setText("");
               score+=Integer.parseInt(tiles[i-8].getText());
            }
      }		
   }
   //stacks tiles going up
   public void stackTilesUp()
   {
      for(int i=0; i<4; i++)
      {
         if(!(tiles[i+0].getText().isEmpty()))
         {
            if(tiles[i+0].getText().equals(tiles[i+4].getText()))
            {
               tiles[i+0].setText(String.format("%d", (2* Integer.parseInt((tiles[i+4].getText())))));
               tiles[i+4].setText("");
               score+=Integer.parseInt(tiles[i+0].getText());
            }
            else if(tiles[i+0].getText().equals(tiles[i+8].getText()) &&tiles[i+4].getText().isEmpty())
            {	tiles[i+0].setText(String.format("%d", (2* Integer.parseInt((tiles[i+8].getText())))));
               tiles[i+8].setText("");
               score+=Integer.parseInt(tiles[i+0].getText());
            }
            else if(tiles[i+0].getText().equals(tiles[i+12].getText())&&tiles[i+4].getText().isEmpty() &&tiles[i+8].getText().isEmpty())
            {
               tiles[i+0].setText(String.format("%d", (2* Integer.parseInt((tiles[i+12].getText())))));
               tiles[i+12].setText("");
               score+=Integer.parseInt(tiles[i+0].getText());
            }
         }
         if(!(tiles[i+4].getText().isEmpty()))
         {
            if(tiles[i+4].getText().equals(tiles[i+8].getText()))
            {		tiles[i+4].setText(String.format("%d", (2* Integer.parseInt((tiles[i+8].getText())))));
               tiles[i+8].setText("");
               score+=Integer.parseInt(tiles[i+4].getText());
            }
            else if(tiles[i+4].getText().equals(tiles[i+12].getText())&&tiles[i+8].getText().isEmpty())
            {	tiles[i+4].setText(String.format("%d", (2* Integer.parseInt((tiles[i+12].getText())))));
               tiles[i+12].setText("");	
               score+=Integer.parseInt(tiles[i+4].getText());
            }
         }
         if(!(tiles[i+8].getText().isEmpty()))
            if(tiles[i+8].getText().equals(tiles[i+12].getText()))
            {		tiles[i+8].setText(String.format("%d", (2* Integer.parseInt((tiles[i+12].getText())))));
               tiles[i+12].setText("");
               score+=Integer.parseInt(tiles[i+8].getText());
            }
      }		
   }
   //moves the board to the left
   //all tiles are moved as far left as they can go 
   public void moveTilesLeft()
   {
      int i=0;
      for(int j=0;j<4;j++)
      {
         if(j==0)
            i=0;
         else if(j==1)
            i=4;
         else if(j==2)
            i=8;
         else if(j==3)
            i=12;
      	
         if(tiles[i].getText().equals(""))
         {
            if(!(tiles[i+1].getText().isEmpty()))
            {
               tiles[i+0].setText(tiles[i+1].getText());
               tiles[i+1].setText("");
            }
            else if(!(tiles[i+2].getText().isEmpty()))
            {
               tiles[i+0].setText(tiles[i+2].getText());
               tiles[i+2].setText("");
            }
            else if(!(tiles[i+3].getText().isEmpty()))
            {
               tiles[i+0].setText(tiles[i+3].getText());
               tiles[i+3].setText("");
            }
         }
         
         else if(tiles[i+1].getText().equals(""))
         {
            if(!(tiles[i+2].getText().isEmpty()))
            {
               tiles[i+1].setText(tiles[i+2].getText());
               tiles[i+2].setText("");
            }
            else if(!(tiles[i+3].getText().isEmpty()))
            {
               tiles[i+1].setText(tiles[i+3].getText());
               tiles[i+3].setText("");
            }
         }
         else if(tiles[i+2].getText().equals(""))
         {
            if(!(tiles[i+3].getText().isEmpty()))
            {
               tiles[i+2].setText(tiles[i+3].getText());
               tiles[i+3].setText("");
            }
         } 
      }
   }
   //all tiles moved right
   //all tiles are moved as far right as they can go 
   public void moveTilesRight()
   { 
      int i=0;
      for(int j=0; j<4;j++)
      {				
         if(j==0)
            i=3;
         else if(j==1)
            i=7;
         else if(j==2)
            i=11;
         else if(j==3)
            i=15;
      	
         if(tiles[i].getText().equals(""))
         {
         	
            if(!(tiles[i-1].getText().isEmpty()))
            {
               tiles[i-0].setText(tiles[i-1].getText());
               tiles[i-1].setText("");
            }
            else if(!(tiles[i-2].getText().isEmpty()))
            {
               tiles[i-0].setText(tiles[i-2].getText());
               tiles[i-2].setText("");
            }
            else if(!(tiles[i-3].getText().isEmpty()))
            {
               tiles[i-0].setText(tiles[i-3].getText());
               tiles[i-3].setText("");
            }
         }
         
         else if(tiles[i-1].getText().equals(""))
         {
            if(!(tiles[i-2].getText().isEmpty()))
            {
               tiles[i-1].setText(tiles[i-2].getText());
               tiles[i-2].setText("");
            }
            else if(!(tiles[i-3].getText().isEmpty()))
            {
               tiles[i-1].setText(tiles[i-3].getText());
               tiles[i-3].setText("");
            }
         }
         else if(tiles[i-2].getText().equals(""))
         {
            if(!(tiles[i-3].getText().isEmpty()))
            {
               tiles[i-2].setText(tiles[i-3].getText());
               tiles[i-3].setText("");
            }
         } 
      }
   }
   // move the tiles down
   //all tiles are moved as far down as they can go 
   
   public void moveTilesDown()
   {
      for(int i=12; i<16; i++)
      {
         if(tiles[i-0].getText().equals(""))
         {
            if(!(tiles[i-4].getText().isEmpty()))
            {
               tiles[i-0].setText(tiles[i-4].getText());
               tiles[i-4].setText("");
            }
            else if(!(tiles[i-8].getText().isEmpty()))
            {
               tiles[i-0].setText(tiles[i-8].getText());
               tiles[i-8].setText("");
            }
            else if(!(tiles[i-12].getText().isEmpty()))
            {
               tiles[i-0].setText(tiles[i-12].getText());
               tiles[i-12].setText("");
            }
         }
         else if(tiles[i-4].getText().equals(""))
         {
         
            if(!(tiles[i-8].getText().isEmpty()))
            {
               tiles[i-4].setText(tiles[i-8].getText());
               tiles[i-8].setText("");
            }
            else if(!(tiles[i-12].getText().isEmpty()))
            {
               tiles[i-4].setText(tiles[i-12].getText());
               tiles[i-12].setText("");
            }
         }
         else if(tiles[i-8].getText().equals(""))
         {
            if(!(tiles[i-12].getText().isEmpty()))
            {
               tiles[i-8].setText(tiles[i-12].getText());
               tiles[i-12].setText("");
            }
         }
      }
   }
   //moves the tiles up
   //all tiles are moved as far up as they can go 
   public void moveTilesUp()
   {
      for(int i=0; i<4; i++)
      {
         if(tiles[i+0].getText().equals(""))
         {
            if(!(tiles[i+4].getText().isEmpty()))
            {
               tiles[i+0].setText(tiles[i+4].getText());
               tiles[i+4].setText("");
            }
            else if(!(tiles[i+8].getText().isEmpty()))
            {
               tiles[i+0].setText(tiles[i+8].getText());
               tiles[i+8].setText("");
            }
            else if(!(tiles[i+12].getText().isEmpty()))
            {
               tiles[i+0].setText(tiles[i+12].getText());
               tiles[i+12].setText("");
            }
         }//ends first if
         else if(tiles[i+4].getText().equals(""))
         {
            if(!(tiles[i+8].getText().isEmpty()))
            {
               tiles[i+4].setText(tiles[i+8].getText());
               tiles[i+8].setText("");
            }
            else if(!(tiles[i+12].getText().isEmpty()))
            {
               tiles[i+4].setText(tiles[i+12].getText());
               tiles[i+12].setText("");
            }
         }
         else if(tiles[i+8].getText().equals(""))
         {
            if(!(tiles[i+12].getText().isEmpty()))
            {
               tiles[i+8].setText(tiles[i+12].getText());
               tiles[i+12].setText("");
            }
         }
      }
   }
   
   //checks if the game is over
   //when the 2048 tile is achieved the game is won
   public void winCondition()
   {
      for(int i=0; i<16;i++)
         if(tiles[i].getText().equals("2048"))
         {
        	 winTF.setPreferredSize(new Dimension(150,50));
             scoreTF.setPreferredSize(new Dimension(100,50));
             southP.add(resetB);
            southP.add(winTF);
            southP.add(scoreTF);
            southP.add(resetB);
            southP.add(exitB);
            
            northP.setVisible(false);
    
         }
   }
   //checks if the game is over
   //when there are no moves left the game has ended
   public void loseCondition()
   {
      if(
      			!((tiles[0].getText().equals(tiles[4].getText())))
      			&& !((tiles[1].getText().equals(tiles[5].getText())))
      			&& !((tiles[2].getText().equals(tiles[6].getText())))
      			&& !((tiles[3].getText().equals(tiles[7].getText())))
      			&& !((tiles[4].getText().equals(tiles[8].getText())))
      			&& !((tiles[5].getText().equals(tiles[9].getText())))
      			&& !((tiles[6].getText().equals(tiles[10].getText())))
      			&& !((tiles[7].getText().equals(tiles[11].getText())))
      			&& !((tiles[8].getText().equals(tiles[12].getText())))
      			&& !((tiles[9].getText().equals(tiles[13].getText())))
      			&& !((tiles[10].getText().equals(tiles[14].getText())))
      			&& !((tiles[11].getText().equals(tiles[15].getText())))
      
      	   && !((tiles[0].getText().equals(tiles[1].getText())))
      	   && !((tiles[1].getText().equals(tiles[2].getText())))
      	   && !((tiles[2].getText().equals(tiles[3].getText())))
      	   && !((tiles[4].getText().equals(tiles[5].getText())))
      	   && !((tiles[5].getText().equals(tiles[6].getText())))
      	   && !((tiles[6].getText().equals(tiles[7].getText())))
      	   && !((tiles[8].getText().equals(tiles[9].getText())))
      	   && !((tiles[9].getText().equals(tiles[10].getText())))
      	   && !((tiles[10].getText().equals(tiles[11].getText())))
      	   && !((tiles[12].getText().equals(tiles[13].getText())))
      	   && !((tiles[13].getText().equals(tiles[14].getText())))
      	   && !((tiles[14].getText().equals(tiles[15].getText())))
      			)
      {
         for(int f=0;f<16;f++)
         {
            if(tiles[f].getText().equals(""))
               return;
         }
         loseTF.setPreferredSize(new Dimension(150,50));
         scoreTF.setPreferredSize(new Dimension(100,50));
         southP.add(loseTF);
         southP.add(scoreTF);
         southP.add(exitB);
         southP.add(resetB);
         
         northP.setVisible(false);
      		//centerBoardP.setVisible(false);
      }
   }
}//end class
