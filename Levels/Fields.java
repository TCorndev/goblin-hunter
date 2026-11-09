package Levels;

import java.util.Random;

import javax.swing.ImageIcon;

import Enemies.*;

public class Fields extends Level
{
    private int bridgeEvent = 3;
    private int caveExit = 4;
    private int shop = 5;
    public Fields(int width, int height)
    {
        super(width, height);
        image = new ImageIcon("Assets/Fields.png");
        setup();
    }

    @Override
    protected void setup() 
    {
        /* Mountain */
        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                occupied[i][j] = true;
            }
            /* River */
            occupied[i][8] = true;
        }
        /* Cave Entrance */
        occupied[4][3] = false;
        special[4][3] = true;
        /* Bridge */
        occupied[20][8] = false;
        special[20][8] = true;

        /* Trees */
        occupied[7][21] = true;
        occupied[31][31] = true;

        /* Shop */
        special[14][26] = true;
    }

    @Override
    public int special(int xPos, int yPos) 
    {
        int eventNumber = 0;
        if (xPos == 20 && yPos == 8)
        {
            eventNumber = bridgeEvent;
        }
        else if (xPos == 4 && yPos == 3)
        {
            eventNumber = caveExit;
        }
        else if (xPos == 14 && yPos == 26)
        {
            eventNumber = shop;
        }
        return eventNumber;
    }

    @Override
    public Enemy RandomEncounter() 
    {
        Random rng = new Random();
        Enemy enemy;
        int encounter = rng.nextInt(9);
        switch(encounter)
        {
            case 0:
            case 1:
            case 2:
            case 3:
                enemy = new Goblin();
                break;
            case 4:
            case 5:
            case 6:
            case 7:
                enemy = new GoblinShaman();
                break;
            case 8:
            case 9:
                enemy = new GoblinFly();
                break;
            default:
                System.out.println("RandomEncounter 1 default called"); //Change to throw exception
                enemy = new Goblin();
                break;
        }
        return enemy;
    }
    
}
