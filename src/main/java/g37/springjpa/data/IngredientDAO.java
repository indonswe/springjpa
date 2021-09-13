package g37.springjpa.data;


import g37.springjpa.model.Ingredient;

import java.util.List;

public interface IngredientDAO {
    Ingredient create(Ingredient ingredient);
    Ingredient findByName(String ingredient);
    List<Ingredient> findByNameContain(String ingredient);
    List<Ingredient> findAll();
    Ingredient findById(Integer ingredient);
    Ingredient update(Ingredient ingredient);
    void delete(Integer ingredientId);
}
