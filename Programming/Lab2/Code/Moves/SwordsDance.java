package Moves;

import ru.ifmo.se.pokemon.*;

public class SwordsDance extends StatusMove {

    public SwordsDance() {
        super(Type.NORMAL, 0, -1);
    }

    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return true;
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.ATTACK, +2);
    }

    @Override
    protected String describe() {
        return "скурил blunt (Swords Dance)";
    }
}
