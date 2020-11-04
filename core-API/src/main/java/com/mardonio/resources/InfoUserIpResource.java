package com.mardonio.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mardonio.domain.InforUserIp;
import com.mardonio.dto.InforUserIpDTO;
import com.mardonio.services.InfoUserIpService;

@RestController
@RequestMapping(value="/infoUserIp")
public class InfoUserIpResource {
	
	@Autowired
	private InfoUserIpService service;
  
	@GetMapping("/{id}")
	public ResponseEntity<InforUserIp> find(@PathVariable Integer id) {
		InforUserIp obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<InforUserIp> insert(@PathVariable Integer id) {
		InforUserIp obj = new InforUserIp();
		obj = service.insert(id);
		
		obj = service.find(obj.getId());
		return ResponseEntity.ok().body(obj);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<InforUserIpDTO>> findAll() {
		List<InforUserIp> list = service.findAll();
		List<InforUserIpDTO> listDTO = list.stream().map(p -> new InforUserIpDTO(p)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
}