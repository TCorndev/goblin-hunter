package Enemies;

import javax.swing.ImageIcon;

public class GoblinGuardian extends Enemy 
{
    private boolean enrage = false;
    public GoblinGuardian() 
    {
        super("Goblin Guardian",1000, 1, 1, 30, 3, false);
        sprite = new ImageIcon("Assets/Test.png");

    }
    @Override
    public int Action()
    {
        int damage = 0;
        if (enrage == false)
        {
            damage = rng.nextInt(MaATK - MiATK + 1) + MiATK;
            enrage = true;
        }
        else
        {
            this.MaATK++;
            this.dialogue = this.name + " grows stronger...";
            enrage = false;
        }
        return damage;
    }
    @Override
    public String statDrop() 
    {
        this.StatValue = 999;
        return "MHP";
    }
}
