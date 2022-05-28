import java.util.*;

public class GoblinHunter2 {
    /* Basic elements */
    static Scanner sc = new Scanner(System.in);
    static GameFrame frame = new GameFrame();
    static Random rng = new Random();
    /* Panels */
    static GamePanel game = new GamePanel();
    static MenuPanel menu = new MenuPanel();
    static StatusPanel status = new StatusPanel();
    static MapPanel map = new MapPanel();
    static CombatPanel combat = new CombatPanel();

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
        combat.setPlayer(player);
        status.setPlayer(player);
        game.remove(menu);
        game.add(map);
        game.add(status);
        game.addKeyListener(map);
        game.repaint();
    }
    public static void load()
    {

    }
    public static void combat()
    {
        game.remove(map);
        game.add(combat);
        game.addKeyListener(combat);
        Enemy Goblin = new Goblin();
        combat.setEnemy(Goblin);
        combat.resetThresholds();
        combat.startCombat();
        game.repaint();
    }
    public static void map()
    {
        game.remove(combat);
        game.removeKeyListener(combat);
        game.add(map);
        game.addKeyListener(map);
        game.repaint();
    }
}
