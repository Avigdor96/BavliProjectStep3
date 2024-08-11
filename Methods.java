package Level3Version2;

import Level2.MyFuncLevel2;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Scanner;

public class Methods {
    //בדיקת סוג שורה ריקה, מסכת, דף או תוכן
    public static String typeLine(String line) {
        if (line.isEmpty()) {
            return "Empty";
        }
        if (line.startsWith("מסכת ") && line.contains("פרק א")) {
            return "Book";
        }
        if (line.startsWith("דף ") && (line.endsWith(" א") || line.endsWith(" ב")) && (line.split(" ").length < 4)) {
            return "Page";
        }
        if (line.startsWith("גמרא") || line.startsWith("משנה")) {
            return "Content";
        }
        return "";
    }

    // מקבלת מחרוזת ומחזירה את שם המסכת
    public static String correctBookName(String line) {
        return line.substring(0, line.indexOf(" פרק"));
    }

    //מקבלת מחרוזת ומחזירה את השם התקין של דף
    public static String correctNamePage(String line) {
        return line.split(" ")[0] + " " + line.split(" ")[1];
    }

    // יצירת תיקייה של הבבלי מחולק למסכתות ודפים ומחזירה את הנתיב
    public static String createBavliDirctory() {
        Bavli bavli = new Bavli();
        String path = "C:\\Users\\a0556\\OneDrive\\שולחן העבודה\\Bavli";
        File base = new File("C:\\Users\\a0556\\OneDrive\\שולחן העבודה\\Bavli");
        if (!base.exists()) {
            base.mkdir();

            String textPath = "C:\\Users\\a0556\\OneDrive\\שולחן העבודה\\__תלמוד בבלי טקסט_.txt";
            File fileOfText = new File(textPath);
            String currentBook = "";
            String currentFatherPage = "";
            String currentPage = "";
            try {
                Scanner scanner = new Scanner(fileOfText);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    switch (MyFuncLevel2.typeLine(line)) {
                        case "Book":
                            String correctBookName = MyFuncLevel2.correctBookName(line);
                            File newBook = new File(base, correctBookName);
                            if (!newBook.exists()) {
                                newBook.mkdir();
                            }
                            currentBook = newBook.getAbsolutePath().trim();
                            break;
                        case "Page":
                            File fatherPage = new File(currentBook, MyFuncLevel2.correctNamePage(line));
                            if (!fatherPage.exists()) {
                                fatherPage.mkdir();
                            }

                            currentFatherPage = currentBook + "/" + fatherPage.getName();
//
                            File file = new File(currentFatherPage, line);
                            if (!file.exists()) {
                                file.createNewFile();
                            }

                            currentPage = file.getAbsolutePath().trim();
                            break;


                        case "Content":
                            FileWriter fw = new FileWriter(currentPage, true);
                            fw.write(line + "\n");
                            fw.close();
                            break;

                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }

        }
        return path;
    }
    //מקבל "בבלי" ומייצר מערך של מסכתות ומערך של השמות שלהם
    public static void createBookAndNames(Bavli bavli) {
        File file = new File(bavli.getPath());
        File[] files = file.listFiles();
        String[] namesBooks = new String[files.length];
        Book[] books = new Book[files.length];
        for (int i = 0; i < files.length; i++) {
            namesBooks[i] = files[i].getName();
            books[i] = new Book(files[i].getName());
            books[i].setPath(files[i].getPath());
        }
        bavli.setBooksNames(namesBooks);
        bavli.setBooks(books);
        createFatherPages(books);
    }
    //מקבל מערך של ספרים ויוצר מערך של דפים ומערך של שמות הדפים
    public static void createFatherPages(Book[] books) {
        for (int i = 0; i < books.length; i++) {
            File file = new File(books[i].getPath());
            File[] files = file.listFiles();
            String[] pageFathersNames = new String[files.length];
            PageFather[] pageFathers = new PageFather[files.length];
            for (int j = 0; j < pageFathersNames.length; j++) {
                pageFathersNames[j] = (files[j].getName());
                pageFathers[j] = new PageFather(files[j].getName());
                pageFathers[j].setPath(files[j].getPath());
            }
            books[i].setPageFathers(pageFathers);
            books[i].setPageFatherNames(pageFathersNames);
        }
    }
    // מקבלת שם של ספר ומערך שמות הספרים ומחזיר את המיקום של הספר
    public static int exportLocationBook(String bookName, String[] booksNames) {
        int location = binarySearch(booksNames, bookName);
        return location;
    }
    // בקשת שם של ספר בודק מבערך השמות ומחזיר מיקום של הספר על ידי קריאה לפונקציה
    public static int checkValidBook(Bavli bavli) {
        System.out.println("Enter name of book: ");
        Scanner scanner = new Scanner(System.in);
        String name = "מסכת " + scanner.nextLine();
        int i = exportLocationBook(name, bavli.getBooksNames());
        if (i == -1) {
            System.out.println("Book not found enter again.");
            return checkValidBook(bavli);
        }
        return i;
    }
    // מקבל שם של דף ומערך שמות הדפים של מסכת ומחזיר מיקום של הדף
    public static int exportLocationPage(String pageName, String[] pagesNames) {
        int location = binarySearch(pagesNames, pageName);
        return location;
    }
    // מקבל מיקום של ספר ומבקש דף בודק תקינות הדף ומחזיר את המיקום שלו על ידי קריאה לפונקציה
    public static int checkValidPage(Bavli bavli, int bookLocation) {
        System.out.println("Enter name of page: ");
        Scanner scanner = new Scanner(System.in);
        String page = "דף " + scanner.nextLine();
        //חילוץ מיקום דף
        int i = exportLocationPage(page, bavli.getBooks()[bookLocation].getPageFatherNames());
        if (i == -1) {
            System.out.println("Page not found enter again.");
            return checkValidPage(bavli, bookLocation); // אם הדף לא נמצא
        }
        return i; // החזרת מיקום קובץ
    }

    // מקבל קובץ ומדפיס את תוכן הדף
    public static void printPage(File file) {
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file1 = files[i];
            try {
                Scanner scanner1 = new Scanner(file1);
                while (scanner1.hasNextLine()) {
                    String line = scanner1.nextLine();
                    System.out.println(line);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    // חיפוש בינארי על מערך של מחרוזות
    public static int binarySearch(String[] strings, String string) {
        Arrays.sort(strings);
        int mid = 0;
        int low = 0;
        int high = strings.length - 1;
        while (high >= low) {
            mid = (low + high) / 2;
            int x = strings[mid].compareTo(string);
            if (x == 0) {
                return mid;  // מחזיר מיקום
            } else if (x < 0) {
                low = mid + 1;
            } else if (x > 0) {
                high = mid - 1;
            }
        }
        return -1; // אם לא נמצא
    }

}