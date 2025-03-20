package com.tpi_pais.mega_store.products.mapper;

import com.tpi_pais.mega_store.products.dto.DetalleVentaDTO;
import com.tpi_pais.mega_store.products.dto.VentaDTO;
import com.tpi_pais.mega_store.products.dto.VentaUsuarioDTO;
import com.tpi_pais.mega_store.products.model.Venta;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class VentaMapper {

    private VentaMapper() {}

    public static VentaDTO toDTO(Venta model, ArrayList<DetalleVentaDTO> detalles) {
        VentaDTO dto = new VentaDTO();
        dto.setId(model.getId());
        dto.setFechaVenta(model.getFechaCreacion());
        dto.setUsuarioId(model.getUsuario().getId());
        dto.setUsuario(model.getUsuario().getNombre());
        dto.setTotalVenta(model.getTotalVenta());
        dto.setDetalles(detalles);
        return dto;
    }
    
    public static Venta toEntity(VentaDTO dto) {
        Venta entity = new Venta();
        entity.setId(dto.getId());
        entity.setFechaCreacion(dto.getFechaVenta());
        entity.setUsuario(null);
        entity.setTotalVenta(dto.getTotalVenta());
        return entity;
    }

    public static VentaUsuarioDTO toDTOUsuario(Venta model) {
        VentaUsuarioDTO dto = new VentaUsuarioDTO();
        dto.setId(model.getId());
        dto.setFechaVenta(model.getFechaCreacion());
        dto.setTotalVenta(model.getTotalVenta());
        return dto;
    }
}
