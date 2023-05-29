package com.comitfy.crm.app.mapper;

import com.comitfy.crm.app.dto.AutoCompleteDTO;
import com.comitfy.crm.app.dto.ProductDTO;
import com.comitfy.crm.app.dto.ProductMaterialDTO;
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

    @Autowired
    MaterialMapper mapper;

    @Override
    public ProductDTO entityToDTO(Product entity) {
        try {
            ProductDTO productDTO = new ProductDTO();


            List<ProductMaterial> productMaterials = productMaterialRepository.findAllByProduct(entity);


            //BeanUtils.copyProperties(productDTO, entity);
            productDTO.setCode(entity.getCode());
            productDTO.setName(entity.getName());
            productDTO.setUuid(entity.getUuid());
            productDTO.setReceipts(entity.getReceipts());
            productDTO.setId(entity.getId());

            for (ProductMaterial productMaterial : productMaterials) {


                ProductMaterialDTO productMaterialDTO = new ProductMaterialDTO();
                productMaterialDTO.setMaterial(mapper.entityToDTO(productMaterial.getMaterial()));
                productMaterialDTO.setQuantity(productMaterial.getAmount());
                productMaterialDTO.setProduct(null);
                productDTO.getProductMaterials().add(productMaterialDTO);

            }

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
            product.setReceipts(mapper.writeValueAsString(dto.getReceipts()));

            product = productRepository.save(product);
            for (ProductMaterialRequestDTO pmrDto : dto.getMaterialList()) {
                Material material = materialRepository.findByUuid(pmrDto.getMaterialUUID()).get();
                ProductMaterial productMaterial = new ProductMaterial();
                productMaterial.setProduct(product);
                productMaterial.setMaterial(material);
                productMaterial.setAmount(pmrDto.getAmount());
                productMaterialRepository.save(productMaterial);

            }
            return product;
        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }

    }


    public Product requestDTOToExistEntity(ProductRequestDTO dto, Product product) {


        try {
            ObjectMapper mapper = new ObjectMapper();
            product.setCode(dto.getCode());
            product.setName(dto.getName());
            product.setReceipts(mapper.writeValueAsString(dto.getReceipts()));
            product = productRepository.save(product);

            productMaterialRepository.deleteByProduct(product);

            for (ProductMaterialRequestDTO pmrDto : dto.getMaterialList()) {
                Material material = materialRepository.findByUuid(pmrDto.getMaterialUUID()).get();
                ProductMaterial productMaterial = new ProductMaterial();
                productMaterial.setProduct(product);
                productMaterial.setMaterial(material);
                productMaterial.setAmount(pmrDto.getAmount());
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
