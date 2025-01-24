package com.generation.blogpessoal.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// A ANOTAÇÃO @CONFIGURATION INDICA QUE ESTA CLASSE É UMA CLASSE DE CONFIGURAÇÃO DO SPRING.
@Configuration
// A ANOTAÇÃO @ENABLEWEBSecurity HABILITA A SEGURANÇA NA APLICAÇÃO WEB.
@EnableWebSecurity
public class BasicSecurityConfig {

	@Autowired
	private JwtAuthFilter authFilter; // FILTRO DE AUTENTICAÇÃO JWT (UTILIZADO PARA AUTENTICAR E VALIDAR O TOKEN).

	// DEFINE O BEAN PARA O USERDETAILSSERVICE, RESPONSÁVEL POR CARREGAR OS DETALHES
	// DO USUÁRIO.
	@Bean
	UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl(); // RETORNA A IMPLEMENTAÇÃO DO USERDETAILSSERVICE.
	}

	// DEFINE O BEAN PARA O ENCODER DE SENHAS (USANDO O BCrypt, QUE É UM ALGORITMO
	// DE HASH SEGURO).
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // RETORNA O ENCODER DE SENHA BCrypt.
	}

	// DEFINE O BEAN PARA O AUTENTICATION PROVIDER, RESPONSÁVEL POR REALIZAR A
	// AUTENTICAÇÃO.
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService()); // ASSOCIA O USERDETAILSSERVICE AO PROVIDER.
		authenticationProvider.setPasswordEncoder(passwordEncoder()); // DEFINE O ENCODER DE SENHA PARA O PROVIDER.
		return authenticationProvider; // RETORNA O PROVIDER CONFIGURADO.
	}

	// DEFINE O BEAN PARA O MANAGER DE AUTENTICAÇÃO, USADO PARA GERENCIAR O PROCESSO
	// DE AUTENTICAÇÃO.
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager(); // RETORNA O MANAGER DE AUTENTICAÇÃO.
	}

	// DEFINE O BEAN PARA O FILTRO DE SEGURANÇA, QUE GERA A CONFIGURAÇÃO DE
	// SEGURANÇA HTTP.
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// CONFIGURA O GERENCIAMENTO DE SESSÃO PARA "STATELESS", O QUE SIGNIFICA QUE NÃO
		// HAVERÁ ESTADO DE SESSÃO.
		http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				// DESABILITA A PROTEÇÃO CSRF, QUE NÃO É NECESSÁRIA EM UMA API RESTFUL SEM
				// ESTADO.
				.csrf(csrf -> csrf.disable())

				// ATIVA O SUPORTE PARA CORS (Cross-Origin Resource Sharing) COM CONFIGURAÇÃO
				// PADRÃO.
				.cors(withDefaults());

		// CONFIGURA AS REGRAS DE AUTORIZAÇÃO DAS ROTAS.
		http.authorizeHttpRequests((auth) -> auth.requestMatchers("/usuarios/logar").permitAll() // PERMITE O ACESSO
																									// PÚBLICO À ROTA DE
																									// LOGIN.
				.requestMatchers("/usuarios/cadastrar").permitAll() // PERMITE O ACESSO PÚBLICO À ROTA DE CADASTRO DE
																	// USUÁRIOS.
				.requestMatchers("/error/**").permitAll() // PERMITE O ACESSO PÚBLICO A ERROS.
				.requestMatchers(HttpMethod.OPTIONS).permitAll() // PERMITE O ACESSO PÚBLICO A REQUISIÇÕES OPTIONS.
				.anyRequest().authenticated()) // EXIGE AUTENTICAÇÃO PARA QUALQUER OUTRA REQUISIÇÃO.
				.authenticationProvider(authenticationProvider()) // DEFINE O PROVIDER DE AUTENTICAÇÃO.
				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class) // ADICIONA O FILTRO DE
																							// AUTENTICAÇÃO ANTES DO
																							// FILTRO DE AUTENTICAÇÃO
																							// PADRÃO.
				.httpBasic(withDefaults()); // HABILITA O AUTENTICAÇÃO HTTP BÁSICA (USADA PARA APIs).

		// RETORNA A CONFIGURAÇÃO DE SEGURANÇA.
		return http.build();
	}
}
