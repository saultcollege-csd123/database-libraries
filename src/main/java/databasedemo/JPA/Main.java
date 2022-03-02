/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasedemo.JPA;

import javax.persistence.*;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 *
 * @author rod
 */
public class Main {
    
    public static void main(String[] args) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("bookstore");
        EntityManager em = emfactory.createEntityManager();
//        em.getTransaction().begin();
        
        Author author = em.find(Author.class, 3);

        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Author.class);
        Root<Author> from = cq.from(Author.class);
        var select = cq.select(from);
        var q = em.createQuery(select);
        var authors = q.getResultList();

        for ( Author a : authors ) {
            for (Book b : a.getBooks()) {
                System.out.println(b);
            }
        }
        
        printBooksInCategory(em, "Classic");

//        Author a = new Author();
//        a.setFirstName("Douglas");
//        a.setLastName("Adams");
//        em.persist(a);
//
//        Book b = new Book();
//        b.setTitle("The Hitchiker's Guide to the Galaxy");
//        b.setAuthor(a);
//        em.persist(b);
//
//        em.getTransaction().commit();


        em.close();
        emfactory.close();

    }
    
    public static void printBooksInCategory(EntityManager em, String categoryName ) {
        TypedQuery<Book> q = em.createQuery(
                "SELECT b FROM Book b "
                + "JOIN b.categories cat "
                + "WHERE cat.name = :catname",
                Book.class
        );
        
        q.setParameter("catname", categoryName);

        q.getResultList().forEach(System.out::println);
        
    }
    
}
