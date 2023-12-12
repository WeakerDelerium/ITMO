package obj.classes;

import obj.abclasses.Entity;
import obj.exceptions.ToManyArgumentsException;
import obj.exceptions.WrongTypeArgumentsException;

public class Alive extends Entity {
    public final Huzzle huzzle = new Huzzle("мордочка");

    public Alive(String name) {
        super(name);
    }

    private class Huzzle extends Entity {
        public Huzzle(String name) {
            super(name);
        }
    }

    public void say(String... vars) {
        class Phrase {
            final String phrase;
            String verb = "сказал";

            public Phrase(String phrase) {
                this.phrase = phrase;
            }

            public Phrase(String verb, String phrase) {
                this.verb = verb;
                this.phrase = phrase;
            }

            @Override
            public String toString() {
                return getName() + " " + this.verb + ": \"" + this.phrase + "\"";
            }
        }

        if (vars.length == 1) {
            System.out.println(new Phrase(vars[0]));
        } else if (vars.length == 2) {
            System.out.println(new Phrase(vars[0], vars[1]));
        } else {
            throw new ToManyArgumentsException("Количество аргументов больше двух");
        }
    }

    private void checkIntersectException(Object... objects) {
        String message = "Неверные входные данные";

        for (Object obj : objects) {
            if (obj == this) {
                throw new UnsupportedOperationException(message);
            }
        }
        if (objects.length == 2 && objects[1] == objects[0]) {
            throw new UnsupportedOperationException(message);
        }
    }

    private void checkTypeException(Object obj1, Object obj2) throws WrongTypeArgumentsException {
        if (obj1 instanceof Alive && obj2 instanceof Alive) {
            throw new WrongTypeArgumentsException();
        }
    }

    private void checkCorrectException(Object... objects) {
        for (Object obj : objects) {
            if (obj instanceof Huzzle) {
                throw new UnsupportedOperationException("Невозможно произвети действие над объектом, который является частью другого");
            }
        }
    }

    public void come(Inanimate obj) {
        System.out.println(getName() + " пришел в " + obj.getName());
    }

    public void comeUp(Entity obj) {
        checkIntersectException(obj);
        checkCorrectException(obj);

        System.out.println(getName() + " подошёл к " + obj.getName());
    }

    public void climbOn(Inanimate obj) {
        System.out.println(getName() + " влез на " + obj.getName());
    }

    public void getIn(Entity obj1, Inanimate obj2) {
        checkIntersectException(obj1);
        checkCorrectException(obj1);

        System.out.println(getName() + " достал " + obj1.getName() + " с " + obj2.getName());
    }

    public void takeOff(Entity obj1, Entity obj2) throws WrongTypeArgumentsException {
        checkIntersectException(obj1, obj2);
        checkTypeException(obj1, obj2);
        checkCorrectException(obj1, obj2);

        System.out.println(getName() + " снял " + obj1.getName() + " с " + obj2.getName());
    }

    public void lookIn(Inanimate obj) {
        System.out.println(getName() + " заглянул в " + obj.getName());
    }

    public void stuck(Entity obj1, Entity obj2) throws WrongTypeArgumentsException {
        checkIntersectException(obj1, obj2);
        checkTypeException(obj1, obj2);
        checkCorrectException(obj2);

        System.out.println(getName() + " сунул " + obj1.getName() + " в " + obj2.getName());
    }

    public void lick(Entity obj) {
        checkIntersectException(obj);

        System.out.println(getName() + " лизнул " + obj.getName());
    }

    public void carry(Entity obj1, Entity obj2) throws WrongTypeArgumentsException {
        checkIntersectException(obj1, obj2);
        checkTypeException(obj1, obj2);
        checkCorrectException(obj1, obj2);

        System.out.println(getName() + " понес " + obj1.getName() + " к " + obj2.getName());
    }

    public void lookOut(Inanimate obj) {
        System.out.println(getName() + " выглянул из " + obj.getName());
    }

    public void put(Entity obj1, Entity obj2) throws WrongTypeArgumentsException {
        checkIntersectException(obj1, obj2);
        checkTypeException(obj1, obj2);
        checkCorrectException(obj1, obj2);

        System.out.println(getName() + " поставил " + obj1.getName() + " на " + obj2.getName());
    }

    public void getOut(Inanimate obj) {
        System.out.println(getName() + " вылез из " + obj.getName());
    }
}
