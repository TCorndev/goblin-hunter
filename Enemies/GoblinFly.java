package Enemies;

public class GoblinFly extends Enemy 
{
    public GoblinFly()
    {
        super("Goblin Fly",5, 1, 2, 30, 1,false);
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
        String stat = "Speed";
        this.StatValue = 18;
        return stat;
    }
}
