import java.util.HashMap;
import java.util.ArrayList;

public class Player {
    private HashMap<String, Integer> stats = new HashMap<>(); //Contains stats
    private ArrayList<Item> items = new ArrayList<>();
    private int swordLevel;
    private int shieldLevel;
    private int[] playerPos = new int[2];

    Player() //Default constructor
    {
        /* Starting stats */
        stats.put("HP", 10);
        stats.put("MHP", 10);
        stats.put("MP", 10);
        stats.put("MMP", 10);
        stats.put("MiATK", 1);
        stats.put("MaATK", 3);
        stats.put("MiSOL", 1);
        stats.put("MaSOL", 3);
        stats.put("DEF", 0);
        stats.put("HREGEN", 0);
        stats.put("MREGEN", 0);
        stats.put("Speed", 10);
        stats.put("Gold", 1000);

        /*Unlocks(Items, Spells, etc.. Bought in store) */
        //Spells
        items.add(new Item("Scroll of Heal", "This scroll will unlock the Heal spell!", "(Restores HP equal to SOL)", 10, false));
        items.add(new Item("Scroll of Fire", "This scroll will unlock the Fire spell!", "(Does damage equal to 5 times your SOL)", 100, false));
        items.add(new Item("Scroll of Haste", "This scroll will unlock the Haste spell!", "(Greatly increases your speed, allowing for more attacks)", 500, false));
        items.add(new Item("Scroll of Shield", "This scroll will unlock the Shield spell", "(Greatly reduces your damage taken)", 2000, false));



        //Items

        /* Starting Equipment Levels */
        swordLevel = 1;
        shieldLevel = 1;

        /* Starting Posistion */
        playerPos[0] = 20; //X Coordinate
        playerPos[1] = 18; //Y Coordinate
    }
    //Player(Stuff) (For loading)
    public HashMap<String, Integer> getStats()
    {
        return stats;
    }
    public ArrayList<Item> getItems()
    {
        return items;
    }
    public int getSwordLevel()
    {
        return swordLevel;
    }
    public int getShieldLevel()
    {
        return shieldLevel;
    }
    public int[] getPlayerPos()
    {
        return playerPos;
    }
    public void setGold(int cost)
    {
        stats.replace("Gold", stats.get("Gold") - cost);
    }
    public void update()
    {
        
    }
}
