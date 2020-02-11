package HospitalUI;

import HospitalDAO.DAOManager;
import HospitalVaadin.*;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import javax.servlet.annotation.WebServlet;
import java.util.List;
import com.vaadin.ui.Window;

@Theme("mytheme")

public class MyUI extends UI
{
    private DAOManager serviceDAO = DAOManager.getDAOManager();
    private DoctorService serviceDoc = DoctorService.getInstanceDoc();       //GUI service doctor
    private PatientService servicePat = PatientService.getInstancePat();     //GUI service patient
    private RecipeService serviceRec = RecipeService.getInstanceRec();       //GUI service recipe

    private Grid gridDoc = new Grid();
    private Grid gridPat = new Grid();
    public Grid gridRec = new Grid();

    private Grid gridStatistic = new Grid();

    public TextField filterTextDoc = new TextField();
    private TextField filterTextPat = new TextField();
    private TextField filterTextRec = new TextField();

    private TextField filterTextRecDescription = new TextField();

    private DoctorForm formDoc = new DoctorForm(this);
    private PatientForm formPat = new PatientForm(this);
    private RecipeForm formRec = new RecipeForm(this);

    Window subwindow = new Window();

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        subwindow.setModal(true);

        VerticalLayout layout = new VerticalLayout();

        filterTextDoc.setInputPrompt("Поиск по врачам");
        filterTextDoc.addTextChangeListener(e ->{
            gridDoc.setContainerDataSource(new BeanItemContainer<>(Doctor.class,
                    serviceDoc.findAll(e.getText())));
        });

        filterTextPat.setInputPrompt("Поиск по пациентам");
        filterTextPat.addTextChangeListener(e ->{
            gridPat.setContainerDataSource(new BeanItemContainer<>(Patient.class,
                    servicePat.findAll(e.getText())));
        });

        filterTextRec.setInputPrompt("Поиск по рецептам");
        filterTextRec.addTextChangeListener(e ->{
            gridRec.setContainerDataSource(new BeanItemContainer<>(Recipe.class,
                    serviceRec.findAll(e.getText())));
        });

        filterTextRecDescription.setInputPrompt("Поиск по описанию");


        Button clearFiltrTexBtnDoc= new Button("Отменить");
        clearFiltrTexBtnDoc.addClickListener(e->{
            filterTextDoc.clear();
            updateList();
        });

        Button clearFiltrTexBtnPat= new Button("Отменить");
        clearFiltrTexBtnPat.addClickListener(e->{
            filterTextPat.clear();
            updateList();
        });

        Button clearFiltrTexBtnRec= new Button("Отменить");
        clearFiltrTexBtnRec.addClickListener(e->{
            filterTextRec.clear();
            filterTextRecDescription.clear();
            formRec.SelectPriorityRecipe.clear();
            formRec.SelectPatientRecipe.clear();
            updateList();
        });


        Button Enter = new Button("Применить");
        Enter.addClickListener(e->{

            if ((filterTextRecDescription.getValue().isEmpty())&&(formRec.SelectPriorityRecipe.getValue() == null)&&(formRec.SelectPatientRecipe.getValue() == null))
            {
                updateList();
                return;
            }

            if((formRec.SelectPriorityRecipe.getValue() == null)&&(formRec.SelectPatientRecipe.getValue() == null))
            {
                String ValueDescription = filterTextRecDescription.getValue();
                gridRec.setContainerDataSource(new BeanItemContainer<>(Recipe.class,
                        serviceRec.findDescription((ValueDescription))));
                return;

            }

            if((filterTextRecDescription.getValue().isEmpty())&&(formRec.SelectPriorityRecipe.getValue() == null))
            {
                String ValuePatient = formRec.SelectPatientRecipe.getValue().toString();
                gridRec.setContainerDataSource(new BeanItemContainer<>(Recipe.class,
                        serviceRec.findPriorityPatient(ValuePatient)));
                return;

            }

            if((filterTextRecDescription.getValue().isEmpty())&&(formRec.SelectPatientRecipe.getValue() == null))
            {
                String ValuePriority = formRec.SelectPriorityRecipe.getValue().toString();
                gridRec.setContainerDataSource(new BeanItemContainer<>(Recipe.class,
                        serviceRec.findPriorityPatient(ValuePriority)));
                return;

            }

            if((filterTextRecDescription.getValue().isEmpty()))
            {
                String ValuePatient = formRec.SelectPatientRecipe.getValue().toString();
                String ValuePriority = formRec.SelectPriorityRecipe.getValue().toString();
                gridRec.setContainerDataSource(new BeanItemContainer<>(Recipe.class,
                        serviceRec.findPriorityPatient((ValuePriority+ValuePatient))));
                return;
            }


            if((formRec.SelectPriorityRecipe.getValue() == null))
            {
                String ValueDescription = filterTextRecDescription.getValue();
                String ValuePatient = formRec.SelectPatientRecipe.getValue().toString();

                List Description = serviceRec.findDescription(ValueDescription);
                List Patient = serviceRec.findPriorityPatient(ValuePatient);

                Description.retainAll(Patient);

                gridRec.setContainerDataSource(new BeanItemContainer<>(Recipe.class,Description));
                return;

            }

            if((formRec.SelectPatientRecipe.getValue() == null))
            {
                String ValueDescription = filterTextRecDescription.getValue();
                String ValuePriority = formRec.SelectPriorityRecipe.getValue().toString();

                List Description = serviceRec.findDescription(ValueDescription);
                List Priority = serviceRec.findPriorityPatient(ValuePriority);

                Description.retainAll(Priority);

                gridRec.setContainerDataSource(new BeanItemContainer<>(Recipe.class,Description));
                return;
            }

                String ValueDescription = filterTextRecDescription.getValue();
                String ValuePriority = formRec.SelectPriorityRecipe.getValue().toString();
                String ValuePatient = formRec.SelectPatientRecipe.getValue().toString();

                List Description = serviceRec.findDescription(ValueDescription);
                List PriorityPatient =  serviceRec.findPriorityPatient((ValuePriority+ValuePatient));

                Description.retainAll(PriorityPatient);

                gridRec.setContainerDataSource(new BeanItemContainer<>(Recipe.class,Description));
        });


        CssLayout filteringDoc = new CssLayout();
        filteringDoc.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        filteringDoc.addComponents(filterTextDoc,clearFiltrTexBtnDoc);

        CssLayout filteringPat = new CssLayout();
        filteringPat.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        filteringPat.addComponents(filterTextPat,clearFiltrTexBtnPat);

        CssLayout filteringRec = new CssLayout();
        filteringRec.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        filteringRec.addComponents(filterTextRec,clearFiltrTexBtnRec);

        CssLayout filteringRecDescrPriorPat = new CssLayout();
        filteringRecDescrPriorPat.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        filteringRecDescrPriorPat.addComponents(filterTextRecDescription,formRec.SelectPriorityRecipe,formRec.SelectPatientRecipe,Enter,clearFiltrTexBtnRec);


        Button addCustomerBtnDoc = new Button("Добавить нового врача");
        addCustomerBtnDoc.addClickListener(e->{
            gridDoc.select(null);
            formDoc.setDoctor(new Doctor());
            subwindow.setContent(formDoc);
            addWindow(subwindow);
        });


        Button CloseDocStat= new Button("Закрыть статистику");
        CloseDocStat.addClickListener(e->{
            updateList();
        });

        CloseDocStat.setStyleName(ValoTheme.BUTTON_PRIMARY);
        CloseDocStat.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);

        Button ButtonDocStat = new Button("Показать статистику");
        ButtonDocStat.addClickListener(e->{
            gridStatistic.setColumns("statistic");

            List<Doctor> doctors = serviceDoc.findAll(filterTextDoc.getValue());
            List<Recipe> recipes = serviceRec.findAll(filterTextRec.getValue());

            int index = 0;

            for (Doctor doc : doctors){
                for (Recipe rec : recipes)
                {
                    if(doc.getId()==rec.getDoctor_id())
                    {
                        index++;
                    }
                }

                DoctorStat doctorStat = new DoctorStat();
                doctorStat.setStatistic(doc.toString()+" выписал(а) "+String.valueOf(index)+" рецепт(ов)");
                serviceDoc.ContactsDoctorStat.put(doc.getId(), doctorStat);
                index = 0;
            }


            gridStatistic.setContainerDataSource(new BeanItemContainer<>(DoctorStat.class, serviceDoc.ContactsDoctorStat.values()));
            VerticalLayout LayoutStatistic = new VerticalLayout(gridStatistic,CloseDocStat);

            LayoutStatistic.setMargin(true);
            LayoutStatistic.setSpacing(true);
            LayoutStatistic.setComponentAlignment(CloseDocStat, Alignment.MIDDLE_CENTER);

            subwindow.setContent(LayoutStatistic);
            addWindow(subwindow);

        });


        Button addCustomerBtnPat = new Button("Добавить нового пациента");
        addCustomerBtnPat.addClickListener(e->{
            gridPat.select(null);
            formPat.setPatient(new Patient());
            subwindow.setContent(formPat);
            addWindow(subwindow);
        });

        Button addCustomerBtnRec = new Button("Добавить новый рецепт");
        addCustomerBtnRec.addClickListener(e->{

            if (formRec.DoctorSelect.getValue() != null) {
                formRec.DoctorSelect.setValue(null);}

            if (formRec.PatientSelect.getValue() != null) {
                formRec.PatientSelect.setValue(null);}

            formRec.setRecipe(new Recipe());
            subwindow.setContent(formRec);
            addWindow(subwindow);
        });

        HorizontalLayout toolbarDoc = new HorizontalLayout(filteringDoc,addCustomerBtnDoc,ButtonDocStat);
        toolbarDoc.setSpacing(true);

        HorizontalLayout toolbarPat = new HorizontalLayout(filteringPat,addCustomerBtnPat);
        toolbarPat.setSpacing(true);

        HorizontalLayout toolbarRec = new HorizontalLayout(filteringRec,addCustomerBtnRec);
        toolbarRec.setSpacing(true);

        HorizontalLayout toolbarRecDescrPriorPat = new HorizontalLayout(filteringRecDescrPriorPat);
        toolbarRecDescrPriorPat.setSpacing(true);

        gridDoc.setColumns("firstname","secondname","lastname","specialization");
        HorizontalLayout mainDoc = new HorizontalLayout(gridDoc,formDoc);
        mainDoc.setSpacing(true);
        mainDoc.setSizeFull();
        gridDoc.setSizeFull();
        mainDoc.setExpandRatio(gridDoc,1);

        gridPat.setColumns("firstname","secondname","lastname","phone");
        HorizontalLayout mainPat = new HorizontalLayout(gridPat,formPat);
        mainPat.setSpacing(true);
        mainPat.setSizeFull();
        gridPat.setSizeFull();
        mainPat.setExpandRatio(gridPat,1);

        gridRec.setColumns("description","duration","doctor","patient");
        HorizontalLayout mainRec = new HorizontalLayout(gridRec,formRec);
        mainRec.setSpacing(true);
        mainRec.setSizeFull();
        gridRec.setSizeFull();
        mainRec.setExpandRatio(gridRec,1);

        layout.addComponents(toolbarDoc,mainDoc);
        layout.addComponents(toolbarPat,mainPat);
        layout.addComponents(toolbarRec,toolbarRecDescrPriorPat,mainRec);

        updateList();

        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);
        formDoc.setVisible(false);

        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);
        formPat.setVisible(false);

        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);
        formRec.setVisible(false);

        gridDoc.addSelectionListener(event ->{
            if (event.getSelected().isEmpty())
            {
                    formDoc.setVisible(false);
            }
            else {
                Doctor doctor = (Doctor) event.getSelected().iterator().next();
                formDoc.setDoctor(doctor);

                try {
                    doctor = (Doctor) doctor.clone();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);}

                formDoc.cancelDoc(doctor);
                subwindow.setContent(formDoc);
                addWindow(subwindow);
            }
        } );

        gridPat.addSelectionListener(event ->{
            if (event.getSelected().isEmpty()){
                formPat.setVisible(false);
            }
            else {
                Patient patient = (Patient) event.getSelected().iterator().next();
                formPat.setPatient(patient);

                try {
                    patient = (Patient) patient.clone();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);}

                formPat.cancelPat(patient);
                subwindow.setContent(formPat);
                addWindow(subwindow);
            }
        } );

        gridRec.addSelectionListener(event ->{
            if (event.getSelected().isEmpty()){
                formRec.setVisible(false);
            }
            else {
                Recipe recipe = (Recipe) event.getSelected().iterator().next();
                formRec.setRecipe(recipe);

                try {
                    recipe = (Recipe) recipe.clone();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);}

                formRec.cancelRec(recipe);
                subwindow.setContent(formRec);
                addWindow(subwindow);

                formRec.DoctorSelect.setNullSelectionAllowed(false);
                formRec.DoctorSelect.setValue(serviceDoc.ContactsDoctor.get(recipe.doctor_id));
                formRec.PatientSelect.setNullSelectionAllowed(false);
                formRec.PatientSelect.setValue(servicePat.ContactsPatient.get(recipe.patient_id));

            }
        } );
    }

    public void closeWindow(){
        subwindow.close();
    }

    public void updateList (){

        List<Doctor> doctors = serviceDoc.findAll(filterTextDoc.getValue());
        gridDoc.setContainerDataSource(new BeanItemContainer<>(Doctor.class, doctors));

        List<Patient> patients = servicePat.findAll(filterTextPat.getValue());
        gridPat.setContainerDataSource(new BeanItemContainer<>(Patient.class, patients));

        List<Recipe> recipes = serviceRec.findAll(filterTextRec.getValue());
        gridRec.setContainerDataSource(new BeanItemContainer<>(Recipe.class, recipes));

        serviceDoc.ContactsDoctorStat.clear();
        formRec.DoctorSelect.removeAllItems();
        formRec.PatientSelect.removeAllItems();
        formRec.SelectPatientRecipe.removeAllItems();
        formRec.DoctorSelect.addItems(serviceDoc.ContactsDoctor.values());
        formRec.PatientSelect.addItems(servicePat.ContactsPatient.values());
        formRec.SelectPatientRecipe.addItems(servicePat.ContactsPatient.values());

        subwindow.close();
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {}
}

