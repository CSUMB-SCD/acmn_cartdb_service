package freedomphones.cartsvc;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import freedomphones.cartsvc.cart.Cart;
import freedomphones.cartsvc.cart.Item;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class CartController{
    @Autowired
    ICartRepository cartRepository;

    @PostMapping(value = "/addItem/{username}")
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
    public HashMap<String, HashMap<String, String>> getCartByUsername(@PathVariable String username){
        Cart cart = cartRepository.findByUsername(username);
        HashMap<String, HashMap<String, String>> json = new HashMap<String, HashMap<String, String>>();
        HashMap<String, Item> items = cart.getItems();
        for(Map.Entry<String, Item> entry: items.entrySet()){
            final String uri = "https://freedomphones-zuul-svc.herokuapp.com/phone-service/findById/{id}";
            RestTemplate restTemplate = new RestTemplate();
            Item item = entry.getValue();
            HashMap<String, String> params = new HashMap<String, String>();
            HashMap<String, String> item_response = new HashMap<String, String>();
            params.put("id", item.getProduct());
            item_response.put("product", restTemplate.getForObject(uri, String.class, params));
            item_response.put("qty", item.getQuantity().toString());
            json.put(item.getProduct(), item_response);
        }
        return json;
    }
}