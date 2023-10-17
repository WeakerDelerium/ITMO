package Pokemons;

import ru.ifmo.se.pokemon.*;
import Moves.*;

public class Ludicolo extends Pokemon {

    public Ludicolo(String name, int level) {

        super(name, level);

        setType(Type.WATER, Type.GRASS);

        setStats(80, 70, 70, 90, 100, 70);

        addMove(new FocusBlast());
        addMove(new Memento());
        addMove(new Bite());
        addMove(new Blizzard());
    }
}
