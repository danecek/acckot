package acc.model;

import java.util.Objects;
import java.util.Optional;

public class AccGroup implements Comparable<AccGroup> {

   // public static final AccGroup POC_UCET_ROZVAZNY_GROUP = Osnova.instance.getGroup("961").get();

    private final GroupEnum groupType;
    private AccGroup parent;
    private final String number;
    private final String fullName;

    public AccGroup(GroupEnum groupType, String number, String fullName) {
        this.groupType = groupType;
        this.number = number;
        this.fullName = fullName;
    }

    public void setParent(AccGroup parent) {
        this.parent = parent;
    }

    public Optional<AccGroup> getOptParent() {
        return Optional.ofNullable(parent);
    }

    public GroupEnum getGroupType() {
        return groupType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AccGroup)) {
            return false;
        }
        return compareTo((AccGroup) obj) == 0;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.number);
        return hash;
    }

    public boolean isActive() {
        if (groupType == GroupEnum.CLASS) {
            switch (getNumber()) {
                case "2":
                    return true;
                default:
                    return false;
            }
        }
        return parent.isActive();
    }

    boolean isPassive() {
        if (groupType == GroupEnum.CLASS) {
            switch (getNumber()) {
                case "3":
                case "9":
                    return true;
                default:
                    return false;
            }
        }
        return parent.isPassive();
    }

    public boolean isBalanced() {
        return isPassive() || isActive();
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return fullName.substring(0, Math.min(30, fullName.length()));
    }

    @Override
    public String toString() {
        return getNumber() + " - " + getName();
    }

    @Override
    public int compareTo(AccGroup o) {
        return number.compareTo(o.number);
    }

}
