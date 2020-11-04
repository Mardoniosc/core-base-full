package com.mardonio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mardonio.domain.HistoricoLog;
import com.mardonio.domain.Usuario;
import com.mardonio.dto.HistoricoLogDTO;
import com.mardonio.repositories.HistoricoLogRepository;
import com.mardonio.services.exceptions.DataIntegrityException;
import com.mardonio.services.exceptions.ObjectNotFoundException;

@Service
public class HistoricoLogService {
	
	@Autowired
	private HistoricoLogRepository repo;
	
	@Autowired
	private UsuarioService usuarioService;

	public HistoricoLog find(Integer id) {
		Optional<HistoricoLog> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + HistoricoLog.class.getName()));
	}
	
	public HistoricoLog insert(HistoricoLog obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public HistoricoLog update(HistoricoLog obj) {
		HistoricoLog newObj = find(obj.getId());
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
	
	public HistoricoLog fromDTO(HistoricoLogDTO objDTO) {
		Usuario u1 = usuarioService.find(objDTO.getUsuarioId());
		return new HistoricoLog(objDTO.getId(), objDTO.getDataAcesso(), u1, objDTO.getLoginIp(), objDTO.getTabela(), objDTO.getTabela());
	}
	
	public List<HistoricoLog> findAll() {
		return repo.findAll();
	}
	
	public Page<HistoricoLog> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	private void updateData(HistoricoLog newObj, HistoricoLog obj) {
		newObj.setUsuario(obj.getUsuario());
	}
	
}
