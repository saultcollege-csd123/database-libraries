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
public class Author {
    
    @Id
    @GeneratedValue( strategy=GenerationType.AUTO )
    private Integer id;
            
    // When the DB column name differs from the field name we have to specify
    // the correct mapping
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    
    @OneToMany(mappedBy="author")
    //@JoinColumn(name = "author_id")
    private List<Book> books;
    
    // Any fields marked with the @Transient attribute will be ignored by JPA
    @Transient
    private String somethingTransient;
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String name) { firstName = name; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String name) { lastName = name; }
    
    public List<Book> getBooks() { return books; }
    public void setBooks(List<Book> books) { this.books = books; }
    
    @Override
    public String toString() {
        return id + " " + firstName + " " + lastName;
    }
}
