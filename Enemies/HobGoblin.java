package Enemies;

import javax.swing.ImageIcon;

public class HobGoblin extends Enemy 
{
    public HobGoblin() 
    {
        super("Hobgoblin",60, 3, 5, 20, 3, false);
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
            stat = "MaATK";
            this.StatValue = 25;
            break;
        } 
        return stat;
    }
}
