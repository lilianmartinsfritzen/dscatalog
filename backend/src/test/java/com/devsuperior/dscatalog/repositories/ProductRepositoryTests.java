package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {
	
	@Autowired
	private ProductRepository repository;
	
	private long existingId;
	private long nonExistingId;	
	private long countTotalProducts;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalProducts = 25;
	}
	
	@Test
	public void saveShouldPersistWithAutoincrementIdIsNull() {
		
		Product product = Factory.createProduct();
		product.setId(null);
		
		product = repository.save(product);
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts + 1, product.getId());
	}

	@Test
	public void findByIdShouldReturnNonEmptyObjectWhenIdExists() {
		
		// Act
		repository.findById(existingId);
		
		// Assert  // isPresente está testando se existe um obj dentro do Optional, como usamos assertTrue estamos testando se o objeto está presente.
		Optional<Product> result = repository.findById(existingId);
		Assertions.assertTrue(result.isPresent());
	}	
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		
		// Act
		repository.deleteById(existingId);
		
		// Assert  // isPresente está testando se existe um obj dentro do Optional, como usamos assertFalse estamos testando se o objeto não está presente.
		Optional<Product> result = repository.findById(existingId);
		Assertions.assertFalse(result.isPresent());
	}	
	
	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
		
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExistingId);
		});
	}
}
