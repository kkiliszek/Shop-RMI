package server;

import java.util.Random;

public class User {

    private static int MONEY = 5000;
    private String name;
    private String surname;
    private String identification;
    private double money;
    private Cart cart;

    public User(String name, String surname, String identification)
    {
        this.name = name;
        this.surname = surname;
        this.money = generateMoney();
        this.identification = identification;
        this.cart = new Cart();
    }

    private double generateMoney()
    {
        return new Random().nextInt(MONEY) + 1500;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getIdentification() {
        return identification;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getInformation()
    {
        return "Imie: " + name + "\nNazwisko: " + surname + "\nStan konta: " + money + "zl" + "\n*Nacisnij enter aby wrocic do menu*\n";
    }

    public void pay(double price)
    {
        this.money=money-price;
    }

    public String orderInformation()
    {
        return "Imie: " + name + "\nNazwisko: " + surname;
    }

    @Override
    public String toString()
    {
        return name + "!\n" + "Posiadasz " + money + "zl";
    }
}
