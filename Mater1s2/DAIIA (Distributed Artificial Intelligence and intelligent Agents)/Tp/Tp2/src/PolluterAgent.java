import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class PolluterAgent extends Agent {
    private Environment environment;

    @Override
    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            environment = (Environment) args[0];
            

            // Add a behavior to pollute a random cell every 3 seconds
            addBehaviour(new TickerBehaviour(this, 3000) {
                @Override
                protected void onTick() {
                    int x = (int) (Math.random() * 10);
                    int y = (int) (Math.random() * 10);

                    environment.polluteCell(x, y);

                    environment.printMatrix();


                    ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                    message.addReceiver(new AID("CleanerAgent", AID.ISLOCALNAME));
                    message.setContent(x + "," + y);
                    send(message);

                }
            });
        } else {
            System.err.println("Environment instance not provided to PolluterAgent");
            doDelete();
        }
    }
}
