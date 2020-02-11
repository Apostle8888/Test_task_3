package HospitalDAO;

import HospitalUI.Patient;
import java.sql.SQLException;
import java.util.List;

public interface PatDao {

    void add(Patient patient) throws SQLException;

    List<Patient> getAll() throws SQLException;

    Patient getById(Long id) throws SQLException;

    void update(Patient patient) throws SQLException;

    void remove(Patient patient) throws SQLException;

}
