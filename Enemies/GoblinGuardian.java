package Enemies;

public class GoblinGuardian extends Enemy 
{
    private boolean enrage = false;
    public GoblinGuardian() 
    {
        super("Goblin Guardian",2000, 1, 1, 35, 1000, false);

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
            this.MiATK++;
            this.dialogue = this.name + " grows stronger...";
            enrage = false;
        }
        return damage;
    }
    @Override
    public String statDrop() 
    {
        this.StatValue = 250;
        return "MHP";
    }
}
