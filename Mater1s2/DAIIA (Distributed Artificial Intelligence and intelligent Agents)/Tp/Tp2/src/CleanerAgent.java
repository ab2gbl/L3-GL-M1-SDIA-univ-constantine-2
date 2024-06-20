import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class CleanerAgent extends Agent {
    private Environment environment;
    private boolean cleaningComplete = true;

    @Override
    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            environment = (Environment) args[0];

            addBehaviour(new CyclicBehaviour(this) {
                @Override
                public void action() {
                    if (!cleaningComplete) {
                        cleanAllPollutedCells();

                        environment.printMatrix();

                        cleaningComplete = true;
                    } else {
                        ACLMessage message = receive();
                        if (message != null) {
                            String content = message.getContent();
                            String[] coordinates = content.split(",");
                            int x = Integer.parseInt(coordinates[0]);
                            int y = Integer.parseInt(coordinates[1]);

                            int n = (int) (Math.random() * 1);

                            
                            try {
                                Thread.sleep(2000); 
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            ACLMessage reply;
                            reply = message.createReply();
                            reply.setPerformative( ACLMessage.INFORM );
                            reply.setContent("clear");
                            if (n==0){
                                environment.cleanCell(x, y);
                                reply.setContent("clear");
                            }else{

                                reply.setContent("not clear");
                            }

                            
                            

                            environment.printMatrix();
                        } else {
                            block();
                        }
                    }
                }
            });
        } else {
            System.err.println("Environment instance not provided to CleanerAgent");
            doDelete();
        }
    }

    private void cleanAllPollutedCells() {
        // Implement logic to clean all polluted cells in the environment
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (environment.isPolluted(i, j)) {
                    try {
                        Thread.sleep(2000); // 0.2 seconds
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // wait  second;
                    environment.cleanCell(i, j);
                }else{
                    try {
                        Thread.sleep(100); // 0.1 seconds
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //wa wait 0.1 second;
                }
            }
        }
    }
}
