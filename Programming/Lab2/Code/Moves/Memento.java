package Moves;

import ru.ifmo.se.pokemon.*;

public class Memento extends StatusMove {

    public Memento() {
        super(Type.DARK, 0, 1);
    }

    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return true;
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.SPECIAL_ATTACK, -6);
        pokemon.setMod(Stat.ATTACK, -6);
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        Effect.sleep(pokemon);
    }

    @Override
    protected String describe() {
        return "скурил blunt (Memento)";
    }
}
