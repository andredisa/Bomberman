import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Map extends JPanel implements KeyListener {

    private final int width = 17;
    private final int height = 13;
    private final int blockSize = 40;
    private final String wallImage = "img/tile_wall.png";
    private final String grassImage = "img/tile_grass.png";
    private final String woodImage = "img/tile_wood.png";

    private Image imgPlayer;
    private BufferedImage buffer;
    private int playerX = 1;
    private int playerY = 1;
    private int moveSpeed = 1;

    private List<String> blocchiFissi = new ArrayList<>();
    private List<String> blocchiDistruttibili = new ArrayList<>();
    private List<String> giocatori = new ArrayList<>();

    public Map() {
        setPreferredSize(new Dimension(width * blockSize + 1, height * blockSize + 1));
        buffer = new BufferedImage(width * blockSize, height * blockSize, BufferedImage.TYPE_INT_ARGB);

        try {
            imgPlayer = ImageIO.read(Map.class.getResource("img/player2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setFocusable(true);
        addKeyListener(this);
    }

    public void gestisciMessaggio() {
        try {
            Messaggio ma = Client.riceviMessaggio();

            if (ma != null) {
                String messageType = ma.getTipo();

                switch (messageType) {
                    case "blocchiFissi":
                        blocchiFissi = ma.getDati();
                        break;
                    case "blocchiDistruttibili":
                        blocchiDistruttibili = ma.getDati();
                        break;
                    default:
                        System.out.println("Unhandled message type: " + messageType);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gestisciMessaggio();
        disegnaCampo(buffer.getGraphics());
        disegnaBlocchi(buffer.getGraphics(), blocchiFissi, wallImage);
        disegnaBlocchi(buffer.getGraphics(), blocchiDistruttibili, woodImage);
        disegnaPlayer(buffer.getGraphics());

        g.drawImage(buffer, 0, 0, this);
    }

    public void disegnaCampo(Graphics g) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                drawBlock(g, i, j, grassImage);
            }
        }
    }

    public void drawBlock(Graphics g, int x, int y, String imagePath) {
        try {
            Image blockImage = ImageIO.read(Map.class.getResource(imagePath));
            g.drawImage(blockImage, x * blockSize, y * blockSize, blockSize, blockSize, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disegnaBlocchi(Graphics g, List<String> blocchi, String imagePath) {
        try {
            for (String s : blocchi) {
                String[] blockData = s.split(";");
                if (blockData.length == 3) {
                    int x = Integer.parseInt(blockData[0].trim());
                    int y = Integer.parseInt(blockData[1].trim());
                    drawBlock(g, x, y, imagePath);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disegnaPlayer(Graphics g) {
        g.drawImage(imgPlayer, playerX * blockSize, playerY * blockSize, blockSize, blockSize, null);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                playerX -= moveSpeed;
                break;
            case KeyEvent.VK_RIGHT:
                playerX += moveSpeed;
                break;
            case KeyEvent.VK_UP:
                playerY -= moveSpeed;
                break;
            case KeyEvent.VK_DOWN:
                playerY += moveSpeed;
                break;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Add any necessary code for key released events
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Add any necessary code for key typed events
    }
}
