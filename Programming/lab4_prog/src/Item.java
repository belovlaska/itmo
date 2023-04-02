public abstract class Item implements Lying {
    private String name;

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Item)) return false;

        Item p = (Item) obj;
        return this.name.equals(p.name);
    }

    @Override
    public String toString() {
        return this.name;
    }

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void lie(String plate) {
        System.out.printf("%s лежит на %s\n", toString(), plate);
    }

    public String info() {
        return name;
    }


}
