package Enemies;

public class GoblinKing extends Enemy
{
    private int attackcount = 5;
    private int attacknumber = 0;
    private int baseSpeed = 80;
    private boolean flurry = false;
    private boolean massiveAttack = false;
    private boolean enrage = false;
    public GoblinKing()
    {
        super("Goblin King", 10000, 10, 20, 80, 100000, true);
    }

    @Override
    public int Action() 
    {
        int action = rng.nextInt(7);
        int damage = 0;
        int flurryaction = 7;
        int massiveAttackaction = 8;
        int massiveAttackdamage = 200;
        int healing = 200;
        if (flurry == true)
        {
            action = flurryaction;
        }
        if (massiveAttack == true)
        {
            action = massiveAttackaction;
        }
        if (this.HP < 4000 && enrage == false)
        {
            this.MaATK = this.MaATK * 2;
            this.MiATK = this.MiATK * 2;
            baseSpeed = baseSpeed + 10;
            this.Speed = baseSpeed;
            enrage = true;
            this.dialogue = this.name + " gains absurd power!!!";
        }
        else
        {
            switch(action)
            {
                case 0:
                case 1:
                case 2:
                case 3:
                    damage = rng.nextInt(MaATK - MiATK + 1) + MiATK;
                    break;
                case 4:
                    this.dialogue = this.name + " prepares a magic dance.";
                    flurry = true;
                    break;
                case 5:
                    this.dialogue = this.name + " channels Thunder and Lightning...";
                    massiveAttack = true;
                    break;
                case 6:
                    if(this.HP < this.MHP)
                    {
                        this.HP = this.HP + healing;
                        if (this.HP > this.MHP)
                        {
                            this.HP = this.MHP;
                        }
                        this.dialogue = this.name + " uses the power of voodoo to heal for " + healing;
                    }
                    else
                    {
                        Action();
                    }
                    break;                        
                case 7:
                    if (attacknumber < attackcount)
                    {
                        this.Speed = 999;
                        attacknumber++;
                        damage = rng.nextInt(MaATK - MiATK + 1) + MiATK;
                    }
                    else
                    {
                        flurry = false;
                        this.Speed = baseSpeed;
                        this.dialogue = this.name + "'s dancing finally ends.";
                        attacknumber = 0;
                    }
                    break;
                case 8:
                    damage = massiveAttackdamage;
                    massiveAttack = false;
                    break;
                }
        }   
        return damage;
    }

    @Override
    public String statDrop() 
    {
        this.StatValue = 0;
        return "MHP";
    }
    
}
