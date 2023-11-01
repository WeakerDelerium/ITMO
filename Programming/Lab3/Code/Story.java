import obj.classes.Alive;
import obj.classes.NotAlive;
import obj.classes.Place;
import obj.enums.ActionEnum;
import obj.enums.PlaceEnum;

public class Story {

    static Alive bear = new Alive("Винни Пух", ActionEnum.DEFAULT);
    static Alive piglet = new Alive("Пятачок", ActionEnum.DEFAULT);
    static NotAlive it = new NotAlive("Это", ActionEnum.DEFAULT);

    static Place hole = new Place(PlaceEnum.FROM_VERY_DEEP_HOLE);
    static Place pot = new Place(PlaceEnum.IN_POT);
    static Place trap = new Place(PlaceEnum.TO_TRAP);

    static public void tellStory() {
        bear.setAction(ActionEnum.PUSH_IN);
        System.out.println(bear + " " + pot);

        bear.setAction(ActionEnum.LICK);
        System.out.println(bear);

        bear.setAction(ActionEnum.MAKE_SURE);
        System.out.println(bear);

        bear.setAction(ActionEnum.CARRY);
        System.out.println(bear + " " + trap);

        piglet.setAction(ActionEnum.LOOK_OUT);
        System.out.println(piglet + " " + hole);

        piglet.setAction(ActionEnum.ASK);
        System.out.println(piglet + piglet.say("Принес?"));

        bear.setAction(ActionEnum.SAY);
        System.out.println(bear + bear.say("Да, но он не совсем полный"));

        piglet.setAction(ActionEnum.LOOK_IN);
        System.out.println(piglet + " " + pot);

        piglet.setAction(ActionEnum.ASK);
        System.out.println(piglet + piglet.say("Это все, что у тебя осталось?"));

        bear.setAction(ActionEnum.SAY);
        System.out.println(bear + bear.say("Да"));

        it.setAction(ActionEnum.WAS_TRUTH);
        System.out.println(it);
    }
}
