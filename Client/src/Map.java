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
    private Image giocatore1;
    private Timer timer;
    
    private float player1X = 1;
    private float player1Y = 1;

    private float moveSpeed = 1;
    private BufferedImage offScreenImage;
    private boolean[][] collisionMap;

    public Map() {
        setPreferredSize(new Dimension(width * blockSize + 1, height * blockSize + 1));
        setBackground(new Color(30, 70, 30));
        try {
            giocatore1 = ImageIO.read(Map.class.getResource("img/player1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setFocusable(true);
        addKeyListener(this);
        timer = new Timer(1000 / 60, this);
        timer.start();

        //controlli movimento giocatore
        collisionMap = new boolean[width][height];
        initializeCollisionMap();
    }

    private void initializeCollisionMap() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == 0 || j == 0 || i == width - 1 || j == height - 1 || (i % 2 == 0 && j % 2 == 0)) {
                    collisionMap[i][j] = true;
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
        drawPlayers(offScreenGraphics);
        g.drawImage(offScreenImage, 0, 0, this);
    }

    private void drawBlocks(Graphics g) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (collisionMap[i][j]) {
                    BloccoFisso bf = new BloccoFisso(i, j);
                    bf.paintBlock(g);
                }
            }
        }
    }

    private void drawPlayers(Graphics g) {
        g.drawImage(giocatore1, Math.round(player1X) * blockSize, Math.round(player1Y) * blockSize, blockSize, blockSize,
                this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                if (!collisionMap[(int) (player1X)][(int) (player1Y - moveSpeed)]) {
                    player1Y -= moveSpeed;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (!collisionMap[(int) (player1X)][(int) (player1Y + moveSpeed)]) {
                    player1Y += moveSpeed;
                }
                break;
            case KeyEvent.VK_LEFT:
                if (!collisionMap[(int) (player1X - moveSpeed)][(int) (player1Y)]) {
                    player1X -= moveSpeed;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (!collisionMap[(int) (player1X + moveSpeed)][(int) (player1Y)]) {
                    player1X += moveSpeed;
                }
                break;
        }
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
