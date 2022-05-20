import javax.swing.JPanel;
import java.util.*;
import java.awt.*;

public class StatusPanel extends JPanel 
{
    
    HashMap<String, Integer> stats = new HashMap<>();
    StatusPanel()
    {
        this.setBounds(0, 0, 1000, 100);
        this.setBackground(Color.gray);
        this.setFocusable(true);
        this.setVisible(true);
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g)
    {
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("HP: " + stats.get("HP") + "/" + stats.get("MHP"), 0, 15);
        g.drawString("MP: " + stats.get("MP") + "/" + stats.get("MMP"), 0, 30);

        g.drawString("ATK: " + stats.get("MiATK") + "-" + stats.get("MaATK"), 500, 15);
        g.drawString("SOL: " + stats.get("MiSOL") + "-" + stats.get("MaSOL"), 500, 30);
        g.drawString("DEF: " + stats.get("DEF"), 500, 45);
        g.drawString("HP Regen: " + stats.get("HREGEN"), 500, 60);
        g.drawString("MP Regen: " + stats.get("MREGEN"), 500, 75);
        g.drawString("Speed: " + stats.get("Speed"), 500, 90);
    }
    public void setStats(HashMap<String, Integer> stats)
    {
        this.stats = stats;
    }
}
