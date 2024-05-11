package bll;

import java.util.List;
import java.util.NoSuchElementException;

import dao.ProductsDAO;
import model.Products;

public class ProductsBLL {

    public int insertProduct(Products products) {
        return ProductsDAO.insert(products);
    }

    public void deleteProductById(int productId) {
        ProductsDAO.deleteById(productId);
    }

    public Products findProductById(int id) {
        Products st = ProductsDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The product with id = " + id + " was not found!");
        }
        return st;
    }

    public List<Products> findProducts() {
        return ProductsDAO.find();
    }

    public void updateProduct(Products products) {
        ProductsDAO.update(products);
    }

}