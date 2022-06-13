import java.io.Serializable;

public class Item implements Serializable
{
 private String name;
 private String description1;
 private String description2;
 private int cost;
 private boolean unlocked;
    Item(String name, String description1, String description2, int cost, boolean unlocked)
    {
        this.name = name;
        this.description1 = description1;
        this.description2 = description2;
        this.cost = cost;
        this.unlocked = unlocked;
    }
    public String getName()
    {
        return this.name;
    }
    public String getDescription1()
    {
        return this.description1;
    }
    public String getDescription2()
    {
        return this.description2;
    }
    public int getCost()
    {
        return this.cost;
    }
    public boolean getUnlocked()
    {
        return this.unlocked;
    }
    public void setUnlocked(Boolean unlocked)
    {
        this.unlocked = unlocked;
    }
}
