import ru.ifmo.se.pokemon.Type;

public class Gallade extends Kirlia {
    public Gallade(String name, int level){
        super(name, level);
        setType(Type.PSYCHIC, Type.FIGHTING);
        setStats(68, 125, 65, 65, 115, 80);
        addMove(new HealPulse());
    }
}
