package Enemies;


public class GoblinArcher extends Enemy 
{
    public GoblinArcher() 
    {
        super("Goblin Archer",180, 12, 13, 45, 500, false);
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
