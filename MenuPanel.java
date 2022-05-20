import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.*;

public class MenuPanel extends JPanel implements ActionListener
{
    JButton StartButton = new JButton();
    JButton ExitButton = new JButton();

    MenuPanel()
    {
        this.setBounds(0, 0, 1000, 1000);
        this.setBackground(Color.green);
        this.setFocusable(true);
        this.setVisible(true);
        this.setLayout(null);
        this.add(StartButton);
        this.add(ExitButton);
        StartButton.setBounds(450, 700, 100, 25);
        StartButton.setText("Start!");
        StartButton.addActionListener(this);
        ExitButton.setBounds(450, 900, 100, 25);
        ExitButton.setText("Exit Game");
        ExitButton.addActionListener(this);
    }    
    public void actionPerformed(ActionEvent event)
    {
        if (event.getSource() == StartButton) 
        {
            GoblinHunter2.start();
        }
        if (event.getSource() == ExitButton)
        {
            GoblinHunter2.close();
        }
    }
}
