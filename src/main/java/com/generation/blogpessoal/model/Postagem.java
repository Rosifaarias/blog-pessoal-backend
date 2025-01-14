package com.generation.blogpessoal.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity // etiquetas para entender os comportamentos de classe/att e metodos
@Table(name = "tb_postagens") // indica o nome da tabela no banco de dados
public class Postagem {
	@Id // ELE É A CHAVE PRIMARIA DESSA TABELA
	@GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO INCREMENTO
	private Long id;

	@NotBlank(message = "Esse campo é obrigatório") // not null TITULO DA POSTAGEM
	@Size(min = 5, max = 100, message = "Digite no minino 5 e no maximo 100 caracteres")
	private String titulo;

	@NotBlank(message = "Esse campo é obrigatório") // not null TITULO DA POSTAGEM
	@Size(min = 10, max = 1000, message = "Digite no minino 5 e no maximo 100 caracteres")
	private String texto;

	@UpdateTimestamp
	private LocalDateTime data;

	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Tema tema; // conectando a postagem
	

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

}
