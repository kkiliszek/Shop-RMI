package server;

import java.util.ArrayList;

public class Cart {

    private ArrayList<Product> cart = new ArrayList<>();
    private double cost = 0;

    public void addToCart(Product product)
    {
        cart.add(product);
        cost = cost + product.getPrice();
        product.decreaseAmount();
    }

    public ArrayList<Product> getCart() {
        return cart;
    }

    public void leaveProducts()
    {
        for(Product product : cart)
        {
            product.increaseAmount();
        }
        cart.clear();
        cost=0;
    }

    public void removeFromCart()
    {
        cost=0;
        cart.clear();
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String toString()
    {
        String string = "";
        for(Product product : cart)
        {
            string += product.toStringCart();
        }
        return "Zawartosc koszyka: \n" + string + "Suma: " + cost + "zl" + "\n*Nacisnij enter aby wrocic do menu*\n";
    }
}
