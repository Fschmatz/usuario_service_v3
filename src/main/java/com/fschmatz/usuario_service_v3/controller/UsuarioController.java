package com.fschmatz.usuario_service_v3.controller;

import com.fschmatz.usuario_service_v3.entity.Usuario;
import com.fschmatz.usuario_service_v3.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@Transactional
@RequestMapping("/usuario")
public class UsuarioController {

    UsuarioRepository repository;

    @GetMapping("/criarConta")
    public String criarConta() {
        return "criarConta";
    }

    @GetMapping("/listarUsuarios")
    public ModelAndView listarUsuarios(){
        ModelAndView mv = new ModelAndView("listarUsuarios");
        Iterable<Usuario> usuarios = repository.findAll();
        mv.addObject("usuarios", usuarios);
        return mv;
    }

    @GetMapping("/homeUsuario/{id}")
    public ModelAndView homeUsuario(@PathVariable("id") Integer id){
        ModelAndView mv = new ModelAndView("homeUsuario");
        Usuario usuario =  repository.getById(id);
        mv.addObject("usuario", usuario);
        return mv;
    }

    //NOVA CONTA
    @PostMapping("/add")
   public String form(@Validated Usuario usuario, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Verifique os campos!");
            return "redirect:/error";
        }
        repository.save(usuario);
        attributes.addFlashAttribute("mensagem", "Sucesso!");
        return "redirect:http://localhost:9090/usuario/listarUsuarios";
    }

    ///////////////////
    //EDITAR CONTA

    @RequestMapping(value="/edit/{id}",method = RequestMethod.GET)
    public ModelAndView editUsuario(@PathVariable("id") Integer id){
        ModelAndView mv = null;
        Optional<Usuario> usuario = repository.findById(id);
        if(usuario.isPresent()){
            mv = new ModelAndView("editarConta");
            mv.addObject("usuario", usuario.get());
        }
        return mv;
    }

    @RequestMapping("/edit/{id}")
    public String editUsuario(@PathVariable("id") Integer id, @Validated Usuario usuario, BindingResult result, Model model){

        Optional<Usuario> usuarioOptional = repository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuarioSalvo = usuarioOptional.get();
            BeanUtils.copyProperties(usuario, usuarioSalvo, "id_usuario");
            repository.save(usuarioSalvo);
        }

        return "redirect:https://g1.globo.com/";
    }












    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        try {
            List<Usuario> items = new ArrayList<Usuario>();

            repository.findAll().forEach(items::add);

            if (items.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Usuario> getById(@PathVariable("id") Integer id) {
        Optional<Usuario> existingItemOptional = repository.findById(id);

        if (existingItemOptional.isPresent()) {
            return new ResponseEntity<>(existingItemOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario Usuario) {
        try {
            Usuario savedItem = repository.save(Usuario);
            System.out.println(savedItem.toString());
            return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Usuario> update(@PathVariable("id") Integer id, @RequestBody Usuario Usuario) {
        Optional<Usuario> existingUsuarioOptional = repository.findById(id);
        if (existingUsuarioOptional.isPresent()) {
            Usuario UsuarioSalva = existingUsuarioOptional.get();
            BeanUtils.copyProperties(Usuario, UsuarioSalva, "id_Usuario");
            return new ResponseEntity<>(repository.save(UsuarioSalva), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Integer id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

}