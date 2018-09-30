package com.egartech.sppi.web;

import com.egartech.sppi.model.ProductType;
import com.egartech.sppi.model.ProductTypeAttribute;
import com.egartech.sppi.repo.ProductTypeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by alander on 30.09.18.
 */
@Controller
public class ProductTypeController {

    @Autowired
    ProductTypeRepository productTypeRepository;

    @RequestMapping(value = "productType/{id}/attributes", method = RequestMethod.GET)
    public ResponseEntity<ProductTypeAttribute[]> getProductTypeAttributes(@PathVariable("id") Long id) {
        ProductType productType = productTypeRepository.findOne(id);
        String attributeString = productType.getAttributes();
        Gson gson = new Gson();
        ProductTypeAttribute[] result = gson.fromJson(attributeString,ProductTypeAttribute[].class);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
