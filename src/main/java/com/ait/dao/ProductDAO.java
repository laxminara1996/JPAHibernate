package com.ait.dao;

import com.ait.entity.ProductEntity;

public interface ProductDAO {
	
	ProductEntity saveProduct(ProductEntity product);
	
	ProductEntity loadProductById(Integer productId);
	
	ProductEntity updateProductById(Integer productId, Double new_Unit_Price);
	
	void deleteProductById(Integer productId);
	
	void testEntityStates();

}
