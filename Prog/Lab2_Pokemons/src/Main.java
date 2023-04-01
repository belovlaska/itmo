import ru.ifmo.se.pokemon.Battle;

public class Main {
    public static void main(String[] args) {
        Battle zaruba = new Battle();

        zaruba.addAlly(new Hitmontop("Martin", 4));
        zaruba.addAlly(new Spiritomb("Artem", 3));
        zaruba.addFoe(new Tyrogue("Dima", 5));
        zaruba.addFoe(new Ralts("Dima Pilnou", 6));
        zaruba.addFoe(new Kirlia("Nikita", 4));
        zaruba.addAlly(new Gallade("Vadim", 5));
        zaruba.go();

    }
}