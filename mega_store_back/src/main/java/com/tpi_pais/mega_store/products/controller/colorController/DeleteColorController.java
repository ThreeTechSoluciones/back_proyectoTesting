package com.tpi_pais.mega_store.products.controller.colorController;

import com.tpi_pais.mega_store.configs.SessionRequired;
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.model.Color;
import com.tpi_pais.mega_store.products.service.IColorService;
import com.tpi_pais.mega_store.products.service.IProductoService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class DeleteColorController {

    private final IColorService modelService;

    private final ResponseService responseService;

    private final IProductoService productoService;

    public DeleteColorController(IColorService modelService, ResponseService responseService, IProductoService productoService) {
        this.modelService = modelService;
        this.responseService = responseService;
        this.productoService = productoService;
    }

    @SessionRequired
    @DeleteMapping("/color/{id}")
    public ResponseEntity<ApiResponse<Object>>  eliminar(@PathVariable Integer id) {
        Color model = modelService.buscarPorId(id);
        if (productoService.tieneProductoAsociado(model.getId(), "color")) {
            throw new BadRequestException("El color tiene productos asociados, no se puede eliminar");
        }
        modelService.eliminar(model);
        return responseService.successResponse(model, "Objeto eliminado");
    }
}

