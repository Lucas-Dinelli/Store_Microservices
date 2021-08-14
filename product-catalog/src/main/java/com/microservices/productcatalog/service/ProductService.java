package com.microservices.productcatalog.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.productcatalog.dto.ProductDTO;
import com.microservices.productcatalog.model.Product;
import com.microservices.productcatalog.repository.ProductRepository;
import com.microservices.productcatalog.service.exceptions.ObjectNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {
	
	private ProductRepository productRepository;
	
	private ModelMapper modelMapper;
	
	public ProductDTO create(ProductDTO productDTO) {
		Product product = modelMapper.map(productDTO, Product.class);
		productDTO = modelMapper.map(productRepository.save(product), ProductDTO.class);
		return productDTO;
	}
	
	public ProductDTO findById(Long id) {
		Optional<Product> optionalProduct = productRepository.findById(id);
		return modelMapper.map(optionalProduct.orElseThrow(() -> new ObjectNotFoundException("Product not found!")), ProductDTO.class);
	}
	
	public List<ProductDTO> findAll(){
		List<Product> products = productRepository.findAll();
		return products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).collect(Collectors.toList());
	}
	
	public ProductDTO update(Long id, ProductDTO productDTOUpdated) {
		findById(id);
		Product productToUpdate = modelMapper.map(productDTOUpdated, Product.class);
		productToUpdate.setId(id);
		Product productUpdated = productRepository.save(productToUpdate);
		return modelMapper.map(productUpdated, ProductDTO.class);
	}
	
	public ProductDTO updateAmount(Long id, Integer quantity) {
		ProductDTO productDTO = findById(id);
		Product productUpdated = new Product();
		Integer newAmount = productDTO.getAmount() - quantity;
		if(newAmount >= 0) {
			productDTO.setAmount(productDTO.getAmount() - quantity);
			productUpdated = productRepository.save(modelMapper.map(productDTO, Product.class));
		}
		return modelMapper.map(productUpdated, ProductDTO.class);
	}
	
	public void delete(Long id) {
		findById(id);
		productRepository.deleteById(id);
	}

}
