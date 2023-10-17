package Moves;

import ru.ifmo.se.pokemon.*;

public class HydroPump extends SpecialMove {

    public HydroPump() {
        super(Type.WATER, 110, 0.8);
    }

    @Override
    protected String describe() {
        return "достал glock (Hydro Pump)";
    }
}
