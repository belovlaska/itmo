import ru.ifmo.se.pokemon.Type;

public class Hitmontop extends Tyrogue {
    public Hitmontop(String name, int level){
        super(name, level);
        setStats(50, 95, 95, 35, 110, 70);
        setType(Type.FIGHTING);
        addMove(new CloseCombat());
    }
}
