package HospitalVaadin;

import HospitalUI.Recipe;
import Service.RecService;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecipeService {

    RecService recService = new RecService();   //DAO service recipe

    private static RecipeService instanceRec;
    private static final Logger LOGGER = Logger.getLogger(RecipeService.class.getName());
    public final HashMap<Long, Recipe> ContactsRecipe = new HashMap<>();

    public RecipeService() {}

    public static RecipeService getInstanceRec() {
        if (instanceRec == null) {
            instanceRec = new RecipeService();
            instanceRec.RecipeData();
        }
        return instanceRec;
    }

    public synchronized List<Recipe> findAll() {
        return findAll(null);
    }

    public synchronized List<Recipe> findAll(String stringFilter) {
        ArrayList<Recipe> arrayList = new ArrayList<>();
        for (Recipe contact : ContactsRecipe.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(RecipeService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<Recipe>() {

            @Override
            public int compare(Recipe o1, Recipe o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }

    public synchronized List<Recipe> findAll(String stringFilter, int start, int maxresults) {
        ArrayList<Recipe> arrayList = new ArrayList<>();
        for (Recipe contact : ContactsRecipe.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(RecipeService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<Recipe>() {

            @Override
            public int compare(Recipe o1, Recipe o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        int end = start + maxresults;
        if (end > arrayList.size()) {
            end = arrayList.size();
        }
        return arrayList.subList(start, end);
    }

    public synchronized List<Recipe> findDescription(String stringFilter) {
        ArrayList<Recipe> arrayList = new ArrayList<>();
        for (Recipe contact : ContactsRecipe.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.getDescription().toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(RecipeService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<Recipe>() {

            @Override
            public int compare(Recipe o1, Recipe o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }

    public synchronized List<Recipe> findPriorityPatient(String stringFilter) {
        ArrayList<Recipe> arrayList = new ArrayList<>();
        for (Recipe contact : ContactsRecipe.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || (contact.getPatient().toString()+contact.getPriority().toString()).toLowerCase().contains(stringFilter.toLowerCase())
                        || (contact.getPriority().toString()+contact.getPatient().toString()).toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(RecipeService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<Recipe>() {

            @Override
            public int compare(Recipe o1, Recipe o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }


    public synchronized long count() {
        return ContactsRecipe.size();
    }

    public synchronized void delete(Recipe recipe) {

        try {
            recService.remove(recipe);
        } catch (SQLException e) {
            e.printStackTrace();}

        try {
            List<Recipe> recipeList = recService.getAll();  //Вывод всех сделанных запросов
            for (Recipe r:recipeList){System.out.println(r);}
        }
        catch (SQLException e){e.printStackTrace();}

        ContactsRecipe.remove(recipe.getId());
    }

    public synchronized void save(Recipe recipe) {
        if (recipe == null) {
            LOGGER.log(Level.SEVERE,
                    "Recipe is null.");
            return;
        }
        if (recipe.getId() == null)
        {
            Long i=Long.valueOf(count());
            recipe.setId(i++);
        }
        try {
            recipe = (Recipe) recipe.clone();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        // Взаимодействие с БД (hsqlhosp) в таблице RECIPES

        if (ContactsRecipe.get(recipe.getId()) == null)
        {
            try {
                recService.add(recipe);                   //Добавление нового врача в таблицу RECIPES из БД (hsqlhosp)
            }
            catch (SQLException e)
            {e.printStackTrace();}
        }

        try {
            recService.update(recipe);                     //Обновление RECIPES в БД (hsqlhosp)
        }
        catch (SQLException e){e.printStackTrace();}

        try {
            List<Recipe> recipeList = recService.getAll();  //Вывод всех сделанных запросов RECIPES из БД (hsqlhosp)
            for (Recipe r:recipeList){System.out.println(r);}
        }
        catch (SQLException e){e.printStackTrace();}

        ContactsRecipe.put(recipe.getId(), recipe);

    }

    public void RecipeData() {

        try {
            List<Recipe> recList = recService.getAll();
            for (Recipe recipe:recList)
            {
                ContactsRecipe.put(recipe.getId(), recipe);
            }

        }
        catch (SQLException e)
        {e.printStackTrace();}

    }
}