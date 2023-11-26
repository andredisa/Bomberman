import java.util.ArrayList;
import java.util.*;

public class GestioneBlocchi {
    private List<BloccoFisso> blockMap;
    private List<BloccoDistruttibile> woodBlock;
    private List<Giocatore> giocatori;

    public GestioneBlocchi(List<Giocatore> giocatori) {
        this.blockMap = new ArrayList<>();
        this.woodBlock = new ArrayList<>();
        this.giocatori = giocatori;

        initializeBlockMap();
    }

    private void initializeBlockMap() {
        for (int i = 0; i < Server.WIDTH; i++) {
            for (int j = 0; j < Server.HEIGHT; j++) {
                if (i == 0 || j == 0 || i == Server.WIDTH - 1 || j == Server.HEIGHT - 1 || i % 2 == 0 && j % 2 == 0) {
                    this.blockMap.add(new BloccoFisso(i, j));
                } else if (giocatori.size() >= 2 && !(Math.abs(i - giocatori.get(0).getPosX()) <= 2
                        && Math.abs(j - giocatori.get(0).getPosY()) <= 2) &&
                        !(Math.abs(i - giocatori.get(1).getPosX()) <= 2
                                && Math.abs(j - giocatori.get(1).getPosY()) <= 2)
                        && Math.random() < 0.4) {
                    this.woodBlock.add(new BloccoDistruttibile(i, j));
                }
            }
        }
    }

    public boolean isBloccoFisso(int x, int y) {
        return blockMap.stream().anyMatch(blocco -> blocco.getX() == x && blocco.getY() == y);
    }

    public boolean isBloccoDistruttibile(int x, int y) {
        return woodBlock.stream().anyMatch(blocco -> blocco.getX() == x && blocco.getY() == y);
    }

    public void removeBlocchiDistruttibili(List<String> distruttibili) {
        Iterator<BloccoDistruttibile> iterator = woodBlock.iterator();
        while (iterator.hasNext()) {
            BloccoDistruttibile blocco = iterator.next();
            String posizione = blocco.getX() + ";" + blocco.getY();
            if (distruttibili.contains(posizione)) {
                iterator.remove();
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

    public static List<String> blockMap_toString(List<BloccoFisso> blocchiFissi) {
        List<String> result = new ArrayList<>();
        for (BloccoFisso blocco : blocchiFissi) {
            result.add(blocco.toString());
            System.out.println(blocco.toString());
        }
        return result;
    }

    public static List<String> woodBlock_toString(List<BloccoDistruttibile> blocchiDistruttibili) {
        List<String> result = new ArrayList<>();
        for (BloccoDistruttibile blocco : blocchiDistruttibili) {
            result.add(blocco.toString());
        }
        return result;
    }

}
