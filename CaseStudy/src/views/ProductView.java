package views;


import model.Product;
import service.IProductService;
import service.ProductServer;
import utils.AppUtils;
import utils.InstantUtils;

import java.time.Instant;
import java.util.List;
import java.util.Scanner;

public class ProductView {
    private final Scanner scanner = new Scanner(System.in);
    private final IProductService productService;

    public ProductView() {
        this.productService = new ProductServer();
    }

    public void showProduct() {
        System.out.println("\t▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬ DANH SACH SAN PHAM ▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        System.out.printf("\t│\t\t%-6s   ││        %-15s   ││       %-15s   ││   %12s      ││       %-13s    ││    %16s       │\n", "ID", "Ten san pham", "Gia san pham", "So luong", "Ngay tao", "Ngay cap nhat");
        System.out.println("\t▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        List<Product> products = productService.findAll();
        for (Product product : products) {
            System.out.printf("\t│\t%-10s   ││   %-20s   ││    %15s      ││     %12s    ││    %-15s    ││    %19s    │\n",
                    product.getId(),
                    product.getTitle(),
                    InstantUtils.doubleToVND(product.getPrice()),
                    InstantUtils.quantityProducts(product.getQuantity()),
                    InstantUtils.instantToString(product.getCreatedAt()),
                    product.getUpdatedAt() == null ? "" : InstantUtils.instantToString(product.getUpdatedAt())
            );
        }
        System.out.println("\t▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬\n");

    }

    public void addProduct() {
        do {
            try {
                long id = System.currentTimeMillis() / 1000;
                String productName = inputProductName();
                double productPrice = inputPrice();
                int productQuantity = inputQuantity();
                Product newProduct = new Product(id, productName, productPrice, productQuantity);
                productService.add(newProduct);
                System.out.println("\n Them san pham thanh cong!");
            } catch (Exception e) {
                System.out.println("Nhap sai! Vui long nhap lai.");
            }
        } while (AppUtils.isRetry(InputOption.ADD));
    }

    public void updateProduct() {
        boolean isRetry = false;
        do {
            try {
                showProduct();
                System.out.print("Nhap id ban muon tim kiem: ");
                long id = inputId();
                System.out.println("\t█████████████ SỬA █████████████\n"
                        + "\t█                             █\n"
                        + "\t█    1. Doi ten san pham      █\n"
                        + "\t█    2. Doi Gia san pham      █\n"
                        + "\t█    3. Sua so luong          █\n"
                        + "\t█    4. Quay lại              █\n"
                        + "\t█                             █\n"
                        + "\t███████████████████████████████");
                int option = AppUtils.retryChoose(1, 4);
                Product newProduct = new Product();
                newProduct.setId(id);
                switch (option) {
                    case 1:
                        String productName = inputProductName();
                        newProduct.setTitle(productName);
                        productService.update(newProduct);
                        System.out.println("Ban da doi ten thanh cong!");
                        break;
                    case 2:
                        double price = inputPrice();
                        newProduct.setPrice(price);
                        productService.update(newProduct);
                        System.out.println("Ban da doi gia thanh cong!");
                        break;
                    case 3:
                        int quantity = inputQuantity();
                        newProduct.setQuantity(quantity);
                        productService.update(newProduct);
                        System.out.println("Ban da so luong gia thanh cong!");
                        break;
                }
                isRetry = option != 4 && AppUtils.isRetry(InputOption.UPDATE);
            } catch (Exception e) {
                System.out.println("Nhap sai! Vui long nhap lai.");
            }
        } while (isRetry);
    }

    public void removeProduct() {
        showProduct();
        System.out.println("Nhap id ban muon xoa: ");
        int idProduct = inputId();
        productService.remove(idProduct);
        showProduct();
    }

    public void searchProductByTitle() {
        try {
            showProduct();
            System.out.println("Nhap ten san pham: ");
            String title = scanner.nextLine();
            do {
//                title.toLowerCase();
                if (title.equals("")) {
                    System.out.println("Ten san pham khong duoc de trong.");
                    title = scanner.nextLine();
                    continue;
                }
                break;
            }while (true);
            productService.searchByName(title.toLowerCase());
        } catch (Exception e) {
            System.out.println("Nhap sai! Vui long nhap lai");
        }
    }

    public void searchProductById() {
        try {
            showProduct();
            System.out.println("Nhap id cua san pham: ");
            long id = inputId();
            productService.searchById(id);
        } catch (Exception e) {
            System.out.println("Nhap sai! Vui long nhap lai");
        }
    }

    public void sortQuantityASC() {
        productService.sortQuantityASC();
    }

    public void sortQuantityDESC() {
        productService.sortQuantityDESC();
    }

    public void launch() {

        while (true) {
            System.out.print("\t\t████████████████████████████████████████████████████████████████████████████████████████████████████" +
                    "\n\t\t█                                                                                                  █" +
                    "\n\t\t█                                       PRODUCT MANAGEMENT                                         █" +
                    "\n\t\t█                                           Main Menu                                              █" +
                    "\n\t\t█                                                                                                  █" +
                    "\n\t\t█                   1. Hien thi danh sach.                                                         █" +
                    "\n\t\t█                   2. Them san pham.                                                              █" +
                    "\n\t\t█                   3. Sua san pham.                                                               █" +
                    "\n\t\t█                   4. Xoa san pham.                                                               █" +
                    "\n\t\t█                   5. Tim ten san pham.                                                           █" +
                    "\n\t\t█                   6. Tim ID san pham.                                                            █" +
                    "\n\t\t█                   7. Sap xep gia tang dan.                                                       █" +
                    "\n\t\t█                   8. Sap xep gia giam dan.                                                       █" +
                    "\n\t\t█                   9. Quay lai.                                                                   █" +
                    "\n\t\t█                                                                                                  █" +
                    "\n\t\t████████████████████████████████████████████████████████████████████████████████████████████████████\n" +
                    "\n\t\tChon chuc nang ma ban muon: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    showProduct();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    removeProduct();
                    break;
                case 5:
                    searchProductByTitle();
                    break;
                case 6:
                    searchProductById();
                    break;
                case 7:
                    sortQuantityASC();
                    break;
                case 8:
                    sortQuantityDESC();
                    break;
                case 9:
                    MainLauncher.menuOption();
                    break;
                default:
                    System.out.println("So da chon khong hop le! Vui long nhap lai.");
            }
        }
    }

    // kiem tra ten, gia, so luong sp
    public String inputProductName() {
        System.out.println("Nhap ten san pham: ");
        System.out.print("═╬═══► ");
        String nameProduct = scanner.nextLine();
        while (nameProduct.trim().equals("")) {
            System.out.println("Ten san pham khong duoc de trong");
            System.out.print("═╬═══► ");
            nameProduct = scanner.nextLine();
        }
//        do {
//            if (nameProduct.isEmpty()) {
//                System.out.println("Ten san pham khong duoc de trong");
//                System.out.print("═╬═══► ");
//                nameProduct = scanner.nextLine();
//                continue;
//            }
//            break;
//        } while (true);
        return nameProduct;
    }

    public int inputQuantity() {
        do {
            System.out.println("nhap so luong san pham: ");
            int quantity = scanner.nextInt();
            if (quantity < 0) {
                System.out.println("Nhap khong dung! Vui long nhap lai.");
                continue;
            }
            return quantity;
        }
        while (true);
    }

    public double inputPrice() {
//        boolean isEntry = false;
//        System.out.println("nhap gia san pham (vd: 10.000 VND )");
//        double price = scanner.nextDouble();
        do {
            System.out.println("nhap gia san pham (vd: 10.000 VND )");
            double price = scanner.nextDouble();
            if (price < 0 || price % 1000 != 0) {
                System.out.println("Nhap khong dung! Vui long nhap lai");
//                price = scanner.nextDouble();
                continue;
            }
            return price;
        }
        while (true);
    }

    public int inputId() {
        boolean isRetry = false;
        int id;
        do {
//            System.out.print("Nhap id ban muon tim kiem:");
//        System.out.print("═╬═══► ");
            id = scanner.nextInt();
            boolean exist = productService.existsById(id);
            if (!exist) {
                System.out.println("id nay khong ton tai! Vui long nhap lai");
                isRetry = !exist;
            }
            if (exist) {
                return id;
            }
        } while (isRetry);
        return id;
    }
}
