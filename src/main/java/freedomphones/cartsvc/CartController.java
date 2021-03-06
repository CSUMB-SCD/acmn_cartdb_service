package freedomphones.cartsvc;


import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import freedomphones.cartsvc.cart.Cart;
import freedomphones.cartsvc.cart.Item;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class CartController{
    @Autowired
    ICartRepository cartRepository;

    @PostMapping(value = "/addItem/{username}", produces="application/json")
    @ResponseBody
    public String addItem(@RequestBody Item item, @PathVariable String username) {
        Cart cart = cartRepository.findByUsername(username);

        HashMap<String, Item> items = cart.getItems();
        String product = item.getProduct();
        if(items.containsKey(product)){
            Item updated_item = items.get(product);
            updated_item.setQuantity(updated_item.getQuantity() + item.getQuantity());
            items.put(product, updated_item);
        }
        else{
            items.put(product, item);
        }
        cart.setItems(items);

        cartRepository.save(cart);
        return new String("Success");
    }
    @GetMapping(value = "/deleteItem/{username}/{prod_id}", produces = "application/json")
    @ResponseBody
    public String deleteItem(@PathVariable String username, @PathVariable String prod_id) {
        Cart cart = cartRepository.findByUsername(username);
        HashMap<String, Item> items = cart.getItems();
        items.remove(prod_id);
        cart.setItems(items);
        cartRepository.save(cart);
        return new String("Success");
    }
    @GetMapping(value="getCart/{username}", produces="application/json")
    public Cart getCartByUsername(@PathVariable String username){
        Cart cart = cartRepository.findByUsername(username);
        return cart;
    }
    @GetMapping(value="/clearCart/{username}", produces="application/json")
    public String clearCart(@PathVariable String username){
        Cart cart = cartRepository.findByUsername(username);
        HashMap<String, Item> items = new HashMap<String, Item>();
        cart.setItems(items);
        cartRepository.save(cart);
        return "Success";
    }
}