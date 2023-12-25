package resource;

public class Bill {
    public int days;
    public String demage;
    public int rent;
    public int distance;
    public Bill(int day,String demage,int rent,int distance){
        this.days=day;
        this.demage=demage;
        this.rent=rent;
        this.distance=distance;
    }
    @Override
    public String toString(){
        return ""+days+"-"+demage+"-"+rent+"-"+distance;
    }
}
