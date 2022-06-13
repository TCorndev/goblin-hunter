package Levels;

import Enemies.Enemy;

public abstract class Level 
{
    protected int width;
    protected int height;
    protected boolean[][] occupied;
    protected boolean[][] special;
    protected String dialogue;

    Level(int width, int height)
    {
        this.width = width;
        this.height = height;
        occupied = new boolean[width][height];
        special = new boolean[width][height];
    }
    public boolean[][] getOccupied()
    {
        return this.occupied;
    }
    public boolean[][] getSpecial()
    {
        return this.special;
    }
    public String getDialogue()
    {
        return this.dialogue;
    }
    protected abstract void setup();
    public abstract int special(int xPos, int yPos);
    public abstract Enemy RandomEncounter();
}
