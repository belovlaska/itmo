import ru.ifmo.se.pokemon.*;

public class RockTomb extends PhysicalMove {
    public RockTomb(){
        super(Type.ROCK, 60.0, 0.95);
    }

    @Override
    protected String describe() {
        return "использует Rock Tomb";
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        super.applyOppEffects(pokemon);

        pokemon.addEffect(
                new Effect().stat(Stat.SPEED, -1)
        );
    }

}
