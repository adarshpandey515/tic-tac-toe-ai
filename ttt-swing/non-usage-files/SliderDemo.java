import javax.swing.*;
import java.awt.*;

public class SliderDemo extends JFrame {
    private JSlider slider1;
    private JSlider slider2;

    public SliderDemo() {
        setTitle("Slider Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        slider1 = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        slider2 = new JSlider(JSlider.HORIZONTAL, 0, 100, 25);

        slider1.setMajorTickSpacing(20);
        slider1.setMinorTickSpacing(5);
        slider1.setPaintTicks(true);
        slider1.setPaintLabels(true);

        slider2.setMajorTickSpacing(20);
        slider2.setMinorTickSpacing(5);
        slider2.setPaintTicks(true);
        slider2.setPaintLabels(true);

        JPanel sliderPanel = new JPanel(new GridLayout(2, 1));
        sliderPanel.add(slider1);
        sliderPanel.add(slider2);

        add(sliderPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SliderDemo());
    }
}
