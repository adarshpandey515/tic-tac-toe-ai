import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PageSwitchingDemo extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    // private JButton[][] buttons

    public PageSwitchingDemo() {
        setTitle("Page Switching Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        JButton[][] buttons;
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        JPanel homePage = new JPanel();
        
        
                JPanel enterPage = new JPanel();
                enterPage.setLayout(new GridLayout(3, 3));
                buttons = new JButton[3][3];
                for(int row = 0; row < 3; ++row) {
                    for(int col = 0; col < 3; ++col) {
                       buttons[row][col] = new JButton("");
                      buttons[row][col].setFont(new Font("Arial", 0, 60));
                        
                       enterPage.add(buttons[row][col]);
                    }
                 }
                homePage.add(new JLabel("Welcome to the Home Page"));


        cardPanel.add(homePage, "home");
        cardPanel.add(enterPage, "enter");

        JButton homeButton = new JButton("Go to Home Page");
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "home");
            }
        });

        JButton enterButton = new JButton("Go to Enter Page");
        enterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "enter");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(homeButton);
        buttonPanel.add(enterButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(cardPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PageSwitchingDemo());
    }
}
