public abstract class Holyday {
    private String name;
    private String date;
//    int[] dateIntArr;
    Human person;
    public Holyday(String name, String date){
        this.name = name;
        this.date = date;
//        parseDate(date);
    }
//    public void parseDate(String date){
//        String[] dateArr = date.split(".");
//        int[] dateIntArr = new int[dateArr.length];
//        for(int l = 0; l < 3; l++){
//            dateIntArr[l] = Integer.parseInt(dateArr[l]);
//        }
//        this.dateIntArr = dateIntArr;
//    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}

