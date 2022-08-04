package com.comitfy.healtie.commercial.mapper;

import com.comitfy.healtie.commercial.dto.ProductClickDTO;
import com.comitfy.healtie.commercial.dto.request.ProductClickRequestDTO;
import com.comitfy.healtie.commercial.entity.ProductClick;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductClickMapper implements BaseMapper<ProductClickDTO, ProductClickRequestDTO, ProductClick> {
    @Override
    public ProductClickDTO entityToDTO(ProductClick entity) {
        ProductClickDTO productClickDTO = new ProductClickDTO();
        productClickDTO.setProductUUID(entity.getProductUUID());
        productClickDTO.setOrderUUID(entity.getOrderUUID());
        productClickDTO.setUserUUID(entity.getUserUUID());
        productClickDTO.setUuid(entity.getUuid());
        return productClickDTO;
    }

    @Override
    public ProductClick dtoToEntity(ProductClickDTO dto) {
        ProductClick productClick = new ProductClick();
        productClick.setProductUUID(dto.getProductUUID());
        productClick.setOrderUUID(dto.getOrderUUID());
        productClick.setUserUUID(dto.getUserUUID());
        return productClick;
    }

    @Override
    public ProductClick requestDTOToEntity(ProductClickRequestDTO dto) {
        ProductClick productClick = new ProductClick();
        productClick.setProductUUID(dto.getProductUUID());
        productClick.setOrderUUID(dto.getOrderUUID());
        productClick.setUserUUID(dto.getUserUUID());
        return productClick;
    }

    @Override
    public ProductClick requestDTOToExistEntity(ProductClick productClick, ProductClickRequestDTO dto) {

        productClick.setProductUUID(dto.getProductUUID());
        productClick.setOrderUUID(dto.getOrderUUID());
        productClick.setUserUUID(dto.getUserUUID());
        return productClick;
    }

    @Override
    public List<ProductClick> dtoListToEntityList(List<ProductClickDTO> productClickDTOS) {
        List<ProductClick> productClickList = new ArrayList<ProductClick>();
        for (ProductClickDTO productClickDTO : productClickDTOS) {
            ProductClick productClick = dtoToEntity(productClickDTO);
            productClickList.add(productClick);
        }
        return productClickList;
    }

    @Override
    public List<ProductClickDTO> entityListToDTOList(List<ProductClick> productClicks) {
        List<ProductClickDTO> productClickDTOList = new ArrayList<ProductClickDTO>();
        for (ProductClick productClick : productClicks) {
            ProductClickDTO productClickDTO = entityToDTO(productClick);
            productClickDTOList.add(productClickDTO);
        }
        return productClickDTOList;
    }

    @Override
    public PageDTO<ProductClickDTO> pageEntityToPageDTO(Page<ProductClick> pageEntity) {
        PageDTO<ProductClickDTO> pageDTO = new PageDTO<ProductClickDTO>();
        List<ProductClick> entityList = pageEntity.toList();
        List<ProductClickDTO> productClickDTOList = entityListToDTOList(entityList);
        pageDTO.setStart(pageEntity, productClickDTOList);

        return pageDTO;
    }
}
