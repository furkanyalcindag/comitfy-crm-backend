package com.comitfy.crm.app.controller;

import com.comitfy.crm.app.dto.ProductDTO;
import com.comitfy.crm.app.dto.requestDTO.ProductRequestDTO;
import com.comitfy.crm.app.entity.Product;
import com.comitfy.crm.app.mapper.ProductMapper;
import com.comitfy.crm.app.repository.ProductRepository;
import com.comitfy.crm.app.service.ProductService;
import com.comitfy.crm.app.specification.ProductSpecification;
import com.comitfy.crm.util.common.BaseCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("product")
public class ProductController extends BaseCrudController<ProductDTO, ProductRequestDTO, Product, ProductRepository, ProductMapper, ProductSpecification, ProductService> {


    @Autowired
    ProductService productService;

    @Autowired
    ProductMapper productMapper;


    @Override
    protected ProductService getService() {
        return productService;
    }

    @Override
    protected ProductMapper getMapper() {
        return productMapper;
    }


    @PostMapping("/save-all")
    public ResponseEntity<ProductDTO> save(@RequestBody ProductRequestDTO body) {

        return new ResponseEntity<>(getService().save(body), HttpStatus.CREATED);
    }


    @PutMapping("put-all/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable UUID id, @RequestBody ProductRequestDTO body) {

        return new ResponseEntity<>(getService().update(body, id), HttpStatus.OK);
    }


}
