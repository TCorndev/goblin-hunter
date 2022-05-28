import java.util.HashMap;

public class Player {
    private HashMap<String, Integer> stats = new HashMap<>(); //Contains stats
    private boolean[] magicUnlocks = new boolean[4]; //Determines if spells are unlocked. 0: heal, 1: fire, 2: haste, 3: shield
    private int swordLevel;
    private int shieldLevel;
    private int[] playerPos = new int[2];

    Player() //Default constructor
    {
        stats.put("HP", 10);
        stats.put("MHP", 10);
        stats.put("MP", 10);
        stats.put("MMP", 10);
        stats.put("MiATK", 1);
        stats.put("MaATK", 5);
        stats.put("MiSOL", 5);
        stats.put("MaSOL", 8);
        stats.put("DEF", 1);
        stats.put("HREGEN", 0);
        stats.put("MREGEN", 0);
        stats.put("Speed", 10);
        stats.put("Gold", 0);
        swordLevel = 1;
        shieldLevel = 1;
    }
    //Player(Stuff) (For loading)
    public HashMap<String, Integer> getStats()
    {
        return stats;
    }
    public boolean getMagicUnlocks(int i)
    {
        //Maybe add Try catch here
        return magicUnlocks[i];
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
    
}
