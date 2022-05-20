import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

public class CombatPanel extends JPanel implements KeyListener
{
    int EHP;
    CombatPanel()
    {
        this.setBounds(0, 100, 1000, 900);
        this.setBackground(Color.blue);
        this.setFocusable(true);
        this.setVisible(true);
        this.setLayout(null);

    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g)
    {
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("HP: " + EHP, 450, 780);
        g.setColor(Color.black);
        g.fillRect(450, 800, 80, 100);
        g.setColor(Color.white);
        g.drawString("Attack", 455, 820);
        g.drawString("Defend", 455, 840);
        g.drawString("Magic", 455, 860);
    }
    public void setEHP(int EHP)
    {
        this.EHP = EHP;
    }
	@Override
	public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        if (e.getKeyCode() == 10 || e.getKeyCode() == 32)
        {
            GoblinHunter2.attack();
            if(EHP <= 0)
            {
                GoblinHunter2.map();
                GoblinHunter2.game.removeKeyListener(this);
                GoblinHunter2.EHP = 100; //Replace this with a combatend method in main
            }
        }
        else
        {
            System.out.println(e.getKeyCode());
        }
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
