package views;

import utils.AppUtils;
import service.IUserService;
import service.UserService;

import java.util.Scanner;

public class AdminView {
    private  final IUserService userService;
    private final Scanner scanner = new Scanner(System.in);

    public AdminView() {
        this.userService = UserService.getInstance();
    }

    public void adminLogin() {
        boolean isRetry;
        System.out.println("══════════════════════════ DANG NHAP HE THONG ══════════════════════════");
        do {
            System.out.print("Username: ");
            String username = AppUtils.retryString("username");
            System.out.print("passwork: ");
            String passwork = AppUtils.retryString("passwork");
            if(userService.adminLogin(username, passwork) == null) {
                System.out.println("Tai khoan khong hop le!");
                isRetry = isRetry();
            } else {
                System.out.println("ban dang nhap thanh cong: ");
                System.out.println("CHAO MUNG BAN DA DEN VOI KHO CUA MAI CONG PHUOC \n");
                isRetry = false;
            }
        } while (isRetry);
    }
    // Xuat hien thong bao cho nguoi ta chon de nhap lai hay khong
    private boolean isRetry() {
        do {
            try {
                System.out.println("\t ████████████████████ CHON ██████████████████████");
                System.out.println("\t █                                              █");
                System.out.println("\t █   1.Nhan 'y' de dang nhap lai                █");
                System.out.println("\t █   2.Nhan 'n' de thoat chuong trinh           █");
                System.out.println("\t █                                              █");
                System.out.println("\t ████████████████████████████████████████████████");
                System.out.print(" ═╬════► ");
                String option = scanner.nextLine();
                switch (option) {
                    case "y" :
                        return true;
                    case "n" :
                        System.out.println("\tTạm biệt. Hẹn gặp lại!");
                        System.exit(5);
                        break;
                    default:
                        System.out.println("Chon chuc nang khong dung! Vui long chon lai");
                        break;
                }
            } catch (Exception e){
                System.out.println("nhap sai! Vui long nhap lai");
                e.printStackTrace();
            }
        } while (true);
    }
}
