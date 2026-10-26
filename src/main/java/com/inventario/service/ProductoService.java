package com.inventario.service;

import com.inventario.dto.ProductoRequest;
import com.inventario.dto.ProductoResponse;
import com.inventario.exception.ProductoNotFoundException;
import com.inventario.model.Producto;
import com.inventario.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductoResponse> listarTodos() {
        return productoRepository.findAll()
                .stream()
                .map(ProductoResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductoResponse obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(
                        "Producto con id " + id + " no encontrado"));
        return new ProductoResponse(producto);
    }

    public ProductoResponse crear(ProductoRequest request) {
        if (productoRepository.existsByCodigo(request.getCodigo())) {
            throw new IllegalArgumentException(
                    "Ya existe un producto con el código: " + request.getCodigo());
        }
        Producto producto = mapToEntity(request);
        return new ProductoResponse(productoRepository.save(producto));
    }

    public ProductoResponse actualizar(Long id, ProductoRequest request) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(
                        "Producto con id " + id + " no encontrado"));

        if (productoRepository.existsByCodigoAndIdNot(request.getCodigo(), id)) {
            throw new IllegalArgumentException(
                    "Ya existe un producto con el código: " + request.getCodigo());
        }

        producto.setNombre(request.getNombre());
        producto.setCodigo(request.getCodigo());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setCantidad(request.getCantidad());
        producto.setCategoria(request.getCategoria());

        return new ProductoResponse(productoRepository.save(producto));
    }

    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ProductoNotFoundException(
                    "Producto con id " + id + " no encontrado");
        }
        productoRepository.deleteById(id);
    }

    private Producto mapToEntity(ProductoRequest request) {
        Producto p = new Producto();
        p.setNombre(request.getNombre());
        p.setCodigo(request.getCodigo());
        p.setDescripcion(request.getDescripcion());
        p.setPrecio(request.getPrecio());
        p.setCantidad(request.getCantidad());
        p.setCategoria(request.getCategoria());
        return p;
    }
}
