package app.dao;

import app.dto.CategoryDTO;
import app.model.Category;
import jakarta.persistence.EntityManager;


public class CategoryDAO {

    private EntityManager entityManager;

    private CategoryDTO categoryDTO;

    public CategoryDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public Category findByName(String categoryName) {
        try {
            return entityManager.createQuery("SELECT c FROM Category c WHERE c.categoryName = :categoryName", Category.class)
                    .setParameter("categoryName", categoryName)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Category save(Category category) {
        category = new Category();
        category.setCategoryName(categoryDTO.getCategoryName());
        entityManager.persist(category);
        return category;
    }
}
