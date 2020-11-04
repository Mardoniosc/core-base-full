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

import com.mardonio.domain.HistoricoLog;
import com.mardonio.dto.HistoricoLogDTO;
import com.mardonio.services.HistoricoLogService;

@RestController
@RequestMapping(value="/historicoslog")
public class HistoricoLogResource {
	
	@Autowired
	private HistoricoLogService service;
  
	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
		HistoricoLog obj = service.find(id);
		return ResponseEntity.ok(obj);
	}


	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody HistoricoLogDTO objDTO) {
		HistoricoLog obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody HistoricoLogDTO objDTO, @PathVariable Integer id){
		HistoricoLog obj = service.fromDTO(objDTO);
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
	public ResponseEntity<List<HistoricoLogDTO>> findAll() {
		List<HistoricoLog> list = service.findAll();
		List<HistoricoLogDTO> listDTO = list.stream().map(h -> new HistoricoLogDTO(h)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<HistoricoLogDTO>> findPage(
				@RequestParam(value = "page", defaultValue = "0" ) Integer page,
				@RequestParam(value = "linesPerPage", defaultValue = "24" ) Integer linesPerPage,
				@RequestParam(value = "orderBy", defaultValue = "dataAcesso" ) String orderBy,
				@RequestParam(value = "direction", defaultValue = "ASC" ) String direction
			) {
		Page<HistoricoLog> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<HistoricoLogDTO> listDTO = list.map(p -> new HistoricoLogDTO(p));
		return ResponseEntity.ok().body(listDTO);
	}

}
