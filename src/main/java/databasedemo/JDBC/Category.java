/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasedemo.JDBC;

import java.util.Objects;

/**
 *
 * @author rod
 */
public class Category {
    
    private Integer id;
    private String name;
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    // We need to implement equals so that comparisons in Lists work properly
    // when adding/removing Categories in the Book class
    @Override
    public boolean equals(Object o) {
        if ( o == null ) { return false; }
        
        if ( ! (o instanceof Category c) ) { return false; }

        return c.id.equals(this.id);
    }

    // Since we implement equals it's always a good idea to implement hashCode
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.name);
        return hash;
    }
    
}
