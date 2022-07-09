package Enemies;
import java.util.Random;

import javax.swing.ImageIcon;
public abstract class Enemy 
{
    
    protected int HP;
    protected int MHP;
    protected int MiATK;
    protected int MaATK;
    protected int Speed;
    protected int Gold;
    protected int StatValue;
    protected String name;
    protected boolean boss;
    protected String dialogue;
    protected Random rng = new Random();
    protected ImageIcon sprite;
    private ImageIcon background;

    Enemy(String name, int HP, int MiATK, int MaATK, int Speed, int Gold, boolean boss)
    {
        this.name = name;
        this.HP = HP;
        this.MHP = HP;
        this.MiATK= MiATK;
        this.MaATK = MaATK;
        this.Speed = Speed;
        this.Gold = Gold;
        this.boss = boss;
        sprite = new ImageIcon("Assets/Enemies/" + this.name + ".png");
    }
    public int getHP()
    {
        return this.HP;
    }
    public void setHP(int HP)
    {
        this.HP = HP;
    }
    public int getSpeed()
    {
        return this.Speed;
    }
    public int getGold()
    {
        return this.Gold;
    }
    public int getStatValue()
    {
        return this.StatValue;
    }
    public String getName()
    {
        return this.name;
    }
    public boolean getBoss()
    {
        return this.boss;
    }
    public String getDialogue()
    {
        return this.dialogue;
    }
    public ImageIcon getSprite()
    {
        return sprite;
    }
    public ImageIcon getBackground(int level)
    {
        switch (level)
        {
            case 0:
                background = new ImageIcon("Assets/Tutorial Battle.png");
                break;
            case 1:
                background = new ImageIcon("Assets/Fields Battle.png");
                break;
            case 2:
                background = new ImageIcon("Assets/Cave Battle.png");
                break;
            case 3:
                background = new ImageIcon("Assets/Castle Battle.png");
                break;
        }
        return background;
    }
    public abstract int Action();
    public abstract String statDrop();
}
