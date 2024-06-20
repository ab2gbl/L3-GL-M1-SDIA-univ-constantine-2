import spade
from spade.agent import Agent
from spade.behaviour import OneShotBehaviour
from spade.message import Message
from spade.template import Template
import random

import numpy as np
import random

actions = ["up", "down", "left", "right"]
matrix = [[0 for _ in range(10)] for _ in range(10)]


class polluted(spade.agent.Agent):
    async def setup(self):
        print("\ I'm agent {},and am a polluted".format(str(self.jid)))
        for i in range(10):
            x = random.randint(0,9)
            y = random.randint(0,9)
            matrix[x][y] = 1
            
def display_matrix():
    for row in matrix:
        print(row)
    print("\n")

class Observer(spade.agent.Agent):
    async def setup(self):
        print("\ I'm agent {},and am a Observer".format(str(self.jid)))
        path=[]
        for i in range(10):
            if i % 2 == 0:
                for j in range(10):
                    if j == 9:
                        path.append(actions[1])
                    else:
                        path.append(actions[3])
                    matrix[i][j] = 8
                    #display_matrix()
            else:
                for j in range(9, -1, -1):
                    if j == 0:
                        path.append(actions[1])
                    else:
                        path.append(actions[2])
                    matrix[i][j] = 8
            
                    #display_matrix()
        print(path)
        

async def main():
    dummy = polluted("ab2gbl@xabber.de", "Samy1234!")
    
    await dummy.start()
    
    display_matrix()
    cleaner = Observer("ab2gbl2@xabber.de", "Samy1234!")
    await cleaner.start()

if __name__ == "__main__":
    spade.run(main())




