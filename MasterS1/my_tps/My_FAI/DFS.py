graph={
    0:[1,2,3],
    1:[3],
    2:[4],
    3:[5,6],
    4:[5,7],
    5:[2],
    6:[],
    7:[]
}
queue=[]
visited=[]
adj=[]
def DFS(start,end):
    queue.append(start)
    #'''
    while(queue[0]!=end):
        a=queue.pop(0)
        visited.append(a)
        if graph[a]!=[]:
            
            adj.append(a)
            
            for i in graph[a][::-1]:
                if i not in visited:
                    queue.insert(0,i)
        
        print(queue,adj)
    '''
    for j in range(6):
        a=queue.pop(0)
        visited.append(a)
        adj.append(a)
            
        for i in graph[a][::-1]:
            if (i not in visited):
                queue.insert(0,i)
            
        print(queue,adj)
    '''
DFS(0,6)