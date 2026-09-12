package com.dcl.service;

import java.util.List;

import com.dcl.dto.ProductDto;
import com.dcl.request.AddProduct;
import com.dcl.request.UpdateRequest;

public interface ProductService {

	ProductDto addProduct(AddProduct request);
	
	ProductDto getProductById(Integer productId);
	
	List<ProductDto> getAllProducts();
	
	ProductDto updateProduct(Integer productId, UpdateRequest request);
	
	void deleteProductById(Integer productId);
}
