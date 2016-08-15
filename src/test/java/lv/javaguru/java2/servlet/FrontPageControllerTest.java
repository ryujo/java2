package lv.javaguru.java2.servlet;

import lv.javaguru.java2.IntegrationTest;
import lv.javaguru.java2.database.jdbc.CategoryDAOImpl;
import lv.javaguru.java2.domain.Category;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


public class FrontPageControllerTest extends IntegrationTest {

    private CategoryDAOImpl categoryDAO = new CategoryDAOImpl();
    private FrontPageController frontPageController = new FrontPageController(categoryDAO);
    Map<String, Object> mvcModelData = new HashMap<>();

    @Before
    public void before() {
        createCategories();
    }

    @Test
    @Ignore
    public void doReturnListOfCategories() {
    }

    @Test
    @Ignore
    public void doNotReturnProducts() {
    }


    private void createCategories() {
        Category category;
        category = new Category();
        category.setName("test1");
        categoryDAO.create(category);

        category = new Category();
        category.setName("test2");
        categoryDAO.create(category);
    }
}