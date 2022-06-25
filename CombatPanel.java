import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Enemies.Enemy;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CombatPanel extends JPanel implements KeyListener
{
    private final int WIDTH = 1000;
    private final int HEIGHT = 900;

    private final int COMBATBOXWIDTH = 80;
    private final int COMBATBOXHEIGHT = 90;

    private final int THRESHOLD = 1000;

    private final int ENEMYSPRITEX = 450;
    private final int ENEMYSPRITEY = 500;
    private int cursorState;
    /* Combat variables*/
    private Enemy enemy;
    private boolean playerTurn = false;
    private boolean defend = false;
    private boolean shieldactive = false;
    private int hastevalue = 0;
    private int hasteduration;
    private Random rng = new Random();
    private boolean endCombat = false;
    private boolean gameover = false;
    /* Spell costs */
    private int healcost = 3;
    private int firecost = 10;
    private int shieldcost = 5;
    private int hastecost = 20;

    /* Animation variables */
    private int animationnumber;
    private HashMap<String, Boolean> animations = new HashMap<>();

    private String dialogue[] = new String[4];
    //private Boolean enemyTurn = false; //NYI
    private boolean magic = false;
    
    private int playerActionThreshold = THRESHOLD;
    private int enemyActionThreshold = THRESHOLD;
    
    /* Player Information */
    private Player player;
    private HashMap<String, Integer> stats;
    
    CombatPanel()
    {
        this.setBounds(0, 100, WIDTH, HEIGHT);
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setVisible(true);
        this.setLayout(null);

        animations.put("Attack", false);
        animations.put("Heal", false);
        animations.put("Fire", false);
        animations.put("Shield", false);
        animations.put("Haste", false);
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    } 
    private void draw(Graphics g)
    {
        g.drawImage(enemy.getBackground(player.getLevel()).getImage(), 0, 0, null);
        g.setFont(new Font("Courier New", Font.BOLD, 20)); //Font

        /* Enemy Sprite */
        if (endCombat == false)
        {
            g.drawImage(enemy.getSprite().getImage(), ENEMYSPRITEX, ENEMYSPRITEY, null);
        }

        /* Shield animation */
        if (shieldactive == true)
        {
            int latice = 50;
            if (player.getResonace() == true)
            {
                g.setColor(Color.cyan);
            }
            else
            {
                g.setColor(Color.blue);
            }
            for(int i = 0; i < WIDTH/latice; i++)
            {
                g.drawLine(0, i*latice, WIDTH, i*latice);
                g.drawLine(i*latice, 0, i*latice, HEIGHT);
            } 
        }

        /* Haste animation */
        if (animations.get("Haste") == true && animationnumber > 0)
        {
            ImageIcon[] haste = new ImageIcon[8];
            for (int i = 0; i < haste.length; i++)
            {
                haste[i] = new ImageIcon("Assets/Haste/haste_" + i + ".png");
            }
            g.drawImage(haste[animationnumber - 1].getImage(), 0, 0, null);
        }

        /* Player action box */
        g.setColor(Color.black);
        g.fillRect(450, 800, COMBATBOXWIDTH, COMBATBOXHEIGHT);
        g.setColor(Color.white);
        g.drawRect(450, 800, COMBATBOXWIDTH, COMBATBOXHEIGHT);
        g.drawString("Attack", 455, 820);
        g.drawString("Magic", 455, 840);
        g.drawString("Defend", 455, 860);
        g.drawString("Flee", 455, 880);

        if (magic == true)
        {
            /* Magic box */
            g.setColor(Color.black);
            g.fillRect(540, 800, COMBATBOXWIDTH, COMBATBOXHEIGHT);
            g.setColor(Color.white);
            g.drawRect(540, 800, COMBATBOXWIDTH, COMBATBOXHEIGHT);
            if (player.getMagicUnlock()[0] == true)
            {
                g.drawString("Heal", 545, 820);
            }
            if (player.getMagicUnlock()[1] == true)
            {
                g.drawString("Fire",545 , 840);
            }
            if (player.getMagicUnlock()[2] == true)
            {
                g.drawString("Shield", 545, 860);
            }
            if (player.getMagicUnlock()[3] == true)
            {
                g.drawString("Haste", 545, 880);
            }
        }
        /* Cursor */
        if (playerTurn == true)
        {
            int cursorYOffset = cursorState * 20;
            int cursorXOffset = 0;
            if (magic == true)
            {
                cursorXOffset = 85;
            }
            int[] xpoints = {535 + cursorXOffset, 545 + cursorXOffset, 545 + cursorXOffset};
            int[] ypoints = {815 + cursorYOffset, 825 + cursorYOffset, 805 + cursorYOffset};
            g.fillPolygon(xpoints, ypoints, 3);
        }

        /* Dialogue Box */
        int dialogueOffset = 25;
        g.setColor(Color.black);
        g.fillRect(200, 100, 600, 100);
        g.setColor(Color.white);
        g.drawRect(200, 100, 600, 100);
        for(int i = 0; i < dialogue.length; i++)
        {
            if (dialogue[i] != null)
            {
                g.drawString(dialogue[i], 205, 195 - dialogueOffset * i);
            }
        }

        /* Attack animation */
        if (animations.get("Attack") == true && animationnumber > 0)
        {
            ImageIcon[] slash = new ImageIcon[5];
            for (int i = 0; i < slash.length; i++)
            {
                slash[i] = new ImageIcon("Assets/Slash/slash_" + i + ".png");
            }
            g.drawImage(slash[animationnumber - 1].getImage(), ENEMYSPRITEX, ENEMYSPRITEY, null);
        }

        /* Heal animation */
        if (animations.get("Heal") == true && animationnumber > 0)
        {
            ImageIcon[] heal = new ImageIcon[5];
            for (int i = 0; i < heal.length; i++)
            {
                heal[i] = new ImageIcon("Assets/Heal/heal_" + i + ".png");
            }
            g.drawImage(heal[animationnumber - 1].getImage(), 0, 200, null);
            g.drawImage(heal[animationnumber - 1].getImage(), 0, 800, null);
            g.drawImage(heal[animationnumber - 1].getImage(), 900, 200, null);
            g.drawImage(heal[animationnumber - 1].getImage(), 900, 800, null);
        }

        /* Fire animation */
        if (animations.get("Fire") == true && animationnumber > 0)
        {
            ImageIcon[] fire = new ImageIcon[5];
            for (int i = 0; i < fire.length; i++)
            {
                fire[i] = new ImageIcon("Assets/Fire/fire_" + i + ".png");
            }
            g.drawImage(fire[animationnumber - 1].getImage(), ENEMYSPRITEX, ENEMYSPRITEY, null);
        }

    }
    public void setPlayer(Player player)
    {
        this.player = player;
        this.stats = player.getStats();
        this.gameover = false;
    }
    public void setup(Enemy enemy)
    {
        this.enemy = enemy;
        clearDialogue();
        updateDialogue("A " + enemy.getName() + " has appeared!");
        this.shieldactive = false;
        this.hastevalue = 0;
        this.hasteduration = 0;
        this.playerActionThreshold = THRESHOLD;
        this.enemyActionThreshold = THRESHOLD;
        this.endCombat = false;
        startCombat();
    }
    public void startCombat()
    {
        cursorState = 0;
        player.statcheck();
        magic = false;
        GoblinHunter2.game.repaint();
        if (enemy.getHP() <= 0)
        {
            enemydefeated();
        }
        else
        {
            Timer timer = new Timer();
            TimerTask task = new TimerTask() 
            {
                @Override
                public void run() 
                {
                    playerActionThreshold = playerActionThreshold - stats.get("Speed") - hastevalue;
                    if (playerActionThreshold <= 0)
                    {
                        playerTurn = true;
                        player.regen();
                        checkhaste();
                        defend = false;
                        playerActionThreshold = playerActionThreshold + THRESHOLD;
                        timer.cancel();
                        GoblinHunter2.game.repaint();
                    }
                    enemyActionThreshold = enemyActionThreshold - enemy.getSpeed();
                    if (enemyActionThreshold <= 0)
                    {
                        enemyActionThreshold = enemyActionThreshold + THRESHOLD;
                        enemyAttack(timer);
                        //timer.cancel();
                        GoblinHunter2.game.repaint(); //Normally animations will cover this repaint
                    }
                }
            };
            timer.scheduleAtFixedRate(task, 0, 1);
        }
    }
    public void checkhaste()
    {
        if (hasteduration > 0)
        {
            hasteduration--;
            System.out.println("ops");
        }
        else if (hastevalue > 0)
        {
            hastevalue = 0;
            updateDialogue("You haste spell has worn off...");
        }
    }
    private void enemyAttack(Timer timer)
    {
        int shieldValue = 1;
        if (defend == true)
        {
            shieldValue = shieldValue + player.getShieldLevel();
        }
        int damage = enemy.Action();
        if (damage <= 0)
        {
            updateDialogue(enemy.getDialogue()); //Special dialogue, called whenever an enemy uses a special power(healing, enraging, etc)
        }
        else
        {
            damage = damage - stats.get("DEF") * shieldValue; //Reduce damage by player's defence value, multiple by shield value(if blocking)
            if (damage < 0)
            {
                damage = 0;
            }
            updateDialogue(enemy.getName() + " attacks for " + damage + " damage!"); //Default enemy attacking dialogue
            if (shieldactive == true) //Change this into a method due to modifiers
            {
                if (player.getResonace() == true)
                {
                    damage = damage * -1;
                }
                else
                {
                    damage = 0;
                }
                updateDialogue("Your shield spell absorbs the blow!");
                shieldactive = false;
            }
        }
        stats.replace("HP", stats.get("HP") - damage);
        if (stats.get("HP") <= 0)
        {
            stats.replace("HP", 0);
            updateDialogue("You fall before " + enemy.getName() + "...");
            gameover = true;
            playerTurn = false;
            timer.cancel();
        }
    }
    private void attack()
    {
        playerTurn = false;
        int damage = (rng.nextInt(stats.get("MaATK") - stats.get("MiATK") + 1) + stats.get("MiATK")) * player.getSwordLevel();
        enemy.setHP(enemy.getHP() - damage);
        animation("Attack", 5);
        updateDialogue("You attack for " + damage + " damage!");
        if (player.getBloodthirst() == true)
        {
            int heal = (int)(damage / 4);
            updateDialogue("And heal for " + heal + " HP!");
            stats.replace("HP", stats.get("HP") + heal);
            player.statcheck();
        }
    }
    private void heal()
    {
        int length = 5;
        int heal = rng.nextInt(stats.get("MaSOL") - stats.get("MiSOL") + 1) + stats.get("MiSOL");
        stats.replace("HP", stats.get("HP") + heal);
        stats.replace("MP", stats.get("MP") - healcost);
        animation("Heal", length);
        updateDialogue("You restore " + heal + " HP!");
        player.statcheck();
    }
    private void fire()
    {
        int length = 5;
        int damage = (rng.nextInt(stats.get("MaSOL") - stats.get("MiSOL") + 1) + stats.get("MiSOL")) * player.getFireMultiplier();
        stats.replace("MP", stats.get("MP") - firecost);
        enemy.setHP(enemy.getHP() - damage);
        animation("Fire", length);
        updateDialogue("You ignite " + enemy.getName() + " for " + damage + " damage!");
    }
    private void shield()
    {
        shieldactive = true;
        updateDialogue("You surrounded yourself with a magic shield!");
        stats.replace("MP", stats.get("MP") - shieldcost);
        startCombat();
    }
    private void haste()
    {
        int length = 8;
        hastevalue = rng.nextInt(stats.get("MaSOL") - stats.get("MiSOL") + 1) + stats.get("MiSOL");
        stats.replace("MP", stats.get("MP") - hastecost);
        animation("Haste", length);
        hasteduration = player.getHasteDuration();
        updateDialogue("Speed increased by " + hastevalue + " for " + hasteduration + " rounds!");
    }
    private void animation(String animation, int length)
    {
        animations.replace(animation, true);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() 
        {
            @Override
            public void run() 
            {
                GoblinHunter2.game.repaint();
                if (animationnumber < length)
                {
                    animationnumber++;
                }
                else
                {
                    animations.replace(animation, false);
                    animationnumber = 0;
                    timer.cancel();
                    startCombat();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 100);
    }
    private void flee()
    {
        if (stats.get("Speed") > enemy.getSpeed())
        {
            GoblinHunter2.map();
        }
        else
        {
            updateDialogue("Not fast enough to escape!");
            startCombat();
        }
    }
    private void enter()
    {
        if (magic == false)
        {
            switch(cursorState)
            {
                case 0: //Attack
                    attack();
                    break;
                case 1: //Magic
                    magic = true;
                    cursorState = 0;
                    break;
                case 2: //Defend
                    defend = true;
                    updateDialogue("You hold your shield up high...");
                    startCombat();
                    break;
                case 3: //Flee
                    if (enemy.getBoss() == false)
                    {
                        flee();
                    }
                    else
                    {
                        updateDialogue("There's no running from this!");
                    }
                    break;
            }
        }
        else if (magic == true)
        {
            switch(cursorState)
            {
                case 0: //Heal
                    if (player.getMagicUnlock()[0] == true)
                    {
                        if (stats.get("MP") >= healcost)
                        {
                            heal();
                        }
                        else
                        {
                            updateDialogue("Not enough MP! (Cost: " + healcost + " MP)");
                        }
                    }
                    break;
                case 1: //Fire
                    if (player.getMagicUnlock()[1] == true)
                    {
                        if (stats.get("MP") >= firecost)
                        {
                            fire();
                        }
                        else
                        {
                            updateDialogue("Not enough MP! (Cost: " + firecost + " MP)");
                        }
                    }
                    break;
                case 2: //Shield
                    if (player.getMagicUnlock()[2] == true)
                    {
                        if (stats.get("MP") >= shieldcost)
                        {
                            shield();
                        }
                        else
                        {
                            updateDialogue("Not enough MP! (Cost: " + shieldcost + " MP)");;
                        }
                    }
                    break;
                case 3: //Haste
                    if (player.getMagicUnlock()[3] == true)
                    {
                        if (stats.get("MP") >= hastecost)
                        {
                            haste();
                        }
                        else
                        {
                            updateDialogue("Not enough MP! (Cost: " + hastecost + " MP)");;
                        }
                    }
                    break;
            }     
        }
    }
    private void enemydefeated()
    {
        String statdrop = enemy.statDrop();
        boolean statup = false;
        if (stats.get(statdrop) < enemy.getStatValue())
        {
            statup = true;
            if (player.getLevel() == 0)
            {
                stats.replace(statdrop, stats.get(statdrop) + 1);
            }
            else
            {
                stats.replace(statdrop, stats.get(statdrop) + player.getLevel());
                if(stats.get(statdrop) > enemy.getStatValue())
                {
                    stats.replace(statdrop, enemy.getStatValue());
                }
            }
        }
        player.statcheck();
        stats.replace("Gold", stats.get("Gold") + enemy.getGold());
        animationnumber = 0;
        updateDialogue(enemy.getName() + " perishes!");
        updateDialogue("You get " + enemy.getGold() + " gold!");
        if (statup == true)
        {
            updateDialogue("You feel yourself grow stronger!");
        }
        else
        {
            updateDialogue("You learn nothing from this battle.");
        }
        endCombat = true;
    }
    private void updateDialogue(String string)
    {
        for (int i = dialogue.length - 1; i > 0; i--)
        {
            dialogue[i] = dialogue[i - 1];
        }
        dialogue[0] = string;
    }
    private void clearDialogue()
    {
        for (int i = 0; i < dialogue.length; i++)
        {
            dialogue[i] = "";
        }
    }

	@Override
	public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) 
    {
        if (playerTurn == true)
        {
            switch(e.getKeyCode())
            {
                //Enter or Space
                case 10:
                case 32:
                    enter();
                    break;
                //Up or W
                case 38:
                case 87:
                    if (cursorState > 0)
                    {
                        cursorState--;
                    }
                    break;
                //Down or S    
                case 40:
                case 83:
                    if (cursorState < 3)
                    {
                        cursorState++;
                    }
                    break;
                //Escape
                case 27:
                    if (magic == true)
                    {
                        magic = false;
                        cursorState = 0;
                    }
                    break;
            }
        }
        else if (gameover == true)
        {
            GoblinHunter2.gameover();
        }
        else if (endCombat == true)
        {
            GoblinHunter2.map();
        }
        this.repaint();
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
