package Levels;

import Enemies.Enemy;

public class Tutorial extends Level 
{
    Tutorial(int width, int height)
    {
        super(width, height);
    }

    @Override
    protected void setup() 
    {

    }

    @Override
    public int special(int xPos, int yPos) 
    {
        return 0;
        
    }

    @Override
    public Enemy RandomEncounter() 
    {
        return null;
    }
}
