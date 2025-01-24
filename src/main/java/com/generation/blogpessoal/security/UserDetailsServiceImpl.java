package com.generation.blogpessoal.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
//A ANOTAÇÃO @SERVICE INDICA QUE ESTA CLASSE É UM SERVIÇO GERIDO PELO SPRING (USADA PARA LÓGICA DE NEGÓCIO).
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	// A ANOTAÇÃO @AUTOWIRED INICIA O USUARIOREPOSITORY PARA ACESSAR O BANCO DE DADOS.
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		// AQUI, O MÉTODO 'findByUsuario' BUSCA UM USUÁRIO NO BANCO DE DADOS COM O NOME DE USUÁRIO INFORMADO.
		Optional<Usuario> usuario = usuarioRepository.findByUsuario(userName);
		
		 // SE O USUÁRIO FOR ENCONTRADO, RETORNA UM 'UserDetailsImpl' COM AS INFORMAÇÕES DO USUÁRIO.
		if (usuario.isPresent())
			return new UserDetailsImpl(usuario.get());
		else
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);// SE O USUÁRIO NÃO FOR ENCONTRADO, UM EXCEÇÃO DE STATUS 'FORBIDDEN' (403) É LANÇADA.
			
	}
}