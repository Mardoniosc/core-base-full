package com.mardonio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mardonio.domain.InforUserIp;
import com.mardonio.domain.Usuario;
import com.mardonio.repositories.InforUserIpRepository;
import com.mardonio.services.exceptions.DataIntegrityException;
import com.mardonio.services.exceptions.ObjectNotFoundException;

@Service
public class InfoUserIpService {

	@Autowired
	private InforUserIpRepository repo;
	
	@Autowired
	private UsuarioService userService;
	
	private String urlInfo = "http://ip-api.com/json";
	RestTemplate restTemplate = new RestTemplate();
	
	public InforUserIp find(Integer id) {
		Optional<InforUserIp> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + InforUserIp.class.getName()));
	}
	
	
	public InforUserIp insert(Integer id) {
		InforUserIp obj = restTemplate.getForObject(urlInfo, InforUserIp.class);
		obj.setId(null);
		Usuario u1 = userService.find(id);
		obj.setUsuario(u1);
		return repo.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um perfil que tenha usuário/permissoes associados");
		}
	}
	
	public List<InforUserIp> findAll() {
		return repo.findAll();
	}
	
	public Page<InforUserIp> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
}
