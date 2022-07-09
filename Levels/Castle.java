package Levels;

import java.util.Random;

import javax.swing.ImageIcon;

import Enemies.*;

public class Castle extends Level
{
    private int exitToCave = 10;
    private int goblinkingEvent = 11;
    public Castle(int width, int height)
    {
        super(width, height);
        image = new ImageIcon("Assets/Castle.png");
        setup();
    }

    @Override
    protected void setup() 
    {
        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                occupied[i][j] = true;
            }
        }
        int hallwayX = 19;
        /* Bridge to throne room */
        for(int j = height - 1; j > 15; j--)
        {
            occupied[hallwayX][j] = false;
            if( j < 28 && j > 22 || j < 18)
            {
                occupied[hallwayX + 1][j] = false;
                occupied[hallwayX - 1][j] = false;
            }
            if ( j < 27 && j > 23)
            {
                occupied[hallwayX + 2][j] = false;
                occupied[hallwayX - 2][j] = false;
            }
            if (j == 25)
            {
                occupied[hallwayX + 3][j] = false;
                occupied[hallwayX - 3][j] = false;
            }
        }
        /* Goblin King */
        special[19][16] = true;

        /* Exit to Cave */
        special[19][35] = true;
        
    }

    @Override
    public int special(int xPos, int yPos) 
    {
        int eventnumber = 0;
        if (xPos == 19 && yPos == 35)
        {
            eventnumber = exitToCave;
        }
        if (xPos == 19 && yPos == 16)
        {
            eventnumber = goblinkingEvent;
            this.dialogue = "You've won the game!";
            this.dialogue2 = "Now get 100% power!";
        }
        return eventnumber;
    }

    @Override
    public Enemy RandomEncounter() 
    {
        Random rng = new Random();
        Enemy enemy;
        int encounter = rng.nextInt(6);
        switch(encounter)
        {
            case 0:
                enemy = new GoblinGuardian();
                break;
            case 1:
                enemy = new GoblinKnight();
                break;
            case 2:
                enemy = new GoblinDeathfly();
                break;
            case 3:
                enemy = new GoblinArchmage();
                break;
            case 4:
                enemy = new GoblinPriest();
                break;
            case 5:
                enemy = new GoblinArcher();
                break;
            default:
                System.out.println("RandomEncounter 1 default called"); //Change to throw exception
                enemy = new Goblin();
                break;
        }
        return enemy;    
    }
    
}
