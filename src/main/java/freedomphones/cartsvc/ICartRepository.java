package freedomphones.cartsvc;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import freedomphones.cartsvc.cart.Cart;

@Repository
public interface ICartRepository extends MongoRepository<Cart, String>{
    @Query(value = "{'username.username':?0}")
    Cart findByUsername(String username);
}