package Enemies;


public class GoblinShaman extends Enemy
{
    public GoblinShaman()
    {
        super("Goblin Shaman",12, 1, 2, 7, 3,false);

    }
    @Override
    public int Action() 
    {
        int action = rng.nextInt(2);
        int damage = 0;
        int healing = 4;
        switch(action)
        {
            case 0:
                damage = rng.nextInt(MaATK - MiATK + 1) + MiATK;
                break;
            case 1:
                if(this.HP < this.MHP)
                {
                    this.HP = this.HP + healing;
                    if (this.HP > this.MHP)
                    {
                        this.HP = this.MHP;
                    }
                    this.dialogue = this.name + " restores " + healing + " health!";
                }
                else
                {
                    Action();
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
            stat = "MMP";
            this.StatValue = 20;
            break;
            case 1:
            stat = "MaSOL";
            this.StatValue = 10;
            break;
        } 
        return stat;
    }
}
