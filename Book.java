package Level3Version2;

public class Book {
    private String name;
    private String path;
    private PageFather[] pageFathers;
    private String[] pageFatherNames;

    public Book(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public PageFather[] getPageFathers() {
        return pageFathers;
    }

    public void setPageFathers(PageFather[] pageFathers) {
        this.pageFathers = pageFathers;
    }

    public String[] getPageFatherNames() {
        return pageFatherNames;
    }

    public void setPageFatherNames(String[] pageFatherNames) {
        this.pageFatherNames = pageFatherNames;
    }
}
