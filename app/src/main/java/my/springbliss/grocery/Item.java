package my.springbliss.grocery;

public class Item {
int item_id;
String product_name;
String brand;
String product_price;
String selling_price,image_id;
int qty;

    public Item(String image_id, String product_name, String brand, String product_price, String selling_price, int qty) {
        this.image_id = image_id;
        this.product_name = product_name;
        this.brand = brand;
        this.product_price = product_price;
        this.selling_price = selling_price;
        this.qty = qty;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public Item(String image_id, int item_id, String product_name, String brand, String product_price, String selling_price, int qty) {
        this.image_id = image_id;
        this.item_id = item_id;
        this.product_name = product_name;
        this.brand = brand;
        this.product_price = product_price;
        this.selling_price = selling_price;
        this.qty = qty;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(String selling_price) {
        this.selling_price = selling_price;
    }


}
