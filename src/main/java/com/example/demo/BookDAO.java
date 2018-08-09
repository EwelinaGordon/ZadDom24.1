package com.example.demo;

import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/library?characterEncoding=utf8&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private Connection connection;

    public BookDAO() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("No driver found");
        } catch (SQLException e) {
            System.out.println("Could not establish connection");
        }
    }


    public void save(Book book) {
        final String query = "insert into book(category, isbn, published_date, title) values (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, book.getCategory().toString());
            preparedStatement.setString(2, book.getIsbn());
            preparedStatement.setDate(3, Date.valueOf(book.getPublishedDate()));
            preparedStatement.setString(4, book.getTitle());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not save record");
        }
    }

    public List<Book> readBooks() {
        List<Book> resultList = new ArrayList<>();
        final String query = "select id, category, isbn, published_date, title from book";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Book book = new Book();
                book.setId(result.getLong("id"));
                book.setCategory(Category.valueOf(result.getString("category")));
                book.setIsbn(result.getString("isbn"));
                book.setPublishedDate(result.getDate("published_date").toLocalDate());
                book.setTitle(result.getString("title"));
                resultList.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Could not find book");
        }
        return resultList;
    }

    public void update(Book book) {
        final String query = "update book set isbn = ? where id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, book.getIsbn());
            preparedStatement.setLong(2, book.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not update item");
        }
    }

    public void delete(int id) {
        final String query = "delete from book where id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not delete item");
        }

    }

}
