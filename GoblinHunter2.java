import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import Enemies.*;

public class GoblinHunter2 
{
    /* Basic elements */
    private static GameFrame frame = new GameFrame();
    /* Panels */
    public static GamePanel game = new GamePanel();
    private static MenuPanel menu = new MenuPanel();
    private static StatusPanel status = new StatusPanel();
    private static MapPanel map = new MapPanel();
    private static CombatPanel combat = new CombatPanel();
    private static ShopPanel shop = new ShopPanel();
    
    private static Player player;
    
    public static void main (String[] args)
    {
        frame.add(game); //game added to frame
        game.add(menu); //menu added to game
        frame.pack();
        frame.setLocationRelativeTo(null);
    }
    /*Called on either the map or the menu, exits the game. */
    public static void close()
    {
        frame.dispose();
        System.exit(0);
    }
    /*Called when the start button is pressed, creates a new player object */
    public static void start()
    {
        player = new Player(); // New player created
        startgame();
    }
    /*Called when the load button is pressed, loads the player object from a file */
    public static void load() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        ObjectInputStream load = new ObjectInputStream(new FileInputStream("Save.txt"));
        player = (Player) load.readObject(); //Load player object from file
        load.close();
        startgame();
    }
    /*Called after either start or load, sets up the panels with the player information */
    private static void startgame()
    {
        status.setPlayer(player);
        map.setPlayer(player);
        combat.setPlayer(player);
        shop.setPlayer(player);
        game.remove(menu);
        game.add(status);
        game.add(map);
        game.addKeyListener(map);
        game.repaint();
    }
    /*Called when combat starts, removes the map panel and adds the combat panel */
    public static void combat(Enemy enemy)
    {
        game.remove(map);
        game.removeKeyListener(map);
        game.add(combat);
        game.addKeyListener(combat);
        combat.setup(enemy);
        game.repaint();
    }
    /*Called either when leaving the shop or combat ends, removes the panel and adds the map panel */
    public static void map()
    {
        game.remove(combat);
        game.removeKeyListener(combat);
        game.remove(shop);
        game.removeKeyListener(shop);
        game.add(map);
        game.addKeyListener(map);
        game.repaint();
    }
    /*Called when entering the shop */
    public static void shop()
    {
        game.remove(map);
        game.removeKeyListener(map);
        game.add(shop);
        game.addKeyListener(shop);
        shop.resetShop();
        game.repaint();
    }
    /*Called when the player dies */
    public static void gameover()
    {
        game.remove(combat);
        game.removeKeyListener(combat);
        game.remove(status);
        game.add(menu);
        game.repaint();
    }
}
