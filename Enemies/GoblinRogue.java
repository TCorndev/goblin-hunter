package Enemies;

import javax.swing.ImageIcon;

public class GoblinRogue extends Enemy 
{
    private int attackcount = 3;
    private int attacknumber = 0;
    private boolean flurry = false;
    public GoblinRogue() 
    {
        super("Goblin Rogue",50, 2, 4, 25, 3, false);
        sprite = new ImageIcon("Assets/Test.png");

    }
    @Override
    public int Action()
    {
        int action = rng.nextInt(4);
        int damage = 0;
        int flurryaction = 4;
        if (flurry == true)
        {
            action = flurryaction;
        }
        switch (action)
        {
            case 0:
            case 1:
            case 2:
                damage = rng.nextInt(MaATK - MiATK + 1) + MiATK;
                break;
            case 3:
                this.dialogue = this.name + " prepares a flurry of attacks...";
                flurry = true;
                break;
            case 4:
                if (attacknumber < attackcount)
                {
                    this.Speed = 999;
                    attacknumber++;
                    damage = rng.nextInt(MaATK - MiATK + 1) + MiATK;
                }
                else
                {
                    flurry = false;
                    this.Speed = 25;
                    this.dialogue = this.name + "'s flurry finally ends";
                    attacknumber = 0;
                }
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
            stat = "MiATK";
            this.StatValue = 15;
            break;
            case 1:
            stat = "Speed";
            this.StatValue = 35;
            break;
        } 
        return stat;
    }
}
