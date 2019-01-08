package acc.integration;

import acc.integration.impl.AcountDAODefault;
import acc.model.AnalAcc;
import acc.model.AccGroup;
import acc.model.AccId;
import acc.util.AccException;
import java.util.List;
import java.util.Optional;

public abstract class AccountDAO {

    public static AccountDAO instance = new AcountDAODefault();

    public abstract void create(AccGroup skupina, String no, String name) throws AccException;

    public abstract List<AnalAcc> getAll() throws AccException;

    public abstract Optional<AnalAcc> getByNumber(String name) throws AccException;

    public abstract AnalAcc getPocatecniUcetRozvazny() throws AccException;

    public abstract void delete(AccId id) throws AccException;

    public abstract List<AnalAcc> getBalancing() throws AccException;

    public abstract List<AnalAcc> getByGroup(AccGroup accg) throws AccException;

    public abstract List<AnalAcc> getByClass(AccGroup accg) throws AccException;

}
