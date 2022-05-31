package Enemies;
import java.util.Random;
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
    protected Random rng = new Random();

    Enemy(String name, int HP, int MiATK, int MaATK, int Speed, int Gold)
    {
        this.name = name;
        this.HP = HP;
        this.MHP = HP;
        this.MiATK= MiATK;
        this.MaATK = MaATK;
        this.Speed = Speed;
        this.Gold = Gold;
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
  /*  public int getATK()
    {
        return ATK;
    }
    public void setATK(int ATK)
    {
        this.ATK = ATK;
    }
    public void setSpeed(int Speed)
    {
        this.Speed = Speed;
    } */
    public abstract int Action();
    public abstract String statDrop();
}
