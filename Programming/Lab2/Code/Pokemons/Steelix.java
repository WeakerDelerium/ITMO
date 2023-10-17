package Pokemons;

import ru.ifmo.se.pokemon.*;
import Moves.*;

public class Steelix extends Pokemon {

    public Steelix(String name, int level) {

        super(name, level);

        setType(Type.STEEL, Type.GROUND);

        setStats(75, 85, 200, 55, 65, 30);

        addMove(new HydroPump());
        addMove(new SwordsDance());
        addMove(new Magnitude());
    }
}
