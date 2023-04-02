public enum Feelings {
    HAPPY("счастлив"),
    ANGRY("зол"),
    GLAD("рад"),
    LIKE("нравится");

    private String feeling;
    Feelings(String feeling){
        this.feeling = feeling;
    }
    public String getFeeling(){return this.feeling;}
}
