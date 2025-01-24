package com.generation.blogpessoal.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.generation.blogpessoal.model.Usuario;
//IMPLEMENTAÇÃO DA CLASSE UserDetails PARA INTEGRAÇÃO COM O SISTEMA DE AUTENTICAÇÃO DO SPRING SECURITY
public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;
	private String userName;// Armazena o e-mail (nome de usuário).
	private String password;// Armazena a senha.
	private List<GrantedAuthority> authorities;// ARMAZENA AS AUTORIDADES/ROLES DO USUÁRIO.
	
	// CONSTRUTOR QUE RECEBE UM OBJETO 'Usuario' E DEFINE O USERNAME E PASSWORD
	public UserDetailsImpl(Usuario user) {
		this.userName = user.getUsuario();
		this.password = user.getSenha();
	}
	  // CONSTRUTOR PADRÃO
	public UserDetailsImpl() {
	}
	// MÉTODO RESPONSÁVEL POR RETORNAR AS AUTORIDADES OU "ROLES" DO USUÁRIO (NESTE EXEMPLO, É DEIXADO VAZIO).
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return authorities;// RETORNA A LISTA DE AUTORIDADES DO USUÁRIO.
	}
	 // MÉTODO RESPONSÁVEL POR RETORNAR A SENHA DO USUÁRIO
	@Override
	public String getPassword() { // RETORNA A SENHA ARMAZENADA.

		return password;
	}
	// MÉTODO RESPONSÁVEL POR RETORNAR O NOME DE USUÁRIO (E-MAIL)
	@Override
	public String getUsername() {// RETORNA O NOME DE USUÁRIO ARMAZENADO (E-MAIL).

		return userName;
	}
	@Override
	public boolean isAccountNonExpired() {// RETORNA 'TRUE' SE A CONTA NÃO ESTIVER EXPIRADA.
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {// RETORNA 'TRUE' SE A CONTA NÃO ESTIVER BLOQUEADA.
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {// RETORNA 'TRUE' SE AS CREDENCIAIS NÃO ESTIVEREM EXPIRADAS.
		return true;
	}

	@Override
	public boolean isEnabled() {// RETORNA 'TRUE' SE O USUÁRIO ESTIVER HABILITADO.
		return true;
	}
}
