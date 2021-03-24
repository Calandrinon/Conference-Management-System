public class Paper {
    private String name;
    private String subject;
    private String author;
    private String title;
    private String filename;

    public Paper(String name, String subject, String author, String title, String filename) {
        this.name = name;
        this.subject = subject;
        this.author = author;
        this.title = title;
        this.filename = filename;
    }


    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getFilename() {
        return filename;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}