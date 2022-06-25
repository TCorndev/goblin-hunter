package Enemies;

import javax.swing.ImageIcon;

public class GoblinSpider extends Enemy 
{
    public GoblinSpider() 
    {
        super("Goblin Spider",20, 3, 3, 50, 3, false);
        sprite = new ImageIcon("Assets/Test.png");

    }
    @Override
    public int Action()
    {
        int damage = rng.nextInt(MaATK - MiATK + 1) + MiATK;
        return damage;
    }
    @Override
    public String statDrop() 
    {
        String stat = null;
        int drop = rng.nextInt(2);
        switch(drop)
        {
            case 0:
            stat = "MHP";
            this.StatValue = 100;
            break;
            case 1:
            stat = "Speed";
            this.StatValue = 35;
            break;
        } 
        return stat;
    }
}
