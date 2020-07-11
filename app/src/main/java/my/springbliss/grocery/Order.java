package my.springbliss.grocery;

public class Order {
    String imageid;
    String productname;
    String brandname;
    String productprice;
    String sellingPrice;
    String weight;
    int qty;
    int itemId,sellerId;
    String user_name,user_mobile,user_address,status;
    public Order(String imageid, String productname, String brandname, String productprice, String sellingPrice, int qty) {
        this.imageid = imageid;
        this.productname = productname;
        this.brandname = brandname;
        this.productprice = productprice;
        this.sellingPrice = sellingPrice;
        this.qty = qty;
    }

    public Order(String imageid, String productname, String sellingPrice, int qty, int itemId, int sellerId) {
        this.imageid = imageid;
        this.productname = productname;
        this.sellingPrice = sellingPrice;
        this.qty = qty;
        this.itemId = itemId;
        this.sellerId = sellerId;
    }

    public Order(int itemId, int sellerId, String productname, String sellingPrice, int qty) {
        this.itemId = itemId;
        this.sellerId = sellerId;
        this.productname = productname;
        this.sellingPrice = sellingPrice;
        this.qty = qty;
    }

    public Order(int itemId, int sellerId, String productname, String sellingPrice, int qty, String user_name, String user_mobile, String user_address, String imageid, String status, String weight) {
        this.productname = productname;
        this.sellingPrice = sellingPrice;
        this.qty = qty;
        this.itemId = itemId;
        this.sellerId = sellerId;
        this.user_name = user_name;
        this.user_mobile = user_mobile;
        this.user_address = user_address;
        this.imageid = imageid;
        this.status = status;
        this.weight=weight;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getImageid() {
        return imageid;
    }

    public void setImageid(String imageid) {
        this.imageid = imageid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
