import java.io.*;
import java.util.*;

public class UCSTraversal{
    //private int node;
    static List<List<Integer>> graph=new ArrayList<List<Integer>>();
    static HashMap<List<Integer>,Integer> cost=new HashMap<List<Integer>,Integer> ();
    private boolean visited[];

    //private Queue<Integer> que;

    // UCSTraversal(int v){
    //     //node=v;
    //     adj=new LinkedList[v];
    //     visited=new boolean[v];
    //     for(int i=0; i<v; i++){
    //         adj[i]=new LinkedList<Integer>();
    //     } 
    //     //que = new LinkedList<Integer>();
    // }
    // void insertEdge(int src,int dest,int cout){
    //     adj[src].add(dest,cout);
    // }

    static List<Integer> UCS(int start, List<Integer> goal){
        //visited[vertex] = true;
        List<Integer> answer=new ArrayList<Integer>();

        List <Tuple<Integer,Integer>> queue=new ArrayList<Tuple<Integer,Integer>>();
        for (int i=0;i<goal.size();i++)
            answer.add(Integer.MAX_VALUE);
        queue.add(new Tuple<Integer,Integer>(0,start));
        HashMap<Integer,Integer>visited=new HashMap<Integer,Integer>();
        int count=0;
        while(!queue.isEmpty()){
            Tuple<Integer,Integer> q=queue.get(0);
            Tuple<Integer,Integer> p=new Tuple<Integer,Integer>(-q.x, q.y);
            queue.remove(0);
            if(goal.contains(p.y)){
                int index=goal.indexOf(p.y);
                if(answer.get(index)==Integer.MAX_VALUE){
                    count++;
                }
                if(answer.get(index)>p.x){
                    answer.set(index,p.x);
                }
                queue.remove(0);
                if(count==goal.size())
                    return answer;
            }     
            if(!visited.containsKey(p.y)){
                for(int i=0; i<graph.get(p.y).size();i++){
                  queue.add(new Tuple <Integer, Integer>((p.x+
                  (cost.containsKey(Arrays.asList(p.y,graph.get(p.y).get(i)))?
                  cost.get(Arrays.asList(p.y,graph.get(p.y).get(i))):0))*-1,
                        graph.get(p.y).get(i)));
                }
            }       
            
            visited.put(p.y,1);
        }
        return answer;
    }


        
        

public static void main(String[] args) {

    
        graph=new ArrayList<List<Integer>>();
        for(int i=0;i<7;i++){
            graph.add(new ArrayList<Integer>());
        }

        graph.get(0).add(1);
        graph.get(0).add(3);
        graph.get(3).add(1);
        graph.get(3).add(6);
        graph.get(3).add(4);
        graph.get(1).add(6);
        graph.get(4).add(2);
        graph.get(4).add(5);
        graph.get(2).add(1);
        graph.get(5).add(2);
        graph.get(5).add(6);
        graph.get(6).add(4);


        cost.put(Arrays.asList(0,1), 2);
        cost.put(Arrays.asList(0,3), 5);
        cost.put(Arrays.asList(1,6), 1);
        cost.put(Arrays.asList(3,1), 5);
        cost.put(Arrays.asList(3,6), 6);
        cost.put(Arrays.asList(3,4), 2);
        cost.put(Arrays.asList(2,1), 4);
        cost.put(Arrays.asList(4,2), 4);
        cost.put(Arrays.asList(4,5), 3);
        cost.put(Arrays.asList(5,2), 6);
        cost.put(Arrays.asList(5,6), 3);
        cost.put(Arrays.asList(6,4), 7);

        List<Integer> goal=new ArrayList<Integer>();
        goal.add(6);
        List <Integer> answer=UCS(0, goal);
        System.out.println("Min cost from 0(S) to 6(G) is "+answer.get(0));


    }



} 
