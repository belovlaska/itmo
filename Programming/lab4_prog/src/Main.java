import static javax.swing.text.StyleConstants.Family;

public class Main{
    public static void main(String[] args){
        Adult dad = new Adult('m', "папа");
        Adult mom = new Adult('f', "мама");
        Child kid = new Child("Сванте", 'm', "Малыш");
        Child bethan = new Child("Бетан", 'f', "сестра");
        Child bosse = new Child("Боссе", 'm', "брат");
        Birthday bday = new Birthday("01.06.1950", kid);
        Bundle bundles = new Bundle(4);
//        Paints paints = new Paints(6);
//        PlasticGun plasticGun = new PlasticGun(8);
//        Book book = new Book();
//        Pants pants = new Pants(Colors.BLUE, Age.FACTORYNEW);
        Item[] gifts = new Item[4];
        gifts[0] = new Paints(6);
        gifts[1] = new PlasticGun(5);
        gifts[2] = new Book();
        gifts[3] = new Pants(Colors.BLUE, Age.FACTORY_NEW);
        bundles.fillBundles(gifts);


        dad.say("Поздравляем!");
        bosse.say("Поздравляем!");
        bethan.say("Поздравляем!");
        class Plate extends Item{

            public Plate() {
                super("поднос");
            }
            @Override
            public void lie(String plate){
                System.out.printf("%s поставили перед %s\n", this, plate);
            }
            static class Pie{
                final static int candlesCount = 8;
                public static void inf() {
                    System.out.println("На нем был пирог с " + candlesCount + " свечками");
                }
            }
        }
        Item plate = new Plate();
        Plate.Pie pie = new Plate.Pie();
        plate.lie(kid.toString());
        pie.inf();
        kid.think("Много " + bundles.getName() + "\nхотя, пожалуй, меньше, чем в прошлые " + bday.getInfo());
        bundles.lie("поднос");
        kid.count(bundles);
        System.out.print("Ho ");
        dad.say("");
        kid.setEmotion(Feelings.GLAD);
        kid.feelEmotion(bundles.getCount() + " сверткам");
        bundles.openBundle();
        kid.setEmotion(Feelings.LIKE);
        kid.feelEmotion("все это");
        kid.think("Какие они милые -- " + mom + ", и " + dad + ", и " + bosse.getName() + ", и " + bethan.getName() + "!\nНи у кого на свете нет таких милых " + mom + " и " + dad + " и " + bosse + " с " + bethan);
        try {
            kid.shooting((PlasticGun) gifts[1], 5);
        } catch (SchoolShootingException e) {
            System.out.println(e);
        }
        Adult.WholeFamily family = dad.new WholeFamily();
        family.sitAndHear(kid);
        family.test();
    }
}