package com.comitfy.crm.app.service;

import com.comitfy.crm.app.dto.ProductDTO;
import com.comitfy.crm.app.dto.requestDTO.ProductRequestDTO;
import com.comitfy.crm.app.entity.Product;
import com.comitfy.crm.app.mapper.ProductMapper;
import com.comitfy.crm.app.repository.ProductRepository;
import com.comitfy.crm.app.specification.ProductSpecification;
import com.comitfy.crm.util.common.BaseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends BaseService<ProductDTO, ProductRequestDTO, Product, ProductRepository, ProductMapper,ProductSpecification> {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductSpecification productSpecification;

    @Autowired
    ProductMapper productMapper;

    @Override
    public ProductRepository getRepository() {
        return productRepository;
    }

    @Override
    public ProductMapper getMapper() {
        return productMapper;
    }

    @Override
    public ProductSpecification getSpecification() {
        return productSpecification;
    }


    @Transactional
    public ProductDTO save(ProductRequestDTO requestDTO) {
        Product entity = getMapper().requestDTOToEntity(requestDTO);

        return getMapper().entityToDTO(entity);
    }
}
