package Level3Version2;

import java.util.Arrays;

public class Test {
    public static int binarySearch(String[] strings, String string) {
        Arrays.sort(strings);
        //System.out.println(Arrays.toString(strings));
        int mid = -1;
        int low = 0;
        int high = strings.length - 1;
        while (high >= low) {
            mid = (low + high) / 2;
            int x = strings[mid].compareTo(string);
            if (x == 0) {
                return mid;
            } else if (x < 0) {
                low = mid + 1;
            } else if (x > 0) {
                high = mid - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        String[] string = {"ברכות", "שבת", "עירובין", "פסחים", "שקלים", "יומא", "סוכה", "ביצה", "ראש השנה", "תענית",
                "מגילה", "מועד קטן", "חגיגה", "יבמות", "כתובות", "נדרים", "נזיר", "סוטה", "גיטין", "קידושין",
                "בבא קמא", "בבא מציעא", "בבא בתרא", "סנהדרין", "מכות", "שבועות", "עבודה זרה", "הוריות",
                "זבחים", "מנחות", "חולין", "בכורות", "ערכין", "תמורה", "כריתות", "מעילה", "תמיד", "מדות", "קינים",
                "כלים", "אהלות", "נגעים", "פרה", "טהרות", "מקוואות", "נידה"};
        System.out.println(binarySearch(string, ""));
    }

}