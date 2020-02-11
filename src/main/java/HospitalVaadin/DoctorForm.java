package HospitalVaadin;

import HospitalUI.Doctor;
import HospitalUI.MyUI;
import HospitalUI.Recipe;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class DoctorForm extends FormLayout {

    private TextField firstName = new TextField("Имя");
    private TextField secondName = new TextField("Отчество");
    private TextField lastName = new TextField("Фамилия");
    private TextField specialization = new TextField("Специализация");

    private Button save = new Button("Сохранить");
    private Button delete = new Button("Удалить");
    private Button cancel = new Button("Отменить");


    private DoctorService serviceDoc = DoctorService.getInstanceDoc();   //GUI service doctor
    private RecipeService serviceRec = RecipeService.getInstanceRec();   //GUI service recipe
    private Doctor doctor;
    private MyUI myUI;

    public DoctorForm (MyUI myUI){

        this.myUI = myUI;

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.addClickListener(e->{
            doctor.ChangeDoctor(doctor.getFirstname(),doctor.getSecondname(),doctor.getLastname(),doctor.getSpecialization());
            save();

        });

        delete.addClickListener(e->delete(doctor));

        cancel.setStyleName(ValoTheme.BUTTON_PRIMARY);
        cancel.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);
        cancel.addClickListener(e->cancelDoc(doctor));

        setSizeUndefined();

        HorizontalLayout buttons =new HorizontalLayout(save,cancel,delete);
        buttons.setSpacing(true);
        addComponents(firstName,secondName,lastName,specialization,buttons);
    }

    public void setDoctor (Doctor doctor) {

        this.doctor = doctor;
        BeanFieldGroup.bindFieldsUnbuffered(doctor,this);

        delete.setVisible(doctor.isPersisted());
        setVisible(true);
        firstName.selectAll();
    }

    private void save(){

        serviceDoc.save(doctor);

            for (Recipe contact : serviceRec.ContactsRecipe.values())
            {
                if(doctor.getId()==contact.getDoctor_id())
                {
                    contact.setDoctor(doctor.toString());
                    serviceRec.save(contact);
                }
            }

        myUI.updateList();
        setVisible(false);
    }

    private void delete(Doctor doctor){

        try {
            doctor = (Doctor) doctor.clone();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        for (Recipe recipe : serviceRec.ContactsRecipe.values()){
            if(doctor.getId()==recipe.getDoctor_id()){
                myUI.updateList();
                setVisible(false);
                return;
            }
        }
        serviceDoc.delete(doctor);
        myUI.updateList();
        setVisible(false);
    }

    public void cancelDoc(Doctor doctor){
        setDoctor(doctor);
        myUI.closeWindow();
    }

}
