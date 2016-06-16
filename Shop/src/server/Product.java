package server;

public class Product {

    private int id;
    private int amount;
    private double price;
    private String name;
    private String brand;
    private String kind;

    public Product(int id, String brand, String name, String kind, int amount, double price){
        this.id=id;
        this.brand=brand;
        this.name=name;
        this.kind=kind;
        this.amount=amount;
        this.price=price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void decreaseAmount()
    {
        amount = amount-1;
    }

    public void increaseAmount()
    {
        amount = amount+1;
    }

    public String toString()
    {
        return "\nID produktu: " + id + "\nNazwa: " + name + "\nProducent: " + brand + "\nCena: " + price + "zl\nIlosc w magazynie: " + amount + "\n----------------------\n";
    }
    public String toStringCart()
    {
        return "\nID produktu: " + id + "\nNazwa: " + name + "\nProducent: " + brand + "\nCena: " + price + "zl" + "\n----------------------\n";
    }

}
