'''from collections import defaultdict
 
class Graph:
 
    def __init__(self,vertices):
        self.V = vertices
        self.graph = defaultdict(list)
    
    def addEdge(self, u, v, w, heuristic):
        self.graph[u].append((v, w, heuristic))
    
    def Astar(self,start,end):
        
        
    
    
    
g=Graph(7)
g.addEdge(0, 1, 1, 3)  
g.addEdge(0, 2, 5, 2)  
g.addEdge(0, 3, 2, 2)  
g.addEdge(1, 4, 7, 2)  
g.addEdge(2, 5, 4, 2)  
g.addEdge(3, 2, 1, 2)  
g.addEdge(3, 4, 6, 2)  
g.addEdge(4, 6, 3, 1)
g.addEdge(5, 4, 1, 1)
g.addEdge(5, 6, 3, 1)'''

arbre={
    0:[3,[(1,1),(2,5),(3,2)]],
    1:[2,[(4,7)]],
    2:[2,[(5,4)]],
    3:[2,[(2,1),(4,6)]],
    4:[1,[(6,3)]],
    5:[1,[(4,1),(6,3)]],
    6:[0,[]]
}

def Astar(tree,start,goal):
    f=[([start],0,tree[start][0])]
    print(f)
    while f[0][0][-1]!=goal:
        path=f[0][0]
        i=path[-1]
        cout=f[0][1]
        f.pop(0)
        b=tree[i][1][::-1]
        for element in b:
            node=element[0]
            a=path+[node]
            w=element[1]
            f.insert(0,(a,cout+w,tree[node][0]))
        f=sorted(f, key=lambda x: x[1]+x[2])

        print(f)
    print("path is "+str(f[0][0]))

            
Astar(arbre,0,6)
        