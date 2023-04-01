import ru.ifmo.se.pokemon.*;

public class WorkUp extends StatusMove {
    public WorkUp(){
        super(Type.NORMAL, 0.0, 1.0);
    }

    @Override
    protected String describe() {
        return "использует Work Up";
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        super.applySelfEffects(pokemon);

        pokemon.addEffect(
                new Effect()
                        .stat(Stat.SPECIAL_ATTACK, 1)
                        .stat(Stat.ATTACK, 1)
        );
    }
}
