package views;

import model.Role;
import model.User;
import service.IUserService;
import service.UserService;
import utils.AppUtils;
import utils.InstantUtils;
import utils.ValidateUtils;

import java.util.List;
import java.util.Scanner;

public class UserView {
    private IUserService userService;
    private final Scanner scanner = new Scanner(System.in);

    public UserView() {
        this.userService = UserService.getInstance();
    }

    public void deleUser(InputOption option) {
        
    }

    public void updateUser() {
        boolean isRetry = false;
        do {
            try {
                showUsers(InputOption.UPDATE);
                //Neu Id khong Ton tai se khong thoat ra khoi vong lap trong ham inputId
                long id = inputId(InputOption.UPDATE);
                System.out.println("\t██████████████ SUA ██████████████\n"
                                 + "\t█                               █\n"
                                 + "\t█     1. Doi ten                █\n"
                                 + "\t█     2. Doi so dien thoai      █\n"
                                 + "\t█     3. Doi dia chi            █\n"
                                 + "\t█     4. Doi gmail              █\n"
                                 + "\t█     5. Quay lai               █\n"
                                 + "\t█                               █\n"
                                 + "\t█████████████████████████████████");
                int option = AppUtils.retryChoose(1, 5);
                User newUser = new User();
                newUser.setId(id);
                switch (option) {
                    case 1:
                        String name = inputFullname(InputOption.UPDATE);
                        newUser.setFullName(name);
                        userService.update(newUser);
                        System.out.println("Ban da doi ten thang cong!");
                        break;
                    case 2:
                        String phone = inputPhone(InputOption.UPDATE);
                        newUser.setMobile(phone);
                        userService.update(newUser);
                        System.out.println("Bạn đã đổi số điện thoại thành công");
                        break;
                    case 3:
                        String address = inputAddress(InputOption.UPDATE);
                        newUser.setAddress(address);
                        userService.update(newUser);
                        System.out.println("Ban da doi dia chi thanh cong");
                        break;
                    case 4:
                        String email = inputEmail();
                        newUser.setEmail(email);
                        userService.update(newUser);
                        System.out.println("Ban da doi dia chi thanh cong");
                        break;
                    case 5:
                        UserMenuView.launch();
                        break;
                }
//                isRetry = option != 5 && AppUtils.isRetry(InputOption.UPDATE );
            } catch (Exception e) {
                System.out.println("Nhap sai! vui long nhap lai.");
            }
        } while (isRetry);
    }

    public void showUsers(InputOption inputOption) {
        System.out.println("----------------------------------------------------------------------------- DANH SACH NGUOI DUNG ----------------------------------------------------------------------------");
        System.out.printf("\t%-18s %-17s %-28s %-25s %-18s %-18s %-22s %-13s \n", "Id", "Ten", "So dien thoai", "Email", "Dia chi", "Nguoi dung", "Ngay tao", "Ngay cap nhat");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        List<User> users = userService.findAll();
        for (User user : users) {
            System.out.printf(" %-13d |  %-19s |   %-15s |   %-22s   |  %-20s  |    %-8s  | %20s | %20s |\n",
                    user.getId(),
                    user.getFullName(),
                    user.getMobile(),
                    user.getEmail(),
                    user.getAddress(),
                    user.getRole(),
                    InstantUtils.instantToString(user.getCreatedAt()),
                    user.getUpdatedAt() == null ? "" : InstantUtils.instantToString(user.getUpdatedAt())
            );
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        if (inputOption == InputOption.SHOW)
            AppUtils.isRetry(InputOption.SHOW);
    }

    private int inputId(InputOption option) {
        int id;
        switch (option) {
            case ADD:
                System.out.println("Nhap id");
                break;
            case UPDATE:
                System.out.println("Nhap id ban muon sua");
                break;
        }
        boolean isRetry = false;
        do {
            id = AppUtils.retryParseInt();
            boolean exist = userService.existById(id);
            switch (option) {
                case ADD:
                    if(exist)
                        System.out.println("id nay da ton tai. Vui long nhap id khac!");
                    isRetry = exist;
                    break;
                case UPDATE:
                    if (!exist)
                        System.out.println("Khong tim thay id! Vui long nhap lai");
                    isRetry = !exist;
                    break;
            }
        }while (isRetry);
        return id;
    }

    public void addUser() {
        do {
            try {
                String username = inputUsername();
                String password = inputPassword();
                String fullName = inputFullname(InputOption.ADD);
                String phone = inputPhone(InputOption.ADD);
                String address = inputAddress(InputOption.ADD);
                String email = inputEmail();
                User newUser = new User(username.trim(), password.trim(), fullName.trim(), phone.trim(), email.trim(), address.trim(), Role.USER);
                setRole(newUser);
                userService.add(newUser);
                System.out.println("Đã thêm thành công!");
            } catch (Exception e) {
                System.out.println("Nhap sai! Vui long nhap lai!");
            }
        } while (AppUtils.isRetry(InputOption.ADD));
    }

    public void setRole(User newuser) {
        System.out.println("=== SET ROLE ===");
        System.out.println("∥              ∥");
        System.out.println("∥   1. USER    ∥");
        System.out.println("∥   2. ADMIN   ∥");
        System.out.println("∥              ∥");
        System.out.println("================");
        System.out.println("Chọn Role: ");
        System.out.print("═╬═══► ");
        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option) {
            case 1:
                newuser.setRole(Role.USER);
                break;
            case 2:
                newuser.setRole(Role.ADMIN);
                break;
            default:
                System.out.println("Nhap khong dung! VUi long nhap lai");
                setRole(newuser);
        }
    }

    public String inputEmail() {
        System.out.println("Nhap email (vd: maiphuoc244@gmail.com)");
        System.out.print("═╬═══► ");
        String email;
        do {
            // email phai dung dinh dang
            if (!ValidateUtils.isEmailValid(email = scanner.nextLine())) {
                System.out.println("Email " + email + "cua ban khong dung dinh dang! Vui long kiem tra va nhap lai ");
                System.out.println("Nhap email (vd: maiphuoc244@gmail.com)");
                System.out.print("═╬═══► ");
                continue;
            }
            // email co ton tai hay khong
            if (userService.existsByEmail(email)) {
                System.out.println("Email " + email + "cua ban da ton tai! Vui long nhap lai");
                System.out.println("Nhap email (vd: maiphuoc244@gmail.com)");
                System.out.print("═╬═══► ");
                continue;
            }
            break;
        } while (true);
        return email;
    }

    public String inputAddress(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhap dia chi (vd: Hue)");
                break;
            case UPDATE:
                System.out.println("Nhap dia chi ban muon doi!");
                break;
        }
        System.out.print("═╬═══► ");
        return scanner.nextLine();
    }

    public String inputPhone(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhap so dien thoai: (vd: 0778224495)");
                break;
            case UPDATE:
                System.out.println("Nhap so dien thoai muon doi.");
                break;
        }
        System.out.print("═╬═══► ");
        String phone;
        do {
            if (!ValidateUtils.isPhoneValid(phone = scanner.nextLine())) {
                System.out.println("So " + phone + "cua ban khong dung! Vui long nhap lai! (So dien thoai phai 10 so va bat dau la so 0)");
                System.out.println("Nhap so dien thoai (vd: 0778224495)");
                System.out.print("═╬═══► ");
                continue;
            }
            if (userService.existsByPhone(phone)) {
                System.out.println("So nay da ton tai! Moi ban nhap lai.");
                System.out.print("═╬═══► ");
                continue;
            }
            break;
        } while (true);
        return phone;
    }

    public String inputFullname(InputOption option) {

        switch (option) {
            case ADD:
                System.out.println("Nhap ho ten (vd: Mai Cong Phuoc) ");
                break;
            case UPDATE:
                System.out.println("Nhap ten ma ban muon doi");
                break;
        }
        System.out.print("═╬═══► ");
        String fullName;
        while (!ValidateUtils.isNameValid(fullName = scanner.nextLine())) {
            System.out.println("Ten" + fullName + "khong dung dinh dang! Vui long nhap lai. (ten phai viet hoa chu cai dau và khong dau)");
            System.out.println("Nhap ten: (vd: Mai Cong phuoc)");
            System.out.print("═╬═══► ");
        }
        return fullName;
    }

    public String inputPassword() {
        System.out.println("Nhap mat khau (mat khau phai > 6 ki tu)");
        System.out.print("═╬═══► ");
        String password;
        while (!ValidateUtils.isPasswordValid(password = scanner.nextLine())) {
            if (password.trim().equals("")) {
                System.out.println("mat khau khong duoc de trong.");
                continue;
            }
            System.out.println("mat khau yeu! Vui long nhap lai.");
            System.out.print("═╬═══► ");
        }
        return password;
    }

    public String inputUsername() {
        System.out.println("Nhap Username (in hoa ki tu dau, khong co ki tu dat biet).");
        System.out.print("═╬═══► ");
        String username;

        do {
            if (!ValidateUtils.isUsernameValid(username = AppUtils.retryString("username"))) {
                System.out.println(username + " cua ban khong dung dinh dang! Vui long nhap lai.");
                System.out.print("═╬═══► ");
                continue;
            }
            if (userService.existsByUserName(username)) {
                System.out.println("Username nay da ton tai! Vui long nhap lai");
                System.out.print(" ═╬═══► ");
                continue;
            }
            break;
        } while (true);
        return username;
    }
}
