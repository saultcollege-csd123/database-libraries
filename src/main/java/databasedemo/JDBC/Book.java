/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasedemo.JDBC;

import java.util.List;

/**
 *
 * @author rod
 */
public class Book {
    
    private Integer id;
    private String title;
    private Author author;
    private List<Category> categories;
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String t) { title = t; }
    
    public Author getAuthor() { return author; }
    public void setAuthor(Author a) { author = a; }
    
    public List<Category> getCategories() { return categories; }
    public void addToCategory(Category c) {
        
        // Don't add it again if it's already in there
        if ( categories.contains(c) ) { return; }
        
        categories.add(c);
    }
    public void removeFromCategory(Category c) {
        categories.remove(c);
    }
    
}
