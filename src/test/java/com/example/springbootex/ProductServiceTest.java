package com.example.springbootex;

import com.example.springbootex.entity.Product;
import com.example.springbootex.repository.ProductRepository;
import com.example.springbootex.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("Given there are available products, when retrieving a product by id, then the correct one is retrieved")
    void givenThereAreAvailableProducts_whenRetrievingAProductById_thenTheCorrectProductIsRetrieved() {
        final int productId = 5;
        final Product product = mock(Product.class);
        final String productName = "Zuzu";

        when(product.getName()).thenReturn(productName);
        when(product.getId()).thenReturn(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        final Product productResulted = productService.get(productId);

        assertNotNull(productResulted);
        assertThat(productResulted.getName(), is(productName));
        assertThat(productResulted.getId(), not(0));
        assertThat(productResulted.getId(), is(productId));
    }

    @Test
    @DisplayName("Given there are no available products, when retrieving products, then no products are retrieved")
    void givenThereAreNoAvailableProducts_whenRetrievingProducts_thenNoProductsAreRetrieved() {
        when(productRepository.findAll()).thenReturn(new ArrayList<>());

        final List<Product> productsResulted = productService.getAll();

        assertNotNull(productsResulted);
        assertThat(productsResulted.size(), is(0));
    }
}
