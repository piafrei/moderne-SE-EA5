public class Player {
    private String name;
    private int number;
    private String sign;

    Player(int number) {
        this.number = number;
    }

    public Player() {
    }

    void setName(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    int getNumber() {
        return number;
    }

    String getSign() {
        return sign;
    }

    void setSign(String sign) {
        this.sign = sign;
    }
}
