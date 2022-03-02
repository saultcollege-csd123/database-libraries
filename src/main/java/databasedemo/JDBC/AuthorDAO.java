/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasedemo.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author rod
 */
public class AuthorDAO {
    
    private Connection db;
    
    public AuthorDAO(Connection db) {
        this.db = db;
    }
    
    public Author getById(Integer id) {
        try {
            String sql = "SELECT * FROM Author WHERE id = ?";
            try ( PreparedStatement stmt = db.prepareStatement(sql) ) {
                stmt.setInt(1, id);
                
                ResultSet r = stmt.executeQuery();
                
                if ( r.next() ) {
                    Author a = new Author();
                    a.setId(r.getInt(1));
                    a.setFirstName(r.getString(2));
                    a.setLastName(r.getString(3));
                    return a;
                }
            }
        } catch ( Exception e ) {
            System.err.println(e);
        }
        
        return null;
    }
        
    public void save(Author author) {
        
    }
}
