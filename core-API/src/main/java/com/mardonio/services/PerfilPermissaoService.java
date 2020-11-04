package com.mardonio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mardonio.domain.Perfil;
import com.mardonio.domain.PerfilPermissao;
import com.mardonio.domain.PerfilPermissaoPK;
import com.mardonio.domain.Permissao;
import com.mardonio.dto.PerfilPermissaoDTO;
import com.mardonio.repositories.PerfilPermissaoRepository;
import com.mardonio.services.exceptions.DataIntegrityException;
import com.mardonio.services.exceptions.ObjectNotFoundException;

@Service
public class PerfilPermissaoService {

	@Autowired
	private PerfilPermissaoRepository repo;
	
	@Autowired
	private PerfilService perfilService;
	
	@Autowired
	private PermissaoService permissaoService;

	public PerfilPermissao find(PerfilPermissaoPK id) {
		Optional<PerfilPermissao> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Perfil.class.getName()));
	}
	
	public List<PerfilPermissao> findAll() {
		return repo.findAll();
	}
	
	public PerfilPermissao insert(PerfilPermissao obj) {
		return repo.save(obj);
	}
	
	public PerfilPermissao update(PerfilPermissao obj) {
		PerfilPermissao newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public PerfilPermissao fromDTO(PerfilPermissaoDTO objDTO) {
		Perfil pf = perfilService.find(objDTO.getPerfilId());
		Permissao pm = permissaoService.find(objDTO.getPermissaoId());
		
		PerfilPermissao pp = new PerfilPermissao(pf, pm, objDTO.getStatus());
		
		return pp;
	}
	
	public void delete(PerfilPermissaoPK id) {
		find(id);
		try {
			repo.deleteById(id);			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma permissao que tenha Perfils associados");
		}
	}
	
	private void updateData(PerfilPermissao newObj, PerfilPermissao obj) {
		newObj.setId(obj.getId());
		newObj.setStatus(obj.getStatus());
	}
}
