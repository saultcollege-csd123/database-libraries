/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasedemo.JDBC;

import java.sql.*;

/**
 *
 * @author rod
 */
public class Main {
    
    // These two lines are all you should need to change if you wanted to
    // use a different DB engine such as MySQL, MSSQL, PostgreSQL, Oracle, etc
    private static final String JDBC_DRIVER = "org.sqlite.JDBC";
    // Different databases have different syntaxes for their connection strings.
    // SQLite is quite simple and only requires a path (in this case 'demo.db').  
    // Most engines like MySQL and MSSQL require
    // usernames, passwords, and even ports to be specified as well.
    private static final String CONNECTION_STRING = "jdbc:sqlite:demo.db";
     
    public static void main(String[] args) {
        if ( args.length > 0 && args[0].equals("--regen") ) {
            regenDB();
        } else {
            sandbox();
        }
        
    }
    
    public static void sandbox() {
        
        try {
            Class.forName(JDBC_DRIVER);
        
            try (Connection db = DriverManager.getConnection(CONNECTION_STRING) ) {
                regenDB();
            }
            
        } catch ( Exception e ) {
            System.err.println(e);
            System.exit(0);
        }
    }
    
    public static void getAuthors(Connection db) throws SQLException {
        try (Statement stmt = db.createStatement()) {
            ResultSet results = stmt.executeQuery(
                    "SELECT * "
                    + "FROM Author "
                    + "ORDER BY last_name"
            );

            while (results.next()) {

                System.out.printf("%s %s, %s" + System.getProperty("line.separator"),
                        results.getInt("id"),
                        // or results.getInt(1)  <-- this is usually more efficient but less readable
                        results.getString("last_name"),
                        // or results.getInt(2)  <-- this is usually more efficient but less readable
                        results.getString("first_name")
                        // or results.getInt(3)  <-- this is usually more efficient but less readable
                );
            }

        }
    }
    
    public static void addAuthorBAD(Connection db, String firstName, String lastName) throws SQLException {
        
        // WARNING: This method is vulnerable to an SQL injection attack!
        // (Think about what could happen if someone entered on of the 
        // following values for lastName:
        //     D'Amour
        //     '); DROP TABLE Author; --
        // )
        //
        // NEVER USE UNESCAPED VALUES THAT A USER SUPPLIES IN A QUERY
        //
        // You should probably use a prepared statement instead (see addAuthor)
        try ( Statement stmt = db.createStatement() ) {
            stmt.execute("INSERT INTO Author (first_name, last_name) VALUES"
                    + "('" + firstName + "', '" + lastName + "')");
        }
    }
    
    public static void addAuthor(Connection db, String firstName, String lastName) throws SQLException {
        String sql = "INSERT INTO Author (first_name, last_name) VALUES (?, ?)";
        
        try ( PreparedStatement stmt = db.prepareStatement(sql) ) {
            // Using the PreparedStatement interface to set parameters in the
            // query is a safe way to use user-supplied values in a query.
            // The values supplied to the set* methods are automatically escaped
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            
            stmt.executeUpdate();
        }
    }
    
    public static void regenDB() { 
        
        // The JDBC Connection class is the main interface for talking to a database
        Connection db;
        
        try {
            // Register the JDBC driver.
            // Class.forName simply creates a new instance of the given class
            // Every JDBC Driver class has a special static initializer that 
            // registers itself with the JDBC DriverManager class.
            // This is how the specific db engine is tied into the JDBC API.
            Class.forName(JDBC_DRIVER);
            
            db = DriverManager.getConnection(CONNECTION_STRING);
            
            try (Statement stmt = db.createStatement()) {
                stmt.executeUpdate("DROP TABLE IF EXISTS Author");
                stmt.executeUpdate(
                        "CREATE TABLE Author ("
                                + "id INTEGER PRIMARY KEY,"
                                + "first_name VARCHAR(128),"
                                + "last_name VARCHAR(128)"
                                + ");"
                );
                
                stmt.executeUpdate(
                        "INSERT INTO Author (id, first_name, last_name) VALUES"
                                + "(1, 'Arthur', 'Conan Doyle'),"
                                + "(2, 'Mark', 'Twain'),"
                                + "(3, 'Isaac', 'Asimov'),"
                                + "(4, 'H.G.', 'Wells')"
                        
                );
                
                stmt.executeUpdate("DROP TABLE IF EXISTS Category");
                stmt.executeUpdate(
                        "CREATE TABLE Category ("
                                + "id INTEGER PRIMARY KEY,"
                                + "name VARCHAR(128)"
                                + ");"
                );
                stmt.executeUpdate(
                        "INSERT INTO Category (id, name) VALUES"
                                + "(1, 'Classic'),"
                                + "(2, 'Mystery'),"
                                + "(3, 'Science Fiction')"
                );
                
                stmt.executeUpdate("DROP TABLE IF EXISTS Book");
                stmt.executeUpdate(
                        "CREATE TABLE Book ("
                                + "id INTEGER PRIMARY KEY,"
                                + "author_id INTEGER,"
                                + "title VARCHAR(128),"
                                + "FOREIGN KEY ( author_id ) REFERENCES Author(id)"
                                + ");"
                );
                stmt.executeUpdate(
                        "INSERT INTO Book (id, author_id, title) VALUES"
                                + "(1, 1, 'The Adventures of Sherlock Holmes'),"
                                + "(2, 1, 'The Lost World and Other Thrilling Tales'),"
                                + "(3, 2, 'The Adventures of Tom Sawyer'),"
                                + "(4, 2, 'The Adventures of Huckleberry Finn'),"
                                + "(5, 3, 'Foundation'),"
                                + "(6, 3, 'I, Robot'),"
                                + "(7, 3, 'The End of Eternity'),"
                                + "(8, 4, 'The Time Machine'),"
                                + "(9, 4, 'The War of the Worlds')"
                );
                
                stmt.executeUpdate("DROP TABLE IF EXISTS Book_Category");
                stmt.executeUpdate(
                        "CREATE TABLE Book_Category ("
                                + "category_id INTEGER,"
                                + "book_id INTEGER,"
                                + "FOREIGN KEY ( category_id ) REFERENCES Category(id) ON DELETE CASCADE ON UPDATE CASCADE,"
                                + "FOREIGN KEY ( book_id ) REFERENCES Book(id) ON DELETE CASCADE ON UPDATE CASCADE"
                                + ");"
                );
                stmt.executeUpdate(
                        "INSERT INTO Book_Category (book_id, category_id) VALUES"
                                + "(1, 1),"
                                + "(1, 2),"
                                + "(2, 1),"
                                + "(3, 1),"
                                + "(4, 1),"
                                + "(5, 3),"
                                + "(6, 3),"
                                + "(7, 3),"
                                + "(8, 1),"
                                + "(8, 3),"
                                + "(9, 1),"
                                + "(9, 3)"
                );
                
                // This table is necessary so JPA can do automatic id generation
                // See https://github.com/netcharm/sqlite-jdbc/issues/30
                stmt.executeUpdate("DROP TABLE IF EXISTS sequence");
                stmt.executeUpdate("CREATE TABLE sequence (seq_name text, seq_count integer)");
                stmt.executeUpdate("insert into sequence (seq_name, seq_count) values ('SEQ_GEN', 10000)");

            }
            
            db.close();
        } catch ( Exception e ) {
            System.err.println(e);
            System.exit(0);
        }
        
        System.out.println("DB Created!");
    }
}
