import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class slider {
    public static int level=2;
    public static void main(String[] args) {
        JFrame frame = new JFrame("Level Slider Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 100);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create a label to display the current selected level
        JLabel levelLabel = new JLabel("Easy                Medium              Hard               Impossible");

        // Create a slider
        JSlider levelSlider = new JSlider(JSlider.HORIZONTAL, 0, 3, 0);
        levelSlider.setMajorTickSpacing(1);
        levelSlider.setPaintTicks(true);

        // Define level names
        String[] levelNames = {"Easy", "Medium", "Hard", "Impossible"};

        // Add a change listener to the slider
        levelSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedIndex=levelSlider.getValue();
                levelLabel.setText("Easy                Medium              Hard               Impossible");
                switch(selectedIndex){
                    case 0:
                    level = 2;
                    break;
                case 1:
                    level = 4;
                    break;
                case 2:
                    level = 5;
                    break;
                case 3:
                    level = 11;
                    break;
                default:
                    level = 2;
                    break;
                }
                // You can use the selectedIndex here to determine the level for your game logic.
                // For example, you can call a method with the selected level as an argument.
                // handleGameLevel(selectedLevel);
                System.out.println(level);
            }
        });

        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));

        sliderPanel.add(levelLabel);
        sliderPanel.add(levelSlider);

        mainPanel.add(sliderPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
