package service;

import model.Product;
import utils.CSVUtils;
import utils.InstantUtils;

import javax.jws.soap.SOAPBinding;
import java.time.Instant;
import java.util.*;

public class ProductServer implements IProductService {
    public static final String PATHPRODUCT = "data/products.csv";

    private static ProductServer instance;

    public ProductServer() {

    }

    public static ProductServer getInstance() {
        if (instance == null)
            instance = new ProductServer();
        return instance;
    }

    @Override
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();
        List<String> records = CSVUtils.read(PATHPRODUCT);
        for (String record : records) {
            productList.add(Product.parseProduct(record));
        }
        return productList;
    }

    @Override
    public Product findById(long id) {
        List<Product> productList = findAll();
        for (Product product : productList) {
            if (product.getId() == id)
                return product;
        }
        return null;
    }

    public void searchByName(String title) {
        List<Product> productList = findAll();
        for (Product product : productList) {
            if (product.getTitle().toLowerCase().replace(" ","").equals(title.replace(" ",""))) {
                System.out.printf("\nSan pham ban muon tim kiem la: \n█  ID:  %s █ Ten san pham:  %s █ Gia san pham:  %s █ So luong:  %s █ Ngay tao:  %s Nuoc Revive Ngay cap nhat:  %s  █\n\n",
                        product.getId(),
                        product.getTitle(),
                        InstantUtils.doubleToVND(product.getPrice()),
                        InstantUtils.quantityProducts(product.getQuantity()),
                        InstantUtils.instantToString(product.getCreatedAt()),
                        product.getUpdatedAt() == null ? "chua co": InstantUtils.instantToString(product.getUpdatedAt()));
                return;
            }
        }
    }

    @Override
    public void searchById(long id) {
        List<Product> productList = findAll();
        for (Product product : productList) {
            if (product.getId().equals(id)) {
                System.out.printf("San pham ban muon tim kiem la: \nID:  %s █ Ten san pham:  %s █ Gia san pham:  %s █ So luong:  %s █ Ngay tao:  %s █ Ngay cap nhat:  %s\n",
                        product.getId(),
                        product.getTitle(),
                        InstantUtils.doubleToVND(product.getPrice()),
                        InstantUtils.quantityProducts(product.getQuantity()),
                        InstantUtils.instantToString(product.getCreatedAt()),
                        product.getUpdatedAt() == null ? "chua co": InstantUtils.instantToString(product.getUpdatedAt()));
                return;
            }
        }
    }

    @Override
    public boolean existsById(long id) {
        return findById(id) != null;
    }

    @Override
    public void add(Product newProduct) {
//        newProduct.setId(System.currentTimeMillis() / 1000);
        newProduct.setCreatedAt(Instant.now());
        List<Product> productList = findAll();
        for (Product product : productList) {
            String newmane = newProduct.getTitle().replace(" ", "").toLowerCase();
            String nameProduct = product.getTitle().replace(" ", "").toLowerCase();
            if (newmane.equals(nameProduct) && newProduct.getPrice().equals(product.getPrice())) {
                product.setQuantity(product.getQuantity() + newProduct.getQuantity());
                CSVUtils.write(PATHPRODUCT, productList);
                return;
            }
        }
        productList.add(newProduct);
        CSVUtils.write(PATHPRODUCT, productList);
    }

    @Override
    public void update(Product newProduct) {
        List<Product> productList = findAll();
        for (Product product : productList) {
            if (newProduct.getId().equals(product.getId())) {
                if (newProduct.getTitle() != null && !newProduct.getTitle().isEmpty())
                    product.setTitle(newProduct.getTitle());
                if (newProduct.getPrice() != null)
                    product.setPrice(newProduct.getPrice());
                if (newProduct.getQuantity() != null)
                    product.setQuantity(newProduct.getQuantity());
                product.setUpdatedAt(Instant.now());
                CSVUtils.write(PATHPRODUCT, productList);
            }
        }
    }

    @Override
    public void remove(int idProduct) {
        List<Product> productList = findAll();
        for (Product product : productList) {
            if (product.getId() == idProduct) {
                System.out.println(product);
                productList.remove(product);
                CSVUtils.write(PATHPRODUCT, productList);
                return;
            }
        }
    }

//    @Override
//    public Product sortByNameASCE() {
//        List<Product> productList = findAll();
//        Collections.sort(productList, new Comparator<Product>() {
//            @Override
//            public int compare(Product o1, Product o2) {
//                return (o1.getTitle().compareTo(o2.getTitle()));
//            }
//        });
//        return null;
//    }

    @Override
    public Product sortQuantityASC() {
        List<Product> productList = findAll();
        Collections.sort(productList, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                double result = (o1.getPrice() - o2.getPrice());
                if (result == 0)
                    return 0;
                return (o1.getPrice() - o2.getPrice()) > 0 ? 1 : -1;
            }
        });
        CSVUtils.write(PATHPRODUCT, productList);
        return null;
    }

    @Override
    public Product sortQuantityDESC() {
        List<Product> productList = findAll();
        Collections.sort(productList, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                double result = (o2.getPrice() - o1.getPrice());
                if (result == 0)
                    return 0;
                return result > 0 ? 1 : -1;
            }
        });
        CSVUtils.write(PATHPRODUCT, productList);
        return null;
    }

    @Override
    public void updateQuantityy(long id, int quantity) {
        List<Product> productList = findAll();
        for (Product product : productList) {
            if (product.getId() == id) {
                if (product.getQuantity() >= quantity) {
                    product.setQuantity(product.getQuantity() - quantity);
                    CSVUtils.write(PATHPRODUCT, productList);
                    break;
                }
            }
        }
    }

}
