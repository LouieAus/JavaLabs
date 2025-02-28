package com.louie.labwork4_library;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;

import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class App extends Application
{
    private TableView<Person> persons;
    private TableView<Book> table;
    private ObservableList<Book> books;
    private ObservableList<Person> pers;
    
    @Override
    public void start(Stage stage)
    {
        // --- Текст "Моя библиотека" ---
        Text text = new Text("Моя библиотека");
        text.setLayoutX(10);
        text.setLayoutY(15);
        
        // --- Кнопка "Добавить" ---
        Button addBookBtn = new Button();
        addBookBtn.setText("Добавить");
        addBookBtn.setLayoutX(10);
        addBookBtn.setLayoutY(20);
        addBookBtn.setOnAction(e ->
        {
            openAddBookWindow();
        });
        
        // --- Кнопка "Удалить" ---
        Button delBookBtn = new Button();
        delBookBtn.setText("Удалить");
        delBookBtn.setLayoutX(10);
        delBookBtn.setLayoutY(60);
        delBookBtn.setOnAction(event -> 
        {
            deleteSelectedBooks();
        });
        
        // --- Кнопка "Добавить читателя" ---
        Button addPersBtn = new Button();
        addPersBtn.setText("Добавить");
        addPersBtn.setLayoutX(10);
        addPersBtn.setLayoutY(520);
        addPersBtn.setOnAction(e ->
        {
            addPerson();
        });
        
        // --- Слайдер ---
        Slider slider = new Slider(0, 1, 1);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(0.5);
        slider.setBlockIncrement(0.1);
        slider.setLayoutX(620);
        slider.setLayoutY(520);
        
        // --- Таблица книг ---
        table = new TableView<>();
        table.setLayoutX(170);
        table.setLayoutY(10);
        table.setPrefWidth(600);
        table.setPrefHeight(490);
        
        TableColumn<Book, Integer> articleCol = new TableColumn<>("Артикул");
        articleCol.setCellValueFactory(new PropertyValueFactory<>("article"));
        articleCol.setPrefWidth(100);

        TableColumn<Book, String> titleCol = new TableColumn<>("Название книги");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleCol.setPrefWidth(250);

        TableColumn<Book, String> authorCol = new TableColumn<>("Автор");
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorCol.setPrefWidth(200);

        TableColumn<Book, Boolean> availableCol = new TableColumn<>("Наличие");
        availableCol.setCellValueFactory(new PropertyValueFactory<>("available"));
        availableCol.setPrefWidth(50);

        table.getColumns().addAll(articleCol, titleCol, authorCol, availableCol);
        
        // --- Таблица читателей ---
        persons = new TableView<>();
        persons.setLayoutX(10);
        persons.setLayoutY(100);
        persons.setPrefWidth(150);
        persons.setPrefHeight(400);
        
        TableColumn<Person, String> personCol = new TableColumn<>("Читатель");
        personCol.setCellValueFactory(new PropertyValueFactory<>("person"));
        personCol.setPrefWidth(100);
        
        TableColumn<Person, String> bookCol = new TableColumn<>("Книга");
        bookCol.setCellValueFactory(new PropertyValueFactory<>("book"));
        bookCol.setPrefWidth(50);
        
        persons.getColumns().addAll(personCol, bookCol);
        
        Group root = new Group(text, addBookBtn, delBookBtn, persons, addPersBtn, table, slider);
        
        Scene scene = new Scene(root, Color.WHITE);
        stage.setScene(scene);
        stage.setTitle("My Library");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.show();
        
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double brightness = newValue.doubleValue();
            Color backgroundColor = Color.color(brightness, brightness, brightness);
            scene.setFill(backgroundColor);
        });
        
        books = FXCollections.observableArrayList();
        pers = FXCollections.observableArrayList();
        
        try
        (
            Connection connection = DriverManager.getConnection("jdbc:sqlite:books.s3db");
            Statement statement = connection.createStatement();
        )
        {
            ResultSet rs = statement.executeQuery("select * from books");
            while(rs.next())
            {
                books.add(new Book(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4)));
            }
            
            rs = statement.executeQuery("select * from persons");
            while(rs.next())
            {
                pers.add(new Person(rs.getString(1), rs.getString(2)));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace(System.err);
        }
        
        table.setItems(books);
        persons.setItems(pers);
    }
    
    private void openAddBookWindow() {
        Stage addBookStage = new Stage();
        addBookStage.setTitle("Добавить книгу");

        // Поля для ввода данных
        javafx.scene.control.TextField articleField = new javafx.scene.control.TextField();
        javafx.scene.control.TextField titleField = new javafx.scene.control.TextField();
        javafx.scene.control.TextField authorField = new javafx.scene.control.TextField();
        javafx.scene.control.CheckBox availableCheckBox = new javafx.scene.control.CheckBox("Наличие");

        // --- Кнопка "ОК" ---
        Button okButton = new Button("OK");
        okButton.setOnAction(event -> {
            try
            {
                // Получаем данные из полей
                int article = Integer.parseInt(articleField.getText());
                String title = titleField.getText();
                String author = authorField.getText();
                boolean available = availableCheckBox.isSelected();

                // Добавляем новую книгу в таблицу
                books.add(new Book(article, title, author, available));
                
                try (
                    Connection connection = DriverManager.getConnection("jdbc:sqlite:books.s3db");
                    Statement statement = connection.createStatement();
                )
                {
                    Book last_book = books.get(books.size() - 1);
                    statement.execute("INSERT INTO books (article, title, author, available)\n" +
                            "VALUES (" + last_book.getArticle() +
                            ", '" + last_book.getTitle() +
                            "', '" + last_book.getAuthor() +
                            "', " + last_book.isAvailable() + ");");
                }
                catch(SQLException a)
                {
                    a.printStackTrace(System.err);
                }

                addBookStage.close();
            }
            catch (NumberFormatException e)
            {
                System.out.println("Ошибка: артикул должен быть числом!");
            }
        });

        // Контейнер для второго окна
        VBox addBookLayout = new VBox(10,
                new javafx.scene.control.Label("Артикул:"), articleField,
                new javafx.scene.control.Label("Название книги:"), titleField,
                new javafx.scene.control.Label("Автор:"), authorField,
                availableCheckBox, okButton);

        // Сцена для второго окна
        Scene addBookScene = new Scene(addBookLayout, 300, 250);
        addBookStage.setScene(addBookScene);
        addBookStage.show();
    }
    
    private void deleteSelectedBooks()
    {
        // Получаем выбранные строки
        ObservableList<Book> selectedBooks = table.getSelectionModel().getSelectedItems();

        // Удаляем выбранные книги из таблицы
        books.removeAll(selectedBooks);
        
        try (
            Connection connection = DriverManager.getConnection("jdbc:sqlite:books.s3db");
            Statement statement = connection.createStatement();
            )
        {
            for (int i = 0; i != selectedBooks.size(); i++)
            {
                statement.execute("DELETE FROM books WHERE article = " + selectedBooks.get(i).getArticle());
            }
        }
        catch(SQLException a)
        {
            a.printStackTrace(System.err);
        }
    }
    
    private void addPerson() {
        Stage addPersStage = new Stage();
        addPersStage.setTitle("Добавить читателя");

        // Поля для ввода данных
        javafx.scene.control.TextField personField = new javafx.scene.control.TextField();
        javafx.scene.control.TextField bookField = new javafx.scene.control.TextField();

        // --- Кнопка "ОК" ---
        Button okButton = new Button("OK");
        okButton.setOnAction(event -> {
            try
            {
                // Получаем данные из полей
                String per = personField.getText();
                String book = bookField.getText();

                // Добавляем нового читателя в таблицу
                pers.add(new Person(per, book));
                
                try (
                    Connection connection = DriverManager.getConnection("jdbc:sqlite:books.s3db");
                    Statement statement = connection.createStatement();
                )
                {
                    Person last_pers = pers.get(pers.size() - 1);
                    statement.execute("INSERT INTO persons (person, book)\n" +
                            "VALUES ('" + last_pers.getPerson() +
                            "', '" + last_pers.getBook() + "');");
                }
                catch(SQLException a)
                {
                    a.printStackTrace(System.err);
                }

                addPersStage.close();
            }
            catch (NumberFormatException e)
            {
                System.out.println("Ошибка: артикул должен быть числом!");
            }
        });

        // Контейнер для третьего окна
        VBox addBookLayout = new VBox(10,
                new javafx.scene.control.Label("Читатель:"), personField,
                new javafx.scene.control.Label("Название книги:"), bookField,
                okButton);

        // Сцена для третьего окна
        Scene addPersScene = new Scene(addBookLayout, 300, 250);
        addPersStage.setScene(addPersScene);
        addPersStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}