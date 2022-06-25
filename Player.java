import java.util.HashMap;
import java.util.Random;
import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable 
{
    private HashMap<String, Integer> stats = new HashMap<>(); //Contains stats
    private ArrayList<Item> items = new ArrayList<>();
    private int swordLevel;
    private int shieldLevel;
    private int firemultiplier;
    private int hasteduration;
    private boolean resonance;
    private boolean bloodthirst;
    private int[] playerPos = new int[2];
    private int level;
    private boolean[] bosskills = new boolean[4]; 
    private boolean[] magicunlock = new boolean[4]; //Used in the Combat panel to determine if a spell is available to cast
    private double points;
    private final double MAXPOINTS = 2688;

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
        stats.put("Gold", 10000000);

        /*Unlocks(Items, Spells, etc.. Bought in store) */
        //Spells
        items.add(new Item("Scroll of Heal", "This scroll will unlock the Heal spell!", "(Restores HP equal to SOL)", 10, false));
        
        //Items
        items.add(new Item("Leather Armor", "This Armor will reduce the damage you take!", "(DEF + 1)", 10, false));
        items.add(new Item("Iron Sword", "Increases the damage of your sword attacks!", "(DMG Multiplier + 1)", 20, false));
        items.add(new Item("Regen Ring", "Allows you to regenerate HP!", "(HREGEN +1)", 1000, false));
        items.add(new Item("Lifesteal Ring", "Heals you for a portion of the damage you deal!", "(Heals you for 1/4 the damage you deal)", 200000, false ));

        /* Starting Equipment Levels */
        swordLevel = 1;
        shieldLevel = 1;

        /* Spell enhancements */
        firemultiplier = 5;
        hasteduration = 5;
        resonance = false;
        bloodthirst = false;

        /* Starting Posistion */
        playerPos[0] = 20; //X Coordinate
        playerPos[1] = 34; //Y Coordinate
        level = 0; //Starting level(Tutorial)
    }
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
    public boolean[] getBossKills()
    {
        return bosskills;
    }
    public boolean[] getMagicUnlock()
    {
        return magicunlock;
    }
    public int getLevel()
    {
        return level;
    }
    public int getFireMultiplier()
    {
        return firemultiplier;
    }
    public boolean getResonace()
    {
        return this.resonance;
    }
    public boolean getBloodthirst()
    {
        return this.bloodthirst;
    }
    public int getHasteDuration()
    {
        return hasteduration;
    }
    public void setFireMultiplier(int firemultiplier)
    {
        this.firemultiplier = firemultiplier;
    }
    public void setLevel(int level)
    {
        this.level = level;
    }
    public void setBossKills(int i)
    {
        bosskills[i] = true;
    }
    public void setGold(int cost)
    {
        stats.replace("Gold", stats.get("Gold") - cost);
    }
    public void Unlock(String name)
    {
        switch(name)
        {
            /* Armor */
            case "Leather Armor":
                items.add(new Item("Iron Armor", "This Armor will reduce the damage you take!", "(DEF + 1)", 200, false));
                items.add(new Item("Iron Shield", "Reduces damage when defending!", "(DEF Multipier + 1)", 20, false));
                stats.replace("DEF", stats.get("DEF") + 1);
                break;
            case "Iron Armor":
                items.add(new Item("Steel Armor", "This Armor will reduce the damage you take!", "(DEF + 1)", 3000, false));
                stats.replace("DEF", stats.get("DEF") + 1);
                break;
            case "Steel Armor":
                items.add(new Item("Crystal Armor", "This Armor will reduce the damage you take!", "(DEF + 1)", 40000, false));
                stats.replace("DEF", stats.get("DEF") + 1);
                break;
            case "Crystal Armor":
                stats.replace("DEF", stats.get("DEF") + 1);
                break;
            
            /* Scrolls */
            case "Scroll of Heal":
                magicunlock[0] = true;
                items.add(new Item("Scroll of Fire", "This scroll will unlock the Fire spell!", "(Does damage equal to 5 times your SOL)", 100, false));
                break;
            case "Scroll of Fire":
                magicunlock[1] = true;
                items.add(new Item("Scroll of Shield", "This scroll will unlock the Shield spell", "(Greatly reduces your damage taken)", 200, false));
                items.add(new Item("Flame Necklace", "Increases the damage of your Fire spell!", "(Doubles your fire spell's damage)", 4000, false));
                break;
            case "Scroll of Shield":
                magicunlock[2] = true;
                items.add(new Item("Scroll of Haste", "This scroll will unlock the Haste spell!", "(Greatly increases your speed, allowing for more attacks)", 2000, false));
                items.add(new Item("Resonating Ring", "Your shield spell absorb damage!", "(Heals you for damage shield blocks)", 10000, false));
                break;
            case "Scroll of Haste":
                magicunlock[3] = true;
                items.add(new Item("Silver Stopwatch", "Your haste spell will last twice as long!", "(Increase hastes's duration to 10 rounds", 10000, false));
                break;
            
            /* Swords */
            case "Iron Sword":
                items.add(new Item("Steel Sword", "Increases the damage of your sword attacks!", "(DMG Multiplier + 1)", 300, false));
                swordLevel++;
                break;
            case "Steel Sword":
                items.add(new Item("Crystal Sword", "Increases the damage of your sword attacks!", "(DMG Multiplier + 1)", 4000, false));
                swordLevel++;
                break;
            case "Crystal Sword":
                items.add(new Item("Ultimate Sword", "Increases the damage of your sword attacks!", "(DMG Multiplier + 1)", 50000, false));
                swordLevel++;
                break;
            case "Ultimate Sword":
                swordLevel++;
                break;

            /* Shields */
            case "Iron Shield":
                items.add(new Item("Steel Shield", "Reduces damage when defending!", "(DEF Multipier + 1)", 300, false));
                shieldLevel++;
                break;
            case "Steel Shield":
                items.add(new Item("Crystal Shield", "Reduces damage when defending!", "(DEF Multipier + 1)", 4000, false));
                shieldLevel++;
                break;
            case "Crystal Shield":
                items.add(new Item("Adamantine Shield", "Reduces damage when defending!", "(DEF Multipier + 1)", 50000, false));
                shieldLevel++;
                break;
            case "Adamantine Shield":
                shieldLevel++;
                break;

            /* Spell Enhancers */
            case "Flame Necklace":
                firemultiplier = firemultiplier * 2;
                break;
            case "Resonating Ring":
                resonance = true;
                break;
            case "Silver Stopwatch":
                hasteduration = hasteduration * 2;
                break;

            /* Extra */
            case "Regen Ring":
                stats.replace("HREGEN", stats.get("HREGEN") + 1);
                break;
            case "Lifesteal Ring":
                bloodthirst = true;
                break;
        }
    }
    /* Called each step on the world map and every turn in combat */
    public void regen()
    {
        stats.replace("HP", stats.get("HP") + stats.get("HREGEN"));
        stats.replace("MP", stats.get("MP") + stats.get("MREGEN"));
        statcheck();
    }
    public void heal()
    {
        int healcost = 3;
        if (stats.get("MP") >= healcost)
        {
            Random rng = new Random();
            int heal = rng.nextInt(stats.get("MaSOL") - stats.get("MiSOL") + 1) + stats.get("MiSOL");
            stats.replace("HP", stats.get("HP") + heal);
            stats.replace("MP", stats.get("MP") - healcost);
            System.out.println("Heal for: " + heal);
            statcheck();
        }
        else
        {
            System.out.println("Not enough MP!");
        }
    }
    public void fullheal()
    {
        stats.replace("HP", stats.get("MHP"));
        stats.replace("MP", stats.get("MMP"));
    }
    /* Called whenever a stat value changes. Prevents stats from going over their max */
    public void statcheck()
    {
        if (stats.get("MiATK") > stats.get("MaATK"))
        {
            stats.replace("MiATK", stats.get("MaATK"));
        }
        if (stats.get("MiSOL") > stats.get("MaSOL"))
        {
            stats.replace("MiSOL", stats.get("MaSOL"));
        }
        if (stats.get("HP") > stats.get("MHP"))
        {
            stats.replace("HP", stats.get("MHP"));
        }
        if (stats.get("MP") > stats.get("MMP"))
        {
            stats.replace("MP", stats.get("MMP"));
        }
        if (stats.get("MP") < 0)
        {
            stats.replace("MP", 0);
        }
        GoblinHunter2.game.repaint();
    }
    private void checkpoints()
    {
        points = stats.get("MHP") + stats.get("MMP") + stats.get("MiATK") + stats.get("MaATK") + stats.get("MiSOL") + stats.get("MaSOL") + stats.get("Speed");
        for (int i = 0; i < items.size(); i++)
        {
            if (items.get(i).getUnlocked() == true)
            {
                points = points + 10;
            }
        }
    }
    public double getCompletionPercentage()
    {
        checkpoints();
        double percentage = points / MAXPOINTS * 100;
        return (percentage);
    }
}
