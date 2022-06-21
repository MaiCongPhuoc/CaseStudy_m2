package views;

import java.util.Scanner;

public class OrderViewlauncher {
    private final Scanner scanner = new Scanner(System.in);
    public void launch() {
        OrderView orderView = new OrderView();
        boolean is = false;
        do {
            menuOrder();
            System.out.println("\n Chon chuc nang");
            System.out.print("═╬═══► :");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    orderView.addOrder();
                    break;
                case 2:
                    orderView.showListOrder();
                    break;
                case 3:
                    MainLauncher.menuOption();
                    break;
                default:
                    System.out.println("Vui long chon dung chuc nang!!");;
                    break;
            }
        } while (!is);
    }

    public void menuOrder() {
        System.out.print("\t\t████████████████████████ ORDER MENU ███████████████████████" +
                "\n\t\t█                                                         █" +
                "\n\t\t█                                                         █" +
                "\n\t\t█          1. Tao Order.                                  █" +
                "\n\t\t█          2. Xem danh sach order.                        █" +
                "\n\t\t█          3. Quay lai.                                   █" +
                "\n\t\t█                                                         █" +
                "\n\t\t█                                                         █" +
                "\n\t\t███████████████████████████████████████████████████████████\n");
    }
}
