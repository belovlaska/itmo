public class Book extends Item {
    private String author;
    private String nameofBook;
    public Book() {
        super("книга");
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public String getAuthor(){
        return author;
    }
    public void setNameofBook(String name){
        this.nameofBook = nameofBook;
    }
    public String getNameofBook(){
        return nameofBook;
    }
}
