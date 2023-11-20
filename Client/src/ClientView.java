import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ClientView {

    public static void drawField(Graphics g) {
        for (int i = 0; i < Map.WIDTH; i++) {
            for (int j = 0; j < Map.HEIGHT; j++) {
                drawBlock(g, i, j, Map.GRASS_IMAGE);
            }
        }
    }

    public static void drawBlocks(Graphics g, List<String> blocks, String imagePath) {
        for (String s : blocks) {
            String[] blockData = s.split(";");
            if (blockData.length == 3) {
                int x = Integer.parseInt(blockData[0].trim());
                int y = Integer.parseInt(blockData[1].trim());
                drawBlock(g, x, y, imagePath);
            }
        }
    }
    
    public static void drawPlayers(Graphics g, List<String> players, String playerImagePath, String bombImagePath) {
        for (String s : players) {
            String[] playerData = s.split(";");
            if (playerData.length == 3) {
                int x = Integer.parseInt(playerData[0].trim());
                int y = Integer.parseInt(playerData[1].trim());
                boolean hasBomb = Boolean.parseBoolean(playerData[2].trim());

                drawPlayer(g, x, y, playerImagePath);
                if (hasBomb) {
                    drawBlock(g, x, y, bombImagePath);
                }
            }
        }
    }

    private static void drawBlock(Graphics g, int x, int y, String imagePath) {
        try {
            Image blockImage = new ImageIcon(Map.class.getResource(imagePath)).getImage();
            g.drawImage(blockImage, x * Map.BLOCK_SIZE, y * Map.BLOCK_SIZE, Map.BLOCK_SIZE, Map.BLOCK_SIZE, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void drawPlayer(Graphics g, int x, int y, String imagePath) {
        try {
            Image playerImage = new ImageIcon(Map.class.getResource(imagePath)).getImage();
            g.drawImage(playerImage, x * Map.BLOCK_SIZE, y * Map.BLOCK_SIZE, Map.BLOCK_SIZE, Map.BLOCK_SIZE, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
