package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Shop extends Remote {

    void addUser(String name, String surname, String identification) throws RemoteException;
    void addUser(User user) throws RemoteException;
    String getUser(int id) throws RemoteException;
    void addProducts() throws RemoteException;
    String getAllProducts() throws RemoteException;
    String searchByName(String name) throws RemoteException;
    String searchByBrand(String brand)throws RemoteException;
    String searchByPriceRange(int from, int to) throws RemoteException;
    String searchByKind(String kind) throws RemoteException;
    String addToCart(int id, String userIdentification) throws RemoteException;
    String showCart(String userIdentification ) throws RemoteException;
    String showUserInformation(String userIdentification) throws RemoteException;
    String buy(String userIdentification) throws RemoteException;
    String getOrders() throws RemoteException;
    String leaveProductsFromCart(String userIdentification) throws RemoteException;
}
