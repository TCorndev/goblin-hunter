package Enemies;

public class BridgeGoblin extends Enemy
{
    public BridgeGoblin()
    {
        super("Bridge Goblin", 100, 2, 5, 10, 10, true);
    }

    @Override
    public int Action() 
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String statDrop() 
    {
        this.StatValue = 1;
        return "HREGEN";
    }
    
}
