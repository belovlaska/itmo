public class Pants extends Item {
    private Colors color;
    private Age age;
    protected Pants(Colors color, Age age) {
        super("штанишки");
        this.color = color;
        this.age = age;
    }
    @Override
    public String info(){
        return age.getAge() + " " + color.getColor() + " " + getName();
    }
}
