package Service;

import HospitalDAO.DAOManager;
import HospitalDAO.PatDao;
import HospitalUI.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatService extends DAOManager implements PatDao {

    DAOManager daoManager= new DAOManager();

    @Override
    public void add(Patient patient) throws SQLException {
        Connection con = daoManager.dbConnection();
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO PATIENTS (ID, FIRSTNAME, SECONDNAME, LASTNAME, PHONE) VALUES (?, ?, ?, ?, ?)";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1,patient.getId());
            preparedStatement.setString(2, patient.getFirstname());
            preparedStatement.setString(3, patient.getSecondname());
            preparedStatement.setString(4, patient.getLastname());
            preparedStatement.setString(5, patient.getPhone());
            preparedStatement.executeUpdate();
            System.out.println("INSERT INTO PATIENTS");
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("INSERT INTO ERROR PATIENTS");
        } finally {
            if(preparedStatement !=null){
                preparedStatement.close();
            }
            if (con !=null){
                con.close();
            }
        }
    }

    @Override
    public List<Patient> getAll() throws SQLException {
        Connection con = daoManager.dbConnection();
        List<Patient> Patientlist = new ArrayList<>();
        String sql = "SELECT ID, FIRSTNAME, SECONDNAME, LASTNAME, PHONE FROM PATIENTS";

        Statement statement = null;

        try {
            statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Patient patient = new Patient();
                patient.setId(resultSet.getLong("ID"));
                patient.setFirstname(resultSet.getString("FIRSTNAME"));
                patient.setSecondname(resultSet.getString("SECONDNAME"));
                patient.setLastname(resultSet.getString("LASTNAME"));
                patient.setPhone(resultSet.getString("PHONE"));
                Patientlist.add(patient);
                System.out.println("getALL PATIENTS");
            }
        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERROR getALL PATIENTS");
        } finally {
            if(statement !=null){
                statement.close();
            }
            if (con !=null){
                con.close();
            }
        }
        return Patientlist;
    }

    @Override
    public Patient getById(Long id) throws SQLException {
        Connection con = daoManager.dbConnection();
        PreparedStatement preparedStatement = null;
        String sql = "SELECT ID, FIRSTNAME, SECONDNAME, LASTNAME, PHONE FROM PATIENTS WHERE ID=?";
        Patient patient = new Patient();
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery(sql);

            patient.setId(resultSet.getLong("ID"));
            patient.setFirstname(resultSet.getString("FIRSTNAME"));
            patient.setSecondname(resultSet.getString("SECONDNAME"));
            patient.setLastname(resultSet.getString("LASTNAME"));
            patient.setPhone(resultSet.getString("PHONE"));
            preparedStatement.executeUpdate();
            System.out.println("getById PATIENTS");
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("ERROR getById PATIENTS");
        } finally {
            if(preparedStatement !=null){
                preparedStatement.close();
            }
            if (con !=null){
                con.close();
            }
        }
        return patient;
    }

    @Override
    public void update(Patient patient) throws SQLException {
        Connection con = daoManager.dbConnection();
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE PATIENTS SET FIRSTNAME=?, SECONDNAME=?, LASTNAME=?, PHONE=? WHERE ID=?";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, patient.getFirstname());
            preparedStatement.setString(2, patient.getSecondname());
            preparedStatement.setString(3, patient.getLastname());
            preparedStatement.setString(4, patient.getPhone());
            preparedStatement.setLong(5,patient.getId());
            preparedStatement.executeUpdate();
            System.out.println("UPDATE PATIENTS");
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERROR UPDATE PATIENTS");
        } finally {
            if(preparedStatement !=null){
                preparedStatement.close();
            }
            if (con !=null){
                con.close();
            }
        }

    }

    @Override
    public void remove(Patient patient) throws SQLException {
        Connection con = daoManager.dbConnection();
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM PATIENTS WHERE ID=?";

        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1,patient.getId());
            preparedStatement.executeUpdate();
            System.out.println("REMOVE PATIENTS");
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("ERROR REMOVE PATIENTS");
        } finally {
            if(preparedStatement !=null){
                preparedStatement.close();
            }
            if (con !=null){
                con.close();
            }
        }
    }
}
