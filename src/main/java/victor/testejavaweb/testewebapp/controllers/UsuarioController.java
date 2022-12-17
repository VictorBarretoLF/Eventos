package victor.testejavaweb.testewebapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import victor.testejavaweb.testewebapp.domain.Evento;
import victor.testejavaweb.testewebapp.domain.Usuario;
import victor.testejavaweb.testewebapp.repositories.EventoRepository;
import victor.testejavaweb.testewebapp.repositories.UsuarioRepository;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    EventoRepository eventoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/usuarios")
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        Usuario novoUsuario = new Usuario(usuario.getNome());
        usuarioRepository.save(novoUsuario);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }

    @GetMapping("/usuarios/eventos-by-usuario/{usuarioId}")
    public ResponseEntity<List<Evento>> getAllTagsByTutorialId(@PathVariable(value = "usuarioId") Long usuarioId) {

        if (!eventoRepository.existsById(usuarioId)) {
            return ResponseEntity.notFound().build();
        }

        List<Evento> eventos = eventoRepository.findEventosByUsuariosId(usuarioId);

        return new ResponseEntity<>(eventos, HttpStatus.OK);
    }
}