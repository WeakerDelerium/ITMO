package obj.classes;

import obj.abclasses.Entity;
import obj.enums.Details;

import java.util.Objects;

public class Inanimate extends Entity {
    private Details detail = Details.DEFAULT;

    public Inanimate(String name) {
        super(name);
    }

    public Inanimate(String name, Details detail) {
        super(name);
        this.detail = detail;
    }

    public void setDetail(Details detail) {
        this.detail = detail;
    }


    public Details getDetail() {
        return this.detail;
    }

    @Override
    public String getName() {
        if (getDetail() != Details.DEFAULT) {
            return getDetail() + " " + super.getName();
        }
        return super.getName();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName(), getDetail());
    }
}
