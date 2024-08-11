package Level3Version2;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Bavli bavli = new Bavli();
        bavli.setPath(Methods.createBavliDirctory()); // יצירת תיקיות השס ועדכון הנתיב ל"בבלי"
        Methods.createBookAndNames(bavli); // יצירת מערכים של מסכתות ושל שמות מסכתות
        boolean flag = true;
        while (flag){
            System.out.println("Press 1 for search page: ");
            System.out.println("Press 2 for exit: ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            switch (choice){
                case 1:
                    int bookL = Methods.checkValidBook(bavli); // בקשת שם ספר ואתחול המיקום שלו
                    int pageL = Methods.checkValidPage(bavli, bookL); // בקשת שם דף ואתחול המיקום שלו
                    File file = new File(bavli.getBooks()[bookL].getPageFathers()[pageL].getPath()); // יצירת file של הדף המבוקש באמצעות המיקומים
                    Methods.printPage(file); // הדפסת תוכן דף
                    break;
                case 2:
                    flag = false;
                    break;
            }
        }





    }
}
