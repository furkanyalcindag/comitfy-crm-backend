package com.comitfy.crm.app.repository;

import com.comitfy.crm.app.entity.Material;
import com.comitfy.crm.app.entity.Product;
import com.comitfy.crm.app.entity.ProductMaterial;
import com.comitfy.crm.util.common.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMaterialRepository extends BaseRepository<ProductMaterial> {

    List<ProductMaterial> findAllByProduct(Product product);

    ProductMaterial findByProductAndMaterial(Product product, Material material);

    Long deleteByProduct(Product product);
}
