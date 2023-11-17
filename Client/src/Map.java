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
                    case "datiGiocatori":
                        giocatori = ma.getDati();
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
        disegnaGiocatori(buffer.getGraphics(), giocatori);

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
     public void disegnaGiocatori(Graphics g, List<String> giocatori) {
        try {
            for (String s : giocatori) {
                String[] blockData = s.split(";");
                if (blockData.length == 3) {
                    int x = Integer.parseInt(blockData[0].trim());
                    int y = Integer.parseInt(blockData[1].trim());
                    boolean b = Boolean.parseBoolean(blockData[2].trim());
                    disegnaPlayer(g, x, y);
                    if (b) {
                        drawBlock(g, x, y, "img/bomb.png");
                    }
                }
            }
        } catch (Exception e) {
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

    public void disegnaPlayer(Graphics g , int x , int y) {
        g.drawImage(imgPlayer, x * blockSize, y * blockSize, blockSize, blockSize, null);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int newX = playerX;
        int newY = playerY;
        boolean bombaPiazzata = false;
        switch (keyCode) {
            case KeyEvent.VK_UP:
                newY -= moveSpeed;
                break;
            case KeyEvent.VK_DOWN:
                newY += moveSpeed;
                break;
            case KeyEvent.VK_LEFT:
                newX -= moveSpeed;
                break;
            case KeyEvent.VK_RIGHT:
                newX += moveSpeed;
                break;
            case KeyEvent.VK_SPACE:
                bombaPiazzata = true;
                break;
        }
        playerX = newX;
        playerY = newY;

        Client.inviaCoordinate(newX, newY, bombaPiazzata);
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
