package br.com.pcinfo.loja.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.pcinfo.loja.models.Product;

@Repository
public class ProductDao {

	@PersistenceContext
	private EntityManager manager;

	public void save(Product product) {
		manager.persist(product);
	}
	
	public List<Product> lista() {
		return manager.createQuery("select distinct(p) from Product p join fetch p.prices", Product.class)
				.getResultList();
	}

	public Product find(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
