package views;

import model.Product;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainLauncher {
    private static ProductView productView = new ProductView();
    private static Scanner scanner = new Scanner(System.in);

    public static void lauch() {
        AdminView adminView = new AdminView();
        adminView.adminLogin();
        menuOption();
    }

    public static void menuOption() {
        do {
            mainmenu();
            try {

                System.out.println("\n Vui long nhap chuc nang!!");
                System.out.print(" ═╬══► :\t");
                int number = scanner.nextInt();
                switch (number) {
                    case 1:
                        UserMenuView.launch();
                        break;
                    case 2:
                        productView.launch();
                        break;
                    case 3:
                        OrderViewlauncher orderViewlauncher = new OrderViewlauncher();
                        orderViewlauncher.launch();
                        break;
                    case 4:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Chon chuc nang khong dung! Vui long chon lai!");
                        menuOption();
                }
            } catch (InputMismatchException io) {
                System.out.println("Nhap sai! Vui long nhap lai!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public static void mainmenu() {
        System.out.println("\n\t ███████████████ MAIN MENU ███████████████");
        System.out.println("\t █                                       █");
        System.out.println("\t █      1. Quan li nguoi dung            █");
        System.out.println("\t █      2. Quan li kho                   █");
        System.out.println("\t █      3. Quan li don dat hang          █");
        System.out.println("\t █      4. Thoat chuong trinh            █");
        System.out.println("\t █                                       █");
        System.out.println("\t █████████████████████████████████████████");
    }
}
