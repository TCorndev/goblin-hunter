import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.*;

public class MenuPanel extends JPanel implements ActionListener
{
    private JButton StartButton = new JButton();
    private JButton ExitButton = new JButton();
    private JButton LoadButton = new JButton();
    private ImageIcon title = new ImageIcon("Assets/Title.png");
    
    MenuPanel()
    {
        this.setBounds(0, 0, 1000, 1000);
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setVisible(true);
        this.setLayout(null);
        this.add(StartButton);
        this.add(ExitButton);
        this.add(LoadButton);
        StartButton.setBounds(450, 700, 100, 25);
        StartButton.setText("Start!");
        StartButton.addActionListener(this);
        LoadButton.setBounds(450, 800, 100, 25);
        LoadButton.setText("Load");
        LoadButton.addActionListener(this);
        ExitButton.setBounds(450, 900, 100, 25);
        ExitButton.setText("Exit Game");
        ExitButton.addActionListener(this);
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g)
    {
        g.drawImage(title.getImage(), 250, 300, null);
    }
    /*Called when a button is pushed */
    public void actionPerformed(ActionEvent event)
    {
        if (event.getSource() == StartButton) 
        {
            GoblinHunter2.start();
        }
        if (event.getSource() == LoadButton)
        {
            try 
            {
                GoblinHunter2.load();
            } catch (Exception e) 
            {
                System.out.println(e);
            }
        }
        if (event.getSource() == ExitButton)
        {
            GoblinHunter2.close();
        }
    }
}
