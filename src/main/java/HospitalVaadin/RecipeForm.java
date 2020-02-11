package HospitalVaadin;

import HospitalUI.MyUI;
import HospitalUI.Doctor;
import HospitalUI.Patient;
import HospitalUI.Recipe;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class RecipeForm extends FormLayout {

    private TextField description = new TextField("Описание");
    private PopupDateField recDate = new PopupDateField("Дата создания");
    private TextField duration = new TextField("Срок действия");
    private NativeSelect priority = new NativeSelect("Приоритет");

    public ComboBox DoctorSelect = new ComboBox("Выбор врача");
    public ComboBox PatientSelect = new ComboBox("Выбор пациента");

    public ComboBox SelectPriorityRecipe = new ComboBox(" Поиск по приоритету ");
    public ComboBox SelectPatientRecipe = new ComboBox("Поиск по пациенту");

    private Button save = new Button("Сохранить");
    private Button delete = new Button("Удалить");
    private Button cancel = new Button("Отменить");


    private DoctorService serviceDoc = DoctorService.getInstanceDoc();     //GUI service doctor
    private PatientService servicePat = PatientService.getInstancePat();   //GUI service patient
    private RecipeService serviceRec = RecipeService.getInstanceRec();     //GUI service recipe

    private Recipe recipe;
    private MyUI myUI;

    public RecipeForm(MyUI myUI){

        this.myUI = myUI;

        priority.addItems(RecipePriority.values());

        DoctorSelect.addItems(serviceDoc.ContactsDoctor.values());
        PatientSelect.addItems(servicePat.ContactsPatient.values());

        SelectPriorityRecipe.addItems(RecipePriority.values());
        SelectPatientRecipe.addItems(servicePat.ContactsPatient.values());

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.addClickListener(e->{
            recipe.ChangeRecipe(recipe.getDescription(),recipe.getRecDate(),recipe.getDuration(),recipe.getPriority(),recipe.getDoctor_id(),recipe.getPatient_id(),recipe.getDoctor(),recipe.getPatient());
            save();
        });

        delete.addClickListener(e->delete());

        cancel.setStyleName(ValoTheme.BUTTON_PRIMARY);
        cancel.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);
        cancel.addClickListener(e->cancelRec(recipe));

        setSizeUndefined();

        HorizontalLayout buttons =new HorizontalLayout(save,cancel,delete);
        buttons.setSpacing(true);

        addComponents(description,recDate,duration,priority,DoctorSelect,PatientSelect,buttons);

        DoctorSelect.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent event) {

                if (DoctorSelect.getValue()== null) {
                    return;
                }

                Doctor doctor= (Doctor) event.getProperty().getValue();

                try {
                    doctor = (Doctor) doctor.clone();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                String value = event.getProperty().getValue().toString();

                recipe.setDoctor_id(doctor.getId());
                recipe.setDoctor(value);

                DoctorSelect.setNullSelectionAllowed(false);
                DoctorSelect.setValue(serviceDoc.ContactsDoctor.get(recipe.getDoctor_id()));

            }
        });
        DoctorSelect.setImmediate(true);



        PatientSelect.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent event) {

                if (PatientSelect.getValue() == null) {
                    return;
                }

                Patient patient= (Patient) event.getProperty().getValue();

                try {
                    patient = (Patient) patient.clone();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                String value = event.getProperty().getValue().toString();

                recipe.setPatient_id(patient.getId());
                recipe.setPatient(value);

                PatientSelect.setNullSelectionAllowed(false);
                PatientSelect.setValue(servicePat.ContactsPatient.get(recipe.getPatient_id()));

            }
        });
        PatientSelect.setImmediate(true);


        SelectPriorityRecipe.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent event) {}
        });
        SelectPriorityRecipe.setImmediate(true);


        SelectPatientRecipe.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent event) {}
        });
        SelectPatientRecipe.setImmediate(true);

    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
        BeanFieldGroup.bindFieldsUnbuffered(recipe,this);
        delete.setVisible(recipe.isPersisted());
        setVisible(true);
        description.selectAll();
    }

    private void save(){
        serviceRec.save(recipe);
        myUI.updateList();
        setVisible(false);
    }

    private void delete(){
        serviceRec.delete(recipe);
        myUI.updateList();
        setVisible(false);
    }

    public void cancelRec(Recipe recipe){
        setRecipe(recipe);
        myUI.closeWindow();
    }

}
