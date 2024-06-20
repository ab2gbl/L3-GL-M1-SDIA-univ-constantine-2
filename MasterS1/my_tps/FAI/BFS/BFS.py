arbre={
    0:[1,3,4],
    1:[2],
    2:[],
    3:[5],
    4:[5],
    5:[]
}

def BFS(arbre,start,goal):
    frontiere=[start]
    while frontiere[0]!=goal:
        i=frontiere[0]
        frontiere.pop(0)
        for element in arbre[i]:
            frontiere.append(element)
        print(frontiere)

BFS(arbre,0,5)