package my.springbliss.grocery;

public class Myorder {
    String date,time,order_id,status,total_item,amount,address;

    public Myorder(String date, String time, String order_id, String status, String total_item, String amount, String address) {
        this.date = date;
        this.time = time;
        this.order_id = order_id;
        this.status = status;
        this.total_item = total_item;
        this.amount = amount;
        this.address=address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal_item() {
        return total_item;
    }

    public void setTotal_item(String total_item) {
        this.total_item = total_item;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
