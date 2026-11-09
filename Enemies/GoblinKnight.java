package Enemies;


public class GoblinKnight extends Enemy 
{
    public GoblinKnight() 
    {
        super("Goblin Knight",200, 10, 17, 25, 750, false);

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
