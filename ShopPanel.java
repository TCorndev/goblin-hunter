import javax.swing.JPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.ArrayList;
import java.awt.*;

public class ShopPanel extends JPanel implements KeyListener
{
    /* Panel Dimensions */
    private final int WIDTH = 1000;
    private final int HEIGHT = 900;
    /*Initializing variable*/
    private boolean shopping = false;
    /*Dialogue*/
    private String shopSpeech1;
    private String shopSpeech2;
    private String cost;
    /*Item Box & cursor */
    private int cursorState;
    private int page;
    private int lastpage;
    private final int PAGESIZE = 4;
    
    /*Player variables*/
    private Player player;
    private ArrayList<Item> playerItems;
    private HashMap<String, Integer> stats;
    /*Item variables */
    private ArrayList<Item> Inventory = new ArrayList<>();

    ShopPanel()
    {
        this.setBounds(0, 100, WIDTH, HEIGHT);
        this.setBackground(Color.blue);
        this.setFocusable(true);
        this.setVisible(true);
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }
    private void draw(Graphics g)
    {
        
        /* Dialogue Box */
        g.setFont(new Font("Courier New", Font.BOLD, 30));
        g.setColor(Color.black);
        g.fillRect(50, 50, WIDTH - 100, 100);
        g.setColor(Color.white);
        g.drawRect(50, 50, WIDTH - 100, 100);
        g.drawString(shopSpeech1, 55, 80);
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString(shopSpeech2, 55, 110);
        g.drawString(cost, 55, 140);
        g.drawString("Gold: " + stats.get("Gold"), 55, 170);

        /* Item Box */
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.setColor(Color.black);
        g.fillRect(600, 200, 200, 110);
        g.setColor(Color.white);
        g.drawRect(600, 200, 200, 110);
        g.drawString(page + "/" + lastpage, 605, 220);
        g.drawLine(600, 220, 800, 220);
        int itemOffset = 220;
        for (int i = (page - 1) * PAGESIZE; i < PAGESIZE * page && i < Inventory.size(); i++) //Holy crap this for loop was complicated
        {
            itemOffset = itemOffset + 20;
            g.drawString(Inventory.get(i).getName(), 605, itemOffset);
        }

        if (shopping == true)
        {
            int cursorYOffset = cursorState * 20;
            int[] xpoints = {595, 585, 585};
            int[] ypoints = {235 + cursorYOffset, 245 + cursorYOffset, 225 + cursorYOffset};
            g.fillPolygon(xpoints, ypoints, 3);
        }
    }
    public void setPlayer(Player player)
    {
        this.player = player;
        this.playerItems = player.getItems();
        this.stats = player.getStats();
    }
    /* This method needs to be called every time shop is entered or item is bought*/
    public void setup()
    {
        Inventory.add(new Item("HP Restore", "Fully restore your HP", "(Cost determined based on missing HP)", stats.get("MHP") - stats.get("HP"), false));
        Inventory.add(new Item("MP Restore", "Fully restore your MP", "(Cost determined based on missing MP)", stats.get("MMP") - stats.get("MP"), false));
        for(int i = 0; i < playerItems.size(); i++)
        {
            if (playerItems.get(i).getUnlocked() == false)
            {
                Inventory.add(playerItems.get(i));
            }
        }
        int inventorySize = Inventory.size();
        while(inventorySize > PAGESIZE)
        {
            lastpage++;
            inventorySize = inventorySize - PAGESIZE;
        }
    }
    private void itemSelected()
    {
        int i = cursorState + PAGESIZE * (page - 1);
        if (i < Inventory.size())
        {
            shopSpeech1 = Inventory.get(i).getDescription1();
            shopSpeech2 = Inventory.get(i).getDescription2();
            cost = "Cost: " + Inventory.get(i).getCost();
        }
    }
    private void buyItem()
    {
        int i = cursorState + PAGESIZE * (page - 1);
        if (Inventory.get(i).getCost() <= stats.get("Gold"))
        {
            if (i == 0)
            {
                stats.replace("Gold", stats.get("Gold") - Inventory.get(i).getCost());
                stats.replace("HP", stats.get("MHP"));
            }
            if (i == 1)
            {
                stats.replace("Gold", stats.get("Gold") - Inventory.get(i).getCost());
                stats.replace("MP", stats.get("MMP"));
            }
            if (i > 1)
            {
                stats.replace("Gold", stats.get("Gold") - Inventory.get(i).getCost());
                Inventory.get(i).setUnlocked(true);
            }
            Inventory.clear();
            setup();
        }
    }
    public void resetShop()
    {
        shopSpeech1 = "Welcome to my shop. Please take a look around.";
        shopSpeech2 = "(Press any key to shop)";
        cost = "";
        shopping = false;
        lastpage = 1;
        page = 1;
        cursorState = 0;
        Inventory.clear(); 
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        if (shopping == true)
        {
            switch(e.getKeyCode())
            {
                //Enter or Space
                case 10:
                case 32:
                    buyItem();;
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
                //Left or A
                case 37:
                case 65:
                    if (page > 1)
                    {
                        cursorState = 0;
                        page--;
                    }
                    break;
                //Right or D
                case 68:
                case 39:
                    if (page < lastpage)
                    {
                        cursorState = 0;
                        page++;
                    }
                    break;
                //Escape
                case 27:
                    GoblinHunter2.map();
                    break;
            }
        }
        else
        {
            shopping = true;
        }
        itemSelected();
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}
