package acc.model;

public class AnalAcc extends AccGroup {

    private final AccId id;
    private final String anal;

    public AnalAcc(int id, AccGroup group, String anal, String name) {
        super(GroupEnum.ANAL, name, name);
        setParent(group);
        this.anal = anal;
        this.id = new AccId(id);
    }

    public String getAnal() {
        return anal;
    }

    public boolean isPocUcetRozv() {
        return getOptParent().equals(Osnova.instance.getPocatecniUcetRozvazny());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AnalAcc)) {
            return false;
        }
        return ((AnalAcc) obj).getId().equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public AccId getId() {
        return id;
    }

    @Override
    public String getNumber() {
        return getOptParent().get().getNumber() + anal;
    }

    public String getNumberName() {
        if (isPocUcetRozv()) {
            return "";
        }
        return getNumber() + " - " + getName();
    }

    @Override
    public String toString() {
        return getNumber();
    }

    public AccGroup getSyntAccount() {
        return getOptParent().get();
    }

    public AccGroup getAClass() {
        return getSyntAccount() //
                .getOptParent().get() // group
                .getOptParent().get(); // class
    }

}
