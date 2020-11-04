package com.mardonio.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mardonio.domain.Usuario;
import com.mardonio.dto.LoginDTO;
import com.mardonio.repositories.UsuarioRepository;
import com.mardonio.services.exceptions.AuthorizationException;
import com.mardonio.services.exceptions.ObjectNotFoundException;
import com.mardonio.services.util.CriptografaSenha;

@Service
public class AuthService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CriptografaSenha criptografaSenha;

	@Autowired
	private EmailService emailService;

	private Random rand = new Random();
	
	public Usuario findByEmail(String email) {

		Usuario obj = usuarioRepository.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Tipo: " + Usuario.class.getName());
		}
		return obj;
	}
	
	public void sendNewPassword(String email) {
		Usuario usuario = usuarioRepository.findByEmail(email);

		if(usuario == null) {
			throw new ObjectNotFoundException("E-mail não encontrado!");
		}

		String newPass = newPassword();
		usuario.setSenha(criptografaSenha.md5(newPass));

		usuarioRepository.save(usuario);
		emailService.sendNewPasswordEmail(usuario, newPass);
		
		
	}
	
	public Usuario login(LoginDTO loginDTO) {
		Usuario obj = usuarioRepository.findByEmail(loginDTO.getLogin());
		if (obj == null) {
			throw new AuthorizationException("Usuario/Senha incorreto");
		}
		String senhaCriptografada = criptografaSenha.md5(loginDTO.getSenha());
		
		if(senhaCriptografada.equals(obj.getSenha())) {
			return obj;			
		}
		throw new AuthorizationException("Usuario/Senha incorreto");
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i=0; i< 10 ; i++) {
			vet[i] = randomChar();
		}

		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		if (opt == 0) { // gera um digito 48 / 57
			return (char) (rand.nextInt(10) + 48);
		} else if(opt == 1) { // gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);
		} else { // gera letra minuscula
			return (char) (rand.nextInt(26) + 97);
		}
	}

	


}
