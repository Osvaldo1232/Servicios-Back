package com.primaria.app.Service;
import java.util.Optional;


import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.primaria.app.Model.Director;
import com.primaria.app.Model.Estudiante;
import com.primaria.app.Model.Profesor;
import com.primaria.app.Model.Usuario;
import com.primaria.app.repository.UsuarioRepository;

@Service
public class UsuarioService {

	
    public final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UsuarioRepository getUsuarioRepository() {
        return usuarioRepository;
    }
    
    public Usuario save(Usuario usuario) {
        // Hashea la contraseña antes de guardar
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

    
    public Optional<Usuario> authenticate(String email, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            // Compara contraseña hasheada
            if (passwordEncoder.matches(password, usuario.getPassword())) {
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }
    
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    
    
    
    public Object buscarUsuarioPorId(String id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (usuario instanceof Estudiante) {
            return (Estudiante) usuario;
        } else if (usuario instanceof Profesor) {
            return (Profesor) usuario;
        } else if (usuario instanceof Director) {
            return (Director) usuario;
        } else {
            return usuario;
        }
    }
    
  

}
