package utils;

import views.InputOption;

import java.util.Scanner;

public class AppUtils {
    static Scanner scanner = new Scanner(System.in);

    public static  int retryParseInt() {
        int result;
        do {
            System.out.println(" ═╬════► \t");
            try {
                result = Integer.parseInt(scanner.nextLine());
                return result;
            } catch (Exception e) {
                System.out.println("Nhap sai! vui long nhap lai");
            }
        } while (true);
    }

    //kiem tra
    public static int retryChoose(int min, int max) {
        int option;
        do {
            System.out.print(" ═╬════► \t");
            try {
                option = Integer.parseInt(scanner.nextLine());
                if(option > max || option < min) {
                    System.out.println("Chon chuc nang khong dung! Vui long chon lai");
                    continue;
                }
                break;
            }catch (Exception e) {
                System.out.println("Nhap sai! Vui long nhap lai");
            }
        } while (true);
        return option;
    }
    // kiem tra phai chuoi rong hay khong
    public static String retryString(String fielName) {
        String result;
        while ((result = scanner.nextLine()).isEmpty()) {
            System.out.printf("%s khong duoc de trong \n", fielName);
            System.out.print(" ═╬════► :");
        }
        return result;
    }

    public static boolean isRetry(InputOption inputOption) {
        do {
            switch (inputOption) {
                case ADD:
                    System.out.println("Nhan 'y' de them tiep \t|\t 'q' de quay lai \t|\t 't' de thoat chuong trinh");
                    break;
                case UPDATE:
                    System.out.println("Nhan 'y' de sua tiep \t|\t 'q' de quay lai\t|\t 't' de thoat chuong trinh");
                    break;
                case DELETE:
                    System.out.println("Nhan 'q' de quay lai\t|\t 't' de thoat chuong trinh");
                    break;
                case SHOW:
                    System.out.println("Nhan 'q' de quay lai \t|\t 't' de thoat chuong trinh");
                    break;
                default:
                    throw  new IllegalStateException("gia tri khong mong doi " + inputOption);
            }
            System.out.print(" ═╬═══► \t");
            String option = scanner.nextLine();
            switch (option) {
                case "y":
                    return true;
                case "q":
                    return false;
                case "t":
                    System.out.println("Tam biet!!! hen gap lai sau!");
                    System.exit(5);
                    break;
                default:
                    System.out.println("Chon chuc nang khong dung! Vui long chon lai");
                    break;
            }
        }while (true);
    }
}
