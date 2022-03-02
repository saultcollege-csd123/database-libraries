/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasedemo.JDBC;

/**
 *
 * @author rod
 */
public class Author {
    
    private Integer id;
    private String firstName;
    private String lastName;
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String name) { firstName = name; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String name) { lastName = name; }
    
    @Override
    public String toString() {
        return id + " " + firstName + " " + lastName;
    }
}
