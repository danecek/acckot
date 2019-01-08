package acc.impl;

import acc.integration.AccountDAO;
import acc.model.AnalAcc;
import acc.model.AccGroup;
import acc.model.AccId;
import acc.util.AccException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class H2ReaderDAOImpl extends AccountDAO {

    private PreparedStatement createPs;
    private PreparedStatement getAllPs;
    private PreparedStatement deletePs;
    private Connection conn;

    public H2ReaderDAOImpl() {
        try {
           // new org.h2.Driver();
            conn = DriverManager.getConnection("jdbc:h2:~/Vyuka/apj/Lib2018H2DB", "sa", "");
            createPs = conn.prepareStatement("insert into READERS values(default, ?, ?)");
            getAllPs = conn.prepareStatement("select * from READERS");
            deletePs = conn.prepareStatement("delete from READERS where id = ?");
        } catch (SQLException ex) {
            Logger.getLogger(H2ReaderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void create(String user) throws SQLException {
    try (
        Connection connection = null; //dataSource.getConnection();
        PreparedStatement statement = null//connection.prepareStatement(SQL_INSERT,
 //                                     Statement.RETURN_GENERATED_KEYS);
    ) {
//        statement.setString(1, user.getName());
//        statement.setString(2, user.getPassword());
//        statement.setString(3, user.getEmail());
        // ...

        int affectedRows = statement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
//                user.setId(generatedKeys.getLong(1));
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
    }
}

//    @Override
//    public void create(String name, Address adr) throws LibException {
//        try {
//            createPs.setString(1, name);
//            createPs.setString(2, adr.toString());
//            createPs.execute();
//        } catch (SQLException ex) {
//            throw new LibException(ex);
//        }
//    }
    @Override
    public List<AnalAcc> getAll() throws AccException {
        try {
            List<AnalAcc> readers = new ArrayList<>();
            ResultSet rs = getAllPs.executeQuery();
            while (rs.next()) {
                //             readers.add(new Account(rs.getInt(1),   rs.getString(2)));
            }
            return readers;
        } catch (SQLException ex) {
            throw new AccException(ex);
        }
    }
    private static final Logger LOG = Logger.getLogger(H2ReaderDAOImpl.class.getName());

//    @Override
//    public void delete(AcountId id) throws LibException {
//        try {
//            deletePs.setInt(1, id.getId());
//            deletePs.execute();
//        } catch (SQLException ex) {
//            throw new LibException(ex);
//
//        }
//    }
    public void close() {
        try {
            createPs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(H2ReaderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(AccId id) throws AccException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(AccGroup skupina, String no, String name) throws AccException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<AnalAcc> getBalancing() throws AccException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Optional<AnalAcc> getByNumber(String name) throws AccException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AnalAcc getPocatecniUcetRozvazny() throws AccException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<AnalAcc> getByGroup(AccGroup accg) throws AccException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<AnalAcc> getByClass(AccGroup accg) throws AccException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
