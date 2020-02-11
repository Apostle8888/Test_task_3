package HospitalUI;

import HospitalVaadin.RecipePriority;

import java.io.Serializable;
import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Recipe  implements Serializable, Cloneable
{
    private Long id = null;
    private String description;
    private Date RecDate;
    private String duration;
    private RecipePriority priority;

    Long doctor_id;
    Long patient_id;

    String Doctor;
    String Patient;

    public Recipe(){}

    public Recipe(String doc,String pat){
        this.Doctor=doc;
        this.Patient=pat;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRecDate() {
        return RecDate;
    }
    public void setRecDate(Date recDate) {
        RecDate = recDate;
    }

    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }

    public RecipePriority getPriority() {
        return priority;
    }
    public void setPriority(RecipePriority priority) {
        this.priority = priority;
    }

    public Long getDoctor_id() {
        return doctor_id;
    }
    public void setDoctor_id(Long doctor_id) {
        this.doctor_id = doctor_id;
    }

    public Long getPatient_id() {
        return patient_id;
    }
    public void setPatient_id(Long patient_id) {
        this.patient_id = patient_id;
    }

    public String getDoctor() {
        return Doctor;
    }
    public void setDoctor(String doctor) {
        Doctor = doctor;
    }

    public String getPatient() {
        return Patient;
    }
    public void setPatient(String patient) {
        Patient = patient;
    }

    public boolean isPersisted() {
        return id != null;
    }


    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", RecDate=" + RecDate +
                ", duration='" + duration + '\'' +
                ", priority=" + priority +
                ", doctor_id=" + doctor_id +
                ", patient_id=" + patient_id +
                ", Doctor='" + Doctor + '\'' +
                ", Patient='" + Patient + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        if (id != null ? !id.equals(recipe.id) : recipe.id != null) return false;
        if (description != null ? !description.equals(recipe.description) : recipe.description != null) return false;
        if (RecDate != null ? !RecDate.equals(recipe.RecDate) : recipe.RecDate != null) return false;
        if (duration != null ? !duration.equals(recipe.duration) : recipe.duration != null) return false;
        if (priority != recipe.priority) return false;
        if (doctor_id != null ? !doctor_id.equals(recipe.doctor_id) : recipe.doctor_id != null) return false;
        if (patient_id != null ? !patient_id.equals(recipe.patient_id) : recipe.patient_id != null) return false;
        if (Doctor != null ? !Doctor.equals(recipe.Doctor) : recipe.Doctor != null) return false;
        return Patient != null ? Patient.equals(recipe.Patient) : recipe.Patient == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (RecDate != null ? RecDate.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (priority != null ? priority.hashCode() : 0);
        result = 31 * result + (doctor_id != null ? doctor_id.hashCode() : 0);
        result = 31 * result + (patient_id != null ? patient_id.hashCode() : 0);
        result = 31 * result + (Doctor != null ? Doctor.hashCode() : 0);
        result = 31 * result + (Patient != null ? Patient.hashCode() : 0);
        return result;
    }

    public void ChangeRecipe(String Description, Date ReCDatE, String Duration, RecipePriority Priority, Long DoctoR_id, Long PatienT_id, String DoctoR, String PatienT) {
        if (Description == null)
            throw new NullPointerException(Description);
        if (ReCDatE == null)
            throw new NullPointerException(ReCDatE.toString());
        if (Duration == null)
            throw new NullPointerException(Duration);
        if (Priority == null)
            throw new NullPointerException(Priority.toString());


        if (DoctoR_id == null)
            throw new NullPointerException(DoctoR_id.toString());
        if (PatienT_id == null)
            throw new NullPointerException(PatienT_id.toString());
        if (DoctoR == null)
            throw new NullPointerException(DoctoR);
        if (PatienT == null)
            throw new NullPointerException(PatienT);


        String pattern1 = "[A-Za-z]";
        Pattern p1 = Pattern.compile(pattern1);
        Matcher m1 = p1.matcher(Description+Duration+PatienT);
        while(m1.find()){
            throw new  IllegalArgumentException(Description+Duration+PatienT);
        }

        description = Description;
        duration = Duration;
        priority = Priority;
        Patient = PatienT;

        String pattern2 = "[а-яА-ЯёЁa-zA-Z]";
        Pattern p2 = Pattern.compile(pattern2);
        Matcher m2 = p2.matcher(ReCDatE.toString()+DoctoR_id.toString()+PatienT_id.toString());
        while(m2.find()){
            throw new NumberFormatException(ReCDatE.toString()+DoctoR_id.toString()+PatienT_id.toString());
        }

        RecDate = ReCDatE;
        doctor_id = DoctoR_id;
        patient_id = PatienT_id;

        String pattern3 = "[A-Za-z0-9]";
        Pattern p3 = Pattern.compile(pattern3);
        Matcher m3 = p3.matcher(DoctoR);
        while(m3.find()){
            throw new  IllegalArgumentException(DoctoR);
        }

        Doctor = DoctoR;
    }

    @Override
    public Recipe clone() throws CloneNotSupportedException {
        return (Recipe) super.clone();
    }

}
