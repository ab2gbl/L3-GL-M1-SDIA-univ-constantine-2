import anim.MyFrame;
import anim.MyPanel;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Team extends Thread{
    private String name;

    static boolean fall1=false;

    static boolean fall2=false;
    private Semaphore[] semaphores1 = {new Semaphore(1), new Semaphore(0), new Semaphore(0), new Semaphore(0)};
    private ThreadGroup threadGroup = new ThreadGroup(name);
    static int scoreTeamA=0;
    static int scoreTeamB=0;



    public Team(String name) {
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
                    if (scoreTeamA<10 && scoreTeamB<10) {
                        fall();
                        System.out.println(team + ": player " + position + "  back to " + (position + 1));
                        semaphores1[(position + 1) % 4].release();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public void Forth(){
            try {
                if (position>0) {
                    semaphores1[position].acquire();
                    if (scoreTeamA<10 && scoreTeamB<10) {
                        fall();
                        System.out.println(team + ": player " + position + " forth to " + (position - 1));
                        semaphores1[(position - 1) % 4].release();
                    }
                }else{
                    semaphores1[position].acquire();
                    if (scoreTeamA<10 && scoreTeamB<10) {
                        shoot();
                        semaphores1[position].release();
                    }

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public void shoot(){
            if(team=="Team1") {
                if( fall1==false) {
                    System.out.println("       *** shot from "+this.team+" ***");
                    scoreTeamA++;
                    System.out.println("+++++++ A goal from "+this.team+", scoreA: "+scoreTeamA+" +++++++");
                }else {
                    System.out.println("-- "+this.team +": no shoot cause the ball is fall, restart --");
                    fall1 = false;
                }
            }else {
                if( fall2==false) {
                    System.out.println("       *** shot from "+this.team+" ***");
                    scoreTeamB++;
                    System.out.println("+++++++ A goal from "+this.team+", scoreB: "+scoreTeamB+" +++++++");
                }else {
                    System.out.println("-- "+this.team +": no shoot cause the ball is fall, restart --");
                    fall2 = false;
                }
            }
        }
        public void fall(){
            Random r=new Random();
            int n= r.nextInt(10);
            if (n==0) {
                if (team=="Team1")
                    fall1 = true;
                else
                    fall2=true;
                System.out.println(team+": player " + position + " fall the ball");
            }
        }
        @Override
        public void run() {
            while(scoreTeamA<10 && scoreTeamB<10) {
                Back();
                Forth();

            }
        }
    }

    public static void main(String[] args) {
        Team t1=new Team("Team1");
        t1.start();

        Team t2=new Team("Team2");
        t2.start();
      //  MyFrame a=new MyFrame();

    }

}