package obj.classes;

import obj.abclasses.Entity;
import obj.enums.Details;

public class Pot extends Inanimate {
    private final String label;

    public Pot(String name, String label) {
        super(name);
        this.label = label;
    }

    public Pot(String name, Details detail, String inscription) {
        super(name, detail);
        this.label = inscription;
    }

    public static class Honey extends Inanimate {
        public Honey(String name) {
            super(name);
        }
    }

    public String getLabel() {
        return this.label;
    }

    public void written() {
        System.out.println("На " + getName() + " было написано \"" + getLabel() + "\"");
    }

    public void was(Entity obj) {
        if (obj == this) {
            throw new UnsupportedOperationException("Несочитаемые данные");
        }
        System.out.println("В " + getName() + " был " + obj.getName());
    }
}
