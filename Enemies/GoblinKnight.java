package Enemies;

import javax.swing.ImageIcon;

public class GoblinKnight extends Enemy 
{
    public GoblinKnight() 
    {
        super("Goblin Knight",100, 10, 17, 25, 3, false);
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
        this.StatValue = 100;
        return "MaATK";
    }
}
