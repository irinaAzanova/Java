public class Passenger {
    private final int survived;
    private final String sex;
    private final double age;
    private final String ticket;
    private final double fare;
    private final String embarked;

    public Passenger(String embarked, String sex, String ticket, int survived, double age, double fare) {
        this.survived = survived;
        this.sex = sex;
        this.age = age;
        this.ticket = ticket;
        this.fare = fare;
        this.embarked = embarked;
    }

    public int getSurvived() {
        return survived;
    }

    public String getSex() {
        return sex;
    }

    public double getAge() {
        return age;
    }

    public String getTicket() {
        return ticket;
    }

    public double getFare() {
        return fare;
    }

    public String getEmbarked() {
        return embarked;
    }
}
