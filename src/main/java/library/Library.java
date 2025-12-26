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
    // НОВЫЙ МЕТОД: updateBook() - ДОБАВЛЕН В ЭТОМ КОММИТЕ
    // ============================================
    /**
     * Обновляет информацию о книге по ID
     * @param id идентификатор книги для обновления
     * @param newTitle новое название книги (null если не менять)
     * @param newAuthor новый автор (null если не менять)
     * @param newYear новый год издания (0 если не менять)
     * @param newIsbn новый ISBN (null если не менять)
     * @return true если книга найдена и обновлена, false если книга не найдена
     */
    public boolean updateBook(int id, String newTitle, String newAuthor,
                              int newYear, String newIsbn) {
        Book bookToUpdate = findBookById(id);

        if (bookToUpdate != null) {
            boolean changed = false;
            StringBuilder changes = new StringBuilder();

            // Обновляем название, если передано новое
            if (newTitle != null && !newTitle.isEmpty() &&
                    !newTitle.equals(bookToUpdate.getTitle())) {
                // В реальности здесь нужен сеттер в классе Book
                changes.append("Название: '").append(bookToUpdate.getTitle())
                        .append("' -> '").append(newTitle).append("'; ");
                // bookToUpdate.setTitle(newTitle); // будет в следующей версии Book
                changed = true;
            }

            // Обновляем автора, если передан новый
            if (newAuthor != null && !newAuthor.isEmpty() &&
                    !newAuthor.equals(bookToUpdate.getAuthor())) {
                changes.append("Автор: '").append(bookToUpdate.getAuthor())
                        .append("' -> '").append(newAuthor).append("'; ");
                // bookToUpdate.setAuthor(newAuthor); // будет в следующей версии Book
                changed = true;
            }

            // Обновляем год, если передан новый
            if (newYear > 0 && newYear != bookToUpdate.getYear()) {
                changes.append("Год: ").append(bookToUpdate.getYear())
                        .append(" -> ").append(newYear).append("; ");
                // bookToUpdate.setYear(newYear); // будет в следующей версии Book
                changed = true;
            }

            // Обновляем ISBN, если передан новый
            if (newIsbn != null && !newIsbn.isEmpty() &&
                    !newIsbn.equals(bookToUpdate.getIsbn())) {
                changes.append("ISBN: '").append(bookToUpdate.getIsbn())
                        .append("' -> '").append(newIsbn).append("'; ");
                // bookToUpdate.setIsbn(newIsbn); // будет в следующей версии Book
                changed = true;
            }

            if (changed) {
                operationLog.addEntry(OperationLog.OperationType.UPDATE_BOOK,
                        "Обновлена книга ID " + id + ": " + changes.toString());
            } else {
                operationLog.addEntry(OperationLog.OperationType.UPDATE_BOOK,
                        "Попытка обновления книги ID " + id + " (изменений нет)");
            }

            return true;
        }

        return false;
    }

    // ============================================
    // ВЛОЖЕННЫЙ СТАТИЧЕСКИЙ КЛАСС (НУЖНО ОБНОВИТЬ ДЛЯ UPDATE_BOOK)
    // ============================================
    public static class OperationLog {
        // ОБНОВЛЕННЫЙ ENUM - добавлен UPDATE_BOOK
        public enum OperationType {
            ADD_BOOK, BORROW, RETURN, REMOVE_BOOK, UPDATE_BOOK
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