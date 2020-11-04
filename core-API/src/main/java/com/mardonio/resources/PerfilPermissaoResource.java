package com.mardonio.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mardonio.domain.Perfil;
import com.mardonio.domain.PerfilPermissao;
import com.mardonio.domain.PerfilPermissaoPK;
import com.mardonio.domain.Permissao;
import com.mardonio.dto.PerfilPermissaoDTO;
import com.mardonio.services.PerfilPermissaoService;

@RestController
@RequestMapping(value="/perfilpermissao")
public class PerfilPermissaoResource {

	@Autowired
	private PerfilPermissaoService service;
	
	@GetMapping("/{perfilId}/{permissaoId}")
	public ResponseEntity<PerfilPermissaoDTO> find(@PathVariable Integer perfilId, @PathVariable Integer permissaoId) {
		Perfil pf = new Perfil(perfilId, null);
		Permissao pm = new Permissao(permissaoId, null, null, null);
		PerfilPermissaoPK id = new PerfilPermissaoPK(pf, pm);
		PerfilPermissao obj = service.find(id);
		PerfilPermissaoDTO ppDTO = new PerfilPermissaoDTO(obj);
		return ResponseEntity.ok().body(ppDTO);
	}

	@GetMapping
	public ResponseEntity<List<PerfilPermissaoDTO>> findAll() {
		List<PerfilPermissao> list = service.findAll();
		List<PerfilPermissaoDTO> listDTO = list.stream().map(p -> new PerfilPermissaoDTO(p)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody PerfilPermissaoDTO objDTO) {
		PerfilPermissao obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{perfilId}/{permissaoId}")
	public ResponseEntity<Void> update(@RequestBody PerfilPermissaoDTO objDTO, @PathVariable Integer perfilId, @PathVariable Integer permissaoId){
		PerfilPermissao obj = service.fromDTO(objDTO);
		Perfil pf = new Perfil(perfilId, null);
		Permissao pm = new Permissao(permissaoId, null, null, null);
		PerfilPermissaoPK id = new PerfilPermissaoPK(pf, pm);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{perfilId}/{permissaoId}")
	public ResponseEntity<Void> delete(@PathVariable Integer perfilId, @PathVariable Integer permissaoId){
		Perfil pf = new Perfil(perfilId, null);
		Permissao pm = new Permissao(permissaoId, null, null, null);
		PerfilPermissaoPK id = new PerfilPermissaoPK(pf, pm);
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
