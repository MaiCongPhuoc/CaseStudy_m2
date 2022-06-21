package service;

import model.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAll();

    Product findById(long id);

    void searchByName(String title);
    void searchById(long id);

    boolean existsById(long id);

    void add(Product newProduct);

    void update(Product newProduct);

    void remove(int newProduct);

//    Product sortByNameASCE();

    Product sortQuantityASC();

    Product sortQuantityDESC();

    void updateQuantityy(long id, int quantity);
}