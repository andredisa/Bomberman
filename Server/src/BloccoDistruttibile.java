public class BloccoDistruttibile {
    private String blockImage;
    private int x;
    private int y;

    public BloccoDistruttibile(int x, int y) {
        this.x = x;
        this.y = y;
        this.blockImage = "img/tile_wood.png";
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String toString() {
        return this.x + ";" + this.y + ";" + this.blockImage;
    }
}
