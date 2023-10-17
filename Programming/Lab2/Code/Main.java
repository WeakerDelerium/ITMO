import ru.ifmo.se.pokemon.*;
import Pokemons.*;

class Main {

    public static void main(String[] args) {

        Battle b = new Battle();

        Pokemon a1 = new Ludicolo("ROCKET", 10);
        Pokemon a2 = new Garbodor("LILDRUGHILL", 10);
        Pokemon a3 = new Corphish("FRESCO", 10);
        Pokemon f1 = new Dodrio("Kai Angel", 10);
        Pokemon f2 = new Steelix("9mice", 10);
        Pokemon f3 = new Snubbull("Toxi$", 10);

        b.addAlly(a1);
        b.addAlly(a2);
        b.addAlly(a3);

        b.addFoe(f1);
        b.addFoe(f2);
        b.addFoe(f3);

        b.go();
    }
}
