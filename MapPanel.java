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
    ImageIcon background = new ImageIcon("Assets/Fields.png");
    
    private Player player;
    private Level location;
    private Enemy enemy;

    private final int WIDTH = 1000;
    private final int HEIGHT = 900;
    private final int SIZE = 25;


    private boolean [][] occupied = new boolean [WIDTH / SIZE][HEIGHT / SIZE];
    private boolean [][] special = new boolean [WIDTH / SIZE][HEIGHT / SIZE];

    private final int x = 0;
    private final int y = 1;

    private final int ENCOUNTERRATE = 25;
    private int encounterCheck = ENCOUNTERRATE;


    private boolean menu = false;
    private int cursorState = 0;

    private boolean dialoguebox = false;

    private int animation;

    MapPanel()
    {        
        this.setBounds(0, 100, WIDTH, HEIGHT);
        this.setBackground(Color.green);
        this.setFocusable(true);
        this.setVisible(true);
        this.addKeyListener(this);
    }
    public void setPlayer(Player player)
    {
        this.player = player;
        createLevel();
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }
    private void draw(Graphics g)
    {

        /* Images drawn */
        g.drawImage(background.getImage(), 0, 0, null);
        g.drawImage(playerIcon.getImage(), player.getPlayerPos()[0] * SIZE, player.getPlayerPos()[1] * SIZE, null);

        
        /*Font set */
        g.setFont(new Font("Courier New", Font.BOLD, 20));

        /* Menu */
        if (menu == true)
        {
            /* Menu box */
            g.setColor(Color.black);
            g.fillRect(460, 405, 80, 90);
            g.setColor(Color.white);
            g.drawRect(460, 405, 80, 90);
            
            g.drawString("Resume", 465, 425);
            g.drawString("Save", 465, 445);
            if (player.getMagicUnlock()[0] == true)
            {
                g.drawString("Heal", 465, 465);
            }
            else
            {
                g.drawString("????", 465, 465);
            }
            g.drawString("Quit", 465, 485);

            /* Cursor */
            int cursorYOffset = cursorState * 20;
            int[] xpoints = {545, 555, 555};
            int[] ypoints = {420 + cursorYOffset, 410 + cursorYOffset, 430 + cursorYOffset};
            g.fillPolygon(xpoints, ypoints, 3);
        }
        /* Dialogue Box */
        if (dialoguebox == true)
        {
            g.setColor(Color.black);
            g.fillRect(200, 200, 600, 60);
            g.setColor(Color.white);
            g.drawRect(200, 200, 600, 60);
            g.drawString(location.getDialogue(), 205, 220);
            g.drawString(location.getDialogue2(), 205, 240);
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
    /*Called to move the player, takes in an integer to determine how the player is moved. */
    private void movePlayer(int xy, int move)
    {
        int[] startPos = {player.getPlayerPos()[x], player.getPlayerPos()[y]}; //This temp variable takes in player's starting posistion before the move to easily move the player back if necessary
        player.getPlayerPos()[xy] = player.getPlayerPos()[xy] + move;
        if (player.getPlayerPos()[x] < 0 || player.getPlayerPos()[y] < 0 || player.getPlayerPos()[x] > WIDTH / SIZE - 1 || player.getPlayerPos()[y] > HEIGHT / SIZE - 1) //Keeps player in bounds
        {
            player.getPlayerPos()[x] = startPos[x];
            player.getPlayerPos()[y] = startPos[y];
        }
        else if (occupied[player.getPlayerPos()[x]][player.getPlayerPos()[y]] == true) //Checks if space is occupied
        {
            player.getPlayerPos()[x] = startPos[x];
            player.getPlayerPos()[y] = startPos[y];
        }
        else if (special[player.getPlayerPos()[x]][player.getPlayerPos()[y]] == true) // Checks for a special event
        {
            event(location.special(player.getPlayerPos()[x], player.getPlayerPos()[y]));
        }
        else
        {
            player.regen();
            if (player.getLevel() > 0)
            {
                encounter(); //encounter checker only runs if player successfully moves and player is not on tutorial level
            }
        }
    }
    /*Called after a successful move, determines if a random encounter is triggered */
    private void encounter()
    {
        Random rng = new Random();
        if (rng.nextInt(encounterCheck) + 1 == encounterCheck)
        {
            encounterCheck = ENCOUNTERRATE;
            enemy = location.RandomEncounter();
            animate();
        }
        else
        {
            encounterCheck--;
        }
    }
    /*Called when a combat is triggered. Starts combat */
    private void animate()
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
    /*Called when the enter key is pressed */
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
                if (player.getMagicUnlock()[0] == true)
                {
                    player.heal();
                }
                break;
            case 3: //Quit
                GoblinHunter2.close();
                break;
        }
    }
    /*Called when the save command is used, saves the player information to a save file */
    private void save() throws IOException
    {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Save.txt"));
        out.writeObject(player);
        out.close();
    }
    /*Called when the map is added, determines the map image and information based on the level from the player object */
    private void createLevel()
    {
        switch(player.getLevel())
        {
            case 0: //Tutorial
                location = new Tutorial(WIDTH / SIZE, HEIGHT / SIZE);
                break;
            case 1: //Fields
                location = new Fields(WIDTH / SIZE, HEIGHT / SIZE);
                break;
            case 2: //Cave
                location = new Cave(WIDTH / SIZE, HEIGHT / SIZE);
                break;
            case 3: //Castle
                location = new Castle(WIDTH / SIZE, HEIGHT / SIZE);
                break;
        }
        dialoguebox = false;
        background.setImage(location.getImageIcon().getImage());
        occupied = location.getOccupied();
        special = location.getSpecial();
        GoblinHunter2.game.repaint();
    }
    /*Called when stepping on to certain spots, calls an event based on the location's event number */
    private void event(int eventNumber)
    {
        switch (eventNumber)
        {
            case 0: //Dialogue
                dialoguebox = true;
                break;
            case 1: //Baby Goblin
                if (player.getBossKills()[0] == false)
                {
                    enemy = new BabyGoblin();
                    animate();
                    player.setBossKills(0);
                }
                break;
            case 2: //Tutorial Exit
                player.setLevel(1);
                player.fullheal();
                player.getPlayerPos()[x] = 20;
                player.getPlayerPos()[y] = 30;
                createLevel();
                break;
            case 3: //Bridge Goblin
                if (player.getBossKills()[1] == false)
                {
                    enemy = new BridgeGoblin();
                    animate();
                    player.setBossKills(1);
                }
                break;
            case 4: //Entering Cave
                player.setLevel(2);
                player.getPlayerPos()[x] = 4;
                player.getPlayerPos()[y] = 34;
                createLevel();
                break;
            case 5: //Shop
                GoblinHunter2.shop();
                break;
            case 6: //Exiting Cave to Fields
                player.setLevel(1);
                player.getPlayerPos()[x] = 4;
                player.getPlayerPos()[y] = 5;
                createLevel();
                break;
            case 7: //Healing Fountain
                player.fullheal();
                dialoguebox = true;
                break;
            case 8: //Rock Goblin
                if (player.getBossKills()[2] == false)
                {
                    enemy = new RockGoblin();
                    animate();
                    player.setBossKills(2);
                }
                break;
            case 9: //Exiting Cave to Castle
                player.setLevel(3);
                player.getPlayerPos()[x] = 19;
                player.getPlayerPos()[y] = 34;
                createLevel();
                break;
            case 10: //Exiting Castle to Cave
                player.setLevel(2);
                player.getPlayerPos()[x] = 33;
                player.getPlayerPos()[y] = 1;
                createLevel();
                break;
            case 11: //Goblin King
                if (player.getBossKills()[3] == false)
                {
                    enemy = new GoblinKing();
                    animate();
                    player.setBossKills(3);
                    event(0);
                }
                break;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {} //Not implemented
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
            switch(e.getKeyCode())
            {
                //Enter or Escape
                case 10:
                case 27:
                    dialoguebox = false;
                    break;
            }
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
    public void keyReleased(KeyEvent e) {} //Not implemented
}
