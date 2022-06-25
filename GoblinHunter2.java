import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;
import Enemies.*;

public class GoblinHunter2 
{
    /* Basic elements */
    static Scanner sc = new Scanner(System.in);
    static GameFrame frame = new GameFrame();
    /* Panels */
    static GamePanel game = new GamePanel();
    static MenuPanel menu = new MenuPanel();
    static StatusPanel status = new StatusPanel();
    static MapPanel map = new MapPanel();
    static CombatPanel combat = new CombatPanel();
    static ShopPanel shop = new ShopPanel();
    
    static Player player;
    
    static Timer timer = new Timer();
    public static void main (String[] args)
    {
        frame.add(game); //game added to frame
        game.add(menu); //menu added to game
        frame.pack();
        frame.setLocationRelativeTo(null);
    }
    public static void close()
    {
        frame.dispose();
        System.exit(0);
    }
    public static void start()
    {
        player = new Player(); // New player created
        startgame();
    }
    public static void load() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        ObjectInputStream load = new ObjectInputStream(new FileInputStream("Save.txt"));
        player = (Player) load.readObject();
        load.close();
        startgame();
    }
    public static void startgame()
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
    public static void combat(Enemy enemy)
    {
        game.remove(map);
        game.removeKeyListener(map);
        game.add(combat);
        game.addKeyListener(combat);
        combat.setup(enemy);
        game.repaint();
    }
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
    public static void shop()
    {
        game.remove(map);
        game.removeKeyListener(map);
        game.add(shop);
        game.addKeyListener(shop);
        shop.resetShop();
        shop.setup();
        game.repaint();
    }
    public static void gameover()
    {
        game.remove(combat);
        game.removeKeyListener(combat);
        game.remove(status);
        game.add(menu);
        game.repaint();
    }
}
