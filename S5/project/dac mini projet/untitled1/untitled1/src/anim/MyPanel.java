package anim;

import java.awt.*;

import java.awt.event.*;
import javax.swing.*;

public class MyPanel extends JPanel implements ActionListener{

    final int PANEL_WIDTH = 200000;
    final int PANEL_HEIGHT = 200000;
    Image ball;
    //Image backgroundImage;
    Image player0;
    Image player1;
    Image player2;
    Image player3;
    Image player4;
    Image player5;
    Image player6;
    Image player7;
    Image corb ;
    Image corb1;

    Timer timer;
    int xVelocity = 1;
    int iVel = 1;
    int x = 180;
    int y = 180;
    int i = 500;
    int z = 500;

    MyPanel(){
        this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        this.setBackground(Color.white);
        ball = new ImageIcon("src\\anim\\ball.png").getImage();
        corb = new ImageIcon("src\\anim\\corb.png").getImage();
        corb1 = new ImageIcon("src\\anim\\corb.png").getImage();

        player0 = new ImageIcon("src\\anim\\a.png").getImage();
        player1 = new ImageIcon("src\\anim\\a.png").getImage();
        player2 = new ImageIcon("src\\anim\\a.png").getImage();
        player3 = new ImageIcon("src\\anim\\a.png").getImage();
        player4 = new ImageIcon("src\\anim\\a.png").getImage();
        player5 = new ImageIcon("src\\anim\\a.png").getImage();
        player6 = new ImageIcon("src\\anim\\a.png").getImage();
        player7 = new ImageIcon("src\\anim\\a.png").getImage();

        //backgroundImage = new ImageIcon("space.png").getImage();

        timer = new Timer(1, this);
        timer.start();
    }
@Override
    public void paint(Graphics g) {

        super.paint(g); // paint background

        Graphics2D g2D = (Graphics2D) g;

        //g2D.drawImage(backgroundImage, 0, 0, null);
        g2D.drawImage(ball, x, y, null);
    g2D.drawImage(corb, 0, 190, null);
    g2D.drawImage(player0, 100, 100, null);
    g2D.drawImage(player1, 400, 100, null);
    g2D.drawImage(player2, 700, 100, null);
    g2D.drawImage(player3, 1000, 100, null);

    g2D.drawImage(ball, x, y, null);
    g2D.drawImage(corb1, 0,490 , null);
    g2D.drawImage(player4, 100, 400, null);
    g2D.drawImage(player5, 400, 400, null);
    g2D.drawImage(player6, 700, 400, null);
    g2D.drawImage(player7, 1000, 400, null);
    g2D.drawImage(ball, i, z, null);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
   if (x>= 1000-ball.getHeight(null) || x < 1 ) {
       if(i>= 700-ball.getHeight(null) || i < 1 ){
           xVelocity = xVelocity * -1;
       }
   }

        x = x + xVelocity;
        i = x +xVelocity;


        repaint();
    }

}