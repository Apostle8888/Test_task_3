package HospitalUI;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Patient implements Serializable, Cloneable
{
    private Long id = null;
    private String firstname;
    private String secondname;
    private String lastname;
    private String phone;

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

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isPersisted() {
        return id != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Patient patient = (Patient) o;

        if (id != null ? !id.equals(patient.id) : patient.id != null) return false;
        if (firstname != null ? !firstname.equals(patient.firstname) : patient.firstname != null) return false;
        if (secondname != null ? !secondname.equals(patient.secondname) : patient.secondname != null) return false;
        if (lastname != null ? !lastname.equals(patient.lastname) : patient.lastname != null) return false;
        return phone != null ? phone.equals(patient.phone) : patient.phone == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (secondname != null ? secondname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }

    public void ChangePatient(String Firstname, String Secondname, String Lastname, String Phone) {
        if (Firstname == null)
            throw new NullPointerException(Firstname);
        if (Secondname == null)
            throw new NullPointerException(Secondname);
        if (Lastname == null)
            throw new NullPointerException(Lastname);
        if (Phone == null)
            throw new NullPointerException(Phone);

        String patternFullName = "[A-Za-z0-9]";
        Pattern p1 = Pattern.compile(patternFullName);
        Matcher m1 = p1.matcher(Firstname+Secondname+Lastname);
        while(m1.find()){
            throw new  IllegalArgumentException(Firstname+Secondname+Lastname);
        }

        firstname = Firstname;
        secondname = Secondname;
        lastname = Lastname;

        String pattern2 = "[а-яА-ЯёЁa-zA-Z]";
        Pattern p2 = Pattern.compile(pattern2);
        Matcher m2 = p2.matcher(Phone);
        while(m2.find()){
            throw new NumberFormatException(Phone);
        }

        String pattern3 = "^(8|\\+7)\\d{10}$";
        Pattern p3 = Pattern.compile(pattern3);
        Matcher m3 = p3.matcher(Phone);
        if (m3.matches()) {
            phone = Phone;
        }
        else { throw new NumberFormatException(Phone);}

    }


    @Override
    public String toString() {
        return lastname+" "+firstname+" "+secondname+" "+"("+phone+")";
    }

    @Override
    public Patient clone() throws CloneNotSupportedException {
        return (Patient) super.clone();
    }

}
