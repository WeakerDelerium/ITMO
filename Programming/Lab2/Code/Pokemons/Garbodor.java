package Pokemons;

import ru.ifmo.se.pokemon.*;
import Moves.*;

public class Garbodor extends Pokemon {

    public Garbodor(String name, int level) {

        super(name, level);

        setType(Type.POISON);

        setStats(80, 95, 82, 60, 82, 75);

        addMove(new ThunderWave());
        addMove(new WaterPulse());
        addMove(new PetaBlizzard());
    }
}
