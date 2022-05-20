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
    
    /* Player */
    static HashMap<String, Integer> stats = new HashMap<>();
    
    static int EHP = 100;
    public static void main (String[] args)
    {
        setup();
    }
    public static void setup()
    {
        /* Declare player starting stats */
        stats.put("HP", 10);
        stats.put("MHP", 10);
        stats.put("MP", 10);
        stats.put("MMP", 10);
        stats.put("MiATK", 1);
        stats.put("MaATK", 5);
        stats.put("MiSOL", 5);
        stats.put("MaSOL", 8);
        stats.put("DEF", 0);
        stats.put("HREGEN", 0);
        stats.put("MREGEN", 0);
        stats.put("Speed", 10);
        status.setStats(stats);

        frame.add(game);
        game.add(menu);
        frame.pack();
    }
    public static void close()
    {
        frame.dispose();
        System.exit(0);
    }
    public static void start()
    {
        game.remove(menu);
        game.add(map);
        game.add(status);
        game.addKeyListener(map);
        game.repaint();
    }
    public static void combat()
    {
        game.remove(map);
        game.add(combat);
        game.addKeyListener(combat);
        combat.setEHP(EHP);
        game.repaint();
    }
    public static void map()
    {
        game.remove(combat);
        game.add(map);
        game.addKeyListener(map);
        game.repaint();
    }
    public static void attack()
    {
        EHP = EHP - (rng.nextInt(stats.get("MaATK") - stats.get("MiATK")) + stats.get("MiATK"));
        combat.setEHP(EHP);
        combat.repaint();
    }
}
