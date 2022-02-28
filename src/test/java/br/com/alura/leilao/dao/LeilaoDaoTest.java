package br.com.alura.leilao.dao;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.ultil.JPAUtil;
import br.com.alura.leilao.ultil.builder.LeilaoBuilder;
import br.com.alura.leilao.ultil.builder.UsuarioBuilder;

@ActiveProfiles("test") 
class LeilaoDaoTest {
	
	private LeilaoDao dao;
	private EntityManager em;
	Usuario usuario;
	private Leilao leilao;
		
	@BeforeEach
	public void beforeEach() {
		em = JPAUtil.getEntityManager();
		this.dao = new LeilaoDao(em);
		em.getTransaction().begin();
		
		usuario = new UsuarioBuilder()
				.nome("fulano")
				.email("fulano@email.com")
				.senha("123")
				.criar();
		
		em.persist(usuario);
		
		leilao = new LeilaoBuilder()
				.nome("Mochila")
				.valorInicial("70")
				.data(LocalDate.now())
				.usuario(usuario)
				.criar();
	}
	
	@AfterEach
	public void afterEach() {
		em.getTransaction().rollback();
	}

	@Test
	void deveriaSalvarUmLeilao() {
		leilao = dao.salvar(leilao);
		Leilao leilaoDoBanco = dao.buscarPorId(leilao.getId());
		Assert.assertNotNull(leilaoDoBanco);
	}
	
	@Test
	void deveriaAtualizarUmLeilao() {
		leilao = dao.salvar(leilao);
		leilao.setNome("Camisa");
		leilao.setValorInicial(BigDecimal.TEN);
		leilao = dao.salvar(leilao);
		
		Leilao leilaoDoBanco = dao.buscarPorId(leilao.getId());
		Assert.assertEquals("Camisa",leilaoDoBanco.getNome());
		Assert.assertEquals(BigDecimal.TEN,leilaoDoBanco.getValorInicial());

	}
	

}
