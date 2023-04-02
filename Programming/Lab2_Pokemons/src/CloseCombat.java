import ru.ifmo.se.pokemon.*;

public class CloseCombat extends PhysicalMove {
    public CloseCombat(){
        super(Type.FIGHTING, 120.0, 1.0);
    }

    @Override
    protected String describe() {
        return "использует Close Combat";
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        super.applySelfEffects(pokemon);

        pokemon.addEffect(
                new Effect().stat(Stat.DEFENSE, -1).stat(Stat.SPECIAL_DEFENSE, -1)
        );
    }
}
