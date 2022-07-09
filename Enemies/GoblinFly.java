package Enemies;


public class GoblinFly extends Enemy 
{
    public GoblinFly()
    {
        super("Goblin Fly",5, 1, 1, 30, 2,false);

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
        String stat = "Speed";
        this.StatValue = 18;
        return stat;
    }
}
