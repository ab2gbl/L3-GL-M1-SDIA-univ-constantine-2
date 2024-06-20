import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class MASMain1 {
    public static void main(String[] args) {
        try {
            // Create the runtime instance
            Environment env=new Environment();
            env.printMatrix();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
