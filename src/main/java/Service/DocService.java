package Service;

import HospitalDAO.DAOManager;
import HospitalDAO.DocDao;
import HospitalUI.Doctor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocService extends DAOManager implements DocDao {

    DAOManager daoManager= new DAOManager();

    String InsertIntoDoctors;

    public void SqlRead() throws SQLException
    {
        Connection con = dbConnection();
        BufferedReader in = null;
        try
        {in = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/src/main/java/Service/TablesDocPatRec.sql"));}
        catch (FileNotFoundException e)
        {e.printStackTrace();}

        String str;
        StringBuffer sb = new StringBuffer();
        try
        {
            while ((str = in.readLine()) != null) {
                sb.append(str + "\n ");}
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();}


        String s = sb.toString();
        int posStartInsett= s.indexOf("INSERT INTO DOCTORS");

        boolean isContain = s.contains("FROM DOCTORS");
        if(isContain){
            int posStartSelectAll= s.indexOf("SELECT");
            String sub =s.substring(posStartSelectAll);
            int posEndDoc=sub.indexOf("\n");
            InsertIntoDoctors = sub.substring(0,posEndDoc);
        }

            if (con !=null){
                con.close();
            }
        }



    @Override
    public void add(Doctor doctor) throws SQLException {
        Connection con = daoManager.dbConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = con.prepareStatement("INSERT INTO DOCTORS (ID, FIRSTNAME, SECONDNAME, LASTNAME, SPECIALIZATION) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setLong(1,doctor.getId());
            preparedStatement.setString(2,doctor.getFirstname());
            preparedStatement.setString(3, doctor.getSecondname());
            preparedStatement.setString(4, doctor.getLastname());
            preparedStatement.setString(5, doctor.getSpecialization());
            preparedStatement.executeUpdate();
            System.out.println("INSERT INTO DOCTORS");
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("INSERT INTO ERROR DOCTORS");
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
    public List<Doctor> getAll() throws SQLException {
        Connection con = daoManager.dbConnection();
        List<Doctor> Doctorlist = new ArrayList<>();
        String sql = "SELECT ID, FIRSTNAME, SECONDNAME, LASTNAME, SPECIALIZATION FROM DOCTORS";

        Statement statement = null;

        try {
            statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Doctor doctor = new Doctor();
                doctor.setId(resultSet.getLong("ID"));
                doctor.setFirstname(resultSet.getString("FIRSTNAME"));
                doctor.setSecondname(resultSet.getString("SECONDNAME"));
                doctor.setLastname(resultSet.getString("LASTNAME"));
                doctor.setSpecialization(resultSet.getString("SPECIALIZATION"));
                Doctorlist.add(doctor);
                System.out.println("getALL DOCTORS");
            }
        }catch (SQLException e) {
         e.printStackTrace();
         System.out.println("ERROR getALL DOCTORS");
        }finally {
            if(statement !=null){
                statement.close();
            }
            if (con !=null){
                con.close();
            }
        }
        return Doctorlist;
    }

    @Override
    public Doctor getById(Long id) throws SQLException {
        Connection con = daoManager.dbConnection();
        PreparedStatement preparedStatement = null;
        String sql = "SELECT ID, FIRSTNAME, SECONDNAME, LASTNAME, SPECIALIZATION FROM DOCTORS WHERE ID=?";
        Doctor doctor = new Doctor();
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery(sql);

            doctor.setId(resultSet.getLong("ID"));
            doctor.setFirstname(resultSet.getString("FIRSTNAME"));
            doctor.setSecondname(resultSet.getString("SECONDNAME"));
            doctor.setLastname(resultSet.getString("LASTNAME"));
            doctor.setSpecialization(resultSet.getString("SPECIALIZATION"));
            preparedStatement.executeUpdate();
            System.out.println("getById DOCTORS");
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("ERROR getById DOCTORS");
        } finally {
            if(preparedStatement !=null){
                preparedStatement.close();
            }
            if (con !=null){
                con.close();
            }
        }
        return doctor;
    }

    @Override
    public void update(Doctor doctor) throws SQLException {
        Connection con = daoManager.dbConnection();
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE DOCTORS SET FIRSTNAME=?, SECONDNAME=?, LASTNAME=?, SPECIALIZATION=? WHERE ID=?";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, doctor.getFirstname());
            preparedStatement.setString(2, doctor.getSecondname());
            preparedStatement.setString(3, doctor.getLastname());
            preparedStatement.setString(4, doctor.getSpecialization());
            preparedStatement.setLong(5,doctor.getId());
            preparedStatement.executeUpdate();
            System.out.println("UPDATE DOCTORS");
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERROR UPDATE DOCTORS");
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
    public void remove(Doctor doctor) throws SQLException {
        Connection con = daoManager.dbConnection();
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM DOCTORS WHERE ID=?";

        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1,doctor.getId());
            preparedStatement.executeUpdate();
            System.out.println("REMOVE DOCTORS");
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("ERROR REMOVE DOCTORS");
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
