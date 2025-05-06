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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CategoryControllerIntegration {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    void createCategory() throws Exception { //* Registrar nueva categoría válida (Abril)
        CategoriaDTO request = new CategoriaDTO(1, "Ofertas", null);
        this.mockMvc.perform(
                post("/products/categoria")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request))
        ).andExpect(status().isOk());
        Optional<Categoria> categoria = categoriaRepository.findById(1);
        Assertions.assertFalse(categoria.get().esEliminado());
    }
    @Test
    @Sql("/scripts/INSERT_CATEGORY.sql")
    void editCategory() throws Exception { //editar nombre de una categoría existente (Ro)
        CategoriaDTO request = new CategoriaDTO(1, "Medias Largas", null);
        this.mockMvc.perform(
                put("/products/categoria/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request))
        ).andExpect(status().isOk());
        Optional<Categoria> categoria = categoriaRepository.findById(1);
        Assertions.assertTrue(categoria.isPresent());
        Assertions.assertEquals("Medias Largas", categoria.get().getNombre());
    }


    @Test //*
    @Sql("/scripts/INSERT_CATEGORY.sql") //Ocultar categoría al eliminarla (Lu)
    void deleteCategory() throws Exception {
        this.mockMvc.perform(
                delete("/products/categoria/1")
        ).andExpect(status().isOk());

        Assertions.assertTrue(categoriaRepository.findById(1).get().esEliminado());
    }








    @Test
    @Sql("/scripts/INSERT_CATEGORY.sql")
    void createCategory_butCategoryIsDeleted() throws Exception {
        CategoriaDTO request = new CategoriaDTO(3, "Zapatillas", null);
        MvcResult result = this.mockMvc.perform(
                        post("/products/categoria")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request))
                ).andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        Assertions.assertTrue(response.contains("Zapatillas"));
    }


    @Test
    @Sql("/scripts/INSERT_CATEGORY.sql") //*
    void createCategory_butNameAlreadyExists() throws Exception {
        CategoriaDTO request = new CategoriaDTO(1, "Remeras", null);
        MvcResult result = this.mockMvc.perform(
                        post("/products/categoria")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        Assertions.assertTrue(response.contains("Ya existe una categoria con ese nombre"));
    }


    @Test
    @Sql("/scripts/INSERT_PRODUCT.sql")
    void deleteCategory_butIsUsed() throws Exception {
        this.mockMvc.perform(
                delete("/products/categoria/1")
        ).andExpect(status().isBadRequest());

        Assertions.assertFalse(categoriaRepository.findById(1).get().esEliminado());
    }

}



