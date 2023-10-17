package Moves;

import ru.ifmo.se.pokemon.*;

public class Barrage extends PhysicalMove {

    public Barrage() {
        super(Type.NORMAL, 15, 0.85);
        super.hits = switch ((int) (Math.random() * 8 + 1)) {
            case 1, 2, 3 -> 2;      // 2 - 3/8
            case 4, 5, 6 -> 3;      // 3 - 3/8
            case 7 -> 4;            // 4 - 1/8
            default -> 5;           // 5 - 1/8
        };
    }

    @Override
    protected String describe() {
        return "shot'нул opp'a (Barrage)";
    }
}
