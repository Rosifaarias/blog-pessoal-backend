package com.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate testeRestTemplate; // SIMULA O INSOMNIA

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@BeforeAll
	void start() {
		 // O método será executado uma vez antes de todos os testes da classe.
		usuarioRepository.deleteAll();
		  // Limpa todos os registros da tabela ou coleção 'usuario' no banco de dados
		usuarioService.cadastrarUsuario(new Usuario(0L, "Root", "root@root.com", "rootroot", "-"));

	}

	@Test
	@DisplayName("Cadastrar Um Usuário") // Nome descritivo do test
	public void deveCriarUmUsuario() {
		// CRIA O CORPO DA REQUISIÇÃO HTTP COM AS INFORMAÇÕES DO NOVO USUÁRIO.
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(
				new Usuario(0L, "Paulo Antunes", "paulo_antunes@email.com.br", "13465278", "-"));

		// ENVIA A REQUISIÇÃO POST PARA O ENDPOINT "/USUARIOS/CADASTRAR" E ARMAZENA A
		// RESPOSTA.
		ResponseEntity<Usuario> corpoResposta = testeRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST,
				corpoRequisicao, Usuario.class);

		// VERIFICA SE A RESPOSTA TEM O STATUS DE 'CREATED' (201), QUE INDICA QUE O
		// USUÁRIO FOI CRIADO COM SUCESSO.
		assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());

	}

	// ESTE TESTE VERIFICA SE O SISTEMA NÃO PERMITE A CRIAÇÃO DE USUÁRIOS COM
	// E-MAILS DUPLICADOS.
	@Test
	@DisplayName("Não deve permitir duplicação do Usuário")
	public void naoDeveDuplicarUsuario() {

		// CADASTRA UM USUÁRIO COM O NOME "MARIA DA SILVA".
		usuarioService.cadastrarUsuario(new Usuario(0L, "Maria da Silva", "maria_silva@email.com.br", "13465278", "-"));

		// CRIA UMA REQUISIÇÃO HTTP PARA TENTAR CADASTRAR O MESMO USUÁRIO NOVAMENTE.
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(
				new Usuario(0L, "Maria da Silva", "maria_silva@email.com.br", "13465278", "-"));

		// ENVIA A REQUISIÇÃO POST E ARMAZENA A RESPOSTA.
		ResponseEntity<Usuario> corpoResposta = testeRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST,
				corpoRequisicao, Usuario.class);

		// VERIFICA SE A RESPOSTA RETORNA 'BAD_REQUEST' (400), INDICANDO QUE A
		// DUPLICAÇÃO FOI IMPEDIDA.
		assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
	}

	// ESTE TESTE VERIFICA SE É POSSÍVEL ATUALIZAR AS INFORMAÇÕES DE UM USUÁRIO.
	@Test
	@DisplayName("Atualizar um Usuário")
	public void deveAtualizarUmUsuario() {

		// CADASTRA UM USUÁRIO CHAMADO "JULIANA ANDREWS".
		Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(
				new Usuario(0L, "Juliana Andrews", "juliana_andrews@email.com.br", "juliana123", "-"));

		// CRIA UM OBJETO COM AS INFORMAÇÕES ATUALIZADAS DO USUÁRIO.
		Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(), "Juliana Andrews Ramos",
				"juliana_ramos@email.com.br", "juliana123", "-");

		// CRIA O CORPO DA REQUISIÇÃO COM OS DADOS DO USUÁRIO A SER ATUALIZADO.
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate);

		// ENVIA A REQUISIÇÃO PUT PARA O ENDPOINT "/USUARIOS/ATUALIZAR" COM AS NOVAS
		// INFORMAÇÕES DO USUÁRIO.
		ResponseEntity<Usuario> corpoResposta = testeRestTemplate.withBasicAuth("root@root.com", "rootroot")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);

		// VERIFICA SE O STATUS DA RESPOSTA É 'OK' (200), INDICANDO QUE A ATUALIZAÇÃO
		// FOI REALIZADA COM SUCESSO.
		assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());

	}

	// VERIFICA SE TODOS OS USUÁRIOS CADASTRADOS SÃO RETORNADOS AO SOLICITAR TODOS
	// OS USUÁRIOS.
	@Test
	@DisplayName("Listar todos os Usuários")
	public void deveMostrarTodosUsuarios() {

		// CADASTRA DOIS NOVOS USUÁRIOS.
		usuarioService.cadastrarUsuario(
				new Usuario(0L, "Sabrina Sanches", "sabrina_sanches@email.com.br", "sabrina123", "-"));

		usuarioService.cadastrarUsuario(
				new Usuario(0L, "Ricardo Marques", "ricardo_marques@email.com.br", "ricardo123", "-"));

		// ENVIA UMA REQUISIÇÃO GET PARA O ENDPOINT "/USUARIOS/ALL" PARA LISTAR TODOS OS
		// USUÁRIOS.
		ResponseEntity<String> resposta = testeRestTemplate.withBasicAuth("root@root.com", "rootroot")
				.exchange("/usuarios/all", HttpMethod.GET, null, String.class);
		// VERIFICA SE A RESPOSTA TEM O STATUS 'OK' (200), INDICANDO QUE A LISTAGEM FOI
		// REALIZADA COM SUCESSO.

		assertEquals(HttpStatus.OK, resposta.getStatusCode());

	}

}
