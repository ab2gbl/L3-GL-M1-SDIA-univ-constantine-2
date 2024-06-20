import spade
import asyncio
from spade.agent import Agent
from spade.behaviour import CyclicBehaviour
from spade.message import Message
from spade.template import Template

import matplotlib.pyplot as plt
from matplotlib.colors import ListedColormap

cmap = ListedColormap(["green", "red"])

taille = 10
actions = [(0,1), (0,-1), (1,0), (-1,0)]
environement= [[0]*taille for _ in range(taille)]

for a in environement:
    print(a)

class Nettoyeur(Agent):
    class InformBehav(CyclicBehaviour):
        async def run(self):
            print("Nettoyeur attends le message de l'observateur")
            msg = await self.receive(timeout=1000) 
            if msg:
                print(f"Message du pollueur: {msg.body}")
                chemin = eval(msg.body)
                print(len(chemin))
                for point in chemin:
                    x = point[1]
                    y = point[0]
                    environement[y][x] = 0
                    #plt.imshow(environement, cmap=cmap, vmin=0, vmax=1)
                    plt.plot(x, y, 'yo', markersize=16)
                    plt.pause(0.1)
                plt.pause(1000000)
                await self.agent.stop()
            else:
                print("aucun message n'a ete lu !")

    async def setup(self):
        print("Nettoyeur a commancer")
        b = self.InformBehav()
        template = Template()
        template.set_metadata("performative", "inform")
        self.add_behaviour(b,template)

import spade
import asyncio
#from DAIIA import *
from spade.agent import Agent
from spade.behaviour import CyclicBehaviour
from spade.message import Message
from spade.template import Template
import copy 
import numpy as np
import random

class Observateur(Agent):
    class ObservateurBehav(CyclicBehaviour):
        async def run(self):
            print("Observateur est en cours d'execution")

            msg = await self.receive(timeout=10)
            cases_poll = []
            
            if msg:
                print(f"Message received with content: {msg.body}")
                for i in range(taille):
                    for j in range(taille):
                        if environement[i][j] == 1:
                            cases_poll.append((i,j))
                print(cases_poll)
                
                
                Q = np.zeros((taille,taille,4))

                episodes = 1000
                chemin =[(0,0)]
                for episode in range(episodes):
                    # Initialisation de l'�tat
                    etat = (0, 0)
                    alpha = 0.1
                    gamma = 0.9
                    cases_toucher = 0
                    epsilon = 1 - (episode // episodes) * 0.9
                    
                    cases_poll_copy = copy.deepcopy(cases_poll)
                    i = 0
                    while True:
                        
                        if np.random.uniform(0, 1) < epsilon:
                            toto = []
                            for c in cases_poll_copy:
                                distances = [self.agent.distance_manhattan((etat[0] + a[0], etat[1] + a[1]), c) for a in actions]
                                action = np.argmin(distances)
                                toto.append((action,distances))
                            tuple_avec_min = min(toto, key=lambda x: x[1])
                            meme_deuxieme_element = [t for t in toto if t[1] == tuple_avec_min[1]]
                            tuple_choisi_au_hasard = random.choice(meme_deuxieme_element)
                            action = tuple_choisi_au_hasard[0]
                        else:
                            action = np.argmax(Q[etat[0], etat[1]])
                        
                        nouvel_etat, recompense , arret= self.agent.step(etat, action,cases_poll_copy)
                        if recompense == 10 :
                            cases_toucher +=1
                            recompense *= cases_toucher
                            
                        Q[etat[0], etat[1], action] = (1 - alpha) * Q[etat[0], etat[1], action] + alpha * (recompense + gamma * np.max(Q[nouvel_etat[0], nouvel_etat[1]]))
                        
                        # Mise � jour de l'�tat
                        etat = nouvel_etat
                        if episode == episodes -1 :
                            chemin.append(etat)
                        # Si l'�tat est terminal, fin de l'�pisode
                        if arret:
                            print("ani dert stop",episode)
                            break
                        
                msg = Message(to="ab2gbl2@xabber.de") 
                msg.set_metadata("performative", "inform") 
                msg.body = str(chemin)
                
                await self.send(msg)
                
                await self.agent.stop()
            else:
                print("Observateur n'a ressu aucun message")

    def step(self,etat, action,cases_poll):
        nouvel_etat = (etat[0] + actions[action][0], etat[1] + actions[action][1])
        recompense = -5
        if nouvel_etat[0] < 0 or nouvel_etat[0] >= taille or nouvel_etat[1] < 0 or nouvel_etat[1] >= taille:
            nouvel_etat = etat
            recompense -= 10
            return nouvel_etat, recompense,False
        
        if nouvel_etat in cases_poll:
            recompense += 15

            cases_poll.remove(nouvel_etat)

        return nouvel_etat, recompense,len(cases_poll) == 0
    
    def distance_manhattan(self,point1, point2):
        return abs(point1[0] - point2[0]) + abs(point1[1] - point2[1])

    async def setup(self):
        print("Observateur est en cour d'execution")
        b = self.ObservateurBehav()
        template = Template()
        template.set_metadata("performative", "inform")
        self.add_behaviour(b, template)
        
import spade
import asyncio
#from DAIIA import *
import random
import numpy as np
from spade.agent import Agent
from spade.behaviour import CyclicBehaviour
from spade.message import Message
from spade.template import Template

class Pollueur(Agent):
    class PollueurBehav(CyclicBehaviour):
        async def run(self):
            print("Pollueur va polluer 10 cases :")
            await asyncio.sleep(6)
            for i in range(10):
                x, y = random.randint(0, len(environement)-1), random.randint(0,len(environement)-1)
                environement[x][y] = 1

            for a in environement:
                print(a)

            plt.ion()
            plt.imshow(environement, cmap=cmap, vmin=0, vmax=1)
            plt.title("Environnement")
            plt.grid(which='major', color='black', linestyle='-', linewidth=1)
            
            plt.xticks(np.arange(-0.5, taille, 1))
            plt.yticks(np.arange(-0.5, taille, 1))
            
            plt.pause(1)
            

            msg = Message(to="ab2gbl1@0nl1ne.at")    
            msg.set_metadata("performative", "inform") 
            msg.body = "j\'ai polluyer"

            await self.send(msg)
            print("pollueur a pollue!")
            
            await self.agent.stop()
            


    async def setup(self):
        print("Nettoyeur a commancer")
        b = self.PollueurBehav()
        template = Template()
        template.set_metadata("performative", "inform")
        self.add_behaviour(b, template)

async def main():
    receiveragent = Pollueur("ab2gbl@xabber.de", "Samy1234!")
    await receiveragent.start(auto_register=True)
    print("Pollueur a demarre")

    observateuragent = Observateur("ab2gbl1@0nl1ne.at", "Samy1234!")
    await observateuragent.start(auto_register=True)
    print("Observateur a demarre")

    senderagent = Nettoyeur("ab2gbl2@xabber.de","Samy1234!")
    await senderagent.start(auto_register=True)
    print("Nettoyer a demarre")
    
    

    await spade.wait_until_finished(senderagent)
    print("Agents finished")
    
if __name__ == "__main__":
    spade.run(main())
