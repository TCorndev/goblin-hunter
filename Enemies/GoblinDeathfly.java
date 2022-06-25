package Enemies;

import javax.swing.ImageIcon;

public class GoblinDeathfly extends Enemy 
{
    public GoblinDeathfly() 
    {
        super("Goblin Deathfly",40, 4, 4, 100, 3, false);
        sprite = new ImageIcon("Assets/Test.png");

    }
    @Override
    public int Action()
    {
        int action = rng.nextInt(4);
        int damage = 0;
        switch (action)
        {
            case 0:
            case 1:
            case 2:
                damage = rng.nextInt(MaATK - MiATK + 1) + MiATK;
                break;
            case 3:
                this.MaATK++;
                this.dialogue = this.name + "'s buzzing grows more ferocious!";
                break;
        }
        return damage;
    }
    @Override
    public String statDrop() 
    {
        this.StatValue = 100;
        return "Speed";
    }
}
