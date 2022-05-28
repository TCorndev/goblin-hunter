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
    ImageIcon icon = new ImageIcon("Test.png");
    final int WIDTH = 1000;
    final int HEIGHT = 900;
    final int SIZE = 25;

    int[] playerPos = new int[] {20, 18}; //Initial Player Starting Position

    int encounterRate = 20;
    int encounterCheck = encounterRate;

    int animation;

    int level = 0;
    MapPanel()
    {        
        this.setBounds(0, 100, WIDTH, HEIGHT);
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setVisible(true);
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
        g.drawImage(icon.getImage(), playerPos[0] * SIZE, playerPos[1] * SIZE, null);
        for(int i = 0; i < animation; i++)
        {
            g.setColor(Color.blue);
            g.drawLine(i, 0, WIDTH - i, HEIGHT);
            g.drawLine(0, HEIGHT - i, WIDTH, i);
        } 
        this.repaint();
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
                GoblinHunter2.combat();
                animation = 0;
                timer.cancel();
            }
          }   
        };
        timer.scheduleAtFixedRate(task, 0, 1);
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode())
        {
            case 37:
            case 65:
                if (playerPos[0] > 0)
                {
                    playerPos[0]--;
                    encounter();
                }
                break;
            case 38:
            case 87:
                if (playerPos[1] > 0)
                {
                    playerPos[1]--;
                    encounter();
                }
                break;
            case 68:
            case 39:
                if (playerPos[0] < WIDTH / SIZE - 1)
                {
                    playerPos[0]++;
                    encounter();
                }
                break;
            case 83:
            case 40:
                if (playerPos[1] < HEIGHT / SIZE - 1)
                {
                    playerPos[1]++;
                    encounter();
                }
                break;
            }
        this.repaint();
    }
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
}
