package HospitalDAO;

import HospitalUI.Recipe;
import java.sql.SQLException;
import java.util.List;

public interface RecDao {

    void add(Recipe recipe) throws SQLException;

    List<Recipe> getAll() throws SQLException;

    Recipe getByDoctorIdAndByPatient(Long doctor,Long patient) throws SQLException;

    void update(Recipe recipe) throws SQLException;

    void remove(Recipe recipe) throws SQLException;

}
