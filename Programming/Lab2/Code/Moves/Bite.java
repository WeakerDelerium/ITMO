package Moves;

import ru.ifmo.se.pokemon.*;

public class Bite extends PhysicalMove {

    public Bite() {
        super(Type.DARK, 60, 1);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        if (Math.random() < 0.3) {
            Effect.flinch(pokemon);
        }
    }

    @Override
    protected String describe() {
        return "shot'нул opp'a (Bite)";
    }
}
