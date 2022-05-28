import java.util.Random;

public class Goblin extends Enemy 
{
    Goblin() 
    {
        super(10, 1, 3, 9, 3);
    }
    @Override
    public int Action()
    {
        Random rng = new Random();
        int damage = rng.nextInt(MaATK - MiATK + 1) + MiATK;
        return damage;
    }
    @Override
    public String statDrop() 
    {
        Random rng = new Random();
        String stat = null;
        int drop = rng.nextInt(2);
         switch(drop)
        {
            case 0:
            stat = "MHP";
            this.StatValue = 20;
            break;
            case 1:
            stat = "MaATK";
            this.StatValue = 10;
            break;
        } 
        return stat;
    }
}
