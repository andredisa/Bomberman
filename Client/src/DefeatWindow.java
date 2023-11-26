import javax.swing.*;
import java.awt.*;

public class DefeatWindow extends JFrame {

    public DefeatWindow() {
        setTitle("scarso");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel to hold the components
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Load the image
        ImageIcon imageIcon = createImageIcon("img/sconfitta.png");
        if (imageIcon != null) {
            JLabel imageLabel = new JLabel(imageIcon);
            JLabel textLabel = new JLabel("Hai perso");
            textLabel.setFont(new Font("Arial", Font.BOLD, 40));
            textLabel.setHorizontalAlignment(JLabel.CENTER);

            // Add the image and text labels to the panel
            panel.add(textLabel, BorderLayout.NORTH);
            panel.add(imageLabel, BorderLayout.CENTER);

            // Set the size of the window
            setSize(600, 600);
            add(panel);
            setLocationRelativeTo(null);
        } else {
            System.err.println("Error loading image.");
        }
    }

    private ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
