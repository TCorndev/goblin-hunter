import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MapPanel extends JPanel implements KeyListener
{
    ImageIcon playerIcon = new ImageIcon("Test.png");
    ImageIcon shopIcon = new ImageIcon();
    private final int WIDTH = 1000;
    private final int HEIGHT = 900;
    private final int SIZE = 25;

    Player player;
    boolean [][] occupied = new boolean [WIDTH / SIZE][HEIGHT / SIZE];
    int[] shopPos = {20, 19};
    int[] playerPos;
    int x = 0;
    int y = 1;

    int encounterRate = 20;
    int encounterCheck = encounterRate;

    int animation;

    int level = 1;
    MapPanel()
    {        
        this.setBounds(0, 100, WIDTH, HEIGHT);
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setVisible(true);
        this.addKeyListener(this);
    }
    public void setPlayer(Player player)
    {
        this.player = player;
        playerPos = player.getPlayerPos();
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g)
    {
      /*  for(int i = 0; i < WIDTH/SIZE; i++)
        {
            g.drawLine(0, i*SIZE, WIDTH, i*SIZE);
            g.setColor(Color.white);
        } */
        g.drawImage(playerIcon.getImage(), playerPos[0] * SIZE, playerPos[1] * SIZE, null);
        for(int i = 0; i < animation; i++)
        {
            g.setColor(Color.blue);
            g.drawLine(i, 0, WIDTH - i, HEIGHT);
            g.drawLine(0, HEIGHT - i, WIDTH, i);
        } 
        this.repaint();
    }
    private void movePlayer(int xy, int move)
    {
        occupied[10][10] = true;
        int[] startPos = {playerPos[x], playerPos[y]}; //This temp variable takes in player's starting posistion before the move to easily move the player back if necessary
        playerPos[xy] = playerPos[xy] + move;
        if (playerPos[x] < 0 || playerPos[y] < 0 || playerPos[x] > WIDTH / SIZE - 1 || playerPos[y] > HEIGHT / SIZE - 1) //Keeps player in bounds
        {
            playerPos = startPos;
        }
        else if (occupied[playerPos[x]][playerPos[y]] == true) //Checks if space is occupied
        {
            playerPos = startPos;
        }
        else if (playerPos[x] == shopPos[x] && playerPos[y] == shopPos[y]) //Checks if space has a shop
        {
            playerPos = startPos;
            GoblinHunter2.shop();
        }
        else
        {
            encounter(); //encounter checker only runs if player successfully moves
        }
    }
    public void encounter()
    {
        System.out.println("Check for encounters...");
        Random rng = new Random();
        if (rng.nextInt(encounterCheck) + 1 == encounterCheck)
        {
            System.out.println("Goblin attack!");
            encounterCheck = encounterRate;
            GoblinHunter2.game.removeKeyListener(this);
            animate();
        }
        else
        {
            encounterCheck--;
        }
    }
    public void animate()
    {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() 
        {
          public void run()
          {
            if (animation < WIDTH)
            {
                animation++;
            }
            else
            {
                startCombat();
                animation = 0;
                timer.cancel();
            }
        }   
    };
    timer.scheduleAtFixedRate(task, 0, 1);
}
    public void startCombat()
    {
    GoblinHunter2.combat(level);
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode())
        {
            //Left or A
            case 37:
            case 65:
                movePlayer(x, -1);
                break;
            //Up or W    
            case 38:
            case 87:
                movePlayer(y, -1);
                break;
            //Right or D
            case 68:
            case 39:
                movePlayer(x, 1);
                break;
            //Down or S
            case 83:
            case 40:
                movePlayer(y, 1);
                break;
            }
        this.repaint();
    }
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
}
