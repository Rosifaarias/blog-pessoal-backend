package com.generation.blogpessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

@RestController // DEFINE AO SPRING QUE ESSA CLASSE É UM CONTROLLER
@RequestMapping("/postagens") // DEFINE QUAL ENDPOINT VAI SER TRATADO POR ESSA CLASSE
@CrossOrigin(origins = "*", allowedHeaders = "*") // LIBERA O ACESSO A QUALUWER FRONT QUE 

public class PostagemController {
	@Autowired // O SPRING DÁ AUTONOMIA  PARA A INTERFACE PODER INVOCAR OS MÉTODOS
	private PostagemRepository postagemRepository; 

	@GetMapping
	public ResponseEntity<List<Postagem>> getAll() {
		return ResponseEntity.ok(postagemRepository.findAll());
	}
}