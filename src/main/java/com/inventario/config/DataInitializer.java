package com.inventario.config;

import com.inventario.model.Usuario;
import com.inventario.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        crearUsuarioSiNoExiste(
                "admin",
                "admin123",
                "Administrador del Sistema",
                "admin@inventario.com",
                "ROLE_ADMIN"
        );

        crearUsuarioSiNoExiste(
                "empleado",
                "empleado123",
                "Carlos Gómez",
                "empleado@inventario.com",
                "ROLE_USER"
        );

        crearUsuarioSiNoExiste(
                "profesor",
                "uceva2026",
                "David Steven Durán Vallejo",
                "dduran@uceva.edu.co",
                "ROLE_ADMIN"
        );
    }

    private void crearUsuarioSiNoExiste(String username, String rawPassword, String nombre, String email, String rol) {
        if (!usuarioRepository.existsByUsername(username)) {
            Usuario usuario = new Usuario(
                    username,
                    passwordEncoder.encode(rawPassword),
                    nombre,
                    email,
                    rol
            );
            usuarioRepository.save(usuario);
        }
    }
}
