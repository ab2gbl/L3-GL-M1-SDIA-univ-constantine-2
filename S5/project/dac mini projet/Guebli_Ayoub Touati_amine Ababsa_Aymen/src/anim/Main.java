package anim;

import anim.MyFrame;
import anim.MyPanel;

import javax.swing.*;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {
    private String name;
    static private Semaphore[] semaphores1 = {new Semaphore(1), new Semaphore(0), new Semaphore(0), new Semaphore(0)};
    static private ThreadGroup threadGroup = new ThreadGroup("name");
    static int scoreTeamA=0;

    static boolean fall=false;



    static class Player implements Runnable {
        int position;
        private Semaphore[] semaphores1 ;
        private String team;

        Player(int position,Semaphore[] semaphore,String team) {
            this.position = position;
            this.semaphores1=semaphore;
            this.team=team;
        }
        public void Back() throws InterruptedException {
            try {
                if (position<4-1) {
                    semaphores1[position].acquire();
                    fall();
                    System.out.println(team+": player " + position + "  back to "+(position+1));
                    //
                    MyPanel.a=100+(position+1)*300;
                    MyPanel.xVelocity=3;
                    Thread.sleep(3000);
                    //
                    semaphores1[(position + 1) % 4].release();
                }
                //
                 else{
                    semaphores1[position].acquire();
                    MyPanel.b=false;
                    semaphores1[position].release();
                }
                //
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public void Forth(){
            try {
                if (position>0) {
                    semaphores1[position].acquire();
                    fall();
                    System.out.println(team+": player " + position + " forth to "+(position-1));
                    //
                    MyPanel.a=100+(position-1)*300;
                    MyPanel.xVelocity=-3;
                    Thread.sleep(3000);
                    //
                    semaphores1[(position - 1) % 4].release();
                }else{
                    semaphores1[position].acquire();


                    shoot();
                    MyPanel.b=true;
                    semaphores1[position].release();

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public void shoot() throws InterruptedException {
            if( fall==false) {
                System.out.println(team+": player " + position + " shoot ");

                //
                MyPanel.a=0;
                MyPanel.xVelocity=-3;
                Thread.sleep(3000);
                //
                    scoreTeamA++;
                    System.out.println(team + ": score = " + scoreTeamA);
                //
                MyPanel.b=true;
                MyPanel.a=100;
                MyPanel.xVelocity=3;
                Thread.sleep(3000);
                //
            }else {
                System.out.println(team+": no goal");
                fall=false;}
        }
        public void fall() throws InterruptedException {
            Random r=new Random();
            int n= r.nextInt(10);
            if (n==0) {
                System.out.println(team+": player " + position + " fall the ball");
                if (MyPanel.b==true){

                    MyPanel.a=100+(position)*300+150;
                    MyPanel.xVelocity=3;
                    Thread.sleep(3000);
                    fall = true;
                    MyPanel.b=false;
                    MyPanel.a=100+(position)*300;
                    MyPanel.xVelocity=-3;
                    Thread.sleep(3000);
                    MyPanel.b=true;

                }else {

                    MyPanel.a=100+(position)*300-150;
                    MyPanel.xVelocity=-3;
                    Thread.sleep(3000);
                    fall = true;
                    MyPanel.b=true;
                    MyPanel.a=100+(position)*300;
                    MyPanel.xVelocity=3;
                    Thread.sleep(3000);
                    MyPanel.b=false;


                }
                fall = true;

            }
        }

        @Override
        public void run() {
            while(scoreTeamA<10 ) {
                try {
                    Back();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Forth();

            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            Thread t = new Thread(threadGroup, new Player(i,semaphores1,"name"), "Thread " + i);
            t.start();
        }

        MyFrame a =new MyFrame();


    }

}