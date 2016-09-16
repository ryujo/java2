package lv.javaguru.java2.servlet;

import lv.javaguru.java2.businesslogic.product.ProductProvider;
import lv.javaguru.java2.businesslogic.product.RateService;
import lv.javaguru.java2.businesslogic.user.UserProvider;
import lv.javaguru.java2.businesslogic.validators.RateValidationService;
import lv.javaguru.java2.domain.User;
import lv.javaguru.java2.servlet.mvc.MVCController;
import lv.javaguru.java2.servlet.mvc.MVCModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Component
public class RateController extends MVCController{

    private static final String CAN_NOT_RATE = "You can rate one time per product";

    @Autowired
    private ProductProvider productProvider;
    @Autowired
    private RateValidationService rateValidationService;
    @Autowired
    private RateService rateService;
    @Autowired
    private UserProvider userProvider;

    @Override
    protected MVCModel executePost(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = userProvider.getUser();
        long productId = Long.parseLong(request.getParameter("productId"));
        productProvider.setProductId(productId);
        request.getSession().setAttribute("productId", productId);

        if(!rateValidationService.canRate(user, productId)){
            map.put("error", CAN_NOT_RATE);
            return new MVCModel(map, "/product.jsp");
        }
        if(request.getParameter("rate") != null){
            int param = Integer.parseInt(request.getParameter("rate"));
            try {
                rateService.rate(param);
            }catch (IllegalArgumentException e){
                return new MVCModel(map, "/product.jsp");
            }
        }
        return redirectTo(ProductController.class);
    }

}
