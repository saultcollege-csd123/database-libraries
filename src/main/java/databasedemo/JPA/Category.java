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
public class Category {
    
    @Id
    @GeneratedValue( strategy=GenerationType.AUTO )
    Integer id;
    
    String name;
    
    @ManyToMany(mappedBy = "categories")
    List<Book> books;
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public List<Book> getBooks() { return books; }
    public void setBooks(List<Book> books) { this.books = books; }
    
}
