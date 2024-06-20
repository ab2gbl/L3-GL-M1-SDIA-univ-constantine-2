arbre={
    0:[1,2,3],
    1:[3],
    2:[4],
    3:[5,6],
    4:[5,7],
    5:[2],
    6:[],
    7:[]
}

def DFS(arbre,start,goal):
    frontiere=[start]
    visited=[start]
    print(frontiere)
    while frontiere[0]!=goal:
        i=frontiere[0]
        frontiere.pop(0)
        b=arbre[i][::-1]
        for element in b:
            if element not in visited:
                visited.append(element)
                frontiere.insert(0,element)
        print(frontiere)

DFS(arbre,0,6)