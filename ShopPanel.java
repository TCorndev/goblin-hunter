import javax.swing.JPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    private boolean exitShop;
    /*Item Box & cursor */
    private int cursorState;
    private int page;
    private int lastpage;
    private final int PAGESIZE = 4;
    
    /*Player variables*/
    private Player player;
    /*Item variables */
    private ArrayList<Item> Inventory = new ArrayList<>();

    ShopPanel()
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
        g.drawString("Gold: " + player.getStats().get("Gold"), 55, 170);

        /* Item Box */
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.setColor(Color.black);
        g.fillRect(600, 200, 200, 110);
        g.setColor(Color.white);
        g.drawRect(600, 200, 200, 110);
        g.drawString(page + "/" + lastpage, 605, 220);
        g.drawLine(600, 220, 800, 220);
        int itemOffset = 220;
        for (int i = (page - 1) * PAGESIZE; i < PAGESIZE * page && i < Inventory.size(); i++)
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
    }
    /* This method is called every time shop is entered or item is bought*/
    public void setup()
    {
        Inventory.add(new Item("HP Restore", "Fully restore your HP", "(Cost determined based on missing HP)", player.getStats().get("MHP") - player.getStats().get("HP"), false));
        Inventory.add(new Item("MP Restore", "Fully restore your MP", "(Cost determined based on missing MP)", player.getStats().get("MMP") - player.getStats().get("MP"), false));
        lastpage = 1;
        for(int i = 0; i < player.getItems().size(); i++)
        {
            if (player.getItems().get(i).getUnlocked() == false)
            {
                Inventory.add(player.getItems().get(i));
            }
        }
        int inventorySize = Inventory.size();
        while(inventorySize > PAGESIZE)
        {
            lastpage++;
            inventorySize = inventorySize - PAGESIZE;
        }
        if (cursorState >= inventorySize && page == lastpage)
        {
            cursorState = inventorySize - 1;
        }
        if (page > lastpage)
        {
            page = lastpage;
        }
    }
    /*Called to move the cursor */
    private void movecursor(int move)
    {
        cursorState = cursorState + move;
        if (page == lastpage)
        {
            int max = (Inventory.size() - (lastpage - 1) * PAGESIZE) - 1;
            if (cursorState < 0)
            {
                cursorState = max;
            }
            if (cursorState > max)
            {
                cursorState = 0;
            }
        }
        else
        {
            if (cursorState < 0)
            {
                cursorState = 3;
            }
            if (cursorState > 3)
            {
                cursorState = 0;
            }
        }
    }
    /*Updates the dialogue with the selected item's information */
    private void itemSelected()
    {
        int i = cursorState + PAGESIZE * (page - 1);
        if (i < Inventory.size() && shopping == true)
        {
            shopSpeech1 = Inventory.get(i).getDescription1();
            shopSpeech2 = Inventory.get(i).getDescription2();
            cost = "Cost: " + Inventory.get(i).getCost();
        }
    }
    /*Called when an item is bought */
    private void buyItem()
    {
        int i = cursorState + PAGESIZE * (page - 1);
        if (Inventory.get(i).getCost() <= player.getStats().get("Gold")) 
        {
            if (i == 0) //HP Restore
            {
                player.getStats().replace("Gold", player.getStats().get("Gold") - Inventory.get(i).getCost());
                player.getStats().replace("HP", player.getStats().get("MHP"));
            }
            if (i == 1) //MP Restore
            {
                player.getStats().replace("Gold", player.getStats().get("Gold") - Inventory.get(i).getCost());
                player.getStats().replace("MP", player.getStats().get("MMP"));
            }
            if (i > 1)
            {
                player.getStats().replace("Gold", player.getStats().get("Gold") - Inventory.get(i).getCost());
                Inventory.get(i).setUnlocked(true);
                player.Unlock(Inventory.get(i).getName());
            }
            Inventory.clear();
            setup();
        }
        GoblinHunter2.game.repaint();
    }
    /*Called when leaving the shop, updates the dialogue based on player's progress */
    private void hintdialogue()
    {
        int progress = 0;
        for (int i = 0; i < player.getBossKills().length; i++)
        {
            if (player.getBossKills()[i] == false)
            {
                progress = i;
                break;
            }
        }
        switch(progress)
        {
            case 1: //Baby Goblin defeated
                shopSpeech1 = "A Powerful Goblin defends the bridge";
                shopSpeech2 = "Make sure you're prepared before attempting to cross it.";
                break;
            case 2: //Bridge Goblin defeated
                shopSpeech1 = "The cave is full of dangerous foes";
                shopSpeech2 = "A shield spell will help you survive";
                break;
            case 3: //Rock Goblin defeated
                shopSpeech1 = "The castle sits atop a volcano";
                shopSpeech2 = "Are you powerful enough to dethrone the Goblin King?";
                break;
            case 4: //Goblin King defeated
                shopSpeech1 = "Wow, you did it!";
                shopSpeech2 = "Feel free to purchase any remaining wares!";
            default:
                break;
        }
    }
    /*Called when entering the shop, resets variables */
    public void resetShop()
    {
        shopSpeech1 = "Welcome to my shop. Please take a look around.";
        shopSpeech2 = "(Press any key to shop)";
        cost = "";
        shopping = false;
        exitShop = false;
        page = 1;
        cursorState = 0;
        Inventory.clear();
        setup();
    }
    @Override
    public void keyTyped(KeyEvent e) {} //Not implemented

    @Override
    public void keyPressed(KeyEvent e) 
    {
        if (exitShop == true)
        {
            GoblinHunter2.map();
        }
        else if (shopping == false)
        {
            shopping = true;
        }
        else if (shopping == true)
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
                    movecursor(-1);
                    break;
                //Down or S    
                case 40:
                case 83:
                    movecursor(1);
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
                    exitShop = true;
                    shopping = false;
                    hintdialogue();
                    break;
            }
            itemSelected();
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {} //Not implemented
    
}
