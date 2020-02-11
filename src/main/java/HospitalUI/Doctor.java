package HospitalUI;

import java.io.Serializable;
import java.util.regex.*;

public class Doctor implements Serializable, Cloneable
{
    private Long id= null;
    private String firstname;
    private String secondname;
    private String lastname;
    private String specialization;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondname() {
        return secondname;
    }
    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSpecialization() {
        return specialization;
    }
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public boolean isPersisted() {
        return id != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Doctor doctor = (Doctor) o;

        if (id != null ? !id.equals(doctor.id) : doctor.id != null) return false;
        if (firstname != null ? !firstname.equals(doctor.firstname) : doctor.firstname != null) return false;
        if (secondname != null ? !secondname.equals(doctor.secondname) : doctor.secondname != null) return false;
        if (lastname != null ? !lastname.equals(doctor.lastname) : doctor.lastname != null) return false;
        return specialization != null ? specialization.equals(doctor.specialization) : doctor.specialization == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (secondname != null ? secondname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (specialization != null ? specialization.hashCode() : 0);
        return result;
    }

    public void ChangeDoctor(String Firstname, String Secondname, String Lastname, String Specialization) {
        if (Firstname == null)
            throw new NullPointerException(Firstname);
        if (Secondname == null)
            throw new NullPointerException(Secondname);
        if (Lastname == null)
            throw new NullPointerException(Lastname);
        if (Specialization == null)
            throw new NullPointerException(Specialization);

        String pattern = "[A-Za-z0-9]";
        Pattern p = Pattern.compile(pattern);
        Matcher m1 = p.matcher(Firstname+Secondname+Lastname+Specialization);
        while(m1.find()){
            throw new  IllegalArgumentException(Firstname+Secondname+Lastname+Specialization);
        }

        firstname = Firstname;
        secondname = Secondname;
        lastname = Lastname;
        specialization = Specialization;
    }

    @Override
    public Doctor clone() throws CloneNotSupportedException {
        return (Doctor) super.clone();
    }

    @Override
    public String toString() {
        return lastname+" "+firstname+" "+secondname+" "+"("+specialization+")";
    }
}
