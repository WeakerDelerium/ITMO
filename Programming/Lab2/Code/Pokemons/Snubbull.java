package Pokemons;

import ru.ifmo.se.pokemon.*;
import Moves.*;

public class Snubbull extends Pokemon {

    public Snubbull(String name, int level) {

        super(name, level);

        setType(Type.FAIRY);

        setStats(60, 80, 50, 40, 40, 30);

        addMove(new HydroPump());
        addMove(new SwordsDance());
        addMove(new Magnitude());
        addMove(new Barrage());
    }
}
