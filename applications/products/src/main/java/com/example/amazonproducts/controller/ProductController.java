package com.example.amazonproducts.controller;

import com.example.amazonproducts.model.Product;
import com.example.amazonproducts.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

  private ProductRepository products;

  public ProductController(ProductRepository products) {
    this.products = products;
  }

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public Product createProduct(@RequestBody Product prod) {
    return products.save(prod);
  }

  @GetMapping
  public List<Product> getAllProducts() {
    return products.findAll();
  }

  @GetMapping("/{id}")
  public Product getOneProduct(@PathVariable("id") Long id) {
    return products.findById(id).get();
  }

  @GetMapping("/{id}/price")
  public Double getProductPrice(@PathVariable("id") Long id) {
    return products.findById(id).get().getPrice();
  }

  @PutMapping("/{id}")
  public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
    Product productToUpdate = products.findById(id).get();

    productToUpdate.setDescription(product.getDescription());
    productToUpdate.setImage(product.getImage());
    productToUpdate.setName(product.getName());
    productToUpdate.setPrice(product.getPrice());

    return products.save(productToUpdate);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteProduct(@PathVariable("id") Long id) {
    products.deleteById(id);
  }
}
