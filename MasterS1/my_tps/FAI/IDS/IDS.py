from collections import defaultdict
 
class Graph:
 
    def __init__(self,vertices):
        self.V = vertices
        self.graph = defaultdict(list)
 
    def addEdge(self,u,v):
        self.graph[u].append(v)
    
    def IDS(self,start,goal,profendeur):
        for i in self.graph[src]:
            print(0)
            
        
Graph(7)