package HospitalDAO;

import HospitalUI.Doctor;
import java.sql.SQLException;
import java.util.List;

public interface DocDao {

    void add(Doctor doctor) throws SQLException;

    List<Doctor> getAll() throws SQLException;

    Doctor getById(Long id) throws SQLException;

    void update(Doctor doctor) throws SQLException;

    void remove(Doctor doctor) throws SQLException;

}
