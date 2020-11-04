package com.mardonio.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mardonio.domain.HistoricoAcesso;
import com.mardonio.dto.HistoricoAcessoDTO;
import com.mardonio.services.HistoricoAcessoService;

@RestController
@RequestMapping(value="/historicoacesso")
public class HistoricoAcessoResource {
	
	@Autowired
	private HistoricoAcessoService service;
  
	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
		HistoricoAcesso obj = service.find(id);
		return ResponseEntity.ok(obj);
	}


	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody HistoricoAcessoDTO objDTO) {
		HistoricoAcesso obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody HistoricoAcessoDTO objDTO, @PathVariable Integer id){
		HistoricoAcesso obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<HistoricoAcessoDTO>> findAll() {
		List<HistoricoAcesso> list = service.findAll();
		List<HistoricoAcessoDTO> listDTO = list.stream().map(h -> new HistoricoAcessoDTO(h)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<HistoricoAcessoDTO>> findPage(
				@RequestParam(value = "page", defaultValue = "0" ) Integer page,
				@RequestParam(value = "linesPerPage", defaultValue = "24" ) Integer linesPerPage,
				@RequestParam(value = "orderBy", defaultValue = "dataAcesso" ) String orderBy,
				@RequestParam(value = "direction", defaultValue = "ASC" ) String direction
			) {
		Page<HistoricoAcesso> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<HistoricoAcessoDTO> listDTO = list.map(p -> new HistoricoAcessoDTO(p));
		return ResponseEntity.ok().body(listDTO);
	}

}
