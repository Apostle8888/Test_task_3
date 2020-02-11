package HospitalDAO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DAOManager
{
    private static DAOManager instanceDAO;
    private static final String DB_DRIVER = "org.hsqldb.jdbcDriver";
    private static final String DB_URL = "jdbc:hsqldb:file:hsqlhosp";

    private static final String DB_USERNAME = "SA";
    private static final String DB_PASSWORD = "";

    Connection connection = null;

    public static DAOManager getDAOManager() {
        if (instanceDAO == null) {
            instanceDAO = new DAOManager();
            try {
                instanceDAO.TablesCreate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return instanceDAO;
    }

    public Connection dbConnection()
    {

        try {
            Class.forName(DB_DRIVER);
            connection=DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
            System.out.println("Connection OK");
        } catch (ClassNotFoundException|SQLException e) {
            e.printStackTrace();
            System.out.println("Connection ERROR");
        }
        return connection;
    }

    public void TablesCreate() throws SQLException
    {
        Connection con = dbConnection();
        BufferedReader in = null;
        try
        {
            in = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/src/main/java/Service/TablesDocPatRec.sql"));
        }
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

        String TablesCreate =s.substring(0,s.indexOf("INSERT INTO DOCTORS"));

        //String subDocInsert =s.substring(s.indexOf("INSERT INTO DOCTORS"));
        //String InsDoctors = s.substring(s.indexOf("INSERT INTO DOCTORS"),s.indexOf("\n"));
        //System.out.println(InsDoctors);


/*
            String subDocSelectAll =s.substring(0,s.indexOf("FROM DOCTORS")+12);
            String SelectAllDoctors = subDocSelectAll.substring(subDocSelectAll.lastIndexOf("SELECT ID"),s.indexOf("FROM DOCTORS")+12);
            System.out.println(SelectAllDoctors);

            String subPatSelectAll =s.substring(0,s.indexOf("FROM PATIENTS")+13);
            String SelectAllPatients = subPatSelectAll.substring(subPatSelectAll.lastIndexOf("SELECT ID"),s.indexOf("FROM PATIENTS")+13);
            System.out.println(SelectAllPatients);

            String subRecSelectAll =s.substring(0,s.indexOf("FROM RECIPES")+12);
            String SelectAllRecipes = subRecSelectAll.substring(subRecSelectAll.lastIndexOf("SELECT ID"),s.indexOf("FROM RECIPES")+12);
            System.out.println(SelectAllRecipes);

            String subDocID =s.substring(0,s.indexOf("FROM DOCTORS WHERE ID=?")+23);
            String SelectIDDoctors = subDocID.substring(subDocID.lastIndexOf("SELECT ID"),s.indexOf("FROM DOCTORS WHERE ID=?")+23);
            System.out.println(SelectIDDoctors);

            String subPatID =s.substring(0,s.indexOf("FROM PATIENTS WHERE ID=?")+24);
            String SelectIDPatients = subPatID.substring(subPatID.lastIndexOf("SELECT ID"),s.indexOf("FROM PATIENTS WHERE ID=?")+24);
            System.out.println(SelectIDPatients);

            String subRecID =s.substring(0,s.indexOf("WHERE DOCTOR=? AND PATIENT=?")+28);
            String SelectIDRecipes = subRecID.substring(subRecID.lastIndexOf("SELECT ID"),s.indexOf("WHERE DOCTOR=? AND PATIENT=?")+28);
            System.out.println(SelectIDRecipes);
*/



        Statement stm = con.createStatement();
        try {
            stm.executeUpdate(TablesCreate);
            System.out.println(TablesCreate);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Tables ERROR");
        }

        finally {
            if(stm !=null){
                stm.close();
            }
            if (con !=null){
                con.close();
            }
        }
    }

}