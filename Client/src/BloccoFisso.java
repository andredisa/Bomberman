import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

class BloccoFisso {
    private Image blockImage;
    private int x;
    private int y;


    public BloccoFisso(int x, int y) {
        this.x = x;
        this.y = y;

        try {
            blockImage = ImageIO.read(Map.class.getResource("img/blocco.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paintBlock(Graphics g) {
        if (blockImage != null) {
            //blocksize
            g.drawImage(blockImage, x * 40, y * 40, 40, 40, null);
        }
    }
}