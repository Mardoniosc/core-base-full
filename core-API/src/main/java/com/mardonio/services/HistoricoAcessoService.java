package com.mardonio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mardonio.domain.HistoricoAcesso;
import com.mardonio.domain.Usuario;
import com.mardonio.dto.HistoricoAcessoDTO;
import com.mardonio.repositories.HistoricoAcessoRepository;
import com.mardonio.services.exceptions.DataIntegrityException;
import com.mardonio.services.exceptions.ObjectNotFoundException;

@Service
public class HistoricoAcessoService {

	@Autowired
	private HistoricoAcessoRepository repo;
	
	@Autowired
	private UsuarioService usuarioService;

	public HistoricoAcesso find(Integer id) {
		Optional<HistoricoAcesso> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + HistoricoAcesso.class.getName()));
	}
	
	public HistoricoAcesso insert(HistoricoAcesso obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public HistoricoAcesso update(HistoricoAcesso obj) {
		HistoricoAcesso newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir historicos de acesso");
		}
	}
	
	public HistoricoAcesso fromDTO(HistoricoAcessoDTO objDTO) {
		Usuario u1 = usuarioService.find(objDTO.getUsuarioId());
		return new HistoricoAcesso(objDTO.getId(), objDTO.getDataAcesso(), u1, null);
	}
	
	public List<HistoricoAcesso> findAll() {
		return repo.findAll();
	}
	
	public Page<HistoricoAcesso> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	private void updateData(HistoricoAcesso newObj, HistoricoAcesso obj) {
		newObj.setUsuario(obj.getUsuario());
	}
	
}
