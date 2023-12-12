import obj.abclasses.Entity;
import obj.classes.Alive;
import obj.classes.Inanimate;
import obj.classes.Pot;
import obj.enums.Details;
import obj.exceptions.WrongTypeArgumentsException;

public class Story {
    static Alive bear = new Alive("Винни Пух");
    static Alive pig = new Alive("Пятачок");
    static Alive they = new Alive("Они") {
        @Override
        public void come(Inanimate obj) {
            System.out.println(getName() + " пошли в " + obj);
        }
    };

    static Entity it = new Entity("Это") {
        @Override
        public String getName() {
            return super.getName() + " было правдой";
        }
    };

    static Pot pot = new Pot("горшок", "М И О Т");
    static Pot.Honey honey = new Pot.Honey("мёд");

    static Inanimate home = new Inanimate("дом");
    static Inanimate buffet = new Inanimate("буфет");
    static Inanimate chair = new Inanimate("стул");
    static Inanimate shelf = new Inanimate("полка", Details.UPPER);
    static Inanimate cap = new Inanimate("крышка", Details.PAPER);
    static Inanimate trap = new Inanimate("западня");
    static Inanimate pit = new Inanimate("Яма", Details.VERY_DEEP);

    static public void tellStory() {
        bear.come(home);
        bear.comeUp(buffet);
        bear.climbOn(chair);
        bear.getIn(pot, shelf);

        pot.written();

        try {
            bear.takeOff(cap, pot);
        } catch (WrongTypeArgumentsException e) {
            System.out.println(e.getMessage());
        }

        bear.lookIn(pot);

        pot.was(honey);

        try {
            bear.stuck(bear.huzzle, pot);
        } catch (WrongTypeArgumentsException e) {
            System.out.println(e.getMessage());
        }

        bear.lick(pot);

        try {
            bear.carry(pot, trap);
        } catch (WrongTypeArgumentsException e) {
            System.out.println(e.getMessage());
        }

        pig.lookOut(pit);
        pig.say("спросил", "Принёс?");

        bear.say("Да, но он не совсем полный");

        pig.lookIn(pot);
        pig.say("спросил", "Это все, что у тебя осталось?");

        bear.say("ответил", "Да");

        System.out.println(it.getName());
        pit.setDetail(Details.DEFAULT);

        try {
            pig.put(pot, pit);
        } catch (WrongTypeArgumentsException e) {
            System.out.println(e.getMessage());
        }

        pit.setDetail(Details.VERY_DEEP);
        pig.getOut(pit);

        they.come(home);
    }
}
