package br.com.alura.leilao.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.ultil.JPAUtil;
import br.com.alura.leilao.ultil.builder.LeilaoBuilder;
import br.com.alura.leilao.ultil.builder.UsuarioBuilder;

class LanceDaoTest {
	

	private LanceDao dao;
	private EntityManager em;
		
	@BeforeEach
	public void beforeEach() {
		em = JPAUtil.getEntityManager();
		this.dao = new LanceDao(em);
		em.getTransaction().begin();
	}
	
	@AfterEach
	public void afterEach() {
		em.getTransaction().rollback();
	}

	@Test
	void deveBuscarMaiorLanceDoLeilao() {

		Usuario usuario = new UsuarioBuilder()
				.nome("Diego").senha("123").email("e@gmail.com").criar();
		
		Leilao leilao = new LeilaoBuilder()
				.nome("Carro")
				.valorInicial("20000.00")
				.data(LocalDate.now())
				.usuario(usuario)
				.criar();
		em.persist(usuario);
		em.persist(leilao);
		
		Lance lance1 = new Lance(usuario,new BigDecimal("24000.00"));
		lance1.setLeilao(leilao);
		em.persist(lance1);
		Lance lance2 = new Lance(usuario,new BigDecimal("22000.00"));
		lance2.setLeilao(leilao);
		em.persist(lance2);
		Lance lance3 = new Lance(usuario,new BigDecimal("21000.00"));
		lance3.setLeilao(leilao);
		em.persist(lance3);

		List<Lance> lances = new ArrayList<>(
				  List.of(lance1,lance2,lance3)
				);
		leilao.setLances(lances);
		em.persist(leilao);
		
		Lance maiorLancaLeilao = dao.buscarMaiorLanceDoLeilao(leilao);
		
		assertEquals(new BigDecimal("24000.00"), maiorLancaLeilao.getValor());
		
//		BigDecimal max = lances.stream()
//				.max(Comparator.comparing(l -> l.getValor())).get().getValor();
		

	}

}
