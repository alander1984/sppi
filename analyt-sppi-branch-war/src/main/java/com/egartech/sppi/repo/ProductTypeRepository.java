package com.egartech.sppi.repo;

import com.egartech.sppi.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    ProductType findByProductTypeCode(String productTypeCode);
}