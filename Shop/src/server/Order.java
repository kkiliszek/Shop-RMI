package server;

import java.util.ArrayList;

public class Order {

    private String name;
    private String surname;
    private double cost;
    private ArrayList<Product> products = new ArrayList<>();


    public Order(Cart cart, User user)
    {
        this.name=user.getName();
        this.surname=user.getSurname();
        this.cost=cart.getCost();
        for (Product product : cart.getCart())
        {
            products.add(product);
        }
    }

    private String productsToString()
    {
        String string = "";
        for(Product product : products) {
            string = string + product.toStringCart();
        }
        return string;
    }

    public String toString()
    {
        return "\n-->" + name + " " + surname + "<--\n" + productsToString() + ">>LACZNA KWOTA: " + cost + "zl\n\n_________________________________________________________________________________________________\n";
    }

}
