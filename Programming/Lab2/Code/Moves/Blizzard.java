package Moves;

import ru.ifmo.se.pokemon.*;

public class Blizzard extends SpecialMove {

    public Blizzard() {
        super(Type.ICE, 110, 0.7);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        if ((int) (Math.random() * 10 + 1) == 1) {
            Effect.freeze(pokemon);
        }
    }

    @Override
    protected String describe() {
        return "достал glock (Blizzard)";
    }
}
