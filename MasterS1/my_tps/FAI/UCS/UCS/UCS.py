arbre={
    0:[(1,2),(3,5)],
    1:[(6,1)],
    2:[(1,4)],
    3:[(1,5),(6,6),(4,2)],
    4:[(2,4),(5,3)],
    5:[(6,3),(2,6)],
    6:[(4,7)],
}
arbre0={
    0:[(1,1),(2,5),(3,15)],
    1:[(4,10)],
    2:[(4,5)],
    3:[(4,5)],
    4:[]
    
}
def UCS(arbre,start,goal):
    frontiere=[([start],0)]
    #visited=[start]
    print(frontiere)
    
    while frontiere[0][0][-1]!=goal:
        path=frontiere[0][0]
        i=path[-1]
        cout=frontiere[0][1]
        frontiere.pop(0)
        b=arbre[i][::-1]
        
        for element in b:
            a=path+[element[0]]
            frontiere.insert(0,(a,cout+element[1]))
        frontiere=sorted(frontiere, key=lambda x: x[1])

        print(frontiere)
        #print(frontiere)
    print("the path is "+str(frontiere[0][0])+" and the cout is "+str(frontiere[0][1]))

UCS(arbre,0,6)