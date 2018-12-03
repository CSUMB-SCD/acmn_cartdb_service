package freedomphones.cartsvc;


import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import freedomphones.cartsvc.cart.Cart;
import freedomphones.cartsvc.cart.Item;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class CartController{
    @Autowired
    ICartRepository cartRepository;

    @RequestMapping(value = "/addItem/{username}", method=RequestMethod.POST)
    @ResponseBody
    public String addItem(@RequestBody Item item, @PathVariable String username) {
        Cart cart = cartRepository.findByUsername(username);

        HashMap<String, Item> items = cart.getItems();
        items.put(item.getProduct(), item);
        cart.setItems(items);

        cartRepository.save(cart);
        return new String("Success");
    }
    @GetMapping("/deleteItem/{username}/{prod_id}")
    @ResponseBody
    public String deleteItem(@PathVariable String username, @PathVariable String prod_id) {
        Cart cart = cartRepository.findByUsername(username);
        HashMap<String, Item> items = cart.getItems();
        items.remove(prod_id);
        cart.setItems(items);
        cartRepository.save(cart);
        return new String("Success");
    }
    @GetMapping("getCart/{username}")
    public Cart getCartByUsername(@PathVariable String username){
        return cartRepository.findByUsername(username);
    }
}