import obj.classes.alive.Patch;
import obj.classes.alive.WinnieThePooh;
import obj.classes.place.Pot;
import obj.classes.place.Trap;
import obj.classes.place.VeryDeepHole;
import obj.classes.unalive.It;
import obj.enums.ActionEnum;
import obj.enums.PlaceEnum;

public class Story {

    static WinnieThePooh bear = new WinnieThePooh("Винни Пух", ActionEnum.DEFAULT);
    static Patch pig = new Patch("Пятачок", ActionEnum.DEFAULT);
    static It it = new It("Это", ActionEnum.DEFAULT);

    static Pot pot = new Pot(PlaceEnum.IN_POT);
    static Trap trap = new Trap(PlaceEnum.TO_TRAP);
    static VeryDeepHole hole = new VeryDeepHole(PlaceEnum.FROM_VERY_DEEP_HOLE);


    static public void tellStory() {
        bear.setEntityAction(ActionEnum.PUSH_IN);
        System.out.println(bear + " " + pot);

        bear.setEntityAction(ActionEnum.LICK);
        System.out.println(bear);

        bear.setEntityAction(ActionEnum.MAKE_SURE);
        System.out.println(bear);

        bear.setEntityAction(ActionEnum.CARRY);
        System.out.println(bear + " " + trap);

        pig.setEntityAction(ActionEnum.LOOK_OUT);
        System.out.println(pig + " " + hole);

        pig.setEntityAction(ActionEnum.ASK);
        System.out.println(pig + pig.say("Принёс?"));

        bear.setEntityAction(ActionEnum.SAY);
        System.out.println(bear + bear.say("Да, но он не совсем полный"));

        pig.setEntityAction(ActionEnum.LOOK_IN);
        System.out.println(pig + " " + pot);

        pig.setEntityAction(ActionEnum.ASK);
        System.out.println(pig + pig.say("Это всё, что у тебя осталось?"));

        bear.setEntityAction(ActionEnum.SAY);
        System.out.println(bear + bear.say("Да"));

        it.setEntityAction(ActionEnum.WAS_TRUTH);
        System.out.println(it);
    }
}
