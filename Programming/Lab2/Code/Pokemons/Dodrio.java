package Pokemons;

import ru.ifmo.se.pokemon.*;
import Moves.*;

public class Dodrio extends Pokemon {

    public Dodrio(String name, int level) {

        super(name, level);

        setType(Type.NORMAL, Type.FLYING);

        setStats(60, 110, 70, 60, 60, 110);

        addMove(new HydroPump());
        addMove(new SwordsDance());
    }
}
