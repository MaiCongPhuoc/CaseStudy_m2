package views;

import model.Order;
import model.OrderItem;
import model.Product;
import service.*;
import utils.InstantUtils;
import utils.ValidateUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderView {
    private final Scanner scanner = new Scanner(System.in);
    private final IProductService productService;
    private final IOrderService orderService;
    private final IOrderItemService orderItemService;

    public OrderView() {
        productService = ProductServer.getInstance();
        orderItemService = OrderItemService.getInstance();
        orderService = OrderService.getInstance();
    }

    public List<OrderItem> addOderItems(long orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        ProductView productView = new ProductView();
        productView.showProduct();
        System.out.println("Nhap so luong don hang ma ban muon mua");
        System.out.print("═╬═══► :");
        int choice = Integer.parseInt(scanner.nextLine());
        while (choice < 0) {
            System.out.println("so luong hoa don khong duoc am:");
            choice = scanner.nextInt();
        }
        int count = 0;
        do {
            try {
                orderItems.add(addOderItem(orderId));
                count++;
            } catch (Exception e) {
                System.out.println("Khong dung! Vui long nhap lai.");
            }
        } while (count < choice);
        return orderItems;
    }

    public void addOrder() {
        try {
            orderItemService.findAll();
            long orderId = System.currentTimeMillis() / 1000;
            System.out.println("Nhap ten (vd: Phuoc Mai)");
            System.out.print("═╬═══► :");
            String name = scanner.nextLine();
            while (name.trim().isEmpty()) {
                System.out.println("Ten cua ban dang de trong");
                System.out.print("═╬═══► :");
                name = scanner.nextLine();
            }
            System.out.println("Nhap so dien thoai");
            System.out.print("═╬═══► :");
            String phone = scanner.nextLine();
            while (!ValidateUtils.isPhoneValid(phone) || phone.trim().isEmpty()) {
                System.out.println("So dien thoai: " + phone + " khong dung dinh dang! Vui long nhap lai.");
                System.out.println("Nhap lai so dien thoai, phai du 10 so.");
                System.out.print("═╬═══► :");
                phone = scanner.nextLine();
            }
            System.out.println("Nhap dia chi cua ban");
            System.out.print("═╬═══► :");
            String adress = scanner.nextLine();
            while (adress.trim().isEmpty()) {
                System.out.println("Dia chi cua ban khong duoc de trong! Vui long nhap lai.");
                System.out.print("═╬═══► :");
                adress = scanner.nextLine();
            }
            Order order = new Order(orderId, name, phone, adress);
            List<OrderItem> orderItems = addOderItems(orderId);
            for (OrderItem orderItem : orderItems) {
                orderItemService.add(orderItem);
            }
            orderService.add(order);
            System.out.println("Da tao order thanh cong: \n" +
                    "\t██████████████████████████████████████\n" +
                    "\t█                                    █\n" +
                    "\t█           1. In bill               █\n" +
                    "\t█           2. Quay lai              █\n" +
                    "\t█           3. Exit                  █\n" +
                    "\t█                                    █\n" +
                    "\t██████████████████████████████████████\n\n" +
                    "Chon chuc nang");
            System.out.print("═╬═══► :");
            do {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        showPaymentInfo(order);
                        break;
                    case 2:
                        OrderViewlauncher orderViewlauncher = new OrderViewlauncher();
                        orderViewlauncher.launch();
                        break;
                    case 3:
                        MainLauncher.mainmenu();
                        break;
                    default:
                        System.out.println("Ban nhap chua dung chuc nang! vui long nhap lai.");
                }
            } while (true);
        } catch (Exception e) {
            System.out.println("Khong dung! Vui long nhap lai.");
        }
    }

    public OrderItem addOderItem(long orderId) {
        do {
            try {
                orderItemService.findAll();
                ProductView productView = new ProductView();
                productView.showProduct();
                long id = System.currentTimeMillis() / 1000;
                System.out.println("Nhap id ban muon mua");
                System.out.print("═╬═══► :");
                int techsId = Integer.parseInt(scanner.nextLine());
                while (!productService.existsById(techsId)) {
                    System.out.println("id san pham khong ton tai! vui long nhap lai");
                    System.out.print("═╬═══► :");
                    techsId = Integer.parseInt(scanner.nextLine());
                }
                Product product = productService.findById(techsId);
                double price = product.getPrice();
                System.out.println("Nhap so luong san pham ban muon mua");
                System.out.print("═╬═══► :");
                int quantity = Integer.parseInt(scanner.nextLine());
                while (!checkQuantityProduct(product, quantity)) {
                    System.out.println("So luong khong du! Vui long thu lai.");
                    System.out.print("═╬═══► :");
                    quantity = Integer.parseInt(scanner.nextLine());
                    if (product.getQuantity() == 0)
                        QContinueTToExist();
                }
                String productName = product.getTitle();
                double total = quantity * price;
                OrderItem orderItem = new OrderItem(id, price, quantity, orderId, techsId, productName, total);
                productService.updateQuantityy(techsId, quantity);
                return orderItem;
            } catch (Exception e) {
                System.out.println("khong dung! Vui long nhap lai.");
            }
        } while (true);
    }

    public boolean checkQuantityProduct(Product product, int quantity) {
        if (quantity <= product.getQuantity())
            return true;
        return false;
    }

    public void showPaymentInfo(Order order) {
        try {
            System.out.println("\t\t\t\t┌───────────────────────────────────────────────────────────────────────────┐");
            System.out.println("\t\t\t\t│░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ HOA DON ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░│");
            System.out.println("\t\t\t\t├───────────────────────────┬───────────────────────────────────────────────┤");
            System.out.printf("\t\t\t\t│\t%-20s\t %-25s %16s │\n", "Ten day du      :       │", order.getFullName(), "");
            System.out.printf("\t\t\t\t│\t%-20s\t %-25s %16s │\n", "So dien thoai   :       │", order.getMobile(), "");
            System.out.printf("\t\t\t\t│\t%-20s\t %-25s %16s │\n", "Dia chi         :       │", order.getAddress(), "");
            System.out.printf("\t\t\t\t│\t%-20s\t %-25s %16s │\n", "Ngay tao        :       │", InstantUtils.instantToString(order.getCreatedAt()), "");
            System.out.println("\t\t\t\t├────┬───────────────────┬──┴──────────────────────────┬────────────────────┤");
            System.out.printf("\t\t\t\t│%-4s│%-17s\t │%-28s │%-19s │\n", "STT", "Ten san pham", "So luong", "Gia");
            System.out.println("\t\t\t\t├────┼───────────────────┼─────────────────────────────┼────────────────────┤");
            List<OrderItem> orderItems = orderItemService.findAll();
            double sum = 0;
            int count = 0;
            for (OrderItem orderItem1 : orderItems) {
                if (orderItem1.getOrderId() == order.getId()) {
                    sum += orderItem1.getTotal();
                    count++;
                    orderItem1.setGrandTotal(sum);
                    orderItemService.update(orderItem1.getOrderId(), orderItem1.getPrice(), sum);
                    System.out.printf("\t\t\t\t│ %1s  │  %-17s│\t %-25s │  %-18s│\n",
                            count,
                            orderItem1.getProductName(),
                            orderItem1.getQuantity(),
                            InstantUtils.doubleToVND(orderItem1.getPrice()));
                    System.out.println("\t\t\t\t├────┴───────────────────┴─────────────────────────────┴────────────────────┤");
                }
            }
                    System.out.println("\t\t\t\t├────┴───────────────────┴─────────────────────────────┴────────────────────┤");
            System.out.printf("\t\t\t\t│                                                  Tong: %17s  │\n",InstantUtils.doubleToVND(sum));
            System.out.println("\t\t\t\t└───────────────────────────────────────────────────────────────────────────┘\n\n");
            QContinueTToExist();
        } catch (Exception e) {
            System.out.println("Nhap khong dung! Vui long nhap lai.");
        }
    }

    public void showListOrder() {
        List<Order> orders = orderService.findAll();
        List<OrderItem> orderItems = orderItemService.findAll();
        OrderItem newOrderItem = new OrderItem();
        List<OrderItem> orderItemList = new ArrayList<>();
        try {
            int count = 0;
            double printTotal = 0;
            double total = 0;
            double sum = 0;
            double grandTotal = 0;
            System.out.println("──────────────────────────────────────────────────────────── DANH SACH HOA DON ─────────────────────────────────────────────────────────────────────");
            for (Order order : orders) {
                for (OrderItem orderItem : orderItems) {
                    if (orderItem.getOrderId() == order.getId()) {
                        newOrderItem = orderItem;
                        orderItemList.add(newOrderItem);
                        double price = orderItem.getPrice();
                        int quantity = orderItem.getQuantity();
                        sum = price * quantity;
                        grandTotal += sum;
                    }
                }
                newOrderItem.setGrandTotal(grandTotal);
                orderItemService.update(newOrderItem.getOrderId(), newOrderItem.getPrice(), grandTotal);
                System.out.println("\t┌───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
                System.out.printf("\t│\t%-20s%-20s%-30s%-20s%-25s%-25s│\n", "Id            : ", order.getId(), " ", "Ten khach hang :", order.getFullName(), "");
                System.out.printf("\t│\t%-20s%-20s%-30s%-20s%-25s%-25s│\n", "So dien thoai : ", order.getMobile(), " ", "Dia chi        : ", order.getAddress(), "");
                System.out.println("\t├───────┬───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┤");
                System.out.printf("\t│\t%-1s │%-19s%-20s%-10s%-15s%-15s%-10s%-10s%-15s %-15s %-1s│\n", "STT", "", "Ten san pham", "", "", "So luong", "", "Gia", "  ", "Tong tien san pham", "");
                System.out.println("\t├───────┼───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┤");

                for (OrderItem orderItem : orderItemList) {
                    count++;
                    total = orderItem.getPrice() * orderItem.getQuantity();
                    System.out.printf("\t│\t%-3d │%-1s%-20s%-20s%-10s%-15s%-15d%-10s%-18s%-11s%14s\t│\n", count, "", "", orderItem.getProductName(), " ", "", orderItem.getQuantity()
                            , "", InstantUtils.doubleToVND(orderItem.getPrice())
                            , "", InstantUtils.doubleToVND(total));
                }
                orderItemList.clear();
                printTotal += grandTotal;
                System.out.printf("\t└───────┴──────────────────────────────────────────────────────────────────────────────────────────────────── Tong don:  %15s ───────┘\n\n\n", InstantUtils.doubleToVND(grandTotal));
                sum = 0;
                grandTotal = 0;
                count = 0;
            }
            System.out.printf("\t\t\t\t\t\t\t┌────────────────── Tong doanh thu: %15s ────────────────────────┐\n", InstantUtils.doubleToVND(printTotal));
            System.out.println("\t\t\t\t\t\t\t└───────────────────────────────────────────────────────────────────────────┘ \n");
            QContinueTToExist();
        } catch (Exception e) {
            System.out.println("Khong dung! Vui long nhap lai.");
        }
    }

    public void QContinueTToExist() {
        boolean is = true;
        do {
            System.out.println("Nhan 'q' de quay lai \t|\t 't' de thoat chuong trinh");
            System.out.print("═╬═══► :");
            String choice = scanner.nextLine();
            switch (choice) {
                case "q":
                    OrderViewlauncher orderViewlauncher = new OrderViewlauncher();
                    orderViewlauncher.launch();
                    break;
                case "t":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Nhap khong dung chuc nang! Vui long nhap lai.");
                    is = false;
                    break;
            }
        } while (!is);
    }
}
