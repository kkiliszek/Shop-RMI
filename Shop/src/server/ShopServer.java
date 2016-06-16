package server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ShopServer extends UnicastRemoteObject implements Shop {

    private ShopServer() throws RemoteException {}

    private ArrayList<User> userList = new ArrayList<User>();
    private ArrayList<Product> productList = new ArrayList<Product>();
    private ArrayList<Order> orderList = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        LocateRegistry.createRegistry(1099);
        System.setProperty("java.security.policy","java.policy");
        System.setSecurityManager(new SecurityManager());

        ShopServer shopServer = new ShopServer();

        Naming.rebind("rmi://127.0.0.1/Meeting", shopServer);

        System.out.println("Serwer wystartowal !!!");
    }

    public void addUser(String name, String surname, String identification) throws RemoteException {

        userList.add(new User(name, surname, identification));
    }

    public void addUser(User user) throws RemoteException {

        userList.add(user);
    }

    public String getUser(int id) throws RemoteException{

        return userList.get(id).toString();
    }

    public String addToCart(int id, String userIdentification) throws RemoteException
    {
        Cart cart = getUserByIdentyfication(userIdentification).getCart();
        for(Product product : productList)
        {
            if(product.getId() == id && product.getAmount() > 0)
            {
                cart.addToCart(product);
                return "Dodano do koszyka: \n" + product.toStringCart() + "\n*Nacisnij enter aby wrocic do menu*";
            }
        }
        return "Nie znaleziono takiego produktu\n*Nacisnij enter aby wrocic do menu*\n";
    }

    public String buy(String userIdentification) throws RemoteException
    {
        Cart cart = getUserByIdentyfication(userIdentification).getCart();
        double cost = cart.getCost();

        if(cost > getUserByIdentyfication(userIdentification).getMoney())
        {
            return "Nie masz wystarczajacych srodkow na koncie." + "\nDo zaplaty: " + cost + "zl" + "\n Posiadasz " + getUserByIdentyfication(userIdentification).getMoney() + "zl\n*Nacisnij enter aby wrocic do menu*\n";
        }
        else
        {
            orderList.add(new Order(cart, getUserByIdentyfication(userIdentification)));
            cart.removeFromCart();
            getUserByIdentyfication(userIdentification).pay(cost);
            return "Zaplaciles: " + cost + "zl." + "  Aktualny stan konta: " + getUserByIdentyfication(userIdentification).getMoney() + "zl\n*Nacisnij enter aby wrocic do menu*\n";
        }
    }

    public String leaveProductsFromCart(String userIdentification) throws RemoteException
    {
        Cart cart = getUserByIdentyfication(userIdentification).getCart();
        cart.leaveProducts();
        return "Oprozniono koszyk\n*Nacisnij enter aby wrocic do menu*\n";
    }

    public String getOrders() throws RemoteException
    {
        String string = "";
        for(Order order : orderList)
        {
            string += order.toString();
        }
        if(string=="")
        {
            return "Brak zamowien\n*Nacisnij enter aby wrocic do menu*\n";
        }
        return "ZAMOWIENIA: \n" + string + "\n*Nacisnij enter aby wrocic do menu*";
    }

    private User getUserByIdentyfication(String identification)
    {
        for(User user : userList)
        {
            if(user.getIdentification().equals(identification))
            {
                return user;
            }
        }
        return null;
    }

    public String showCart(String userIdentification ) throws RemoteException
    {
        return getUserByIdentyfication(userIdentification).getCart().toString();
    }

    public String showUserInformation(String userIdentification) throws RemoteException
    {
        return getUserByIdentyfication(userIdentification).getInformation();
    }

    public void addProducts() throws RemoteException{
        productList.add(new Product(1, "Livin", "Lozko Skagen ", "lozka", 3, 1099.99));
        productList.add(new Product(2, "Livin", "Lozko Prowansja Santo ", "lozka", 1, 1599.99));
        productList.add(new Product(3, "Homelike", "Lozko Trapani", "lozka", 4, 1199.99));
        productList.add(new Product(4, "LaForma", "Sofa Fusion", "sofy", 2, 2199.99));
        productList.add(new Product(5, "Homelike", "Sofa Claudia", "sofy", 5, 1399.99));
        productList.add(new Product(6, "LaForma", "Sofa Perugia", "sofy", 1, 3299.99));
        productList.add(new Product(7, "Homelike", "Lustro Lua", "lustra", 4, 99.99));
        productList.add(new Product(8, "umbra", "Lustro Dima", "lustra", 2, 169.99));
        productList.add(new Product(9, "D2", "Krzeslo Terre", "krzesla", 8, 289.99));
        productList.add(new Product(10, "umbra", "Krzeslo Ally", "krzesla", 9, 350.99));
        productList.add(new Product(11, "Homelike", "Krzeslo Adam", "krzesla", 4, 569.99));
        productList.add(new Product(12, "Massivum", "Stol Lazos", "stoly", 2, 1199.99));
    }

    public String getAllProducts() throws RemoteException
    {
        return "ID produktu: " + productList.get(0).getId() + "          |    " + "ID produktu: " + productList.get(1).getId() + "                 |    " + "ID produktu: " + productList.get(2).getId() + "          |    " + "ID produktu: " + productList.get(3).getId() + "          |    " + "ID produktu: " + productList.get(4).getId() + "         |    " + "ID produktu: " + productList.get(5).getId() + "\n" +
                "Nazwa: " + productList.get(0).getName() + "    |    " + "Nazwa: " + productList.get(1).getName() + "  |    " + "Nazwa: " + productList.get(2).getName() + "    |    " + "Nazwa: " + productList.get(3).getName() + "      |    " + "Nazwa: " + productList.get(4).getName() + "    |    " + "Nazwa: " + productList.get(5).getName() + "\n" +
                "Producent: " + productList.get(0).getBrand() + "        |    " + "Producent: " + productList.get(1).getBrand() + "               |    " + "Producent: " + productList.get(2).getBrand() + "     |    " + "Producent: " + productList.get(3).getBrand() + "      |    " + "Producent: " + productList.get(4).getBrand() + "    |    " + "Producent: " + productList.get(5).getBrand() + "\n" +
                "Cena: " + productList.get(0).getPrice() + "           |    " + "Cena: " + productList.get(1).getPrice() + "                  |    " + "Cena: " + productList.get(2).getPrice() + "           |    " + "Cena: " + productList.get(3).getPrice() + "           |    " + "Cena: " + productList.get(4).getPrice() + "          |    " + "Cena: " + productList.get(5).getPrice() + "\n" +
                "Ilosc w magazynie: " + productList.get(0).getAmount() + "    |    " + "Ilosc w magazynie: " + productList.get(1).getAmount() + "           |    " + "Ilosc w magazynie: " + productList.get(2).getAmount() + "    |    " + "Ilosc w magazynie: " + productList.get(3).getAmount() + "    |    " + "Ilosc w magazynie: " + productList.get(4).getAmount() + "   |    " + "Ilosc w magazynie: " + productList.get(5).getAmount() + "\n" +
                "_ _ _ _ _ _ _ _ _ _ _ _ |_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _|_ _ _ _ _ _ _ _ _ _ _ _ _ _ |_ _ _ _ _ _ _ _ _ _ _ _ _ _ |_ _ _ _ _ _ _ _ _ _ _ _ _ _|_ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
                "ID produktu: " + productList.get(6).getId() + "          |    " + "ID produktu: " + productList.get(7).getId() + "                 |    " + "ID produktu: " + productList.get(8).getId() + "          |    " + "ID produktu: " + productList.get(9).getId() + "         |    " + "ID produktu: " + productList.get(10).getId() + "        |    " + "ID produktu: " + productList.get(11).getId() + "\n" +
                "Nazwa: " + productList.get(6).getName() + "       |    " + "Nazwa: " + productList.get(7).getName() + "             |    " + "Nazwa: " + productList.get(8).getName() + "    |    " + "Nazwa: " + productList.get(9).getName() + "     |    " + "Nazwa: " + productList.get(10).getName() + "    |    " + "Nazwa: " + productList.get(11).getName() + "\n" +
                "Producent: " + productList.get(6).getBrand() + "     |    " + "Producent: " + productList.get(7).getBrand() + "               |    " + "Producent: " + productList.get(8).getBrand() + "           |    " + "Producent: " + productList.get(9).getBrand() + "        |    " + "Producent: " + productList.get(10).getBrand() + "    |    " + "Producent: " + productList.get(11).getBrand() + "\n" +
                "Cena: " + productList.get(6).getPrice() + "             |    " + "Cena: " + productList.get(7).getPrice() + "                   |    " + "Cena: " + productList.get(8).getPrice() + "            |    " + "Cena: " + productList.get(9).getPrice() + "            |    " + "Cena: " + productList.get(10).getPrice() + "           |    " + "Cena: " + productList.get(11).getPrice() + "\n" +
                "Ilosc w magazynie: " + productList.get(6).getAmount() + "    |    " + "Ilosc w magazynie: " + productList.get(7).getAmount() + "           |    " + "Ilosc w magazynie: " + productList.get(8).getAmount() + "    |    " + "Ilosc w magazynie: " + productList.get(9).getAmount() + "    |    " + "Ilosc w magazynie: " + productList.get(10).getAmount() + "   |    " + "Ilosc w magazynie: " + productList.get(11).getAmount() + "\n" +
                "_ _ _ _ _ _ _ _ _ _ _ _ |_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _|_ _ _ _ _ _ _ _ _ _ _ _ _ _ |_ _ _ _ _ _ _ _ _ _ _ _ _ _ |_ _ _ _ _ _ _ _ _ _ _ _ _ _|_ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
                "\n*Nacisnij enter aby wrocic do menu*\n";
    }

    public String searchByName(String name) throws RemoteException
    {
        for(Product product : productList)
        {
            if(product.getName().equals(name))
            {
                return "Znalezione produkty: \n" + product.toString() + "\n*Nacisnij enter aby wrocic do menu*";
            }
        }
        return "Nie znaleziono takiego produktu\n*Nacisnij enter aby wrocic do menu*\n";
    }

    public String searchByBrand(String brand) throws RemoteException
    {
        String string = "";
        for(Product product : productList)
        {
            if(product.getBrand().equals(brand))
            {
                string += product.toString();
            }
        }
        if(string=="")
        {
            return "Nie znaleziono takiego produktu\n*Nacisnij enter aby wrocic do menu*\n";
        }
        return "Znalezione produkty: \n" + string + "\n*Nacisnij enter aby wrocic do menu*";
    }

    public String searchByPriceRange(int from, int to) throws RemoteException
    {
        String string = "";
        for(Product product : productList)
        {
            if(from < product.getPrice() && to > product.getPrice())
            {
                string += product.toString();
            }
        }
        if(string=="")
        {
            return "Nie znaleziono takiego produktu\n*Nacisnij enter aby wrocic do menu*\n";

        }
        return "Znalezione produkty: \n" + string + "\n*Nacisnij enter aby wrocic do menu*";
    }

    public String searchByKind(String kind) throws RemoteException {
        String string = "";
        for (Product product : productList) {
            if (product.getKind().equals(kind)) {
                string += product.toString();
            }
        }
        if (string == "") {
            return "Nie znaleziono takiego produktu\n*Nacisnij enter aby wrocic do menu*\n";
        }
        return "Znalezione produkty: \n" + string + "\n*Nacisnij enter aby wrocic do menu*";
    }
}
