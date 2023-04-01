import ru.ifmo.se.pokemon.*;

public class DrainingKiss extends SpecialMove {
    public DrainingKiss(){ super(Type.FAIRY, 50.0, 1.0);}

    @Override
    protected String describe() {
        return "использует Draining Kiss";
    }

    @Override
    protected void applySelfDamage(Pokemon pokemon, double damage){
        pokemon.setMod(Stat.HP, -(int)Math.round(damage*0.75));
    }
}
