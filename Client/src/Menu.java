import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;

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
        frame.setSize(680, 520);

        // Imposta l'immagine di sfondo
        try {
            File imageFile = new File("E:\\progettoTecno\\Client\\src\\img\\sfondo.png");
            if (!imageFile.exists()) {
                throw new IllegalArgumentException("File non trovato: " + imageFile.getAbsolutePath());
            }

            Image backgroundImage = ImageIO.read(imageFile);
            backgroundImage = backgroundImage.getScaledInstance(frame.getWidth(), frame.getHeight(),
                    Image.SCALE_SMOOTH);

            if (backgroundImage == null) {
                throw new IllegalArgumentException("Impossibile caricare l'immagine: " + imageFile.getAbsolutePath());
            }

            JLabel background = new JLabel(new ImageIcon(backgroundImage));
            panel = new JPanel();
            panel.setOpaque(false);
            panel.setLayout(null);

            connectButton = new JButton("Connect");
            connectButton.setBackground(new Color(33, 150, 100));
            connectButton.setBounds(80, 240, 140, 40);
            connectButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
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
            loadButton.setBackground(new Color(33, 150, 100));
            loadButton.setBounds(80, 300, 140, 40);
            loadButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            loadButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (isConnected) {
                        Map map = new Map();
                        JFrame mapFrame = new JFrame("Map");
                        mapFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        mapFrame.add(map);
                        mapFrame.pack();
                        mapFrame.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Devi connetterti prima la server!");
                    }
                }
            });

            quitButton = new JButton("Exit");
            quitButton.setBackground(new Color(33, 150, 100));
            quitButton.setBounds(80, 360, 140, 40);
            quitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            quitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (isConnected) {
                        // chiudi connessione
                        Client.closeConnection();
                    }
                    System.exit(0);
                }
            });

            panel.add(connectButton);
            panel.add(loadButton);
            panel.add(quitButton);

            background.setLayout(new BorderLayout());
            background.add(panel, BorderLayout.CENTER);
            frame.setContentPane(background);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Errore durante il caricamento dell'immagine di sfondo");
        }

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Menu();
            }
        });
    }
}
