/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasedemo.JPA;

import javax.persistence.*;
import java.util.List;

/**
 *
 * @author rod
 */
@Entity
public class Book {
    
    @Id
    @GeneratedValue( strategy=GenerationType.AUTO )
    Integer id;
    
    String title;
    
    @ManyToOne
    Author author;
    
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "Book_Category", 
            joinColumns = { @JoinColumn(name="book_id") },
            inverseJoinColumns = { @JoinColumn(name="category_id") }
    )
    List<Category> categories;
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String t) { title = t; }
    
    public Author getAuthor() { return author; }
    public void setAuthor(Author a) { author = a; }
    
    public List<Category> getCategories() { return categories; }
    public void setCategories(List<Category> categories) { this.categories = categories; }
 
    @Override
    public String toString() {
        return title + ", by " + author.getFirstName() + " " + author.getLastName();
    }
}
