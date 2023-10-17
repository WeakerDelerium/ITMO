package Moves;

import ru.ifmo.se.pokemon.*;

public class SpeedSwap extends StatusMove {

    private int pokemonAllySpeed;
    private int pokemonFoeSpeed;

    public SpeedSwap() {
        super(Type.PSYCHIC, 0, -1);
    }

    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {

        this.pokemonAllySpeed = (int) pokemon.getStat(Stat.SPEED);
        this.pokemonFoeSpeed = (int) pokemon.getStat(Stat.SPEED);

        return true;
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.SPEED, this.pokemonAllySpeed);
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.SPEED, this.pokemonFoeSpeed);
    }

    @Override
    protected String describe() {
        return "скурил blunt (Speed Swap)";
    }
}
