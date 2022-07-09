import javax.swing.JPanel;
import java.awt.*;

public class StatusPanel extends JPanel 
{
    private Player player;
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
        g.drawString("HP: " + player.getStats().get("HP") + "/" + player.getStats().get("MHP"), 0, 15);
        g.drawString("MP: " + player.getStats().get("MP") + "/" + player.getStats().get("MMP"), 0, 30);
        g.drawString("Power: " + String.format("%.2f", player.getCompletionPercentage()) + "%", 0, 90);

        g.drawString("ATK: " + player.getStats().get("MiATK") + "-" + player.getStats().get("MaATK") + "  x" + player.getSwordLevel(), 500, 15);
        g.drawString("SOL: " + player.getStats().get("MiSOL") + "-" + player.getStats().get("MaSOL"), 500, 30);
        g.drawString("DEF: " + player.getStats().get("DEF"), 500, 45);
        g.drawString("HP Regen: " + player.getStats().get("HREGEN"), 500, 60);
        g.drawString("MP Regen: " + player.getStats().get("MREGEN"), 500, 75);
        g.drawString("Speed: " + player.getStats().get("Speed"), 500, 90);

        g.drawString("Gold:" + player.getStats().get("Gold"), 800, 15);
    }
    public void setPlayer(Player player)
    {
        this.player = player;
    }
}
