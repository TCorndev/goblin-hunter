package Enemies;

public class GoblinDarkMage extends Enemy
{
    private boolean channeling = false;
    public GoblinDarkMage()
    {
        super("Goblin Dark Mage",40, 3, 4, 15, 30,false);

    }
    @Override
    public int Action() 
    {
        int action = rng.nextInt(2);
        int damage = 0;
        int spellaction = 2;
        int spelldamage = 20;
        if (channeling == true)
        {
            action = spellaction;
        }
        switch(action)
        {
            case 0:
                damage = rng.nextInt(MaATK - MiATK + 1) + MiATK;
                break;
            case 1:
                this.dialogue = this.name + " begins charging a powerful spell!";
                channeling = true;
                break;
            case 2:
                damage = spelldamage;
                channeling = false;
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
            this.StatValue = 100;
            break;
            case 1:
            stat = "MaSOL";
            this.StatValue = 20;
            break;
        } 
        return stat;
    }
}
