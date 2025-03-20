package com.tpi_pais.mega_store.auth.service;

import com.tpi_pais.mega_store.auth.dto.PerfilDTO;
import com.tpi_pais.mega_store.auth.dto.UsuarioDTO;
import com.tpi_pais.mega_store.auth.model.Sesion;
import com.tpi_pais.mega_store.auth.model.Usuario;
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    public List<UsuarioDTO> listar();

    public Usuario buscarPorId(Integer id);

    public Usuario buscarPorEmail (String email);

    public UsuarioDTO guardar(UsuarioDTO model);

    public Usuario guardar(Usuario model);

    public void eliminar(Usuario model);

    public void recuperar(Usuario model);

    public Boolean checkPassword (Usuario model, String password);

    public void setPassword(Usuario model, String password);

    public Usuario buscarEliminadoPorId (Integer id);

    public Usuario buscarEliminadoPorEmail (String email);

    public void verificarAtributos (UsuarioDTO modelDTO);

    public void verificarNombre (String nombre,String metodo);

    public void verificarEmail (String email);

    public boolean emailUtilizado (String email);

    public void verificarTelefono (String telefono, String metodo);

    public void verificarDireccion (String direccion, String metodo);

    public void verificarRol (Integer rolId);

    public void verificarPassword (String password);

    public Usuario crearUsuario (UsuarioDTO modelDTO);

    public void enviarCodigoVerificacion (String email, String codigoVerificacion);

    public void verificarCodigoVerificacion (String email, String codigoVerificacion);

    public Sesion login (UsuarioDTO usuarioDTO);

    public void reenviarCodigo (String email);

    public void recuperarContrasena (String email);

    public boolean validarCodigoRecuperacion (String email, String codigoRecuperacion);

    public void restablecerContrasena (String email, String contrasena, String confirmarContrasena);

    public PerfilDTO getPerfil(Integer id);
}
