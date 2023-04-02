

public class Paints extends Item {
    private int count;
    String[] colors = new String[this.count];
    public void addColors(Colors... var1) {
        Colors[] var2 = var1;
        int var3 = var1.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            if(var4 == count){
                break;
            }
            colors[var4] = var2[var4].getColor();
        }
    }
    public Paints(int count) {
        super("коробка красок");
        this.count = count;
    }


}
