import ru.ifmo.se.pokemon.*;
import java.lang.Math.*;

public class DarkPulse extends SpecialMove { //learnt by level 50
    public DarkPulse() {
        super(Type.DARK, 80.0, 1.0);
    }

    @Override
    protected String describe() {
        return "использует Dark Pulse";
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        super.applyOppEffects(pokemon);

        if(Math.random() < 0.2)
            Effect.flinch(pokemon);
    }
}
