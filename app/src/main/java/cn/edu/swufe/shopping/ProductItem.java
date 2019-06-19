package cn.edu.swufe.shopping;

public class ProductItem {
    private int number;
    private String name;
    private String price;
    private String intro;
    private String photo;
    private String type;

    public ProductItem(){
        this.intro = "";
        this.name = "";
        this.number = 0;
        this.photo = "";
        this.type = "";
        this.price = "";
    }

    public ProductItem(String name, int number, String photo, String type, String price, String intro){
        this.intro = intro;
        this.name = name;
        this.number = number;
        this.photo = photo;
        this.type = type;
        this.price = price;
    }
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
