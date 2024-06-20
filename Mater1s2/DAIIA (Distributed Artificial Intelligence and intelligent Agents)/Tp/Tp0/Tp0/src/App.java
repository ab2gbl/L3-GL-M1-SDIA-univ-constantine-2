import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class App extends Agent {

    @Override
    protected void setup() {
        System.out.println("Hello world!!");
        System.out.println("I am ready...");
    }

    public static void main(String[] args) {
        try {
            Runtime runtime = Runtime.instance();
            ProfileImpl profile = new ProfileImpl(false);
            profile.setParameter(Profile.CONTAINER_NAME,"NewContainer");
            AgentContainer agentContainer =runtime.createAgentContainer(profile);
            agentContainer.start();
            AgentController agentController =agentContainer.createNewAgent("FerstAgent","Agents.MyFerstAgent", new Object[]{});
            agentController.start();
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    
}