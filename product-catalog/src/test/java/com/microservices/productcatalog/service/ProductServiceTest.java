package com.microservices.productcatalog.service;

import static com.microservices.productcatalog.utils.ProductUtils.createFakeProductDTO;
import static com.microservices.productcatalog.utils.ProductUtils.createFakeProductModel;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
//import org.springframework.dao.DataIntegrityViolationException;

//import com.management.peopleApi.service.exceptions.ObjectNotFoundException;
import com.microservices.productcatalog.dto.ProductDTO;
import com.microservices.productcatalog.model.Product;
import com.microservices.productcatalog.repository.ProductRepository;
import com.microservices.productcatalog.service.exceptions.ObjectNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
	
	@InjectMocks
	private ProductService productService;
	
	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	@Test
	void create_ReturnsProductDTO_WhenSuccessful() {
		ProductDTO productDTO = createFakeProductDTO();
		Product expectedProduct = createFakeProductModel();
        
        when(modelMapper.map(productDTO, Product.class)).thenReturn(expectedProduct);
        when(productRepository.save(any(Product.class))).thenReturn(expectedProduct);
        when(modelMapper.map(expectedProduct, ProductDTO.class)).thenReturn(productDTO);
        
        productDTO = productService.create(productDTO);
        
        assertEquals(1L, expectedProduct.getId());
        assertEquals(expectedProduct.getName(), productDTO.getName());
        assertEquals(expectedProduct.getAmount(), productDTO.getAmount());
	}
	
	@Test
	void findById_ReturnsProductDTO_WhenSuccessful() {
		ProductDTO productDTO = createFakeProductDTO();
		Product expectedProduct = createFakeProductModel();
		
		when(productRepository.findById(expectedProduct.getId())).thenReturn(Optional.of(expectedProduct));
		when(modelMapper.map(expectedProduct, ProductDTO.class)).thenReturn(productDTO);
		
		productDTO = productService.findById(1L);
		
        assertEquals(expectedProduct.getName(), productDTO.getName());
        assertEquals(expectedProduct.getAmount(), productDTO.getAmount());
	}
	
	@Test
	void findById_ThrowsObjectNotFoundException_WhenProductIsEmpty() {
		
		assertThrows(ObjectNotFoundException.class, () -> productService.findById(1L));
	}
	
	@Test
	void findAll_ReturnsListOfProductDTO_WhenSuccessful() {
		List<ProductDTO> productsDTO = Collections.singletonList(createFakeProductDTO());
		List<Product> expectedProducts = Collections.singletonList(createFakeProductModel());
		
		when(productRepository.findAll()).thenReturn(expectedProducts);
		when(modelMapper.map(expectedProducts.get(0), ProductDTO.class)).thenReturn(productsDTO.get(0));
		
		productsDTO = productService.findAll();
		
		assertEquals(productsDTO.size(), 1);
		assertEquals(expectedProducts.get(0).getId(), productsDTO.get(0).getId());
		assertEquals(expectedProducts.get(0).getName(), productsDTO.get(0).getName());
		assertEquals(expectedProducts.get(0).getAmount(), productsDTO.get(0).getAmount());
	}
	
	@Test
	void findAll_ReturnsEmptyList_WhenNoProductExists() {
		List<ProductDTO> productsDTO;
		List<Product> expectedProducts = Collections.emptyList();
		
		when(productRepository.findAll()).thenReturn(expectedProducts);
		
		productsDTO = productService.findAll();
		
		assertEquals(0, productsDTO.size());
	}
	
	@Test
	void update_ReturnsUpdatedProductDTO_WhenSuccessful() {
		ProductDTO productDTO = createFakeProductDTO();
		Product expectedProduct = createFakeProductModel();
		
		productDTO.setName("Updated Product");
		expectedProduct.setName("Updated Product");
		
		when(productRepository.findById(1L)).thenReturn(Optional.of(expectedProduct));
		when(modelMapper.map(productDTO, Product.class)).thenReturn(expectedProduct);
		when(productRepository.save(any(Product.class))).thenReturn(expectedProduct);
		when(modelMapper.map(expectedProduct, ProductDTO.class)).thenReturn(productDTO);
		
		productDTO = productService.update(1L, productDTO);
		
		assertEquals(1L, expectedProduct.getId());
		assertEquals(expectedProduct.getName(), productDTO.getName());
		assertEquals("Updated Product", productDTO.getName());
		assertEquals(expectedProduct.getAmount(), productDTO.getAmount());
	}
	
	@Test
	void updateAmount_ReturnsUpdatedProductDTO_WhenSuccessful() {
		ProductDTO productDTO = createFakeProductDTO();
		Product expectedProduct = createFakeProductModel();
		
		Integer quantity = 3;
		Integer oldAmount = productDTO.getAmount();
		
		expectedProduct.setAmount(expectedProduct.getAmount() - quantity);
		
		when(productRepository.findById(1L)).thenReturn(Optional.of(expectedProduct));
		when(modelMapper.map(productDTO, Product.class)).thenReturn(expectedProduct);
		when(productRepository.save(any(Product.class))).thenReturn(expectedProduct);
		when(modelMapper.map(expectedProduct, ProductDTO.class)).thenReturn(productDTO);
		
		productDTO = productService.updateAmount(1L, quantity);
		
		assertEquals(1L, expectedProduct.getId());
		assertEquals(expectedProduct.getName(), productDTO.getName());
		assertEquals(expectedProduct.getAmount(), productDTO.getAmount());
		assertEquals(oldAmount - quantity, productDTO.getAmount());
	}
	
	@Test
	void delete_ReturnsVoid_WhenSuccessful() {
		Product expectedProduct = createFakeProductModel();
		
		when(productRepository.findById(1L)).thenReturn(Optional.of(expectedProduct));
		doNothing().when(productRepository).deleteById(expectedProduct.getId());
		
		productService.delete(1L);
	}

}
