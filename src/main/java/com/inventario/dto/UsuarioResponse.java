package com.inventario.dto;

import com.inventario.model.Usuario;

public class UsuarioResponse {

    private Long id;
    private String username;
    private String nombre;
    private String email;
    private String rol;

    public UsuarioResponse() {}

    public UsuarioResponse(Usuario usuario) {
        this.id = usuario.getId();
        this.username = usuario.getUsername();
        this.nombre = usuario.getNombre();
        this.email = usuario.getEmail();
        this.rol = usuario.getRol();
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getRol() { return rol; }
}
