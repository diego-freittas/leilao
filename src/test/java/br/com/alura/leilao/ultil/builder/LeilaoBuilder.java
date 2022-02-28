package br.com.alura.leilao.ultil.builder;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;

public class LeilaoBuilder {
	
	private String nome;
	private BigDecimal valorInicial;
	private Usuario usuario;
	private LocalDate data;

	public LeilaoBuilder nome(String nome) {
		this.nome = nome;
		return this;
	}
	
	public LeilaoBuilder valorInicial(String valor) {
		this.valorInicial = new BigDecimal(valor);
		return this;
	}
	
	public LeilaoBuilder usuario(Usuario usuario) {
		this.usuario = usuario;
		return this;
	}
	
	public LeilaoBuilder data(LocalDate data) {
		this.data = data;
		return this;
	}
	
	public Leilao criar() {
		return new Leilao(nome,valorInicial,data,usuario);
	}

	
}
