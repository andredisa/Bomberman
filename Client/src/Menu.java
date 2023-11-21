import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {

    JFrame frame;
    JPanel panel;
    JButton connectButton;
    JButton loadButton;
    JButton quitButton;

    boolean isConnected = false;

    public Menu() {
        frame = new JFrame();
        frame.setTitle("Bomberman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Bomberman");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        connectButton = new JButton("Connect");
        connectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isConnected) {
                    isConnected = true;
                    Client.connect();
                    JOptionPane.showMessageDialog(frame, "Connessione effettuata");
                } else {
                    JOptionPane.showMessageDialog(frame, "Sei già connesso al server!");
                }
            }
        });

        loadButton = new JButton("Load");
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isConnected) {
                    Map map = new Map();
                    JFrame mapFrame = new JFrame("Map");
                    mapFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    mapFrame.add(map);
                    mapFrame.pack();
                    //frame.setVisible(false);
                    mapFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(frame, "Devi connetterti prima la server!");
                }
            }
        });

        quitButton = new JButton("Exit");
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isConnected) {
                    //chiudi connessione
                    //Client.closeConnection();
                }
                System.exit(0);
            }
        });

        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(connectButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(loadButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(quitButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}