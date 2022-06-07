import javax.swing.JPanel;

import Enemies.Enemy;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CombatPanel extends JPanel implements KeyListener
{
    private final int COMBATBOXWIDTH = 80;
    private final int COMBATBOXHEIGHT = 90;
    private final int THRESHOLD = 1000;
    private int cursorState;
    /* Combat variables*/
    private Enemy enemy;
    private Boolean playerTurn = false;
    //private Boolean enemyTurn = false; //NYI
    private Boolean magic = false;
    private Boolean defend = false;
    
    private int playerActionThreshold = THRESHOLD;
    private int enemyActionThreshold = THRESHOLD;
    
    private Timer timer;

    /* Player Information */
    private Player player;
    private HashMap<String, Integer> stats;
    private ArrayList<Item> items;
    
    
    CombatPanel()
    {
        this.setBounds(0, 100, 1000, 900);
        this.setBackground(Color.blue);
        this.setFocusable(true);
        this.setVisible(true);
        this.setLayout(null);
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    } 
    private void draw(Graphics g)
    {
        
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("HP: " + enemy.getHP(), 450, 780);

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
            if (items.get(0).getUnlocked() == true)
            {
                g.drawString("Heal", 545, 820);
            }
            if (items.get(1).getUnlocked() == true)
            {
                g.drawString("Fire",545 , 840);
            }
            if (items.get(2).getUnlocked() == true)
            {
                g.drawString("Haste", 545, 860);
            }
            if (items.get(3).getUnlocked() == true)
            {
                g.drawString("Shield", 545, 880);
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
    }
    public void setPlayer(Player player)
    {
        this.player = player;
        this.stats = player.getStats();
        this.items = player.getItems();
    }
    public void setEnemy(Enemy enemy)
    {
        this.enemy = enemy;
    }
    public void resetThresholds()
    {
        this.playerActionThreshold = THRESHOLD;
        this.enemyActionThreshold = THRESHOLD;
    }
    public void startCombat()
    {
        playerTurn = false;
        cursorState = 0;
        if (enemy.getHP() <= 0)
        {
            enemydefeated();
        }
        else
        {
            timer = new Timer();
            TimerTask task = new TimerTask() 
            {
                @Override
                public void run() 
                {
                    playerActionThreshold = playerActionThreshold - stats.get("Speed");
                    if (playerActionThreshold <= 0)
                    {
                        playerTurn = true;
                        defend = false;
                        playerActionThreshold = playerActionThreshold + THRESHOLD;
                        timer.cancel();
                        GoblinHunter2.game.repaint(); //Normally animatiions will cover this repaint
                    }
                    enemyActionThreshold = enemyActionThreshold - enemy.getSpeed();
                    if (enemyActionThreshold <= 0)
                    {
                        //enemyTurn = true; //Not yet relevant
                        enemyActionThreshold = enemyActionThreshold + THRESHOLD;
                        enemyAttack();
                        //timer.cancel(); Activate this when animations are put in
                        GoblinHunter2.game.repaint(); //Normally animations will cover this repaint
                    }
                }
            };
            timer.scheduleAtFixedRate(task, 0, 1);
        }
    }
    private void enemyAttack()
    {
        int shieldValue = 1;
        if (defend == true)
        {
            shieldValue = shieldValue + player.getShieldLevel();
        }
        int damage = enemy.Action() - stats.get("DEF") * shieldValue;
        if (damage < 0)
        {
            damage = 0;
        }
        stats.replace("HP", stats.get("HP") - damage);
        if (stats.get("HP") <= 0)
        {
            GoblinHunter2.close();
        }
        System.out.println(damage);
    }
    public void attack()
    {
        Random rng = new Random();
        enemy.setHP(enemy.getHP() - (rng.nextInt(stats.get("MaATK") - stats.get("MiATK") + 1) + stats.get("MiATK")));
        GoblinHunter2.game.repaint();
        startCombat();
    }
    private void flee()
    {
        if (stats.get("Speed") > enemy.getSpeed())
        {
            GoblinHunter2.map();
        }
        else
        {
            System.out.println("Not fast enough to escape!");
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
                    startCombat();
                    break;
                case 3: //Flee
                    flee();
                    break;
            }
        }
        if (magic == true)
        {
            switch(cursorState)
            {
                case 0: //Heal
                    break;
                case 1: //Fire
                    break;
                case 2: //Haste
                    break;
                case 3: //Shield
                    break;
            }     
        }
    }
    private void enemydefeated()
    {
        String statdrop = enemy.statDrop();
        if (stats.get(statdrop) < enemy.getStatValue())
        {
            stats.replace(statdrop, stats.get(statdrop) + 1);
        }
        statcheck();
        stats.replace("Gold", stats.get("Gold") + enemy.getGold());
        GoblinHunter2.map();
    }

    /* This method makes sure that minimum stats don't go above maximum stats. No 7-5 ATK here! May turn this into a static method in main and include HP */
    private void statcheck()
    {
        
        if (stats.get("MiATK") > stats.get("MaATK"))
        {
            stats.replace("MiATK", stats.get("MaATK"));
        }
        if (stats.get("MiSOL") > stats.get("MaSOL"))
        {
            stats.replace("MiSOL", stats.get("MaSOL"));
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
        this.repaint();
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
