import java.util.ArrayList;
import java.util.List;

public class GestioneBlocchi {
    List<BloccoFisso> blockMap;
    List<BloccoDistruttibile> woodBlock;
    List<Giocatore> giocatori;

    public GestioneBlocchi(List<Giocatore> giocatori) {
        this.blockMap = new ArrayList<>();
        this.woodBlock = new ArrayList<>();
        this.giocatori = giocatori;
        // this.player = p;

        initializeBlockMap();
    }

    private void initializeBlockMap() {
        System.out.println(giocatori.size());
        for (int i = 0; i < Server.WIDTH; i++) {
            for (int j = 0; j < Server.HEIGHT; j++) {
                if (i == 0 || j == 0 || i == Server.WIDTH - 1 || j == Server.HEIGHT - 1 || i % 2 == 0 && j % 2 == 0) {
                    this.blockMap.add(new BloccoFisso(i, j));
                } else if (giocatori.size() >= 2 && !(Math.abs(i - giocatori.get(0).getPosX()) <= 2
                        && Math.abs(j - giocatori.get(0).getPosY()) <= 2) &&
                        !(Math.abs(i - giocatori.get(1).getPosX()) <= 2
                                && Math.abs(j - giocatori.get(1).getPosY()) <= 2)) {
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
        return false;
    }

    public void removeBloccoDistruttibile(int x, int y) {
        for (BloccoDistruttibile blocco : woodBlock) {
            if (blocco.getX() == x && blocco.getY() == y) {
                woodBlock.remove(blocco);
                break;
            }
        }
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
