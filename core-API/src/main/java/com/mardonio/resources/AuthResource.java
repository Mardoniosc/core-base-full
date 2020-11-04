package com.mardonio.resources;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mardonio.domain.HistoricoAcesso;
import com.mardonio.domain.InforUserIp;
import com.mardonio.domain.Usuario;
import com.mardonio.dto.EmailDTO;
import com.mardonio.dto.LoginDTO;
import com.mardonio.services.AuthService;
import com.mardonio.services.HistoricoAcessoService;
import com.mardonio.services.InfoUserIpService;
import com.mardonio.services.UsuarioService;

@RestController
@RequestMapping(value="")
public class AuthResource {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private HistoricoAcessoService haService;

	@Autowired
	private InfoUserIpService infoUserIpService;

	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping(value = "/auth/forgot")
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDTO) {
		authService.sendNewPassword(objDTO.getEmail());
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<Usuario> login(@Valid @RequestBody LoginDTO objDTO) {
		Usuario obj = authService.login(objDTO);
		Date date = new Date(System.currentTimeMillis());
		InforUserIp ip = infoUserIpService.insert(obj.getId());
		HistoricoAcesso ha = new HistoricoAcesso(null, date, obj, ip.getQuery());
		haService.insert(ha);
		obj = usuarioService.find(obj.getId());
		obj.setSenha(null);
		return ResponseEntity.ok().body(obj);
	}
}
