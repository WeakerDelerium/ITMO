package Moves;

import ru.ifmo.se.pokemon.*;

public class PetaBlizzard extends PhysicalMove {

    public PetaBlizzard() {
        super(Type.GRASS, 90, 1);
    }


    @Override
    protected String describe() {
        return "shot'нул opp'a (Petal Blizzard)";
    }
}
