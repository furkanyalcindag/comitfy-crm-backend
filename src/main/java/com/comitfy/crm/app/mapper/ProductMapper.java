package com.comitfy.crm.app.mapper;

import com.comitfy.crm.app.dto.AutoCompleteDTO;
import com.comitfy.crm.app.dto.ProductDTO;
import com.comitfy.crm.app.dto.requestDTO.ProductMaterialRequestDTO;
import com.comitfy.crm.app.dto.requestDTO.ProductRequestDTO;
import com.comitfy.crm.app.entity.Material;
import com.comitfy.crm.app.entity.Product;
import com.comitfy.crm.app.entity.ProductMaterial;
import com.comitfy.crm.app.repository.MaterialRepository;
import com.comitfy.crm.app.repository.ProductMaterialRepository;
import com.comitfy.crm.app.repository.ProductRepository;
import com.comitfy.crm.util.common.BaseMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ProductMapper implements BaseMapper<ProductDTO, ProductRequestDTO, Product> {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MaterialRepository materialRepository;

    @Autowired
    ProductMaterialRepository productMaterialRepository;

    @Override
    public ProductDTO entityToDTO(Product entity) {
        try {
            ProductDTO productDTO = new ProductDTO();
            BeanUtils.copyProperties(productDTO, entity);
            return productDTO;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Product entitytoEntity(Product entity) {
        return null;
    }

    @Override
    public Product dtoToEntity(ProductDTO dto) {
        try {
            Product product = new Product();
            BeanUtils.copyProperties(dto, product);
            return product;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Product requestDTOToEntity(ProductRequestDTO dto) {


        try {
            ObjectMapper mapper = new ObjectMapper();
            Product product = new Product();
            product.setCode(dto.getCode());
            product.setName(dto.getName());
            product.setReceipt(mapper.writeValueAsString(dto.getReceipt()));

            product = productRepository.save(product);
            for (ProductMaterialRequestDTO pmrDto : dto.getMaterialList()) {
                Material material = materialRepository.findByUuid(pmrDto.getMaterialUUID()).get();
                ProductMaterial productMaterial = new ProductMaterial();
                productMaterial.setProduct(product);
                productMaterial.setMaterial(material);
                productMaterial.setQuantity(pmrDto.getQuantity());
                productMaterialRepository.save(productMaterial);

            }
            return product;
        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }

    }

    @Override
    public List<Product> dtoListToEntityList(List<ProductDTO> productDTOS) {

        List<Product> productList = new ArrayList<>();

        for (ProductDTO productDTO : productDTOS) {

            Product product = new Product();
            product = dtoToEntity(productDTO);
            productList.add(product);

        }

        return productList;

    }

    @Override
    public List<ProductDTO> entityListToDTOList(List<Product> products) {
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : products) {
            ProductDTO dto = new ProductDTO();
            dto = entityToDTO(product);
            productDTOList.add(dto);
        }
        return productDTOList;
    }

    @Override
    public void update(Product entity, Product updateEntity) {

    }

    @Override
    public AutoCompleteDTO entityToAutoCompleteDTO(Product entity) {

        AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
        autoCompleteDTO.setLabel(entity.getName());
        autoCompleteDTO.setValue(entity.getUuid());
        return autoCompleteDTO;
    }
}
