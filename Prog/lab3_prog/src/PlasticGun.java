public class PlasticGun extends Item {
    private int countOfBullets;
    protected PlasticGun(int countOfBullets) {
        super("игрушечный пистолет");
        this.countOfBullets = countOfBullets;
    }
    public int getCountOfBullets(){
        return countOfBullets;
    }
}
