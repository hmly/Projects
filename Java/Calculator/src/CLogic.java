/**
 * Created by hmly on 6/29/16.
 * Calculator logic
 */
class CLogic {
    private double currentTotal;

    CLogic() {
        currentTotal = 0;
    }
    
    void setTotal(String n) {
        currentTotal = Double.parseDouble(n);
    }

    void addDec(String n) {
        double x = Double.parseDouble(n) / (n.length() * 10);
        currentTotal += x;
    }

    void add(String n) {
        currentTotal += Double.parseDouble(n);
    }

    void subtract(String n) {
        currentTotal -= Double.parseDouble(n);
    }

    void multiply(String n) {
        currentTotal *= Double.parseDouble(n);
    }

    void divide(String n) {
        currentTotal /= Double.parseDouble(n);
    }

    void modulo(String n) {
        currentTotal %= Double.parseDouble(n);
    }

    void reciprocal(String n) {
        currentTotal = 1 / currentTotal;
    }

    void square(String n) {
        currentTotal *= currentTotal;
    }

    void sqroot(String n) {
        currentTotal = Math.sqrt(currentTotal);
    }

    void sin(String n) {
        currentTotal = Math.sin(currentTotal);
    }

    void cos(String n) {
        currentTotal = Math.cos(currentTotal);
    }

    void tan(String n) {
        currentTotal = Math.tan(currentTotal);
    }

    public String toString() {
        if ((currentTotal == Math.floor(currentTotal)) && Double.isFinite(currentTotal)) {
            System.out.println("in");
            return String.format("%d", (long) currentTotal);
        } else {
            return Double.toString(currentTotal);
        }
    }
}
