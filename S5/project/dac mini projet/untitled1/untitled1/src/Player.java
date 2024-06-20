import anim.MyFrame;

import java.util.Random;
public class Player extends Thread {
    private static final int NUM_PLAYERS = 4;
    private static final int NUM_TEAMS = 2;
    private static final int NUM_GOALS = 10;

    private static int scoreTeamA = 0;
    private static int scoreTeamB = 0;



    public int position;
    public Player[] equipe;
    public String TeamName;

    public boolean a;
    public boolean b = false;
    public static boolean fall1=false;
    public static boolean fall2=false;

    Player(ThreadGroup group,int num, Player[] equipe,String TeamName) {
        super(group, TeamName+ " Player " + num);
        this.setName(" " + num);
        this.TeamName=TeamName;
        this.position = num;
        if (position == 0) {
            this.a = true;
        }
        equipe[position] = this;
        this.equipe = equipe;
    }

    public void shoot(){
        System.out.println("        *** shot from "+this.TeamName+" ***");
        if (this.TeamName=="team1"){
            scoreTeamA++;
            System.out.println("+++++++ A goal from "+this.TeamName+", scoreA: "+scoreTeamA+" +++++++");}
        else{
            scoreTeamB++;
            System.out.println("+++++++ A goal from "+this.TeamName+", scoreB: "+scoreTeamB+" +++++++");}
    }


    public void Back() {


        try {
            sleep(100);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }

        //test fall
        Random rand = new Random();
        int n = rand.nextInt(10);
        if (n == 0) {
            System.out.println(this.TeamName+" fall the ball on back from " +(this.position+1)+" to "+(this.position+2));
            if(this.TeamName=="team1")
                fall1=true;
            else
                fall2=true;
        }
        //

        a = false;
        equipe[position + 1].a = true;




        System.out.println(this.TeamName+": back from " + (this.position+1)+" to "+(this.position+2));
        if (position == equipe.length - 2) {
            for (int i = 0; i < equipe.length; i++) {
                if(position != 0) {
                    equipe[i].b = true;}
            }

        }
        synchronized (equipe[position + 1]) {
            equipe[position + 1].notify();
        }

    }

    public void Forth() {

        try {
            sleep(100);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
        // test fall
        Random rand = new Random();
        int n = rand.nextInt(10);
        if (n == 0) {
            System.out.println(this.TeamName+" fall the ball on Forth from " +(this.position+1)+" to "+(this.position));
            if(this.TeamName=="team1")
                fall1=true;
            else
                fall2=true;
        }
        //
        a = false;
        equipe[position - 1].a = true;

        System.out.println(this.TeamName+": Forth from " + (this.position+1)+" to "+(this.position));


        if (position == 1) {
            for (int i = 0; i < equipe.length; i++) {
                if(position != equipe.length-1) {
                    equipe[i].b = false;}
            }

        }
        synchronized (equipe[position - 1]) {
            equipe[position - 1].notify();
        }

    }

    public void run() {
        while (scoreTeamA<10 && scoreTeamB<10 ) {

            if (b == false && position < equipe.length - 1) {
                if (a == false) {
                    try {
                        synchronized (this) {
                            this.wait();
                        }
                        ;
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if(scoreTeamA==10 || scoreTeamB==10)
                    break;

                Back();
            }
            try {
                sleep(200);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (b == true && position > 0) {
                if (a == false) {
                    try {
                        synchronized (this) {
                            this.wait();
                        }

                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if(scoreTeamA==10 || scoreTeamB==10)
                    break;
                Forth();

                if (position==1){
                    if(this.TeamName=="team1") {
                        if (fall1 == false)
                            shoot();
                        else{
                            System.out.println("-- "+this.TeamName
                                    +": no shoot cause the ball is fall, restart --");
                            fall1 = false;}
                    }else {
                        if (fall2 == false)
                            shoot();
                        else{
                            System.out.println("-- "+this.TeamName
                                    +": no shoot cause the ball is fall, restart --");
                            fall2 = false;}
                    }
                }

            }

        }
    }


    public static void main(String[] args) throws InterruptedException {
        new MyFrame();
        // TODO Auto-generated method stub
        ThreadGroup teamA = new ThreadGroup("Team A");
        ThreadGroup teamB = new ThreadGroup("Team B");
        Player equipe1[]=new Player[4];
        Player equipe2[]=new Player[4];
        for (int i=0;i<4;i++){
            new Player(teamA,i,equipe1,"team1").start();
            new Player(teamB,i,equipe2,"team2").start();
        }

    }

}