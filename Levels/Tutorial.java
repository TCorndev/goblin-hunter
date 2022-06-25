package Levels;

import javax.swing.ImageIcon;

import Enemies.Enemy;

public class Tutorial extends Level 
{
    private int babyGoblinEvent = 1;
    private int tutorialExit = 2;

    public Tutorial(int width, int height)
    {
        super(width, height);
        this.image = new ImageIcon("Assets/Tutorial.png");
        setup();
    }

    @Override
    protected void setup() 
    {
        /* Signs */
        special[20][32] = true;
        special[16][25] = true;
        special[10][25] = true;
        special[4][25] = true;
        special[20][12] = true;
        special[20][6] = true;

        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                occupied[i][j] = true;
            }
        }
        /* Main Hall */
        for (int j = 0; j < 35; j ++)
        {
            occupied[20][j] = false;
        }
        /* Side Hall */
        for (int i = 3; i <20; i++ )
        {
            occupied[i][25] = false;
        }
        /* Rooms */
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j ++)
            {
                if (special[i][j] == true)
                {
                    /* Above Sign */
                    occupied[i - 1][j - 1] = false;
                    occupied[i][j - 1] = false;
                    occupied[i + 1][j - 1] = false;

                    /* Next to Sign */
                    occupied[i - 1][j] = false;
                    occupied[i + 1][j] = false;
                    
                    /* Below Sign */
                    occupied[i - 1][j + 1] = false;
                    occupied[i][j + 1] = false;
                    occupied[i + 1][j + 1] = false;
                }
            }
        }

        /*Baby Goblin */
        special[20][9] = true;

        /*Exit */
        special[20][2] = true;
    }

    @Override
    public int special(int xPos, int yPos) 
    {
        int eventNumber = 0;
        /* Sign dialogue */
        if (xPos == 20 && yPos == 32)
        {
            this.dialogue = "Welcome to Goblin Hunter.";
            this.dialogue2 = "";
        }
        if (xPos == 16 && yPos == 25)
        {
            this.dialogue = "ATK is the damage of your attacks.";
            this.dialogue2 = "This is multipled by your sword level.";
        }
        if (xPos == 10 && yPos == 25)
        {
            this.dialogue = "SOL determines the power of your spells";
            this.dialogue2 = "Affects Heal, Fire, and Haste.";
        }
        if (xPos == 4 && yPos == 25)
        {
            this.dialogue = "More speed means more attacks.";
            this.dialogue2 = "DEF reduces damage taken.";
        }
        if (xPos == 20 && yPos == 12)
        {
            this.dialogue = "Killing enemies will improve your stats.";
            this.dialogue2 = "Step on the circle to fight your first enemy.";
        }
        if (xPos == 20 && yPos == 6)
        {
            this.dialogue = "Goblins drop gold when defeated.";
            this.dialogue2 = "Use your gold to buy equipment and spells.";
        }
        /* Baby Goblin Boss Fight */
        if (xPos == 20 && yPos == 9)
        {
            eventNumber = babyGoblinEvent;
        }
        /* Exit */
        if (xPos == 20 && yPos == 2)
        {
            eventNumber = tutorialExit;
        }
        return eventNumber;
    }

    @Override
    public Enemy RandomEncounter() 
    {
        //No encounters in the tutorial
        return null;
    }
}
