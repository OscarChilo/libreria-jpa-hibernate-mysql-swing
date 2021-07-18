package com.orcas;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class LibrosManager {
	
	static EntityManagerFactory factory;
	static EntityManager entityManager;

	public static void main(String[] args) {
		
		begin();
		//create();
		//find();
		//update();
		//query();
		remove();
		end();
		
	}
	
	private static void query() {
		String jpql="Select b from Libro b Where b.precio<30";		
		Query query=entityManager.createQuery(jpql);
		
		@SuppressWarnings("unchecked")
		List<Libro> listLibro=query.getResultList();
		
		for(Libro a : listLibro) {
			System.out.println(a.getTitulo()+","+a.getAutor()+","+a.getPrecio());
		}
	}

	private static void create() {
		Libro newLibro=new Libro();
		
		newLibro.setTitulo("los pasos en el vacio");
		newLibro.setAutor("oscar chilo");
		newLibro.setPrecio(20);
		
		entityManager.persist(newLibro);
	}
	
	private static void update() {
		Libro existLibro=new Libro();
		
		existLibro.setID(4);
		existLibro.setTitulo("la luz en el vacio");
		existLibro.setAutor("oscar chilo");
		existLibro.setPrecio(15);
		
		entityManager.merge(existLibro);		
	}
	
	private static void find() {
		Integer primary_key=3;		
		Libro libro=entityManager.find(Libro.class, primary_key);
		
		System.out.println(libro.getID());
		System.out.println(libro.getTitulo());
		System.out.println(libro.getAutor());
		System.out.println(libro.getPrecio());				
	}
	
	private static void remove() {
		Integer primary_key=2;
		Libro reference=entityManager.getReference(Libro.class, primary_key);
		entityManager.remove(reference);
	}

	private static void begin() {
		factory=Persistence.createEntityManagerFactory("LibroUnit");
		entityManager=factory.createEntityManager();
		
		entityManager.getTransaction().begin();
	}
	
	private static void end() {
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
	}

	

}
