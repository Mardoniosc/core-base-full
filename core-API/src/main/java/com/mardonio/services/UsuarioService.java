package com.mardonio.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mardonio.domain.Perfil;
import com.mardonio.domain.Usuario;
import com.mardonio.dto.UsuarioDTO;
import com.mardonio.dto.UsuarioNewDTO;
import com.mardonio.repositories.UsuarioRepository;
import com.mardonio.services.exceptions.DataIntegrityException;
import com.mardonio.services.exceptions.ObjectNotFoundException;
import com.mardonio.services.util.CriptografaSenha;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repo;
	
	@Autowired
	private CriptografaSenha criptografaSenha;
	
	@Autowired
	private AuthService authService;

	@Value("${assents.disco.raiz}")
	private String raiz;
	
	@Value("${profile.fotos}")
	private String fotosProfile;
	
	@Value("${base.url}")
	private String baseUrl;
	
	@Value("${img.profile.size}")
	private Integer size;
	
	@Autowired
	private ImageService imageService;
	
	private String prefix = "usuario";
	
	public Usuario find(Integer id) {
		Optional<Usuario> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));
	}
	
	public Usuario update(Usuario obj) {
		Usuario newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public Usuario updatePatch(String campo, Integer id, UsuarioDTO user) {
		Usuario newObj = find(id);
		switch (campo) {
			case "status":
				newObj.setStatus(user.getStatus());
				break;
			case "senha":
				authService.sendNewPassword(user.getEmail());
				break;
			default:
				break;
			}
		return repo.save(newObj);
	}
	
	public Usuario insert(Usuario obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}
	}

	public List<Usuario> findAll() {
		return repo.findAll();
	}

	public Page<Usuario> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Usuario fromDTO(UsuarioDTO objDTO) {
		Perfil p1 = new Perfil(objDTO.getPerfilId(), null);
		
		return new Usuario(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), objDTO.getSenha(),
				objDTO.getCpf(), objDTO.getLogin(), objDTO.getImagem(), new Date(System.currentTimeMillis()), 1, objDTO.getDataNascimento(), null, p1);
	}
	
	public Usuario fromDTO(UsuarioNewDTO objDTO) {
		Perfil p1 = new Perfil(objDTO.getPerfilId(), null);
		String senha = criptografaSenha.md5(objDTO.getSenha()); 
		
		Usuario us1 = new Usuario(null, objDTO.getNome(), objDTO.getEmail(),
				senha, objDTO.getCpf(), objDTO.getLogin(), null,
				new Date(System.currentTimeMillis()), 0, objDTO.getDataNascimento(), null, p1);
		
		return us1;
	}
	
	public Usuario findByEmail(String email) {

		Usuario obj = repo.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Tipo: " + Usuario.class.getName());
		}
		obj.setSenha(null);
		return obj;
	}

	private void updateData(Usuario newObj, Usuario obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		newObj.setCpf(obj.getCpf());
		newObj.setDataNascimento(obj.getDataNascimento());
		newObj.setLogin(obj.getLogin());
		newObj.setStatus(obj.getStatus());
		newObj.setPerfil(obj.getPerfil());
	}

	public void uploadProfilePicture(MultipartFile multipartFile, Integer id) {
		this.salvar(this.fotosProfile, multipartFile, id);
	}
	
	public void salvar(String diretorio, MultipartFile arquivo, Integer id) {
		
		Path diretorioPath = Paths.get(this.raiz, diretorio);
		Usuario u = find(id);
		BufferedImage jpgImage = imageService.getJpgImageFromFile(arquivo);
		String nomeDoArquivo = prefix + u.getId() + ".jpg";
		Path arquivoPath = diretorioPath.resolve(nomeDoArquivo);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		
		u.setImagem(fotosProfile + nomeDoArquivo);
		repo.save(u);
		
		try {
			Files.createDirectories(diretorioPath);
			ImageIO.write(jpgImage, "jpg", new File(arquivoPath.toString()));
			salvarNoBanco(arquivoPath.toString(), id);

		} catch (IOException e) {
			throw new RuntimeException("Problemas na tentativa de salvar arquivo.");
		}		
	}
	
	public void salvarNoBanco(String arquivo, Integer id) {
		Usuario u = find(id);
		try {
			BufferedImage imagem = ImageIO.read(new File(arquivo));			
			ByteArrayOutputStream imageProfile = new ByteArrayOutputStream();
			ImageIO.write((BufferedImage)imagem, "jpg", imageProfile );//seta a imagem para bytesImg
			imageProfile.flush();//limpa a variável
			byte[] byteArray = imageProfile.toByteArray();//Converte ByteArrayOutputStream para byte[] 
			imageProfile.close();
			u.setImageProfile(byteArray);
			repo.save(u);
		} catch (Exception e) {
			throw new RuntimeException("Problemas na tentativa de salvar arquivo no banco.");
		}
	}
	
	public void salvaImgDoBancoNoServer(Integer id) {
		Usuario u = find(id);
		BufferedImage img = null; 
		
		Path diretorioPath = Paths.get(this.raiz, this.fotosProfile);
		String nomeDoArquivo = prefix + u.getId() + ".jpg";
		Path arquivoPath = diretorioPath.resolve(nomeDoArquivo);
		
		try {
			img = ImageIO.read(new ByteArrayInputStream(u.getImageProfile()));
			ImageIO.write(img, "JPG", new File(arquivoPath.toString()));
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
}
