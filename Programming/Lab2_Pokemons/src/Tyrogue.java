import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Tyrogue extends Pokemon {
    public Tyrogue(String name, int level){
        super(name, level);
        setType(Type.FIGHTING);
        setStats(35, 35, 35, 35, 35, 35);
        addMove(new WorkUp());
        addMove(new Confide());
        addMove(new Rest());
    }
}
