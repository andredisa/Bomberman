import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Map extends JPanel implements KeyListener {

    public final static int WIDTH = 17;
    public final static int HEIGHT = 13;
    public final static int BLOCK_SIZE = 40;
    public final static String GRASS_IMAGE = "img/tile_grass.png";
    public final static String EXPLOSION_IMAGE = "img/explosion.png";

    public static int clientID;

    private BufferedImage buffer;
    public static int playerX = 1;
    public static int playerY = 1;
    private int moveSpeed = 1;
    boolean bombaPiazzata = false;

    private List<String> blocchiFissi = new ArrayList<>();
    private List<String> blocchiDistruttibili = new ArrayList<>();
    private List<String> giocatori = new ArrayList<>();
    private List<String> bomba = new ArrayList<>();
    private List<String> esplosione = new ArrayList<>();

    private Timer timer;

    public Map() {
        setPreferredSize(new Dimension(WIDTH * BLOCK_SIZE + 1, HEIGHT * BLOCK_SIZE + 1));
        buffer = new BufferedImage(WIDTH * BLOCK_SIZE, HEIGHT * BLOCK_SIZE, BufferedImage.TYPE_INT_ARGB);

        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(8, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gestisciMessaggio();
                repaint();
            }
        });
        timer.start();

        setFocusable(true);
        addKeyListener(this);

    }

    public void gestisciMessaggio() {
        try {
            Messaggio msg = Client.receiveMessage();
            while (msg != null) {
                String messageType = msg.getTipo();
                switch (messageType) {
                    case "idClient":
                        clientID = msg.getId();
                        break;
                    case "blocchiFissi":
                        blocchiFissi = msg.getDati();
                        break;
                    case "blocchiDistruttibili":
                        blocchiDistruttibili = msg.getDati();
                        break;
                    case "datiGiocatori":
                        giocatori = msg.getDati();
                        break;
                    case "datiBomba":
                        bomba = msg.getDati();
                        break;
                    case "datiEsplosione":
                        esplosione = msg.getDati();
                        break;
                    default:
                        System.out.println("Unhandled message type: " + messageType);
                        break;
                }
                msg = Client.receiveMessage();
            }

            if (!giocatori.isEmpty()) {
                for (int i = 0; i < giocatori.size(); i++) {
                    String[] playerData = giocatori.get(i).split(";");
                    // Confronta l'ID come stringa, assicurati che clientID sia di tipo int
                    if (playerData[3].equals(Integer.toString(clientID))) {
                        playerX = Integer.parseInt(playerData[0]);
                        playerY = Integer.parseInt(playerData[1]);
                    }
                }
            }
            ClientView.drawField(buffer.getGraphics());
            ClientView.drawBlocks(buffer.getGraphics(), blocchiFissi);
            ClientView.drawBlocks(buffer.getGraphics(), blocchiDistruttibili);
            ClientView.drawPlayers(buffer.getGraphics(), giocatori);
            ClientView.drawBomb(buffer.getGraphics(), bomba);
            ClientView.drawExplosion(buffer.getGraphics(), esplosione);
            
        } catch (

        Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
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

        Client.sendCoordinates(newX, newY, bombaPiazzata);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        bombaPiazzata = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
