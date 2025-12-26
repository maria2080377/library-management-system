package library;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Создаем библиотеку
        Library library = new Library();

        System.out.println("=== СОЗДАНИЕ БИБЛИОТЕКИ И ДОБАВЛЕНИЕ КНИГ ===\n");

        // Добавление книг
        library.addBook(new Book(1, "Война и мир",
                "Л.Н. Толстой", 1869, "978-5-17-090335-2"));
        library.addBook(new Book(2, "Преступление и наказание",
                "Ф.М. Достоевский", 1866, "978-5-17-090336-9"));
        library.addBook(new Book(3, "Анна Каренина",
                "Л.Н. Толстой", 1877, "978-5-17-090337-6"));
        library.addBook(new Book(4, "Мастер и Маргарита",
                "М.А. Булгаков", 1967, "978-5-17-090338-3"));
        library.addBook(new Book(5, "Евгений Онегин",
                "А.С. Пушкин", 1833, "978-5-17-090339-0"));

        // Демонстрация поиска книг
        System.out.println("=== ПОИСК КНИГ ===");

        // Поиск по ID
        Book book1 = library.findBookById(2);
        if (book1 != null) {
            System.out.println("Найдена книга по ID 2:");
            System.out.println(book1);
            System.out.println();
        }

        // Поиск по автору
        System.out.println("Книги Л.Н. Толстого:");
        List<Book> tolstoyBooks = library.findBooksByAuthor("Л.Н. Толстой");
        for (Book book : tolstoyBooks) {
            System.out.println(book);
        }
        System.out.println();

        // Выдача и возврат книг
        System.out.println("=== ВЫДАЧА И ВОЗВРАТ КНИГ ===");

        // Выдача книги
        System.out.println("Пытаемся выдать книгу с ID 1...");
        if (library.borrowBook(1)) {
            System.out.println("Книга успешно выдана!");
        } else {
            System.out.println("Книга не найдена или уже выдана.");
        }

        // Пытаемся выдать ту же книгу еще раз
        System.out.println("Пытаемся выдать книгу с ID 1 еще раз...");
        if (library.borrowBook(1)) {
            System.out.println("Книга успешно выдана!");
        } else {
            System.out.println("Книга не найдена или уже выдана.");
        }

        // Выдаем другую книгу
        System.out.println("Пытаемся выдать книгу с ID 3...");
        library.borrowBook(3);

        // Возврат книги
        System.out.println("Возвращаем книгу с ID 1...");
        if (library.returnBook(1)) {
            System.out.println("Книга успешно возвращена!");
        } else {
            System.out.println("Книга не найдена или уже в библиотеке.");
        }
        System.out.println();

        // Список доступных книг
        System.out.println("=== ДОСТУПНЫЕ КНИГИ ===");
        List<Book> availableBooks = library.getAvailableBooks();
        for (Book book : availableBooks) {
            System.out.println(book);
        }
        System.out.println();

        // Журнал операций
        System.out.println("=== ЖУРНАЛ ОПЕРАЦИЙ ===");
        library.printOperationLog();

        System.out.println("\n=== ДЕМОНСТРАЦИЯ ЗАВЕРШЕНА ===");
    }
}