import ru.ifmo.se.pokemon.*;

public class Psychic extends SpecialMove {
    public Psychic(){
        super(Type.PSYCHIC, 90.0, 1.0);
    }

    @Override
    protected String describe() {
        return "использует Psychic";
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon){
        super.applyOppEffects(pokemon);

        pokemon.addEffect(
                new Effect().chance(0.1).stat(Stat.SPECIAL_DEFENSE, -1)
        );
    }
}
