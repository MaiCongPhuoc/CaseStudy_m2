package views;

import java.util.Scanner;

public class UserMenuView {
    public static void launch() {
        Scanner scanner = new Scanner(System.in);
        UserView userView = new UserView();
        int option = -1;
        do {
            menuUser();
            try {
                do {
                    System.out.println("chon chuc nang: ");
                    System.out.print(" ═╬════►  \t");
                    option = Integer.parseInt(scanner.nextLine());
                    if (option > 5 || option < 1)
                        System.out.println("chon chu nang khong dung! Vui long nhap lai");
                } while (option > 5 || option < 1);
                switch (option) {
                    case 1:
                        userView.addUser();
                        break;
                    case 2:
                         userView.updateUser();
                        break;
                    case 3:
                        userView.showUsers(InputOption.SHOW);
                        break;
                    case 4:
                        userView.deleUser(InputOption.DELETE);
                        break;
                    case 5:
                        MainLauncher.menuOption();
                        break;
                    default:
                        System.out.println("Chon chuc nang khong dung! Vui long nhap lai!!");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Nhap sai! vui long nhap lai.");
            }
        } while (option != 5);
    }
    public static void menuUser() {
        System.out.println("\t ███████████ USERS MANAGER ███████████████");
        System.out.println("\t █                                       █");
        System.out.println("\t █      1. Them nguoi dung               █");
        System.out.println("\t █      2. Sua thong tin nguoi dung      █");
        System.out.println("\t █      3. Hien danh sach nguoi dung     █");
        System.out.println("\t █      4. Xoa nguoi dung                █");
        System.out.println("\t █      5. Quay lai                      █");
        System.out.println("\t █                                       █");
        System.out.println("\t █████████████████████████████████████████");
    }
}
