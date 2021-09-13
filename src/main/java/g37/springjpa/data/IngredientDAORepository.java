package g37.springjpa.data;


import g37.springjpa.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class IngredientDAORepository implements IngredientDAO {

    //private final EntityManager em;

    private EntityManager em;

    @Autowired
    public IngredientDAORepository(EntityManager em){
        this.em = em;
   }

    /*private EntityManager entityManager;
    @Override
    @Transactional
    public Student persist(Student student) {
        entityManager.persist(student);
        return student;
    }*/

    @Override
    @Transactional
    public Ingredient create(Ingredient ingredient) {
        em.persist(ingredient);
        return ingredient;
    }

    public List<Ingredient> findAll(){
        List<Ingredient> answer = new ArrayList();
        answer = em
                .createQuery("SELECT s FROM Ingredient s")
                .getResultList();
        return answer;
    }

    @Override
    @Transactional(readOnly = true)
    public Ingredient findByName(String ingredientName) {

        //return em.find(Ingredient.class, ingredientName);


        //List<Ingredient> answer = new ArrayList();
        Optional<Ingredient> answer = null;
        Ingredient nameIngredient = null;

        if(ingredientName == null) {
            throw new IllegalArgumentException("String ingredientName = " + ingredientName);
        }

                answer = em
                .createQuery("SELECT s FROM Ingredient s WHERE UPPER(s.ingredientName) = UPPER(?1)", Ingredient.class)
                .setParameter(1,ingredientName)
                .getResultStream().findFirst();

        if (answer.isPresent()) {
            nameIngredient = answer.get();
        }

        // .getSingleResult();//

                //.getResultList(); getresuktstream. findfirst optional

        return nameIngredient;

        /*TypedQuery<Person> createNamedQuery = em.createNamedQuery("findQuery", Person.class);
        createNamedQuery.setParameter("id", id);
        try {
            Person singleResult = createNamedQuery.getSingleResult();
            creationMessage = "User: " + singleResult.getName()+" added to database with success";
        }
        catch (Exception e) {
            System.out.println("Either less than one row, more than one row, or some other error");
        }*/

         /*for (Ingredient i :answer) {
             name = i;
         }*/

         //return name;

        /*return em
                .createQuery("SELECT s FROM Student s WHERE UPPER(s.lastName) = UPPER(?1)", Student.class)
                .setParameter(1, lastName)
                .getResultList();*/
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ingredient> findByNameContain(String ingredientName) {

        //return em.find(Ingredient.class, ingredientName);


        //List<Ingredient> answer = new ArrayList();
        List<Ingredient> answer = null;
        Ingredient nameIngredient = null;

        if (ingredientName == null) {
            throw new IllegalArgumentException("String ingredientName = " + ingredientName);
        }

        /*answer = em
                .createQuery("SELECT s FROM Ingredient s WHERE UPPER(s.ingredientName) = LIKE UPPER (%?1%)", Ingredient.class)
                .setParameter(1, ingredientName)
                .getResultList();*/

        answer =  em.createQuery(
                "SELECT s FROM Ingredient s WHERE " +
                        //
                        //"UPPER(s.firstName) LIKE UPPER(CONCAT('%', :name , '%'))" +
                        //"OR " +
                        "UPPER(s.ingredientName) LIKE UPPER(CONCAT('%', :name, '%'))", Ingredient.class)
                .setParameter("name", ingredientName)
                .getResultList();




        // .getSingleResult();//

        //.getResultList(); getresuktstream. findfirst optional

        return answer;

    }

    @Override
    @Transactional(readOnly = true)
    public Ingredient findById(Integer ingredientId) {
        return em.find(Ingredient.class,ingredientId);
    }

    @Override
    @Transactional
    public Ingredient update(Ingredient ingredient) {
        return em.merge(ingredient);
    }

    @Override
    public void delete(Integer ingredientId) {
       Ingredient toRemove = findById(ingredientId);
        if(toRemove != null){
            em.remove(toRemove);
        }else {
            throw new IllegalArgumentException("Ingredient could not be found");
        }
    }
}
