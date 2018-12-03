package freedomphones.cartsvc.cart;

public class Item{
    private String prod_id;
    private Integer qty;

    public Item(String prod_id, Integer qty){
        this.prod_id = prod_id;
        this.qty = qty;
    }
    public String getProduct(){
        return this.prod_id;
    }
    public void setProduct(String prod_id){
        this.prod_id = prod_id;
    }
    public Integer getQuantity(){
        return this.qty;
    }
    public void setQuantity(Integer qty){
        this.qty = qty;
    }
}