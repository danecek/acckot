package acc.model;

import java.io.Serializable;

public class AbstrId<T extends AbstrId> implements Comparable<T>, Serializable {

    public void setId(int id) {
        this.id = id;
    }

    public AbstrId(int id) {
        this.id = id;
    }

    private int id;

    @Override
    public boolean equals(Object that) {
        if (that == null) {
            return false;
        }
        if (this.getClass() != that.getClass()) {
            return false;
        }
        T bookId = (T) that;
        return compareTo(bookId) == 0;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public int compareTo(T that) {
        return this.id - that.getId();
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }

}
