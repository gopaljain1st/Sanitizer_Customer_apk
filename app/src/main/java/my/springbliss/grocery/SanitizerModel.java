package my.springbliss.grocery;

public class SanitizerModel {
    String itemImage;
    String itemName,itemQuantity,itemLeftPrice,itemPrice,item_desc,item_id,seller_id;


    public SanitizerModel(String itemImage, String itemName, String itemQuantity, String itemLeftPrice, String itemPrice, String item_desc, String item_id, String seller_id) {
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemLeftPrice = itemLeftPrice;
        this.itemPrice = itemPrice;
        this.item_desc = item_desc;
        this.item_id = item_id;
        this.seller_id = seller_id;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public SanitizerModel(String itemImage, String itemName, String itemQuantity, String itemLeftPrice, String itemPrice, String item_desc) {
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemLeftPrice = itemLeftPrice;
        this.itemPrice = itemPrice;
        this.item_desc = item_desc;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public SanitizerModel(String itemImage, String itemName, String itemQuantity, String itemLeftPrice, String itemPrice, String item_desc, String item_id) {
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemLeftPrice = itemLeftPrice;
        this.itemPrice = itemPrice;
        this.item_desc = item_desc;
        this.item_id = item_id;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemLeftPrice() {
        return itemLeftPrice;
    }

    public void setItemLeftPrice(String itemLeftPrice) {
        this.itemLeftPrice = itemLeftPrice;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }
}
