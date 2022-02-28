package br.com.alura.leilao.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.ultil.JPAUtil;

@ActiveProfiles("test") 
class UsuarioDaoTest {
	
	private UsuarioDao dao;
	private EntityManager em;
	
	//private EntityManager em;
	
	@BeforeEach
	public void beforeEach() {
		em = JPAUtil.getEntityManager();
		this.dao = new UsuarioDao(em);
		em.getTransaction().begin();
	}
	
	@AfterEach
	public void afterEach() {
		em.getTransaction().rollback();
	}

	@Test
	void deveriaCadastrarUmLeilao() {
		Usuario usuario = criarUsuarioEPersiste();
		
		Usuario usuarioDoBanco = this.dao.buscarPorUsername(criarUsuarioEPersiste().getNome());
		Assert.assertNotNull(usuarioDoBanco);
	}
	
	
	private Usuario criarUsuarioEPersiste() {
		Usuario usuario = new Usuario("fulano", "fulano@email.com", "123");
		em.persist(usuario);
		return usuario;
	}

}
