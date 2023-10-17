package Pokemons;

import ru.ifmo.se.pokemon.*;
import Moves.*;

public class Corphish extends Pokemon {

    public Corphish(String name, int level) {

        super(name, level);

        setType(Type.WATER);

        setStats(43, 80, 65, 50, 35, 35);

        addMove(new ThunderWave());
        addMove(new WaterPulse());
        addMove(new PetaBlizzard());
        addMove(new SpeedSwap());
    }
}
