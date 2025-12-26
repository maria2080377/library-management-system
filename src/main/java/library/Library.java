package library;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;
    private OperationLog operationLog;

    public Library() {
        this.books = new ArrayList<>();
        this.operationLog = new OperationLog();
    }

    public void addBook(Book book) {
        books.add(book);
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

    public void printOperationLog() {
        operationLog.printLog();
    }

    public String getStatistics() {
        int totalBooks = books.size();
        int availableBooks = 0;
        int borrowedBooks = 0;

        for (Book book : books) {
            if (book.isAvailable()) {
                availableBooks++;
            } else {
                borrowedBooks++;
            }
        }

        return String.format("Статистика библиотеки:\n" +
                        "  Всего книг: %d\n" +
                        "  Доступно для выдачи: %d\n" +
                        "  Выдано читателям: %d\n" +
                        "  Заполненность библиотеки: %.1f%%",
                totalBooks,
                availableBooks,
                borrowedBooks,
                totalBooks > 0 ? (double) borrowedBooks / totalBooks * 100 : 0.0);
    }

    // ============================================
    // НОВЫЙ МЕТОД: removeBook() - ДОБАВЛЕН В ЭТОМ КОММИТЕ
    // ============================================
    /**
     * Удаляет книгу из библиотеки по ID
     * @param id идентификатор книги для удаления
     * @return true если книга найдена и удалена, false если книга не найдена
     */
    public boolean removeBook(int id) {
        Book bookToRemove = findBookById(id);

        if (bookToRemove != null) {
            books.remove(bookToRemove);
            operationLog.addEntry(OperationLog.OperationType.REMOVE_BOOK,
                    "Удалена книга: " + bookToRemove.getTitle() + " (ID: " + id + ")");
            return true;
        }

        return false;
    }

    // ============================================
    // ВЛОЖЕННЫЙ СТАТИЧЕСКИЙ КЛАСС (НУЖНО ОБНОВИТЬ ДЛЯ REMOVE_BOOK)
    // ============================================
    public static class OperationLog {
        // ОБНОВЛЕННЫЙ ENUM - добавлен REMOVE_BOOK
        public enum OperationType {
            ADD_BOOK, BORROW, RETURN, REMOVE_BOOK
        }

        public class LogEntry {
            private OperationType type;
            private LocalDateTime timestamp;
            private String description;

            public LogEntry(OperationType type, String description) {
                this.type = type;
                this.description = description;
                this.timestamp = LocalDateTime.now();
            }

            public OperationType getType() {
                return type;
            }

            public LocalDateTime getTimestamp() {
                return timestamp;
            }

            public String getDescription() {
                return description;
            }

            @Override
            public String toString() {
                return String.format("[%s] %s: %s",
                        timestamp, type, description);
            }
        }

        private List<LogEntry> entries;

        public OperationLog() {
            this.entries = new ArrayList<>();
        }

        public void addEntry(OperationType type, String description) {
            LogEntry entry = new LogEntry(type, description);
            entries.add(entry);
        }

        public List<LogEntry> getEntries() {
            return new ArrayList<>(entries);
        }

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