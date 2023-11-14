import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

class BloccoDistruttibile {
    private Image blockImage;
    private int x;
    private int y;

    public BloccoDistruttibile(int x, int y) {
        this.x = x;
        this.y = y;

        try {
            blockImage = ImageIO.read(Map.class.getResource("img/tile_wood.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void paintBlock(Graphics g) {
        if (blockImage != null) {
            // blocksize
            g.drawImage(blockImage, x * 40, y * 40, 40, 40, null);
        }
    }
}