package com.mardonio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mardonio.domain.Permissao;
import com.mardonio.dto.PermissaoDTO;
import com.mardonio.repositories.PermissaoRepository;
import com.mardonio.services.exceptions.DataIntegrityException;
import com.mardonio.services.exceptions.ObjectNotFoundException;

@Service
public class PermissaoService {
	
	@Autowired
	private PermissaoRepository repo;
	
	public Permissao find(Integer id) {
		Optional<Permissao> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Permissao.class.getName()));
	}
	
	
	public Permissao insert(Permissao obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Permissao update(Permissao obj) {
		Permissao newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma permissão pai que possui permissões filho associadas");
		}
	}
	
	public Permissao fromDTO(PermissaoDTO objDTO) {
		if(objDTO.getPermissaoPai() == null) {
			return new Permissao(objDTO.getId(), objDTO.getDescricao(),
					objDTO.getUrl(), null);
		}
		Permissao p1 = find(objDTO.getPermissaoPai());
		return new Permissao(objDTO.getId(), objDTO.getDescricao(),
													objDTO.getUrl(), p1);
		
	}
	
	public List<Permissao> findAll() {
		return repo.findAll();
	}
	
	public Page<Permissao> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	private void updateData(Permissao newObj, Permissao obj) {
		if(obj.getPermissaoPai() != null) {
			Permissao pp1 = find(obj.getPermissaoPai().getId());
			newObj.setPermissaoPai(pp1);
		} else {
			newObj.setPermissaoPai(null);			
		}
		newObj.setDescricao(obj.getDescricao());
		newObj.setUrl(obj.getUrl());
	}

}
