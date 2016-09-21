package lv.javaguru.java2.servlet;

import lv.javaguru.java2.businesslogic.checkout.CartProvider;
import lv.javaguru.java2.businesslogic.checkout.CartService;
import lv.javaguru.java2.domain.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController  {

    @Autowired
    private CartService cartService;
    @Autowired
    private CartProvider cartProvider;

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String addToCart(@RequestParam ("productId") long id) {
        Cart cart = cartProvider.getCart();
        cartService.addProduct(cart, id);
        return "redirect:index";
    }

    @RequestMapping(value = "/addQuantity", method = RequestMethod.POST)
    public String addQuantity(@RequestParam ("productId") long productId,
                              @RequestParam ("quantity") int quantity) {
        Cart cart = cartProvider.getCart();
        cartService.addProducts(cart, productId, quantity);

        return "redirect:product" + "?productId=" + productId;
    }
}
