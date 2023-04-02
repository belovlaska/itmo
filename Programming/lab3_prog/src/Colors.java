public enum Colors {
    RED("красные"),
    BLUE("синие"),
    GREEN("зеленые"),
    YELLOW("желтые"),
    WHITE("белые"),
    ORANGE("оранжевые"),
    BLACK("черные");

    private String color;
    Colors(String color){
        this.color = color;
    }
    public String getColor(){return this.color;}
}
