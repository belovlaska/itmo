public class Child extends Human{


    public Child(String name, char sex, String role) {
        super(name, sex, role);
    }

    @Override
    public void count(Item obj) {
        System.out.printf("%s быстро сосчитал %s\n", toString(), obj.getName());
    }
    @Override
    public void say(String str) {
        if (this.role != "Малыш") {
            System.out.printf("%s сказал: %s\n", getName(), str);
        }
    }




}
