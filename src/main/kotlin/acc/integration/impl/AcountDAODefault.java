package acc.integration.impl;

import acc.integration.AccountDAO;
import acc.model.AnalAcc;
import acc.model.AccGroup;
import acc.model.AccId;
import acc.model.Osnova;
import acc.util.AccException;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class AcountDAODefault extends AccountDAO {

    static int keyC = 1;
//    public static AnalAcc pocUcetRozv = new AnalAcc(keyC++,
//            AnalAcc.POC_UCET_ROZVAZNY_GROUP, "001", AnalAcc.POC_UCET_ROZVAZNY_GROUP.getName());

    private final NavigableMap<AccId, AnalAcc> accountById = new TreeMap<>();
    private final NavigableMap<String, AnalAcc> accountByName = new TreeMap<>();

    public AcountDAODefault() {
        try {
            //   accountById.put(OsnovapocUcetRozv.getId(), pocUcetRozv);
            create(Osnova.instance.getPocatecniUcetRozvazny(), "001", "Pocatecni ucet rozvazny");
            create(Osnova.instance.getGroup("321").get(), "001", "Dodavatele");
            create(Osnova.instance.getGroup("221").get(), "001", "Fio");
            create(Osnova.instance.getGroup("501").get(), "001", "Material");
        } catch (AccException ex) {
            Logger.getLogger(AcountDAODefault.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void create(AccGroup skupina, String no, String name) throws AccException {
        AnalAcc a = new AnalAcc(keyC++, skupina, no, name);
        accountById.put(a.getId(), a);
        accountByName.put(a.getNumber(), a);
    }

    @Override
    public List<AnalAcc> getAll() throws AccException {
        return new ArrayList<>(accountById.values());
    }

    @Override
    public void delete(AccId id) throws AccException {
        accountById.remove(id);
    }

    @Override
    public Optional<AnalAcc> getByNumber(String number) throws AccException {
        return Optional.ofNullable(accountByName.get(number));
    }

    @Override
    public List<AnalAcc> getBalancing() throws AccException {
        return accountById.values().stream().filter((AnalAcc a) -> a.isBalanced()).collect(Collectors.toList());
    }

    @Override
    public AnalAcc getPocatecniUcetRozvazny() throws AccException {
        return getByNumber(Osnova.instance.getPocatecniUcetRozvazny().getNumber() + "001").get();
    }

    @Override
    public List<AnalAcc> getByGroup(AccGroup accg) throws AccException {
        return accountById.values().stream()
                .filter(a -> a.getSyntAccount().equals(accg))
                .collect(Collectors.toList());
    }

    @Override
    public List<AnalAcc> getByClass(AccGroup accg) throws AccException {
        return accountById.values().stream()
                .filter(a -> a.getAClass().equals(accg))
                .collect(Collectors.toList());
    }

}
