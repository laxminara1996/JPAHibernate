package com.ait.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.ait.dao.ProductDAO;
import com.ait.entity.ProductEntity;

public class ProductDAOImpl implements ProductDAO {
	
	private EntityManagerFactory factory;
	
	public ProductDAOImpl() {
		factory = Persistence.createEntityManagerFactory("test");
	}
			
	

	public ProductEntity saveProduct(ProductEntity product) {
		
		EntityManager entityManager = factory.createEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		try {
			entityManager.persist(product);
			tx.commit();
			System.out.println("Product is inserted to the Database....");
		}
		catch(Exception ex) {
			tx.rollback();
			System.out.println("Error in inserting a product...." + ex);
		}
		finally {
			entityManager.close();
		}
		
		return product;
	}

	public ProductEntity loadProductById(Integer productId) {
		EntityManager entityManager = factory.createEntityManager();
		/*
		 * find() : early load
		 * getReference() : lazy load
		 */
		ProductEntity p = entityManager.find(ProductEntity.class, productId);
		
		entityManager.close();
		return p;
	}

	public ProductEntity updateProductById(Integer productId, Double new_Unit_Price) {
		EntityManager entityManager = factory.createEntityManager();
		ProductEntity p = entityManager.find(ProductEntity.class, productId);
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		p.setUnitPrice(new_Unit_Price);
		tx.commit();
		entityManager.close();
		return p;
	}

	public void deleteProductById(Integer productId) {
		EntityManager entityManager = factory.createEntityManager();
		ProductEntity p = entityManager.find(ProductEntity.class, productId);
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.remove(p);
		tx.commit();
		entityManager.close();
	}



	public void testEntityStates() {
		// TODO Auto-generated method stub
		EntityManager entityManager = factory.createEntityManager();
      /* create new product */
		/*ProductEntity pe = new ProductEntity();//transient state
		pe.setProductId(345);
		pe.setProductName("nag");
		pe.setQuantity(340);
		pe.setUnitPrice(20.0);*/
		ProductEntity pe = entityManager.find(ProductEntity.class, 345);
		//pe is in persistence state
		
		
		/*EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.persist(pe);//persistence state
		tx.commit();*/
		entityManager.detach(pe);//detaches state
		pe.setUnitPrice(34.0);//the change made entity in detached state don't effect in db
		EntityTransaction t = entityManager.getTransaction();
		t.begin();
		entityManager.merge(pe);//moved from detached state to persistence
		t.commit();
		entityManager.close();
	}

}
