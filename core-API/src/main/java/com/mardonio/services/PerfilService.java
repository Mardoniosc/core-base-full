package com.mardonio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mardonio.domain.Perfil;
import com.mardonio.dto.PerfilDTO;
import com.mardonio.repositories.PerfilRepository;
import com.mardonio.services.exceptions.DataIntegrityException;
import com.mardonio.services.exceptions.ObjectNotFoundException;

@Service
public class PerfilService {

	@Autowired
	private PerfilRepository repo;
	
	public Perfil find(Integer id) {
		Optional<Perfil> obj = repo.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Perfil.class.getName()));
	}
	
	public Perfil insert(Perfil obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Perfil update(Perfil obj) {
		Perfil newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um perfil que tenha usuário/permissoes associados");
		}
	}
	
	public Perfil fromDTO(PerfilDTO objDTO) {
		return new Perfil(objDTO.getId(), objDTO.getNome());
	}
	
	public List<Perfil> findAll() {
		return repo.findAll();
	}
	
	public Page<Perfil> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	private void updateData(Perfil newObj, Perfil obj) {
		newObj.setNome(obj.getNome());
	}

}
