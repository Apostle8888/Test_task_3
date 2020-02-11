package Service;

import HospitalDAO.DAOManager;
import HospitalDAO.RecDao;
import HospitalUI.Recipe;
import HospitalVaadin.DoctorService;
import HospitalVaadin.PatientService;
import HospitalVaadin.RecipePriority;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecService extends DAOManager implements RecDao {

    DAOManager daoManager= new DAOManager();
    private DoctorService serviceDoc = DoctorService.getInstanceDoc();     //GUI service doctor
    private PatientService servicePat = PatientService.getInstancePat();    //GUI service patient

    @Override
    public void add(Recipe recipe) throws SQLException {
        Connection con = daoManager.dbConnection();
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO RECIPES (ID, DESCRIPTION, RECDATE, DURATION, PRIORITY, DOCTOR, PATIENT) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = con.prepareStatement(sql);

            preparedStatement.setLong(1,recipe.getId());
            preparedStatement.setString(2,recipe.getDescription());
            preparedStatement.setDate(3,recipe.getRecDate());
            preparedStatement.setString(4,recipe.getDuration());
            preparedStatement.setString(5,recipe.getPriority().name());
            preparedStatement.setLong(6, recipe.getDoctor_id());
            preparedStatement.setLong(7, recipe.getPatient_id());
            preparedStatement.executeUpdate();
            System.out.println("INSERT INTO RECIPES");
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("INSERT INTO ERROR RECIPES");
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
    public List<Recipe> getAll() throws SQLException {
        Connection con = daoManager.dbConnection();
        List<Recipe> Recipelist = new ArrayList<>();
        String sql = "SELECT ID, DESCRIPTION, RECDATE, DURATION, PRIORITY, DOCTOR, PATIENT FROM RECIPES";

        Statement statement = null;

        try {
            statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Recipe recipe = new Recipe();
                recipe.setId(resultSet.getLong("ID"));
                recipe.setDescription(resultSet.getString("DESCRIPTION"));
                recipe.setRecDate(resultSet.getDate("RECDATE"));
                recipe.setDuration(resultSet.getString("DURATION"));
                recipe.setPriority(RecipePriority.valueOf(resultSet.getString("PRIORITY")));
                recipe.setDoctor_id(resultSet.getLong("DOCTOR"));
                recipe.setPatient_id(resultSet.getLong("PATIENT"));
                recipe.setDoctor(serviceDoc.ContactsDoctor.get(resultSet.getLong("DOCTOR")).toString());
                recipe.setPatient(servicePat.ContactsPatient.get(resultSet.getLong("PATIENT")).toString());

                Recipelist.add(recipe);
                System.out.println("getAll RECIPES");
            }
        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERROR getAll RECIPES");
        }finally {
            if(statement !=null){
                statement.close();
            }
            if (con !=null){
                con.close();
            }
        }
        return Recipelist;
    }

    @Override
    public Recipe getByDoctorIdAndByPatient(Long doctor, Long patient) throws SQLException {
        Connection con = daoManager.dbConnection();
        PreparedStatement preparedStatement = null;

        String sql = "SELECT ID, DESCRIPTION, RECDATE, DURATION, PRIORITY, DOCTOR, PATIENT RECIPES WHERE DOCTOR=? AND PATIENT=?";
        Recipe recipe = new Recipe();

        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1,doctor);
            preparedStatement.setLong(2,patient);
            ResultSet resultSet = preparedStatement.executeQuery(sql);

            recipe.setId(resultSet.getLong("ID"));
            recipe.setDescription(resultSet.getString("DESCRIPTION"));
            recipe.setRecDate(resultSet.getDate("RECDATE"));
            recipe.setDuration(resultSet.getString("DURATION"));
            recipe.setPriority(RecipePriority.valueOf(resultSet.getString("PRIORITY")));
            recipe.setDoctor_id(resultSet.getLong("DOCTOR"));
            recipe.setPatient_id(resultSet.getLong("PATIENT"));
            recipe.setDoctor(serviceDoc.ContactsDoctor.get(resultSet.getLong("DOCTOR")).toString());
            recipe.setPatient(servicePat.ContactsPatient.get(resultSet.getLong("PATIENT")).toString());

            preparedStatement.executeUpdate();
            System.out.println("getByDoctorIdAndByPatient RECIPES");

        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERROR getByDoctorIdAndByPatient RECIPES");
        } finally {
            if(preparedStatement !=null){
                preparedStatement.close();
            }
            if (con !=null){
                con.close();
            }
        }
        return recipe;
    }

    @Override
    public void update(Recipe recipe) throws SQLException {
        Connection con = daoManager.dbConnection();
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE RECIPES SET DESCRIPTION=?, RECDATE=?, DURATION=?, PRIORITY=?, DOCTOR=?, PATIENT=? WHERE ID=?";
        try {
            preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, recipe.getDescription());
            preparedStatement.setDate(2,recipe.getRecDate());
            preparedStatement.setString(3, recipe.getDuration());
            preparedStatement.setString(4, recipe.getPriority().name());
            preparedStatement.setLong(5,recipe.getDoctor_id());
            preparedStatement.setLong(6,recipe.getPatient_id());
            preparedStatement.setLong(7,recipe.getId());
            preparedStatement.executeUpdate();
            System.out.println("UPDATE RECIPES");
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERROR UPDATE RECIPES");
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
    public void remove(Recipe recipe) throws SQLException {
        Connection con = daoManager.dbConnection();
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM RECIPES WHERE ID=?";

        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1,recipe.getId());
            preparedStatement.executeUpdate();
            System.out.println("REMOVE RECIPES");
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("ERROR REMOVE RECIPES");
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
