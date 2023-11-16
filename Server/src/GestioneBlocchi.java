import java.util.ArrayList;
import java.util.List;

public class GestioneBlocchi {
    List<BloccoFisso> blockMap;
    List<BloccoDistruttibile> woodBlock;
    Giocatore player;

    public GestioneBlocchi(Giocatore p) {
        this.blockMap = new ArrayList<>();
        this.woodBlock = new ArrayList<>();
        this.player = p;
    }

    private void initializeBlockMap() {

        for (int i = 0; i < Server.WIDTH; i++) {
            for (int j = 0; j < Server.HEIGTH; j++) {
                if (i == 0 || j == 0 || i == Server.WIDTH - 1 || j == Server.HEIGTH - 1 || i % 2 == 0 && j % 2 == 0) {
                    this.blockMap.add(new BloccoFisso(i, j));
                } else if ((i >= player.getPosX() && i <= player.getPosX() + 2)
                        && (j >= player.getPosY() && j <= player.getPosY() + 2)) {
                    this.woodBlock.add(new BloccoDistruttibile(i, j));
                }
            }
        }
    }

    public boolean isBloccoFisso(int x, int y) {
        for (int i = 0; i < blockMap.size(); i++) {
            if (x == blockMap.get(i).getX() && y == blockMap.get(i).getY()) {
                return true; // è un blocco fisso
            }
        }
        return false; // non è un blocco fisso
    }

    public boolean isBloccoDistruttibile(int x, int y) {
        for (int i = 0; i < woodBlock.size(); i++) {
            if (x == woodBlock.get(i).getX() && y == woodBlock.get(i).getY()) {
                return true; // è un blocco fisso
            }
        }
        return false; // non è un blocco fisso
    }
}
