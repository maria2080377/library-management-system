package library;

import java.util.ArrayList;
import java.util.List;

public class Library {
    // Поля класса
    private List<Book> books;
    private OperationLog operationLog;

    // Конструктор
    public Library() {
        this.books = new ArrayList<>();
        // TODO: инициализировать operationLog позже
        this.operationLog = null; // пока не инициализируем
    }

    // Методы (только заглушки без реализации)

    public void addBook(Book book) {
        // TODO: реализовать позже
        System.out.println("Метод addBook будет реализован позже");
    }

    public Book findBookById(int id) {
        // TODO: реализовать позже
        System.out.println("Метод findBookById будет реализован позже");
        return null;
    }

    public List<Book> findBooksByAuthor(String author) {
        // TODO: реализовать позже
        System.out.println("Метод findBooksByAuthor будет реализован позже");
        return new ArrayList<>();
    }

    public boolean borrowBook(int id) {
        // TODO: реализовать позже
        System.out.println("Метод borrowBook будет реализован позже");
        return false;
    }

    public boolean returnBook(int id) {
        // TODO: реализован позже
        System.out.println("Метод returnBook будет реализован позже");
        return false;
    }

    public List<Book> getAvailableBooks() {
        // TODO: реализовать позже
        System.out.println("Метод getAvailableBooks будет реализован позже");
        return new ArrayList<>();
    }

    // Вложенный статический класс - ТОЛЬКО ОБЪЯВЛЕНИЕ
    public static class OperationLog {
        // Пока пустой класс, реализуем во втором коммите
    }

    // Внутренний класс - ТОЛЬКО ОБЪЯВЛЕНИЕ
    public class LogEntry {
        // Пока пустой класс, реализуем во втором коммите
    }

    // Enum для типов операций - ТОЛЬКО ОБЪЯВЛЕНИЕ
    public enum OperationType {
        // Значения добавим во втором коммите
    }
}