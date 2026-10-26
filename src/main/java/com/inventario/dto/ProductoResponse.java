package com.inventario.dto;

import com.inventario.model.Producto;
import java.math.BigDecimal;

public class ProductoResponse {

    private Long id;
    private String nombre;
    private String codigo;
    private String descripcion;
    private BigDecimal precio;
    private Integer cantidad;
    private String categoria;
    private String imagenUrl;

    public ProductoResponse() {}

    public ProductoResponse(Producto p) {
        this.id = p.getId();
        this.nombre = p.getNombre();
        this.codigo = p.getCodigo();
        this.descripcion = p.getDescripcion();
        this.precio = p.getPrecio();
        this.cantidad = p.getCantidad();
        this.categoria = p.getCategoria();
        this.imagenUrl = p.getImagenUrl();
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCodigo() { return codigo; }
    public String getDescripcion() { return descripcion; }
    public BigDecimal getPrecio() { return precio; }
    public Integer getCantidad() { return cantidad; }
    public String getCategoria() { return categoria; }
    public String getImagenUrl() { return imagenUrl; }
}
