package Levels;

import javax.swing.ImageIcon;

import Enemies.Enemy;

public abstract class Level 
{
    protected int width;
    protected int height;
    protected boolean[][] occupied;
    protected boolean[][] special;
    protected String dialogue;
    protected String dialogue2;
    protected ImageIcon image;

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
    public String getDialogue2()
    {
        return this.dialogue2;
    }
    public ImageIcon getImageIcon()
    {
        return image;
    }
    protected abstract void setup();
    public abstract int special(int xPos, int yPos);
    public abstract Enemy RandomEncounter();
}
