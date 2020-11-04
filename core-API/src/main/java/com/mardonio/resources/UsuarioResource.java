package com.mardonio.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mardonio.domain.Usuario;
import com.mardonio.dto.UsuarioDTO;
import com.mardonio.dto.UsuarioNewDTO;
import com.mardonio.services.UsuarioService;

@RestController
@RequestMapping(value="/usuarios")
public class UsuarioResource {
	
	@Autowired
	private UsuarioService service;
	
	//@Autowired
	//private EmailService emailService;
  
	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Usuario obj = service.find(id);
		obj.setSenha(null);
		return ResponseEntity.ok(obj);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody UsuarioNewDTO objDTO) {
		Usuario obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		//Usuario usuarioObj = service.find(obj.getId());
		// emailService.sendOrderConfirmationHtmlEmail(usuarioObj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PatchMapping("{campo}/{id}")
	public ResponseEntity<Void> updatePatch(@RequestBody UsuarioDTO usuario, @PathVariable Integer id, @PathVariable String campo){
		service.updatePatch(campo, id, usuario);
		return ResponseEntity.noContent().build();

	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody UsuarioDTO objDTO, @PathVariable Integer id){
		Usuario obj = service.fromDTO(objDTO); 
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<UsuarioDTO>> findAll() {
		List<Usuario> list = service.findAll();
		List<UsuarioDTO> listDTO = list.stream().map(u -> {
			u.setSenha(null);
			return new UsuarioDTO(u);
		}).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping("/page")
	public ResponseEntity<Page<UsuarioDTO>> findPage(
				@RequestParam(value = "page", defaultValue = "0" ) Integer page,
				@RequestParam(value = "linesPerPage", defaultValue = "24" ) Integer linesPerPage,
				@RequestParam(value = "orderBy", defaultValue = "nome" ) String orderBy,
				@RequestParam(value = "direction", defaultValue = "ASC" ) String direction
			) {
		Page<Usuario> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<UsuarioDTO> listDTO = list.map(u -> {
			u.setSenha(null);
			return new UsuarioDTO(u);
		});
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping("/email")
	public ResponseEntity<Usuario> find(@RequestParam String email) {
		Usuario obj = service.findByEmail(email);
		return ResponseEntity.ok(obj);
	}

	@PostMapping(value = "/picture/{id}")
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name="file") MultipartFile file, 
													@PathVariable Integer id) {
		service.uploadProfilePicture(file, id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/picture/{id}")
	public ResponseEntity<Void> downloadProfilePicture(@PathVariable Integer id) {
		service.salvaImgDoBancoNoServer(id);;
		return ResponseEntity.ok().build();
	}
	
}
