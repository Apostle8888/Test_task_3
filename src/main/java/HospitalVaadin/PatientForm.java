package HospitalVaadin;

import HospitalUI.MyUI;
import HospitalUI.Patient;
import HospitalUI.Recipe;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class PatientForm extends FormLayout {

    private TextField firstName = new TextField("Имя");
    private TextField secondName = new TextField("Отчество");
    private TextField lastName = new TextField("Фамилия");
    private TextField phone = new TextField("Телефон");

    private Button save = new Button("Сохранить");
    private Button delete = new Button("Удалить");
    private Button cancel = new Button("Отменить");

    private PatientService servicePat = PatientService.getInstancePat();   //GUI service patient
    private RecipeService serviceRec = RecipeService.getInstanceRec();     //GUI service recipe
    private Patient patient;
    private MyUI myUI;

    public PatientForm (MyUI myUI){

        this.myUI = myUI;

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.addClickListener(e->{
            patient.ChangePatient(patient.getFirstname(),patient.getSecondname(),patient.getLastname(),patient.getPhone());
            save();

        });

        cancel.setStyleName(ValoTheme.BUTTON_PRIMARY);
        cancel.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);
        cancel.addClickListener(e->cancelPat(patient));

        delete.addClickListener(e->delete(patient));

        setSizeUndefined();
        HorizontalLayout buttons =new HorizontalLayout(save,cancel,delete);
        buttons.setSpacing(true);
        addComponents(firstName,secondName,lastName,phone,buttons);
    }

    public void setPatient (Patient patient) {
        this.patient = patient;
        BeanFieldGroup.bindFieldsUnbuffered(patient,this);

        delete.setVisible(patient.isPersisted());
        setVisible(true);
        firstName.selectAll();
    }

    private void save(){
        servicePat.save(patient);

        for (Recipe contact : serviceRec.ContactsRecipe.values())
        {
            if(patient.getId()==contact.getPatient_id())
            {
                contact.setPatient(patient.toString());
                serviceRec.save(contact);
            }
        }

        myUI.updateList();
        setVisible(false);
    }

    private void delete(Patient patient){

        try {
            patient = (Patient) patient.clone();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        for (Recipe recipe : serviceRec.ContactsRecipe.values()){
            if(patient.getId()==recipe.getPatient_id()){
                myUI.updateList();
                setVisible(false);
                return;
            }
        }
        servicePat.delete(patient);
        myUI.updateList();
        setVisible(false);
    }

    public void cancelPat(Patient patient){
        setPatient(patient);
        myUI.closeWindow();
    }

}
