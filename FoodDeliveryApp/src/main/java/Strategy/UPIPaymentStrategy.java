package Strategy;

public class UPIPaymentStrategy extends PayementStrategy{

    @Override
    public void pay(double amount) {
        System.out.println("Payment Done through UPI");
    }

}
