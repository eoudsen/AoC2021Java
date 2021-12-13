package day13;

public class Fold {

    private String direction;
    private Integer value;

    public Fold(String direction, Integer value) {
        this.direction = direction;
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public String getDirection() {
        return direction;
    }
}
