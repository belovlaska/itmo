public class Birthday extends Holyday{

    private Human person;
    public Birthday(String date, Human person){
        super("день рождения", date);
        this.person = person;
    }

    public String getPerson() {
        return person.toString();
    }
    public String getInfo(){
        return getName() + " " + getPerson();
    }
}
