import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Map extends JPanel implements KeyListener {

    public final static int WIDTH = 17;
    public final static int HEIGHT = 13;
    public final static int BLOCK_SIZE = 40;
    public final static String WALL_IMAGE = "img/tile_wall.png";
    public final static String GRASS_IMAGE = "img/tile_grass.png";
    public final static String WOOD_IMAGE = "img/tile_wood.png";
    public final static String BOMB_IMAGE = "img/bomb.png";
    public final static String PLAYER_IMAGE = "img/explosion.png";

    private int clientID;

    private BufferedImage buffer;
    public static int playerX = 1;
    public static int playerY = 1;
    private int moveSpeed = 1;
    boolean bombaPiazzata = false;

    private List<String> blocchiFissi = new ArrayList<>();
    private List<String> blocchiDistruttibili = new ArrayList<>();
    private List<String> giocatori = new ArrayList<>();

    public Map() {
        setPreferredSize(new Dimension(WIDTH * BLOCK_SIZE + 1, HEIGHT * BLOCK_SIZE + 1));
        buffer = new BufferedImage(WIDTH * BLOCK_SIZE, HEIGHT * BLOCK_SIZE, BufferedImage.TYPE_INT_ARGB);

        setFocusable(true);
        addKeyListener(this);

    }

    public void gestisciMessaggio() {
        try {
            while (true) {
                Messaggio msg = Client.riceviMessaggio();
                if (msg != null) {
                    String messageType = msg.getTipo();
    
                    switch (messageType) {
                        case "idClient":
                            clientID = msg.getIdGiocatore();
                            break;
                        case "blocchiFissi":
                            blocchiFissi = msg.getDati();
                            break;
                        case "blocchiDistruttibili":
                            blocchiDistruttibili = msg.getDati();
                            System.out.println("Ricevuto blocchiDistruttibili n blocchi: " + blocchiDistruttibili.size());
                            break;
                        case "datiGiocatori":
                            giocatori = msg.getDati();
                            break;
                        default:
                            System.out.println("Unhandled message type: " + messageType);
                            break;
                    }
                } else
                    break;
            }
            if (!giocatori.isEmpty()) {
                for (int i = 0; i < giocatori.size(); i++) {
                    String[] playerData = giocatori.get(i).split(";");
                    // Confronta l'ID come stringa, assicurati che clientID sia di tipo int
                    if (playerData.length >= 4 && playerData[3].equals(Integer.toString(clientID))) {
                        playerX = Integer.parseInt(playerData[0]);
                        playerY = Integer.parseInt(playerData[1]);
                    }
                }
            }
            ClientView.drawField(buffer.getGraphics());
            ClientView.drawBlocks(buffer.getGraphics(), blocchiFissi, WALL_IMAGE);
            ClientView.drawBlocks(buffer.getGraphics(), blocchiDistruttibili, WOOD_IMAGE);
            ClientView.drawPlayers(buffer.getGraphics(), giocatori, PLAYER_IMAGE, BOMB_IMAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gestisciMessaggio();
        g.drawImage(buffer, 0, 0, this);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int newX = playerX;
        int newY = playerY;

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
        // playerX = newX;
        // playerY = newY;

        Client.inviaCoordinate(newX, newY, bombaPiazzata);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        bombaPiazzata = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
