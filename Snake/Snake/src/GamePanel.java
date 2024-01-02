import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.util.Random;
//import java.util.Timer;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;

    //ARRAYS TO HOLD THE BODY PART OF SNAKES, x is going to hold the head of the snake
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    //initial body of the snake
    int bodyParts = 6;
    int applesEaten;
    // x and y position of apple
    int appleX;
    int appleY;
    // the snake will go right when game begins
    char direction ='R';
    boolean running = false;
    Timer timer;
    Random random;

    //creating constructor
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    //creating some methods
    public void startGame(){
        newApple();
        running=true;
        timer = new Timer(DELAY,this);
        timer.start();
    }

    public void paintComponent(Graphics g){
    super.paintComponent(g);
    draw(g);
    }

    public void draw(Graphics g){

       if(running){
           //TO CREATE THE GRID SO THAT THE ITEMS TAKES A SPECIFIC GRID.
           for(int i =0; i<SCREEN_HEIGHT/UNIT_SIZE; i++){
               g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
               g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
           }
           //draw the apple
           g.setColor(Color.red);
           g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);

           //draw the head of the snake and body
           for(int i =0; i < bodyParts ; i++){
               if(i==0){
                   g.setColor(Color.green);
                   g.fillRect(x[i], y[i],UNIT_SIZE,UNIT_SIZE);
               }//BODY OF THE SNAKE
               else {
                   g.setColor(new Color(45,180,0));
                   g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                   g.fillRect(x[i], y[i],UNIT_SIZE,UNIT_SIZE);
               }
           }
           //setting the score on game screen
           g.setColor(Color.red);
           g.setFont(new Font("Ink Free", Font.BOLD, 40));
           FontMetrics metrics = getFontMetrics(g.getFont());
           g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());

       }else{
           //gameOver method
           gameOver(g);
       }
    }

    public void newApple(){
        //to place the apple
    appleX = random.nextInt((int) (SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    public void move(){
    //MOVING THE SNAKE
        for(int i = bodyParts; i > 0; i--){
            //shifting the body parts
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction){
            case  'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case  'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case  'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case  'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkApple(){
        //TO GRAB THE APPLE
        if((x[0] == appleX) && (y[0] == appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions(){
        //checks if head collides with body
        for (int i = bodyParts; i > 0; i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }
        //checks if head touches left border
        if(x[0] < 0){
            running = false;
        }
        //checks if head touches right border
        if(x[0] > SCREEN_WIDTH){
            running = false;
        }
        //checks if head touches top border
        if(y[0] < 0){
            running = false;
        }
        //checks if head touches bottom border
        if(y[0] > SCREEN_HEIGHT){
            running = false;
        }
        if(!running){
            timer.stop();
        }
    }

    public void gameOver(Graphics g){
        //score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());

        //Game over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2 );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    //creating inner class
    public  class MyKeyAdapter extends KeyAdapter{

        @Override
        public  void keyPressed(KeyEvent e){
            //to control the snake
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                if(direction !='R'){
                    direction = 'L';
                }
                break;
                case KeyEvent.VK_RIGHT:
                    if(direction !='L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction !='D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction !='U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
