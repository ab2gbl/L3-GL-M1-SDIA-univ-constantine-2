import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentController;

public class MASMain {
    public static void main(String[] args) {
        try {
            Properties properties = new ExtendedProperties();
            properties.setProperty(Profile.GUI, "true");
            ProfileImpl profileImpl = new ProfileImpl(properties);
            

            jade.wrapper.AgentContainer mainContainer = jade.core.Runtime.instance().createMainContainer(profileImpl);
            mainContainer.start();

            Environment environment = new Environment();

            AgentController polluterController = mainContainer.createNewAgent(
                "PolluterAgent",
                PolluterAgent.class.getName(),
                new Object[]{environment});
            polluterController.start();

            AgentController cleanerController = mainContainer.createNewAgent(
                "CleanerAgent",
                CleanerAgent.class.getName(),
                new Object[]{environment});
            cleanerController.start();

            //negotiation
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
