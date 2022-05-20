import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel {
    
    GamePanel()
    {
        this.setPreferredSize(new Dimension(1000, 1000));
        this.setLayout(null);
        this.setBackground(Color.blue);
        this.setFocusable(true);
        this.setVisible(true);
    }
}
