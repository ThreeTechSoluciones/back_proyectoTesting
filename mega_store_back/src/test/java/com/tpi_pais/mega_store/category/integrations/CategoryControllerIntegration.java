package com.tpi_pais.mega_store.category.integrations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tpi_pais.mega_store.products.dto.CategoriaDTO;
import com.tpi_pais.mega_store.products.model.Categoria;
import com.tpi_pais.mega_store.products.repository.CategoriaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerIntegration {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Test
    void createCategory() throws Exception {
        CategoriaDTO request = new CategoriaDTO(1, "Ofertas", null);
        this.mockMvc.perform(
                post("/products/categoria")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request))
        ).andExpect(status().isOk());
        Optional<Categoria> categoria = categoriaRepository.findById(1);
        Assertions.assertFalse(categoria.get().esEliminado());
    }

    @BeforeEach
    void setUp() {
        // Creamos la categoría con ID 10 y nombre "Medias"
        Categoria categoria = new Categoria(10, "Medias", null);
        categoriaRepository.save(categoria);
    }

    @Test
    void editCategory() throws Exception {
        CategoriaDTO request = new CategoriaDTO(10, "Medias Largas", null);
        this.mockMvc.perform(
                put("/products/categoria/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request))
        ).andExpect(status().isOk());
        Optional<Categoria> categoria = categoriaRepository.findById(10);
        Assertions.assertTrue(categoria.isPresent());
        Assertions.assertEquals("Medias Largas", categoria.get().getNombre());
    }
}
