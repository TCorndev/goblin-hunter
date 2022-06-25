package Enemies;

import javax.swing.ImageIcon;

public class GoblinArchmage extends Enemy
{
    private boolean channeling = false;
    public GoblinArchmage()
    {
        super("Goblin Archmage",80, 5, 7, 15, 2,false);
        sprite = new ImageIcon("Assets/Test.png");

    }
    @Override
    public int Action() 
    {
        int action = rng.nextInt(2);
        int damage = 0;
        int spellaction = 2;
        int spelldamage = 60;
        if (channeling == true)
        {
            action = spellaction;
        }
        switch(action)
        {
            case 0:
                damage = rng.nextInt(MaATK - MiATK + 1) + MiATK;
                break;
            case 1:
                this.dialogue = this.name + " begins charging a powerful spell!";
                channeling = true;
                break;
            case 2:
                damage = spelldamage;
                channeling = false;
                break;
        }
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
            stat = "MMP";
            this.StatValue = 999;
            break;
            case 1:
            stat = "MaSOL";
            this.StatValue = 100;
            break;
        } 
        return stat;
    }
}
