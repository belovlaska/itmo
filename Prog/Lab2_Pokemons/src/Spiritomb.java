import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Spiritomb extends Pokemon {
    public Spiritomb(String name, int level){
        super(name, level);
        setStats(50, 92, 108, 92, 108, 35);
        setType(Type.DARK, Type.GHOST);
        addMove(new Snarl());
        addMove(new DarkPulse());
        addMove(new RockTomb());
        addMove(new Confide());
    }
}
