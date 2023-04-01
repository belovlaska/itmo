public class Bundle extends Item {
    private int count;
    public Bundle(int count) {
        super("свертки");
        setCount(count);
    }
    Item[] gifts = new Item[this.count];
//
//    public void fillBundles(Item... gift){
//        Item[] giftArr = gift;
//        for(int i = 0; i < this.count; ++i){
//            gifts[i] = giftArr[i];
//        }
//    }
    public void fillBundles(Item[] gift) throws BadBundlesCountExceptions{
        if(gift.length != count){
            throw new BadBundlesCountExceptions();
        }
        gifts = gift;
    }
    public void setCount(int count1){
        if(count1 > 0){
            this.count = count1;
        }
        else
            System.out.println("Количество подарков не может быть меньше 1. Имейте совесть, у человека день рождения все-таки!");
    }

    public int getCount() {
        return count;
    }

    public void openBundle(){
        for(int i = 0; i < this.count; ++i){
        System.out.printf("B свертке номер %d оказывается %s\n", (i+1), gifts[i].info());
        }
    }
    @Override
    public void lie(String plate){
        System.out.printf("%d свертка лежит на %sе\n", this.count, plate);
    }
}
