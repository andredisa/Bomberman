import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Map extends JPanel implements KeyListener, ActionListener {
    private final int width = 17;
    private final int height = 13;
    private final int blockSize = 40;
    private final String wallImage = "img/tile_wall.png";
    private final String grassImage = "img/tile_grass.png";
    private final String woodImage = "img/tile_wood.png";

    private Image imgPlayer;
    private Timer timer;

    private int playerX = 1;
    private int playerY = 1;
    private int moveSpeed = 1;

    private BufferedImage offScreenImage;
    List<BloccoFisso> blockMap = new ArrayList<>();
    List<BloccoDistruttibile> woodBlock = new ArrayList<>();

    BloccoFisso bf;
    BloccoDistruttibile bd;

    public Map() {
        bf = new BloccoFisso(0, 0);
        bd = new BloccoDistruttibile(0, 0);
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
    }

    public void disegnaBlocchiFissi(){
        try {
            Messaggio m = Client.riceviMessaggio();
            if (m.getTipo() == "blocchiFissi") {
                for (int index = 0; index < m.getDati().size(); index++) {
                  //split x,y,imagePath
                  //richiama il metodo che disegna il blocco
               }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    
    public void disegnaBlocchiDistruttibili(){
        try {
            Messaggio m = Client.riceviMessaggio();
            if (m.getTipo() == "blocchiDistruttibili") {
               for (int index = 0; index < m.getDati().size(); index++) {
                  //split x,y,imagePath
                  //richiama il metodo che disegna il blocco
               }
            }
        } catch (Exception e) {
            // TODO: handle exception
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
        //drawBlocks(offScreenGraphics);
        drawPlayer(offScreenGraphics);
        g.drawImage(offScreenImage, 0, 0, this);

        offScreenGraphics.dispose();
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
       
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
