package Enemies;

public class RockGoblin extends Enemy
{
    private int attackcount = 3;
    private int attacknumber = 0;
    private boolean flurry = false;
    private boolean massiveAttack = false;
    public RockGoblin()
    {
        super("Rock Goblin", 1000, 5, 7, 25, 300, true);

    }

    @Override
    public int Action() 
    {
        int action = rng.nextInt(6);
        int damage = 0;
        int flurryaction = 6;
        int massiveAttackaction = 7;
        int massiveAttackdamage = 40;
        if (flurry == true)
        {
            action = flurryaction;
        }
        if (massiveAttack == true)
        {
            action = massiveAttackaction;
        }
        switch(action)
        {
            case 0:
            case 1:
            case 2:
            case 3:
                damage = rng.nextInt(MaATK - MiATK + 1) + MiATK;
                break;
            case 4:
                this.dialogue = this.name + " prepares a flurry of attacks...";
                flurry = true;
                break;
            case 5:
                this.dialogue = this.name + " prepares a massive attack!";
                massiveAttack = true;
                break;
            case 6:
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
            case 7:
                damage = massiveAttackdamage;
                massiveAttack = false;
                break;
        }

        return damage;
    }

    @Override
    public String statDrop() 
    {
        this.StatValue = 2;
        return "MREGEN";
    }
    
}
