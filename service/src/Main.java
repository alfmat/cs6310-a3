import edu.gatech.cs6310.Service.DeliveryService;

public class Main {

    public static void main(String[] args) {
        DeliveryService simulator = new DeliveryService();
        simulator.commandLoop();
    }
}
