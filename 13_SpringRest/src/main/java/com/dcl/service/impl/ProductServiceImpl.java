package com.dcl.service.impl;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.dcl.dto.ProductDto;
import com.dcl.entity.Product;
import com.dcl.exception.AppException;
import com.dcl.repo.ProductRepository;
import com.dcl.request.AddProduct;
import com.dcl.request.UpdateRequest;
import com.dcl.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository prepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public ProductDto addProduct(AddProduct request) {
		Product p=mapper.map(request, Product.class);
		p=prepo.save(p);
		ProductDto dto=mapper.map(p, ProductDto.class);
		return dto;
	}

	@Override
	public ProductDto getProductById(Integer productId) {
		Product p=prepo.findById(productId).orElse(null);
		
		if(p==null) {
			throw new AppException("Product Not Found!", HttpStatus.NOT_FOUND);
		}
		return mapper.map(p, ProductDto.class);
	}

	@Override
	public List<ProductDto> getAllProducts() {
		List<Product> productList=prepo.findAll();
		
		if(productList==null&&productList.isEmpty()) {
			throw new AppException("No Products found!", HttpStatus.NOT_FOUND);
		}
		
		List<ProductDto> pdtoList=productList.stream()
				   .map((p)->mapper.map(p, ProductDto.class))
				   .collect(Collectors.toList());    
		return pdtoList;
	}

	@Override
	public void deleteProductById(Integer productId) {
		Product p=prepo.findById(productId).orElseThrow(()->new AppException("Product Not Found!", HttpStatus.NOT_FOUND));
		prepo.deleteById(productId);
	}

	@Override
	public ProductDto updateProduct(Integer productId, UpdateRequest request) {
		Product existingProduct=prepo.findById(productId).orElseThrow(()->new AppException("Product Not Found!", HttpStatus.NOT_FOUND));
		mapper.map(request, existingProduct);
		Product afterUpdate=prepo.save(existingProduct);
		return mapper.map(afterUpdate, ProductDto.class);
	}

}
