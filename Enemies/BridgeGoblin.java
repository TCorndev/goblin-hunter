package Enemies;

public class BridgeGoblin extends Enemy
{
    boolean enrage = false;
    public BridgeGoblin()
    {
        super("Bridge Goblin", 100, 2, 5, 10, 10, true);
    }

    @Override
    public int Action() 
    {
        int damage;
        if (this.HP < 40 && enrage == false)
        {
            this.MaATK = this.MaATK + 2;
            this.Speed = this.Speed - 3;
            enrage = true;
            damage = 0;
            this.dialogue = this.name + " becomes enraged!!!";

        }
        else
        {
            damage = rng.nextInt(MaATK - MiATK + 1) + MiATK;
        }

        return damage;
    }

    @Override
    public String statDrop() 
    {
        this.StatValue = 1;
        return "MREGEN";
    }
    
}
