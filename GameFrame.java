import javax.swing.JFrame;

public class GameFrame extends JFrame 
{
    GameFrame()
    {
        this.setTitle("Goblin Hunter 2");
      //  this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
