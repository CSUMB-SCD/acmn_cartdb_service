package freedomphones.cartsvc.cart;

import java.util.HashMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "carts")
public class Cart{
    @Id
    private String id;
    private String username;
    private HashMap<String, Item> items;

    public Cart(String username, HashMap<String, Item> items) {
        this.username = username;
        this.items = items;
    }
    public String getId(){
        return this.id;
    }
    public String getUsername(){
        return this.username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public HashMap<String, Item> getItems(){
        return this.items;
    }
    public void setItems(HashMap<String, Item> items){
        this.items = items;
    }
}