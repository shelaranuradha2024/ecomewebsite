package com.example.springbootmysqldemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import com.example.springbootmysqldemo.model.Product;
import com.example.springbootmysqldemo.repository.ProductRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/product")  // Base URL for all product-related endpoints
@CrossOrigin(origins = "http://localhost:3000") // Enable Cross-Origin Resource Sharing (CORS) from frontend react
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // Add a product with an image (POST request)
    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestParam("name") String name,
                                              @RequestParam("price") double price,
                                              @RequestParam("image") MultipartFile file) {
        try {
            // Check if the uploaded file is empty
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file uploaded");
            }

            String fileName = file.getOriginalFilename();
            File targetFile = new File("D:/uploaded_images/" + fileName);

            // Save the uploaded file to the disk
            file.transferTo(targetFile);

            // Create product entity and set its properties
            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setImageUrl("http://localhost:8081/product/uploads/" + fileName); // URL for image access

            // Save the product to the database
            productRepository.save(product);

            return ResponseEntity.ok("Product added successfully with image: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + e.getMessage());
        }
    }

    // Get an uploaded image (GET request)
    @GetMapping("/uploads/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        try {
            // Resolve the file path to the uploaded images directory
            Path imagePath = Paths.get("D:/uploaded_images/").resolve(imageName).normalize();
            Resource resource = new UrlResource(imagePath.toUri());

            // Check if the file exists and is readable
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // Set MIME type, use IMAGE_PNG if it's a PNG file
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get all products (GET request)
    @GetMapping("/")
    public ResponseEntity<Iterable<Product>> getAllProducts() {
        Iterable<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }
 // In ProductController.java
    @GetMapping("/search")
    public ResponseEntity<Iterable<Product>> searchProducts(@RequestParam("name") String name) {
        Iterable<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        return ResponseEntity.ok(products);
    }


    // Update a product (PUT request)
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, 
                                                @RequestParam("name") String name,
                                                @RequestParam("price") double price,
                                                @RequestParam("image") MultipartFile file) {
        try {
            Product product = productRepository.findById(id).orElse(null);

            if (product == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
            }

            // Update product details
            product.setName(name);
            product.setPrice(price);

            // If a new image is uploaded, save it and update the URL
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                File targetFile = new File("D:/uploaded_images/" + fileName);
                file.transferTo(targetFile);
                product.setImageUrl("http://localhost:8081/product/uploads/" + fileName); // Update image URL
            }

            productRepository.save(product);
            return ResponseEntity.ok("Product updated successfully");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating product: " + e.getMessage());
        }
    }

    // Delete a product (DELETE request)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            productRepository.deleteById(id);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting product: " + e.getMessage());
        }
    }
}
