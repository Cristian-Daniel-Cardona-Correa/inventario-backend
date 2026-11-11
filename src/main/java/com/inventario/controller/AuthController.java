package com.inventario.controller;

import com.inventario.dto.LoginRequest;
import com.inventario.dto.LoginResponse;
import com.inventario.dto.RegistroRequest;
import com.inventario.dto.UsuarioResponse;
import com.inventario.model.Usuario;
import com.inventario.repository.UsuarioRepository;
import com.inventario.security.CustomUserDetailsService;
import com.inventario.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Endpoints para inicio de sesión, registro y verificación de token JWT")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public AuthController(
            AuthenticationManager authenticationManager,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            CustomUserDetailsService userDetailsService
    ) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Valida las credenciales del usuario y retorna un token JWT válido por 24 horas")
    @ApiResponse(responseCode = "200", description = "Autenticación exitosa y retorno del token JWT")
    @ApiResponse(responseCode = "401", description = "Credenciales incorrectas")
    @ApiResponse(responseCode = "422", description = "Error de validación en los campos")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        // Autenticar credenciales
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Obtener usuario de la base de datos
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .or(() -> usuarioRepository.findByEmail(request.getUsername()))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getUsername());

        // Generar claims adicionales en el token
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("email", usuario.getEmail());
        extraClaims.put("nombre", usuario.getNombre());
        extraClaims.put("rol", usuario.getRol());

        String jwt = jwtService.generateToken(userDetails, extraClaims);

        LoginResponse response = new LoginResponse(
                jwt,
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getNombre(),
                usuario.getRol()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/registro")
    @Operation(summary = "Registrar nuevo usuario", description = "Crea una nueva cuenta de usuario en el sistema con contraseña encriptada con BCrypt")
    @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente")
    @ApiResponse(responseCode = "422", description = "Nombre de usuario o correo ya registrados")
    public ResponseEntity<UsuarioResponse> registro(@Valid @RequestBody RegistroRequest request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario '" + request.getUsername() + "' ya está registrado");
        }
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El correo '" + request.getEmail() + "' ya está registrado");
        }

        Usuario usuario = new Usuario(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getNombre(),
                request.getEmail(),
                "ROLE_USER"
        );

        Usuario guardado = usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioResponse(guardado));
    }

    @GetMapping("/perfil")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Obtener perfil del usuario actual", description = "Retorna los datos del usuario autenticado a partir del token JWT enviado en Authorization")
    @ApiResponse(responseCode = "200", description = "Datos del usuario obtenidos exitosamente")
    @ApiResponse(responseCode = "401", description = "Token JWT ausente o inválido")
    public ResponseEntity<UsuarioResponse> perfil() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = auth.getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .or(() -> usuarioRepository.findByEmail(username))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return ResponseEntity.ok(new UsuarioResponse(usuario));
    }
}
