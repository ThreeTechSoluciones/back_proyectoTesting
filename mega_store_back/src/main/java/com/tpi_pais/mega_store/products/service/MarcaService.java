package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.NotFoundException;
import com.tpi_pais.mega_store.products.dto.MarcaDTO;
import com.tpi_pais.mega_store.products.dto.MarcaDTO;
import com.tpi_pais.mega_store.products.mapper.MarcaMapper;
import com.tpi_pais.mega_store.products.model.Marca;
import com.tpi_pais.mega_store.products.model.Marca;
import com.tpi_pais.mega_store.products.repository.MarcaRepository;
import com.tpi_pais.mega_store.utils.ExpresionesRegulares;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MarcaService implements IMarcaService {

    private final MarcaRepository modelRepository;

    public MarcaService(MarcaRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public List<MarcaDTO> listar() {
        List<Marca> marcas = modelRepository.findByFechaEliminacionIsNullOrderByIdAsc();
        return marcas.stream().map(MarcaMapper::toDTO).toList();
    }

    @Override
    public Marca buscarPorId(Integer id) {
        Optional<Marca> model = modelRepository.findByIdAndFechaEliminacionIsNull(id);
        if (model.isEmpty()) {
            throw new NotFoundException("La marca con id " + id + " no existe o se encuentra eliminada.");
        }
        return model.get();
    }

    @Override
    public Marca buscarEliminadoPorId(Integer id) {
        Optional<Marca> model = modelRepository.findByIdAndFechaEliminacionIsNotNull(id);
        if (model.isEmpty()) {
            throw new NotFoundException("La marca con id " + id + " no existe o no se encuentra eliminda.");
        }
        return model.get();
    }
    @Override
    public Marca buscarPorNombre(String nombre) {
        return modelRepository.findByNombre(nombre).orElse(null);
    }

    

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MarcaDTO guardar(MarcaDTO modelDTO) {
        Marca model = MarcaMapper.toEntity(modelDTO);
        return MarcaMapper.toDTO(modelRepository.save(model));
    }
    @Override
    @Transactional (rollbackFor = Exception.class)
    public Marca guardar(Marca model) {
        return modelRepository.save(model);
    }

    @Override
    @Transactional (rollbackFor = Exception.class)
    public void eliminar(Marca model) {

        model.eliminar();
        modelRepository.save(model);
    }
    @Override
    @Transactional (rollbackFor = Exception.class)
    public void recuperar(Marca model) {
        model.recuperar();
        modelRepository.save(model);
    }

    @Override
    public MarcaDTO verificarAtributos (MarcaDTO marcaDTO) {
        if (marcaDTO.noTieneNombre()) {
            throw new BadRequestException("La marca no tiene nombre");
        }
        ExpresionesRegulares expReg = new ExpresionesRegulares();
        if (!expReg.verificarCaracteres(marcaDTO.getNombre())){
            throw new BadRequestException("El nombre enviado contiene caracteres no permitidos.");
        }
        if (!expReg.verificarTextoConEspacios(marcaDTO.getNombre())){
            marcaDTO.setNombre(expReg.corregirCadena(marcaDTO.getNombre()));
            if (Objects.equals(marcaDTO.getNombre(), "")){
                throw new BadRequestException("El nombre tiene un formato incorrecto");
            }
        }
        marcaDTO.capitalizarNombre();
        return marcaDTO;
    }

    @Override
    public boolean marcaExistente(String nombre) {
        return modelRepository.findByNombre(nombre).isPresent();
    }
}
