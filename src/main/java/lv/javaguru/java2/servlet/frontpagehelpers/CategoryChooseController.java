package lv.javaguru.java2.servlet.frontpagehelpers;

import lv.javaguru.java2.database.CategoryDAO;
import lv.javaguru.java2.domain.Category;
import lv.javaguru.java2.servlet.MVCController;
import lv.javaguru.java2.servlet.MVCModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class CategoryChooseController extends MVCController {

    @Autowired
    @Qualifier("ORM_CategoryDAO")
    private CategoryDAO categoryDAO;

    @Override
    public MVCModel executeGet(HttpServletRequest request) {
        long categoryId = Long.valueOf(request.getParameter("id"));
        Category category = categoryDAO.getById(categoryId);
        if (category != null) {
            request.getSession().setAttribute("currentCategory", category);
        }
        return new MVCModel("/index");
    }

    @Override
    public MVCModel executePost(HttpServletRequest request) {
        return new MVCModel("/index");
    }
}