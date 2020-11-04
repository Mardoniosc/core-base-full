package com.mardonio.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.mardonio.domain.Usuario;
import com.mardonio.dto.UsuarioNewDTO;
import com.mardonio.repositories.UsuarioRepository;
import com.mardonio.resources.exception.FieldMessage;

public class UsuarioInsertValidator implements ConstraintValidator<UsuarioInsert, UsuarioNewDTO>{
	
	@Autowired
	private UsuarioRepository repo;
	
	@Override
	public boolean isValid(UsuarioNewDTO objDto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();
		
		Usuario aux = repo.findByEmail(objDto.getEmail());
		
		if(aux != null) {
			list.add(new FieldMessage("email", "E-mail já existente"));
		}

		aux = repo.findByCpf(objDto.getCpf());
		
		if(aux != null) {
			list.add(new FieldMessage("cpf", "CPF já cadastrado"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}

	
}
