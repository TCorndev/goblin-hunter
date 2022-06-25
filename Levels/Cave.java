package Levels;

import java.util.Random;

import javax.swing.ImageIcon;

import Enemies.*;

public class Cave extends Level
{
    private int exitToFields = 6;
    private int healingFountain = 7;
    private int rockGoblinEvent = 8;
    private int exitToCastle = 9;
    public Cave(int width, int height)
    {
        super(width, height);
        image = new ImageIcon("Assets/Cave.png");
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
        /*Main Hallways */
        for(int j = 0; j < height; j++)
        {
            if (j > 4)
            {
                occupied[4][j] = false;
            }
            if (j < 32)
            {
                occupied[33][j] = false;
            }
        }
        /*Side Hallway */
        for(int i = 4; i < 34; i++)
        {
            occupied[i][31] = false;
        }
        /*Exit to fields */
        special[4][35] = true;
        /*Healing Fountain */
        special[4][5] = true;
        /*Rock Goblin */
        special[33][2] = true;
        /*Exit to castle */
        special[33][0] = true;
        

    }

    @Override
    public int special(int xPos, int yPos) 
    {
        int eventNumber = 0;
        if (xPos == 4 && yPos == 35)
        {
            eventNumber = exitToFields;
        }
        if (xPos == 4 && yPos == 5)
        {
            eventNumber = healingFountain;
            this.dialogue = "Ahh, refereshing!";
            this.dialogue2 = "(Fully Healed)";
        }
        if (xPos == 33 && yPos == 2)
        {
            eventNumber = rockGoblinEvent;
        }
        if (xPos == 33 && yPos == 0)
        {
            eventNumber = exitToCastle;
        }
        return eventNumber;
    }

    @Override
    public Enemy RandomEncounter() 
    {
        Random rng = new Random();
        Enemy enemy;
        int encounter = rng.nextInt(10);
        switch(encounter)
        {
            case 0:
            case 1:
            case 2:
                enemy = new HobGoblin();
                break;
            case 3:
            case 4:
            case 5:
                enemy = new GoblinDarkMage();
                break;
            case 6:
            case 7:
            case 8:
                enemy = new GoblinSpider();
                break;
            case 9:
                enemy = new GoblinRogue();
                break;
            default:
                enemy = new Goblin();
                break;
        }
        return enemy;
    }
    
}
