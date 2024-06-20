import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class MyFirstAgent extends Agent {

    @Override
    protected void setup() {
        System.out.println("Hello world!!");
        System.out.println("I am ready...");
    }
    public static void main(String[] args) {
        try {
            // Initialize the JADE runtime
            Runtime runtime = Runtime.instance();

            // Create a profile with the specified properties
            ProfileImpl profile = new ProfileImpl(false);
            profile.setParameter(Profile.CONTAINER_NAME, "NewContainer");

            // Create the agent container
            AgentContainer agentContainer = runtime.createAgentContainer(profile);
            agentContainer.start();

            // Create and start the agent
            AgentController agentController = agentContainer.createNewAgent(
                    "MyFerstAgent",     // Agent name
                    "MyFirstAgent",     // Fully qualified class name of the agent
                    new Object[]{});    // Arguments to be passed to the agent's setup method

            agentController.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
