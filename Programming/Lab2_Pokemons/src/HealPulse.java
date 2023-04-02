import ru.ifmo.se.pokemon.*;

public class HealPulse extends StatusMove {
    public HealPulse() {
        super(Type.PSYCHIC, 0.0, 1.0);
    }

    @Override
    protected String describe() {
        return "использует Heal Pulse";
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.HP, (int) -(pokemon.getHP() + (pokemon.getStat(Stat.HP)) * 0.5));
    }
}
