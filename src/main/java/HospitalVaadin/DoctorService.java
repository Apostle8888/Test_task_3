package HospitalVaadin;

import HospitalUI.Doctor;
import HospitalUI.DoctorStat;
import Service.DocService;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlTool;
import org.hsqldb.cmdline.SqlToolError;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoctorService {

    DocService docService = new DocService();   //DAO service doctor

    private static DoctorService instanceDoc;
    private static final Logger LOGGER = Logger.getLogger(DoctorService.class.getName());
    public final HashMap<Long, Doctor> ContactsDoctor = new HashMap<>();
    public final HashMap<Long, DoctorStat> ContactsDoctorStat = new HashMap<>();

    public DoctorService() {}

    public static DoctorService getInstanceDoc() {
        if (instanceDoc == null) {
            instanceDoc = new DoctorService();
            instanceDoc.DoctorData();

        }
        return instanceDoc;
    }

    public synchronized List<Doctor> findAll() {
        return findAll(null);
    }

    public synchronized List<Doctor> findAll(String stringFilter) {
        ArrayList<Doctor> arrayList = new ArrayList<>();
        for (Doctor doctor : ContactsDoctor.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || doctor.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(doctor.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(DoctorService.class.getName()).log(Level.SEVERE, null, ex);  //серьезная ошибка
            }
        }
        Collections.sort(arrayList, new Comparator<Doctor>() {

            @Override
            public int compare(Doctor doctor1, Doctor doctor2) {
                return (int) (doctor2.getId() - doctor1.getId());
            }
        });
        return arrayList;
    }

    public synchronized List<Doctor> findAll(String stringFilter, int start, int maxresults) {
        ArrayList<Doctor> arrayList = new ArrayList<>();
        for (Doctor doctor : ContactsDoctor.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || doctor.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(doctor.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(DoctorService.class.getName()).log(Level.SEVERE, null, ex);   //серьезная ошибка
            }
        }
        Collections.sort(arrayList, new Comparator<Doctor>() {

            @Override
            public int compare(Doctor doctor1, Doctor doctor2) {
                return (int) (doctor2.getId() - doctor1.getId());
            }
        });
        int end = start + maxresults;
        if (end > arrayList.size()) {
            end = arrayList.size();
        }
        return arrayList.subList(start, end);
    }

    public synchronized long count() {
        return ContactsDoctor.size();
    }

    public synchronized void delete(Doctor doctor) {

        try {
            docService.remove(doctor);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            List<Doctor> doctorList = docService.getAll();  //Вывод всех сделанных запросов
            for (Doctor d:doctorList){System.out.println(d);}
        }
        catch (SQLException e){e.printStackTrace();}

        ContactsDoctor.remove(doctor.getId());
    }

    public synchronized void save(Doctor doctor) {
        if (doctor == null) {
            LOGGER.log(Level.SEVERE,"Doctor is null");     //серьезная ошибка
            return;
        }
        if (doctor.getId() == null)
        {
            Long i=Long.valueOf(count());
            doctor.setId(i++);
        }
        try {
            doctor = (Doctor) doctor.clone();

        } catch (Exception ex) {
            throw new RuntimeException(ex);}

        // Взаимодействие с БД (hsqlhosp) в таблице DOCTORS

        if (ContactsDoctor.get(doctor.getId()) == null)
        {
            try {
                docService.add(doctor);                    //Добавление нового врача в таблицу DOCTORS из БД (hsqlhosp)
            }
            catch (SQLException e)
            {e.printStackTrace();}
        }

        try {
            docService.update(doctor);                       //Обновление DOCTORS в БД (hsqlhosp)
        }
        catch (SQLException e){e.printStackTrace();}

        try {
                List<Doctor> doctorList = docService.getAll();  //Вывод всех сделанных запросов DOCTORS из БД (hsqlhosp)
                for (Doctor d:doctorList){System.out.println(d);}
            }
        catch (SQLException e){e.printStackTrace();}

        ContactsDoctor.put(doctor.getId(), doctor);

    }

    public void DoctorData() {

        try {
            List<Doctor> docList = docService.getAll();      //Вывод всех сделанных запросов
            for (Doctor doctor:docList)
            {
                ContactsDoctor.put(doctor.getId(), doctor);
            }

        }
        catch (SQLException e)
        {e.printStackTrace();}

    }

}