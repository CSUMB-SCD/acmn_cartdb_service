package freedomphones.cartsvc;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import freedomphones.cartsvc.cart.Cart;
import freedomphones.cartsvc.cart.Item;

@Component
public class CartDbSeeder implements CommandLineRunner{
    @Autowired
    ICartRepository cartRepository;

    @Override
    public void run(String... args) throws Exception {
        Cart user = new Cart("user", new HashMap<String, Item>());
        cartRepository.deleteAll();
        List<Cart> carts = Arrays.asList(user);
        cartRepository.saveAll(carts);
    }
}