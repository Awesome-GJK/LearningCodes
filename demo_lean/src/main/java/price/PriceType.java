package main.java.price;

/**
 * @author: gaojiankang
 * @Desc:
 * @create: 2025-01-24 15:50
 **/

public class PriceType {
    private int type;
    private String name;
    private double powerPrice;

    public PriceType(int type, String name, double powerPrice) {
        this.type = type;
        this.name = name;
        this.powerPrice = powerPrice;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPowerPrice() {
        return powerPrice;
    }

    public void setPowerPrice(double powerPrice) {
        this.powerPrice = powerPrice;
    }
}
