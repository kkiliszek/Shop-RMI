package client;

import server.Cart;
import server.Shop;
import server.User;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ShopClient extends JFrame
{

    public static void main(String[] args) {
        try {
            System.setProperty("java.security.policy", "java.policy");
            System.setSecurityManager(new SecurityManager());
            Remote remote = Naming.lookup("rmi://127.0.0.1/Meeting");

            Shop shop;
            String userIdentification;

            if (remote instanceof Shop) {
                shop = (Shop) remote;
                shop.addProducts();

                while(true) {
                    System.out.print("REJESTRACJA \nPodaj imie:\n");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                    String name = bufferedReader.readLine();
                    System.out.print("Podaj nazwisko:\n");
                    BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(System.in));
                    String surname = bufferedReader2.readLine();
                    if (name != null) {
                        userIdentification = name+surname;
                        shop.addUser(name, surname, userIdentification);
                        System.out.println(name + ", rejestracja przebiegla pomyslnie!\n");
                        //System.out.println(shop.getAllProducts());
                        break;
                    }
                }

                while(true)
                {
                    System.out.println("                   <MENU>");
                    System.out.println("Wybierz (1) aby wyswietlic wszystkie produkty");
                    System.out.println("Wybierz (2) aby wyszukac produkty po nazwie");
                    System.out.println("Wybierz (3) aby wyszukac produkty po producencie");
                    System.out.println("Wybierz (4) aby wyszukac produkty po cenie");
                    System.out.println("Wybierz (5) aby wyswietlic produkty z danej kategorii");
                    System.out.println("Wybierz (6) aby dodac produkt do koszyka");
                    System.out.println("Wybierz (7) aby wyswietlic koszyk");
                    System.out.println("Wybierz (8) aby zaplacic za produkty");
                    System.out.println("Wybierz (9) aby wyswietlic informacje o koncie");
                    System.out.println("Wybierz (10) aby usunac produkty z koszyka");

                    Scanner scanner = new Scanner(System.in);
                    int number = scanner.nextInt();

                    switch (number) {
                        case 1: {
                            System.out.println(shop.getAllProducts());
                            break;
                        }
                        case 2: {
                            System.out.println("Podaj nazwe produktu: ");
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                            String productName = bufferedReader.readLine();
                            System.out.println(shop.searchByName(productName));
                            break;
                        }
                        case 3: {
                            System.out.println("Podaj producenta produktu: ");
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                            String brand = bufferedReader.readLine();
                            System.out.println(shop.searchByBrand(brand));
                            break;
                        }
                        case 4: {
                            System.out.println("Wybierz zakres cenowy\nod:  ");
                            Scanner scannerFrom = new Scanner(System.in);
                            int from = scannerFrom.nextInt();
                            System.out.println("do:  ");
                            Scanner scannerTo = new Scanner(System.in);
                            int to = scannerTo.nextInt();
                            System.out.println(shop.searchByPriceRange(from, to));
                            break;
                        }
                        case 5: {
                            System.out.println("Jakimi produktami jestes zainteresowany?: \n(krzesla/stoly/lustra/sofy) ");
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                            String kind = bufferedReader.readLine();
                            System.out.println(shop.searchByKind(kind));
                            break;
                        }
                        case 6: {
                            System.out.println("Podaj ID produktu ktory chcesz dodac do koszyka: ");
                            Scanner scannerId = new Scanner(System.in);
                            int id = scannerId.nextInt();
                            System.out.println(shop.addToCart(id, userIdentification));
                            break;
                        }
                        case 7: {
                            System.out.println(shop.showCart(userIdentification));
                            break;
                        }
                        case 8: {
                            System.out.println(shop.buy(userIdentification));
                            break;
                        }
                        case 9: {
                            System.out.println(shop.showUserInformation(userIdentification));
                            break;
                        }
                        case 10: {
                            System.out.println(shop.leaveProductsFromCart(userIdentification));
                            break;
                        }
                        case 111: {
                            if(userIdentification.equals("adminadmin"))
                            {
                                System.out.println(shop.getOrders());
                            }
                            else
                            {
                                System.out.println("Odmowa dostepu");
                            }
                            break;
                        }

                        default: {
                            System.out.println("*Blad* - nacisnij enter aby wyswietlic menu");
                            break;
                        }
                    }
                    System.in.read();
                }
            }

        } catch (java.rmi.NotBoundException nbe) {
            nbe.printStackTrace();
        } catch (RemoteException re) {
            re.printStackTrace();
        } catch (java.net.MalformedURLException mue) {
            mue.printStackTrace();
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
