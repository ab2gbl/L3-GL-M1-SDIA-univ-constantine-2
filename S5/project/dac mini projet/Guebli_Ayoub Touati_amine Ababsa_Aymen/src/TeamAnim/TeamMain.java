package TeamAnim;

import anim.MyPanel;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class TeamMain extends Thread{
    static final int Goals=3;
    private String name;

    static boolean fall1=false;

    static boolean fall2=false;
    private Semaphore[] semaphores1 = {new Semaphore(1), new Semaphore(0), new Semaphore(0), new Semaphore(0)};
    private ThreadGroup threadGroup = new ThreadGroup(name);
    static int scoreTeamA=0;
    static int scoreTeamB=0;



    public TeamMain(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        play();
    }

    public void play(){
        for (int i = 0; i < 4; i++) {
            Thread t = new Thread(threadGroup, new Player(i,semaphores1,name), "Thread " + i);
            t.start();
        }
    }
    static class Player implements Runnable {
        int position;
        private Semaphore[] semaphores1 ;
        private String team;

        Player(int position,Semaphore[] semaphore,String team) {
            this.position = position;
            this.semaphores1=semaphore;
            this.team=team;
        }
        public void Back(){
            try {
                if (position<4-1) {
                    semaphores1[position].acquire();
                    if (scoreTeamA<Goals && scoreTeamB<Goals) {
                        fall();
                        System.out.println(team + ": player " + position + "  back to " + (position + 1));
                        //
                        if (team == "Team1") {
                            MyTeamPanel.xA = 100 + (position + 1) * 300;
                            MyTeamPanel.xVelocity = 3;
                        } else {
                            MyTeamPanel.iA = 100 + (position + 1) * 300;
                            MyTeamPanel.iVelocity = 3;
                        }
                        Thread.sleep(3000);
                        //
                        semaphores1[(position + 1) % 4].release();
                    }
                }else{
                semaphores1[position].acquire();
                //
                if (team=="Team1")
                    MyTeamPanel.xB=false;
                else
                    MyTeamPanel.iB=false;
                //
                semaphores1[position].release();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public void Forth(){
            try {
                if (position>0) {
                    semaphores1[position].acquire();
                    if (scoreTeamA<Goals && scoreTeamB<Goals) {
                        fall();
                        System.out.println(team + ": player " + position + " forth to " + (position - 1));
                        //
                        if (team=="Team1"){
                            MyTeamPanel.xA=100+(position-1)*300;
                            MyTeamPanel.xVelocity=-3;
                        }else{
                            MyTeamPanel.iA = 100 + (position - 1) * 300;
                            MyTeamPanel.iVelocity =-3;
                        }
                        Thread.sleep(3000);
                        //
                        semaphores1[(position - 1) % 4].release();
                    }
                }else{
                    semaphores1[position].acquire();
                    if (scoreTeamA<Goals && scoreTeamB<Goals) {
                        shoot();
                        if (team=="Team1")
                            MyTeamPanel.xB=true;
                        else
                            MyTeamPanel.iB=true;
                        semaphores1[position].release();
                    }

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public void shoot() throws InterruptedException {
            if(team=="Team1") {
                if( fall1==false) {

                    System.out.println("       *** shot from "+this.team+" ***");
                    //
                    MyTeamPanel.xA=0;
                    MyTeamPanel.xVelocity=-3;
                    Thread.sleep(2000);
                    //
                    scoreTeamA++;
                    System.out.println("+++++++ A goal from "+this.team+", scoreA: "+scoreTeamA+" +++++++");
                    //
                    MyTeamPanel.xB=true;
                    MyTeamPanel.xA=100;
                    MyTeamPanel.xVelocity=3;
                    Thread.sleep(3000);
                    //
                }else {
                    System.out.println("-- "+this.team +": no shoot cause the ball is fall, restart --");
                    fall1 = false;
                }
            }else {
                if( fall2==false) {
                    //
                    MyTeamPanel.iA=0;
                    MyTeamPanel.iVelocity=-3;
                    Thread.sleep(2000);
                    //
                    System.out.println("       *** shot from "+this.team+" ***");
                    scoreTeamB++;
                    System.out.println("+++++++ A goal from "+this.team+", scoreB: "+scoreTeamB+" +++++++");
                    //
                    MyTeamPanel.iB=true;
                    MyTeamPanel.iA=100;
                    MyTeamPanel.iVelocity=3;
                    Thread.sleep(3000);
                    //
                }else {
                    System.out.println("-- "+this.team +": no shoot cause the ball is fall, restart --");
                    fall2 = false;
                }
            }
        }
        public void fall() throws InterruptedException {
            Random r=new Random();
            int n= r.nextInt(15);
            if (n==0) {
                if (team=="Team1"){
                    if (MyTeamPanel.xB==true){
                        MyTeamPanel.xA=100+(position)*300+200;
                        MyTeamPanel.xVelocity=3;
                        Thread.sleep(3000);
                        fall1 = true;
                        MyTeamPanel.xB=false;
                        MyTeamPanel.xA=100+(position)*300;
                        MyTeamPanel.xVelocity=-3;
                        Thread.sleep(3000);
                        MyTeamPanel.xB=true;
                    }else {
                        MyTeamPanel.xA=100+(position)*300-150;
                        MyTeamPanel.xVelocity=-3;
                        Thread.sleep(3000);
                        fall1 = true;
                        MyTeamPanel.xB=true;
                        MyTeamPanel.xA=100+(position)*300;
                        MyTeamPanel.xVelocity=3;
                        Thread.sleep(3000);
                        MyTeamPanel.xB=false;
                    }
                }
                else{
                    if (MyTeamPanel.iB==true){
                        MyTeamPanel.iA=100+(position)*300+200;
                        MyTeamPanel.iVelocity=3;
                        Thread.sleep(3000);
                        fall2=true;
                        MyTeamPanel.iB=false;
                        MyTeamPanel.iA=100+(position)*300;
                        MyTeamPanel.iVelocity=-3;
                        Thread.sleep(3000);
                        MyTeamPanel.iB=true;
                    }else {
                        MyTeamPanel.iA=100+(position)*300-150;
                        MyTeamPanel.iVelocity=-3;
                        Thread.sleep(3000);
                        fall2=true;
                        MyTeamPanel.iB=true;
                        MyTeamPanel.iA=100+(position)*300;
                        MyTeamPanel.iVelocity=3;
                        Thread.sleep(3000);
                        MyTeamPanel.iB=false;}
                }
                System.out.println(team+": player " + position + " fall the ball");
            }
        }
        @Override
        public void run() {
            while(scoreTeamA<Goals && scoreTeamB<Goals) {
                Back();
                Forth();

            }
        }
    }

    public static void main(String[] args) {
        TeamMain t1=new TeamMain("Team1");
        TeamMain t2=new TeamMain("Team2");
        t1.start();
        t2.start();
        MyTeamFrame a=new MyTeamFrame();

    }

}