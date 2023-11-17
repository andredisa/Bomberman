import java.util.ArrayList;
import java.util.List;

public class GestioneBlocchi {
    List<BloccoFisso> blockMap;
    List<BloccoDistruttibile> woodBlock;

    Giocatore player;

    public GestioneBlocchi() {
        this.blockMap = new ArrayList<>();
        this.woodBlock = new ArrayList<>();
        // this.player = p;

        initializeBlockMap();
    }

    private void initializeBlockMap() {
        for (int i = 0; i < Server.WIDTH; i++) {
            for (int j = 0; j < Server.HEIGHT; j++) {
                if (i == 0 || j == 0 || i == Server.WIDTH - 1 || j == Server.HEIGHT - 1 || i % 2 == 0 && j % 2 == 0) {
                    this.blockMap.add(new BloccoFisso(i, j));
                } else if (!(Math.abs(i - 1) <= 2 && Math.abs(j - 1) <= 2)) {
                    this.woodBlock.add(new BloccoDistruttibile(i, j));
                }
            }
        }
    }

    public boolean isBloccoFisso(int x, int y) {
        for (int i = 0; i < blockMap.size(); i++) {
            if (x == blockMap.get(i).getX() && y == blockMap.get(i).getY())
                return true;
        }
        return false;
    }

    public boolean isBloccoDistruttibile(int x, int y) {
        for (int i = 0; i < woodBlock.size(); i++) {
            if (x == woodBlock.get(i).getX() && y == woodBlock.get(i).getY())
                return true;
        }
        return false; // non è un blocco fisso
    }

    public List<BloccoFisso> getBlockMap() {
        return blockMap;
    }

    public void setBlockMap(List<BloccoFisso> blockMap) {
        this.blockMap = blockMap;
    }

    public List<BloccoDistruttibile> getWoodBlock() {
        return woodBlock;
    }

    public void setWoodBlock(List<BloccoDistruttibile> woodBlock) {
        this.woodBlock = woodBlock;
    }

    public void removeBloccoDistruttibile(BloccoDistruttibile blocco) {
        woodBlock.remove(blocco);
    }

}
