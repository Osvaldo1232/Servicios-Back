package com.primaria.app.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.primaria.app.Model.Estatus;
import com.primaria.app.Model.Estudiante;
import com.primaria.app.Model.Profesor;
import com.primaria.app.Model.Usuario;
import com.primaria.app.exception.BusinessException;
import com.primaria.app.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Usuario save(Usuario usuario) {
        List<String> errores = new ArrayList<>(); // 🧮 Lista para acumular errores

        // 🔍 Validar correo duplicado
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            errores.add("El correo ya está en uso");
        }

        // 🔍 Validar matrícula si es estudiante
        if (usuario instanceof Estudiante estudiante) {
            boolean existe = usuarioRepository.findAll().stream()
                    .filter(u -> u instanceof Estudiante)
                    .map(u -> (Estudiante) u)
                    .anyMatch(u -> u.getMatricula().equalsIgnoreCase(estudiante.getMatricula()));
            if (existe) {
                errores.add("La matrícula ya está en uso");
            }
        }

        // 🔍 Validar RFC y Clave Presupuestal si es profesor
        if (usuario instanceof Profesor profesor) {
            boolean rfcExiste = usuarioRepository.findAll().stream()
                    .filter(u -> u instanceof Profesor)
                    .map(u -> (Profesor) u)
                    .anyMatch(u -> u.getRfc() != null && u.getRfc().equalsIgnoreCase(profesor.getRfc()));

            if (rfcExiste) {
                errores.add("El RFC ya está en uso");
            }

            boolean claveExiste = usuarioRepository.findAll().stream()
                    .filter(u -> u instanceof Profesor)
                    .map(u -> (Profesor) u)
                    .anyMatch(u -> u.getClavePresupuestal() != null &&
                                   u.getClavePresupuestal().equalsIgnoreCase(profesor.getClavePresupuestal()));

            if (claveExiste) {
                errores.add("La clave presupuestal ya está en uso");
            }
        }

        // 🚨 Si hubo errores, los acumulamos en un solo mensaje
        if (!errores.isEmpty()) {
            String mensaje = String.join(" | ", errores);
            throw new BusinessException(1000, "Errores de validación: " + mensaje);
        }

        // 🔐 Hashear la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findById(String id) {
        return usuarioRepository.findById(id);
    }

    public Usuario update(Usuario usuario, String nuevaPassword) {
        if (nuevaPassword != null && !nuevaPassword.isBlank()) {
            usuario.setPassword(passwordEncoder.encode(nuevaPassword));
        }
        return usuarioRepository.save(usuario);
    }
    public Usuario authenticate(String email, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            throw new BusinessException(401, "Credenciales inválidas");
        }

        Usuario usuario = usuarioOpt.get();

        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new BusinessException(401, "Credenciales inválidas");
        }

        if (usuario.getEstatus() == null || usuario.getEstatus() != Estatus.ACTIVO) {
            throw new BusinessException(403, "Usuario inactivo, contacte al administrador");
        }

        return usuario;
    }



    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Object buscarUsuarioPorId(String id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException(1000, "Usuario no encontrado"));
    }
    
    public boolean actualizarEstatus(String usuarioId, Estatus estatus) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setEstatus(estatus);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }
}
