package library;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Library {
    // Поля класса
    private List<Book> books;
    private OperationLog operationLog;

    // Конструктор - ТЕПЕРЬ С ИНИЦИАЛИЗАЦИЕЙ
    public Library() {
        this.books = new ArrayList<>();
        this.operationLog = new OperationLog(); // теперь инициализируем
    }

    // Методы (теперь с реализацией)

    public void addBook(Book book) {
        books.add(book);
        // Используем OperationLog для записи операции
        operationLog.addEntry(OperationLog.OperationType.ADD_BOOK,
                "Добавлена книга: " + book.getTitle());
    }

    public Book findBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    public List<Book> findBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                result.add(book);
            }
        }
        return result;
    }

    public boolean borrowBook(int id) {
        Book book = findBookById(id);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            operationLog.addEntry(OperationLog.OperationType.BORROW,
                    "Выдана книга: " + book.getTitle());
            return true;
        }
        return false;
    }

    public boolean returnBook(int id) {
        Book book = findBookById(id);
        if (book != null && !book.isAvailable()) {
            book.setAvailable(true);
            operationLog.addEntry(OperationLog.OperationType.RETURN,
                    "Возвращена книга: " + book.getTitle());
            return true;
        }
        return false;
    }

    public List<Book> getAvailableBooks() {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.isAvailable()) {
                result.add(book);
            }
        }
        return result;
    }

    // Новый метод для вывода журнала операций
    public void printOperationLog() {
        operationLog.printLog();
    }

    // ============================================
    // ВЛОЖЕННЫЙ СТАТИЧЕСКИЙ КЛАСС - ПОЛНАЯ РЕАЛИЗАЦИЯ
    // ============================================
    public static class OperationLog {
        // Enum для типов операций - РЕАЛИЗОВАН
        public enum OperationType {
            ADD_BOOK, BORROW, RETURN
        }

        // ============================================
        // ВНУТРЕННИЙ КЛАСС - ПОЛНАЯ РЕАЛИЗАЦИЯ
        // ============================================
        public class LogEntry {
            // Поля внутреннего класса
            private OperationType type;
            private LocalDateTime timestamp;
            private String description;

            // Конструктор внутреннего класса
            public LogEntry(OperationType type, String description) {
                this.type = type;
                this.description = description;
                this.timestamp = LocalDateTime.now();
            }

            // Геттеры внутреннего класса
            public OperationType getType() {
                return type;
            }

            public LocalDateTime getTimestamp() {
                return timestamp;
            }

            public String getDescription() {
                return description;
            }

            // toString внутреннего класса
            @Override
            public String toString() {
                return String.format("[%s] %s: %s",
                        timestamp, type, description);
            }
        }

        // Поле вложенного класса
        private List<LogEntry> entries;

        // Конструктор вложенного класса
        public OperationLog() {
            this.entries = new ArrayList<>();
        }

        // Методы вложенного класса

        // Добавление записи в журнал
        public void addEntry(OperationType type, String description) {
            LogEntry entry = new LogEntry(type, description);
            entries.add(entry);
        }

        // Получение всех записей
        public List<LogEntry> getEntries() {
            return new ArrayList<>(entries);
        }

        // Печать журнала
        public void printLog() {
            System.out.println("=== ЖУРНАЛ ОПЕРАЦИЙ ===");
            if (entries.isEmpty()) {
                System.out.println("Журнал пуст");
                return;
            }

            for (LogEntry entry : entries) {
                System.out.println(entry);
            }
            System.out.println("=======================");
        }
    }
}