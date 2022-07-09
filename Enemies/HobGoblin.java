package Enemies;


public class HobGoblin extends Enemy 
{
    public HobGoblin() 
    {
        super("Hobgoblin",60, 3, 5, 20, 50, false);

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
