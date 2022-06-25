package Enemies;

import javax.swing.ImageIcon;

public class BabyGoblin extends Enemy
{
    public BabyGoblin()
    {
        super("Baby Goblin", 10, 1, 1, 8, 5, true);
        sprite = new ImageIcon("Assets/Test.png");

    }
    @Override
    public int Action() {
        int damage = 1;
        return damage;
    }

    @Override
    public String statDrop() 
    {
        String stat = "MiATK";
        this.StatValue = 2;
        return stat;
    }
    
}
