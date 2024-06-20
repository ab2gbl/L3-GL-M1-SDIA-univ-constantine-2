import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;

public class JADELauncher {

    public static void main(String[] args) {
        try {
            Runtime runtime = Runtime.instance();
            Properties properties = new ExtendedProperties();
            properties.setProperty(Profile.GUI, "true");
            //properties.setProperty(Profile.MAIN_PORT, "12345");
            ProfileImpl profileImpl = new ProfileImpl(properties);
            AgentContainer mainContainer =
            runtime.createMainContainer(profileImpl);
            mainContainer.start();
            } catch (Exception e) {
            e.printStackTrace();
            }
    }
}
