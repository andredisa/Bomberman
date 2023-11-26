import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ClientView {

    public static void drawField(Graphics g) {
        drawGrid(g, Map.WIDTH, Map.HEIGHT, Map.GRASS_IMAGE);
    }

    public static void drawBlocks(Graphics g, List<String> blocks) {
        drawItems(g, blocks);
    }

    public static void drawPlayers(Graphics g, List<String> players) {
        drawItems(g, players);
    }

    public static void drawExplosion(Graphics g, List<String> explosions) {
        for (String s : explosions) {
            String[] itemData = s.split(";");

            int x = Integer.parseInt(itemData[0].trim());
            int y = Integer.parseInt(itemData[1].trim());
            drawImage(g, x, y, Map.EXPLOSION_IMAGE);
        }
    }

    public static void drawBomb(Graphics g, List<String> bombs) {
        drawItems(g, bombs);
    }

    private static void drawItems(Graphics g, List<String> items) {
        if (items != null) {
            for (String s : items) {
                String[] itemData = s.split(";");

                int x = Integer.parseInt(itemData[0].trim());
                int y = Integer.parseInt(itemData[1].trim());
                String imagePath = itemData[2].trim();

                drawImage(g, x, y, imagePath);
            }
        }
    }

    private static void drawGrid(Graphics g, int width, int height, String imagePath) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                drawImage(g, i, j, imagePath);
            }
        }
    }

    private static void drawImage(Graphics g, int x, int y, String imagePath) {
        try {
            Image image = new ImageIcon(Map.class.getResource(imagePath)).getImage();
            g.drawImage(image, x * Map.BLOCK_SIZE, y * Map.BLOCK_SIZE, Map.BLOCK_SIZE, Map.BLOCK_SIZE, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
