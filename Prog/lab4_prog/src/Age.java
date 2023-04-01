public enum Age {
    BATTLE_SCARED("закаленное в боях"),
    WELL_WORN("поношенное"),
    FIELD_TESTED("после полевых испытаний"),
    MINIMAL_WEAR("немного поношенное"),
    FACTORY_NEW("прямо с завода");
    private String age;
    Age(String age){
        this.age = age;
    }
    public String getAge(){return this.age;}
}
