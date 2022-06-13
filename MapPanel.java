import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import Levels.*;
import Enemies.*;

public class MapPanel extends JPanel implements KeyListener
{
    ImageIcon playerIcon = new ImageIcon("Assets/Hero.png");
    ImageIcon shopIcon = new ImageIcon("Assets/Shop.png");
    ImageIcon background = new ImageIcon("Assets/Fields.png");
    
    private final int WIDTH = 1000;
    private final int HEIGHT = 900;
    private final int SIZE = 25;

    Player player;
    private Level location;
    int level = 1;
    boolean [][] occupied = new boolean [WIDTH / SIZE][HEIGHT / SIZE];
    boolean [][] special = new boolean [WIDTH / SIZE][HEIGHT / SIZE];

    int[] shopPos = {20, 19};
    int[] playerPos;
    int x = 0;
    int y = 1;

    int encounterRate = 20;
    int encounterCheck = encounterRate;
    Enemy enemy;

    boolean menu = false;
    private int cursorState = 0;

    boolean dialoguebox = false;
    private String dialogue;

    int animation;

    MapPanel()
    {        
        this.setBounds(0, 100, WIDTH, HEIGHT);
        this.setBackground(Color.green);
        this.setFocusable(true);
        this.setVisible(true);
        this.addKeyListener(this);
        createLevel();
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

        /* Images drawn */
        g.drawImage(background.getImage(), 0, 0, null);
        g.drawImage(playerIcon.getImage(), playerPos[0] * SIZE, playerPos[1] * SIZE, null);
        g.drawImage(shopIcon.getImage(), shopPos[0] * SIZE, shopPos[1] * SIZE, null);


        /* Collision */
        g.setFont(new Font("Courier New", Font.BOLD, 10));

        for(int i = 0; i < WIDTH / SIZE; i++)
        {
            for (int j = 0; j < HEIGHT / SIZE; j++)
            {
                g.setColor(Color.white);
                g.drawString(i + "", i * SIZE, j * SIZE - 10 + SIZE);
                g.drawString(j + "", i * SIZE, j * SIZE + SIZE); 
                if (occupied[i][j] == true)
                {
      //              g.drawImage(boulderIcon.getImage(), i * SIZE, j * SIZE, null); // In the future collision will be done via background image
                }
            }
        }

        /* Menu */
        if (menu == true)
        {
            /* Menu box */
            g.setColor(Color.black);
            g.fillRect(460, 405, 80, 90);
            g.setColor(Color.white);
            g.drawRect(460, 405, 80, 90);
            
            g.setFont(new Font("Courier New", Font.BOLD, 20));
            g.drawString("Resume", 465, 425);
            g.drawString("Save", 465, 445);
            g.drawString("Heal", 465, 465);
            g.drawString("Quit", 465, 485);

            /* Cursor */
            int cursorYOffset = cursorState * 20;
            int[] xpoints = {545, 555, 555};
            int[] ypoints = {420 + cursorYOffset, 410 + cursorYOffset, 430 + cursorYOffset};
            g.fillPolygon(xpoints, ypoints, 3);
        }

        /* Combat starting animation */
        for(int i = 0; i < animation; i++)
        {
            g.setColor(Color.black);
            g.drawLine(i, 0, WIDTH - i, HEIGHT);
            g.drawLine(0, HEIGHT - i, WIDTH, i);
        } 
        this.repaint();
    }
    private void movePlayer(int xy, int move)
    {
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
        else if (special[playerPos[x]][playerPos[y]] == true) // Checks for a special event
        {
            event(location.special(playerPos[x], playerPos[y]));
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
            enemy = location.RandomEncounter();
            animate();
        }
        else
        {
            encounterCheck--;
        }
    }
    public void animate()
    {
        GoblinHunter2.game.removeKeyListener(this);
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
                    animation = 0;
                    timer.cancel();
                    GoblinHunter2.combat(enemy);
                }
            }   
        };
        timer.scheduleAtFixedRate(task, 0, 1);
    }
    private void enter()
    {
        switch (cursorState)
        {
            case 0: //Resume
                menu = false;
                break;
            case 1: //Save
                try 
                {
                    save();
                } 
                catch (Exception e) 
                {
                    System.out.println(e);
                }
                break;
            case 2: //Heal
            case 3: //Quit
                GoblinHunter2.close();
                break;
        }
    }
    private void save() throws IOException
    {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Save.txt"));
        out.writeObject(player);
        out.close();
    }
    private void createLevel()
    {
        switch(level)
        {
            case 0: //Tutorial
                break;
            case 1: //Fields
                for (int i = 0; i < WIDTH / SIZE; i++)
                {
                    background.setImage(new ImageIcon("Assets/Fields.png").getImage());
                    location = new Fields(WIDTH / SIZE, HEIGHT / SIZE);
                    occupied = location.getOccupied();
                    special = location.getSpecial();
                }
                break;
            case 2: //Tunnel
                break;
            case 3: //Castle
                break;
        }
    }
    private void event(int eventNumber)
    {
        switch (eventNumber)
        {
            case 0: //Dialogue
                dialoguebox = true;
                dialogue = location.getDialogue();
                break;
            case 1: //Baby Goblin
                break;
            case 2: //Tutorial Exit
                level = 1;
                createLevel();
                break;
            case 3: //Bridge Goblin
                enemy = new BridgeGoblin();
                animate();
                break;
            case 4: //Entering Cave
                level = 2;
                createLevel();;
            case 5: //Exiting Cave to Fields
                level = 1;
                createLevel();
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) 
    {
        if (menu == true) //Menu controls
        {
            switch(e.getKeyCode())
            {
                //Enter
                case 10:
                    enter();
                    break;
                //Up or W
                case 38:
                case 87:
                    if (cursorState > 0)
                    {
                        cursorState--;
                    }
                    else
                    {
                        cursorState = 3;
                    }
                    break;
                //Down or S
                case 83:
                case 40:
                    if (cursorState < 3)
                    {
                        cursorState++;
                    }
                    else
                    {
                        cursorState = 0;
                    }
                    break;
                //Escape
                case 27:
                    menu = false;
                    cursorState = 0;
                    break;
            }
        }
        else if (dialoguebox == true) // Dialoguebox
        {

        }
        else // Movement
        {
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
                //Escape
                case 27:
                    menu = true;
                    break;
            }
        }
        this.repaint();
    }
    @Override
    public void keyReleased(KeyEvent e) {}
}
