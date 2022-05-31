import java.util.*;
import Enemies.*;

public class GoblinHunter2 {
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
    }
    public static void close()
    {
        frame.dispose();
        System.exit(0);
    }
    public static void start()
    {
        player = new Player(); // New player created
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
    public static void load()
    {
        
    }
    public static void combat(int level)
    {
        game.remove(map);
        game.removeKeyListener(map);
        game.add(combat);
        game.addKeyListener(combat);
        Enemy enemy;
        switch(level)
        {
            case 1:
                enemy = randomEncounter1();
                break;
            default:
                System.out.println("Combat default called");
                enemy = new Goblin(); //This should never happen
                break;
        }
        combat.setEnemy(enemy);
        combat.resetThresholds();
        combat.startCombat();
        game.repaint();
    }
    private static Enemy randomEncounter1()
    {
        Random rng = new Random();
        Enemy enemy;
        int encounter = rng.nextInt(9);
        switch(encounter)
        {
            case 0:
            case 1:
            case 2:
            case 3:
                enemy = new Goblin();
                break;
            case 4:
            case 5:
            case 6:
            case 7:
                enemy = new GoblinShaman();
                break;
            case 8:
            case 9:
                enemy = new GoblinFly();
                break;
            default:
                System.out.println("RandomEncounter 1 default called"); //Change to throw exception
                enemy = new Goblin();
                break;
        }
        System.out.println(enemy.getName());
        return enemy;
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
}
