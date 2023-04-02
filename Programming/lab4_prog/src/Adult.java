public class Adult extends Human implements Loveble{

    public Adult(char sex, String role) {
        super(sex, role);
    }

    public class WholeFamily{
        public String name = "вся семья";
        public void sitAndHear(Human human) {
            System.out.println(name + " сидела у его кровати и слушала, как " + human.toString() + " стреляет");
        }
        void lover(Loveble instance) {
            instance.love();
        }
        public void test(){
            lover(
                    new Loveble() {

                        @Override
                        public void love() {
                            System.out.printf("%s любила друг друга!\n", name);
                        }
                    }
            );
        }
    }
}
