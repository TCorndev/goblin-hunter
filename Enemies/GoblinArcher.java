package Enemies;

import javax.swing.ImageIcon;

public class GoblinArcher extends Enemy 
{
    public GoblinArcher() 
    {
        super("Goblin Archer",90, 12, 13, 45, 3, false);
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
        this.StatValue = 90;
        return "MiATK";
    }
}
