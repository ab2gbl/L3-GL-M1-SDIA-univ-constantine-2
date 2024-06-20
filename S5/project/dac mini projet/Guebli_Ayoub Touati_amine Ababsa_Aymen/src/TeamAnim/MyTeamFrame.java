package TeamAnim;

import javax.swing.*;

public class MyTeamFrame extends JFrame{

    MyTeamPanel panel;

    public MyTeamFrame(){

        panel = new MyTeamPanel();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}