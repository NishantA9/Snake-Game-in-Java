import javax.swing.*;

public class GameFrame extends JFrame {

    //creating constructor
    GameFrame(){
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        //to show the game in middle
        this.setLocationRelativeTo(null);
    }
}
