import ru.ifmo.se.pokemon.*;

public class Confide extends StatusMove {
    public Confide(){
        super(Type.NORMAL, 0.0, 1.0);
    }

    @Override
    protected String describe() {
        return "использует Confide";
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        super.applyOppEffects(pokemon);

        pokemon.addEffect(
                new Effect().stat(Stat.SPECIAL_ATTACK, -1)
        );
    }
}
