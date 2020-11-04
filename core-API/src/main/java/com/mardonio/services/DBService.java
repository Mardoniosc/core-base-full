package com.mardonio.services;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mardonio.domain.HistoricoAcesso;
import com.mardonio.domain.HistoricoLog;
import com.mardonio.domain.Perfil;
import com.mardonio.domain.PerfilPermissao;
import com.mardonio.domain.Permissao;
import com.mardonio.domain.Usuario;
import com.mardonio.repositories.HistoricoAcessoRepository;
import com.mardonio.repositories.HistoricoLogRepository;
import com.mardonio.repositories.PerfilPermissaoRepository;
import com.mardonio.repositories.PerfilRepository;
import com.mardonio.repositories.PermissaoRepository;
import com.mardonio.repositories.UsuarioRepository;
import com.mardonio.services.util.CriptografaSenha;

@Service
public class DBService {

	@Autowired
	private PerfilRepository perfilRepository;

	@Autowired
	private PermissaoRepository permissaoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private HistoricoAcessoRepository historicoAcessoRepository;
	
	@Autowired
	private HistoricoLogRepository historicoLogRepository;
	
	@Autowired
	private PerfilPermissaoRepository perfilPermissaoRepository;

	@Autowired
	private CriptografaSenha criptografaSenha;
			
	public void instantiateTestDataBase() throws Exception {
		
		Date date = new Date(System.currentTimeMillis());
		
		String dataNascimento = "14/08/1991";
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date dataNascimentoFormatada = new Date(format.parse(dataNascimento).getTime());
		
		Perfil pf1 = new Perfil(null, "root");
		Perfil pf2 = new Perfil(null, "Administrador");
		Perfil pf3 = new Perfil(null, "Gestor");
		Perfil pf4 = new Perfil(null, "Operador");
		Perfil pf5 = new Perfil(null, "Usuário");
		
		Permissao pm1 = new Permissao(null, "Dashboard", "/dashboard", null);
		Permissao pm2 = new Permissao(null, "Home", "/home", null);
		Permissao pm3 = new Permissao(null, "Usuário", "/usuario", null);
		Permissao pm4 = new Permissao(null, "Perfil", "/perfil", null);
		Permissao pm5 = new Permissao(null, "Permissão", "/permissao", null);
		Permissao pm6 = new Permissao(null, "Log", "/log", null);
		
		Permissao pm7 = new Permissao(null, "Cadastrar Usuário", "/usuario/cadastrar", pm3);
		Permissao pm8 = new Permissao(null, "Cadastrar Perfil", "/perfil/cadastrar", pm4);
		Permissao pm9 = new Permissao(null, "Cadastrar Permissão", "/permissao/cadastrar", pm5);

		Permissao pm10 = new Permissao(null, "Pesquisar Usuario", "/usuario/pesquisar", pm3);
		Permissao pm11 = new Permissao(null, "Pesquisar Perfil", "/perfil/pesquisar", pm4);
		Permissao pm12 = new Permissao(null, "Pesquisar Permissão", "/permissao/pesquisar", pm5);

		Permissao pm13 = new Permissao(null, "Pesquisar Log", "/log/pesquisar", pm6);
		Permissao pm14 = new Permissao(null, "Pesquisar Acesso", "/log/pesquisar-acesso", pm6);
		
		Permissao pm15 = new Permissao(null, "Criar Nova Permissao", "/permissao/criar-nova-permissao", pm5);
		
		Usuario u1 = new Usuario(null,"mardonio","mardonio.costa@tecnisys.com.br", criptografaSenha.md5("123123"), "96797404045","mardonio.costa@tecnisys.com.br",
				null, date, 1, dataNascimentoFormatada,null, pf1);
		
		Usuario u2 = new Usuario(null,"Maria","mrdonio.sc@gmail.com", criptografaSenha.md5("123123"), "26821386080","mrdonio.sc@gmail.com",
				null, date, 0, dataNascimentoFormatada, null, pf2);

		HistoricoAcesso ha1 = new HistoricoAcesso(null, date, u1, null);
		HistoricoAcesso ha2 = new HistoricoAcesso(null, date, u2, null);
		HistoricoAcesso ha3 = new HistoricoAcesso(null, date, u2, null);

		HistoricoLog hl1 = new HistoricoLog(null, date, u1, "192.168.0.7", "USUARIO", "POST");
		HistoricoLog hl2 = new HistoricoLog(null, date, u1, "192.168.0.7", "USUARIO", "DELETE");
		
		PerfilPermissao pp1 = new PerfilPermissao(pf1, pm1, 1);
		PerfilPermissao pp2 = new PerfilPermissao(pf1, pm2, 1);
		PerfilPermissao pp3 = new PerfilPermissao(pf1, pm3, 1);
		PerfilPermissao pp4 = new PerfilPermissao(pf1, pm4, 1);
		PerfilPermissao pp5 = new PerfilPermissao(pf1, pm5, 1);
		PerfilPermissao pp6 = new PerfilPermissao(pf1, pm6, 1);
		PerfilPermissao pp7 = new PerfilPermissao(pf1, pm7, 1);
		PerfilPermissao pp8 = new PerfilPermissao(pf1, pm8, 1);
		PerfilPermissao pp9 = new PerfilPermissao(pf1, pm9, 1);
		PerfilPermissao pp10 = new PerfilPermissao(pf1, pm10, 1);
		PerfilPermissao pp11 = new PerfilPermissao(pf1, pm11, 1);
		PerfilPermissao pp12 = new PerfilPermissao(pf1, pm12, 1);
		PerfilPermissao pp13 = new PerfilPermissao(pf1, pm13, 1);
		PerfilPermissao pp14 = new PerfilPermissao(pf1, pm14, 1);
		PerfilPermissao pp19 = new PerfilPermissao(pf1, pm15, 1);

		PerfilPermissao pp15 = new PerfilPermissao(pf2, pm1, 1);
		PerfilPermissao pp16 = new PerfilPermissao(pf3, pm1, 1);
		PerfilPermissao pp17 = new PerfilPermissao(pf4, pm2, 1);
		PerfilPermissao pp18 = new PerfilPermissao(pf5, pm2, 1);
		
		pf1.getPerfilsPermissoes().addAll(Arrays.asList(pp1,pp2,pp3));
		pm1.getPerfilsPermissoes().addAll(Arrays.asList(pp1));
		pm2.getPerfilsPermissoes().addAll(Arrays.asList(pp2));
		pm3.getPerfilsPermissoes().addAll(Arrays.asList(pp3));
		
		perfilRepository.saveAll(Arrays.asList(pf1, pf2, pf3, pf4, pf5));
		
		permissaoRepository.saveAll(Arrays.asList(pm1, pm2, pm3, pm4, pm5, pm6, pm7, pm8, pm9, pm10, pm11, pm12, pm13, pm14, pm15));
		
		usuarioRepository.saveAll(Arrays.asList(u1, u2));
		
		historicoAcessoRepository.saveAll(Arrays.asList(ha1, ha2, ha3));
		
		historicoLogRepository.saveAll(Arrays.asList(hl1, hl2));
		
		perfilPermissaoRepository.saveAll(Arrays.asList(pp1,pp2,pp3,pp4,pp5,pp6,pp7,pp8,pp9,pp10,pp11,pp12,pp13,pp14,pp15,pp16,pp17,pp18,pp19));
		
	}

}
