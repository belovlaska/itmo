public enum Age {
    BATTLESCARED("закаленное в боях"),
    WELLWORN("поношенное"),
    FIELDTESTED("после полевых испытаний"),
    MINIMALWEAR("немного поношенное"),
    FACTORYNEW("прямо с завода");
    private String age;
    Age(String age){
        this.age = age;
    }
    public String getAge(){return this.age;}
}
