package HospitalVaadin;

import HospitalUI.Patient;
import Service.PatService;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientService {

    PatService patService = new PatService();   //DAO service patient

    private static PatientService instancePat;
    private static final Logger LOGGER = Logger.getLogger(PatientService.class.getName());
    public final HashMap<Long, Patient> ContactsPatient = new HashMap<>();

    public PatientService() {}

    public static PatientService getInstancePat() {
        if (instancePat == null) {
            instancePat = new PatientService();
           instancePat.PatientData();
        }
        return instancePat;
    }

    public synchronized List<Patient> findAll() {
        return findAll(null);
    }

    public synchronized List<Patient> findAll(String stringFilter) {
        ArrayList<Patient> arrayList = new ArrayList<>();
        for (Patient contact : ContactsPatient.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(PatientService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<Patient>() {

            @Override
            public int compare(Patient o1, Patient o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }

    public synchronized List<Patient> findAll(String stringFilter, int start, int maxresults) {
        ArrayList<Patient> arrayList = new ArrayList<>();
        for (Patient contact : ContactsPatient.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(PatientService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<Patient>() {

            @Override
            public int compare(Patient o1, Patient o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        int end = start + maxresults;
        if (end > arrayList.size()) {
            end = arrayList.size();
        }
        return arrayList.subList(start, end);
    }

    public synchronized long count() {
        return ContactsPatient.size();
    }

    public synchronized void delete(Patient patient) {

        try {
            patService.remove(patient);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            List<Patient> patientList = patService.getAll();  //Вывод всех сделанных запросов
            for (Patient p:patientList){System.out.println(p);}
        }
        catch (SQLException e){e.printStackTrace();}

        ContactsPatient.remove(patient.getId());
    }

    public synchronized void save(Patient patient) {
        if (patient == null) {
            LOGGER.log(Level.SEVERE,
                    "Patient is null.");
            return;
        }
        if (patient.getId() == null)
        {
            Long i=Long.valueOf(count());
            patient.setId(i++);
        }
        try {
            patient = (Patient) patient.clone();
        } catch (Exception ex) {
            throw new RuntimeException(ex);}

        // Взаимодействие с БД (hsqlhosp) в таблице PATIENTS

        if (ContactsPatient.get(patient.getId()) == null)
        {
            try {
                patService.add(patient);                 //Добавление нового врача в таблицу PATIENTS из БД (hsqlhosp)
            }
            catch (SQLException e)
            {e.printStackTrace();}
        }

        try {
            patService.update(patient);                     //Обновление RECIPES в БД (hsqlhosp)
        }
        catch (SQLException e){e.printStackTrace();}

        try {
            List<Patient>  patientList = patService.getAll();  //Вывод всех сделанных запросов  RECIPES
            for (Patient p:patientList){System.out.println(p);}
        }
        catch (SQLException e){e.printStackTrace();}

        ContactsPatient.put(patient.getId(), patient);

    }

    public void PatientData() {

        try {
            List<Patient> patList = patService.getAll();      //Вывод всех сделанных запросов
            for (Patient patient:patList)
            {
                ContactsPatient.put(patient.getId(), patient);
            }

        }
        catch (SQLException e)
        {e.printStackTrace();}
    }
}