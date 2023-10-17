package Moves;

import ru.ifmo.se.pokemon.*;

public class Magnitude extends PhysicalMove {

    public Magnitude() {
        super(Type.GROUND, 0, 1);
        super.power = switch ((int) (Math.random() * 20 + 1)) {
            case 1 -> 10;                       // 10 - 1/20
            case 2, 3 -> 30;                    // 30 - 2/20
            case 4, 5, 6, 7 -> 50;              // 50 - 4/20
            case 8, 9, 10, 11, 12, 13 -> 70;    // 70 - 6/20
            case 14, 15, 16, 17 -> 90;          // 90 - 4/20
            case 18, 19 -> 110;                 // 110 - 2/20
            default -> 150;                     // 150 - 1/20
        };
    }

    @Override
    protected String describe() {
        return "shot'нул opp'a (Magnitude)";
    }
}
