public class Child extends Human{


    public Child(String name, char sex, String role) {
        super(name, sex, role);
    }

    @Override
    public void count(Item obj) {
        System.out.printf("%s быстро сосчитал %s\n", toString(), obj.getName());
    }




}
