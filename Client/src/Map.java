import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Map extends JPanel implements KeyListener, ActionListener {
    private final int width = 17;
    private final int height = 13;
    private final int blockSize = 40;
    private final String wallImage = "img/tile_wall.png";
    private final String grassImage = "img/tile_grass.png";

    private Image imgPlayer;
    private Timer timer;

    private int playerX = 1;
    private int playerY = 1;
    private int moveSpeed = 1;

    private BufferedImage offScreenImage;
    private boolean[][] blockMap; // mappa collisione blocchi fissi

    public Map() {
        setPreferredSize(new Dimension(width * blockSize + 1, height * blockSize + 1));
        try {
            imgPlayer = ImageIO.read(Map.class.getResource("img/player2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setFocusable(true);
        addKeyListener(this);
        timer = new Timer(1000 / 120, this);
        timer.start();

        initializeBlockMap();
    }

    private void initializeBlockMap() {
        blockMap = new boolean[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == 0 || j == 0 || i == width - 1 || j == height - 1 || i % 2 == 0 && j % 2 == 0) {
                    blockMap[i][j] = true; // tile_wall
                } else {
                    blockMap[i][j] = false; // grass
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (offScreenImage == null) {
            offScreenImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        }

        Graphics offScreenGraphics = offScreenImage.getGraphics();
        offScreenGraphics.setColor(getBackground());
        offScreenGraphics.fillRect(0, 0, getWidth(), getHeight());
        drawBlocks(offScreenGraphics);
        drawPlayer(offScreenGraphics);
        g.drawImage(offScreenImage, 0, 0, this);

        offScreenGraphics.dispose();
    }

    private void drawBlocks(Graphics g) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (blockMap[i][j]) {
                    g.drawImage(loadImage(wallImage), i * blockSize, j * blockSize, blockSize, blockSize, this);
                } else {
                    g.drawImage(loadImage(grassImage), i * blockSize, j * blockSize, blockSize, blockSize, this);
                }
            }
        }
    }

    private void drawPlayer(Graphics g) {
        g.drawImage(imgPlayer, Math.round(playerX) * blockSize, Math.round(playerY) * blockSize, blockSize, blockSize,
                this);
    }

    private Image loadImage(String path) {
        try {
            return ImageIO.read(Map.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int newX = playerX;
        int newY = playerY;
        boolean bombaPiazzata = false;


        switch (keyCode) {
            case KeyEvent.VK_UP:
                if (!blockMap[(playerX)][(playerY - moveSpeed)]) {
                    newY -= moveSpeed;
                    Client.inviaCoordinate(newX, newY, bombaPiazzata);
                }
                break;
            case KeyEvent.VK_DOWN:
                if (!blockMap[(playerX)][(playerY + moveSpeed)]) {
                    newY += moveSpeed;
                    Client.inviaCoordinate(newX, newY, bombaPiazzata);
                }
                break;
            case KeyEvent.VK_LEFT:
                if (!blockMap[(playerX - moveSpeed)][(playerY)]) {
                    newX -= moveSpeed;
                    Client.inviaCoordinate(newX, newY, bombaPiazzata);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (!blockMap[(playerX + moveSpeed)][(playerY)]) {
                    newX += moveSpeed;
                    Client.inviaCoordinate(newX, newY, bombaPiazzata);
                }
                break;
            case KeyEvent.VK_SPACE:
                if (!blockMap[(playerX + moveSpeed)][(playerY)]) {
                    bombaPiazzata = true;
                    //disegnaBomba
                    Client.inviaCoordinate(newX, newY, bombaPiazzata);
                
                }
                break;
        }

        playerX = newX;
        playerY = newY;
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
