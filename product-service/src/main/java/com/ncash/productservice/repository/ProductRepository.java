package com.ncash.productservice.repository;

import com.ncash.productservice.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Product repository.
 */
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {


}
