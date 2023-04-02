public abstract class Human implements Abilities{
    private String name;
    private char sex;
    protected String role;
    private static int counterOfHumans = 0;
    private int id;
    private Feelings emotion;

    public void setEmotion(Feelings emotion) {
        this.emotion = emotion;
    }

    public String getEmotion() {
        return emotion.getFeeling();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return this.role;
    }
    @Override
    public boolean equals(Object obj){

        if (!(obj instanceof Human)) return false;

        Human p = (Human)obj;
        return this.name.equals(p.name);
    }
    @Override
    public int hashCode(){

        return this.id;
    }
    public Human(String name, char sex, String role){
        this.id = ++counterOfHumans;
        this.name = name;
        setSex(sex);
        this.role = role;
    }
    public Human(char sex, String role){
        this.id = ++counterOfHumans;
        setSex(sex);
        this.role = role;
    }

    public void setSex(char sex) {
        if(sex == 'm' || sex == 'f')
            this.sex = sex;
        else{
            System.out.println("Боевые ветролеты не обслуживаются!");
        }
    }

    @Override
    public void count(Item obj) {
        System.out.printf("%s посчитал %s\n", toString(), obj.getName());
    }

    @Override
    public void say(String str) {
        System.out.printf("%s сказал: %s\n", toString(), str);
    }

    @Override
    public void think(String str){
        String[] strArr = str.split("\n");
        System.out.printf("%s, - подумал %s, - %s\n", strArr[0], toString(), strArr[1]);
    }
    @Override
    public void readBook(Book book){
        System.out.printf("%s читает книгу %s\n", toString(), book.getNameofBook());
    }
    @Override
    public void painting(){
        System.out.printf("%s рисует\n", toString());
    }
    @Override
    public void shooting(PlasticGun gun, int times) throws SchoolShootingException{
        if(times > gun.getCountOfBullets())
            throw new SchoolShootingException();
        else{
            for(int i = 0; i < times; ++i){
                System.out.printf("Бах! Произошел выстрел... В пистолете осталость %d пуль\n", (gun.getCountOfBullets() - i - 1));
            }
        }
    }
    @Override
    public void feelEmotion(String reason){
        System.out.printf("%s %s %s\n", toString(), getEmotion(), reason);
    }

}
