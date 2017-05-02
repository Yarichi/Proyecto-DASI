# coding=utf-8
import string
import socket as socket
from collections import defaultdict
import heapq
import Queue as Queue
import threading
import time
import json
from rutasLee import CalculoRutas

agents_pos = dict();

class OrderDispatcher(object):
    def __init__(self, agentNumber):
        self.queue = Queue.Queue()
        self.stop = False
        self.number = agentNumber
        
    def dispatch(self, action):
        self.queue.put(action)
        
    def execution(self):
        finishCondition = True
        print str(self.number)
        while self.stop == False:
            if self.queue.empty() == False and finishCondition:
                obj = self.queue.get()
                #obj.action(agent_host[self.number])
                obj.action(self.number)
                finishCondition = obj.isFinished()

    def stopExecution(self):
        self.stop = True
        
class OrderServer(object):

    def initializeConnection(self, port):
        self.inSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.inSocket.bind(('localhost', port))
        self.inSocket.listen(1)
        (self.clientsocket, self.clientAddress) = self.inSocket.accept()
        time.sleep(1)
        self.outSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.outSocket.connect(("127.0.0.1", port + 1))
        time.sleep(1)
        self.outSocketAck = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.outSocketAck.connect(("127.0.0.1", port + 2))

    def initializeStructures(self, list, dispatcher, initInfo, agents):
        self.index = {}
        self.commandList = []
        for element in list:
            self.index[element[0].lower()] = element[1]
            self.commandList.append(element[0].lower())
        self.orderDispatcher = dispatcher
        self.initInfo = initInfo
        self.lock = threading.Lock()
        self.agents = agents

    def __init__(self, port, actionList, dispatcher, initInfo, agents):
        self.initializeStructures(actionList, dispatcher, initInfo, agents)
        self.initializeConnection(port)
		
    def convertStringToId(self, queryId):
        list = self.initInfo["agentsId"]
        for counter in range(len(list)):
            if list[counter].lower() == queryId.lower():
                return counter
        return -1
        
    def onlyAllowedCharacter(self, message):
        newMessage = ''
        for character in message:
            if (character >= 'a' and character <= 'z') or (character >= 'A' and character <= 'Z') or character == " " or character == "." or (character >= '0' and character <= '9'):
                newMessage = newMessage + character
        return newMessage

    def receiveOrder(self):
        message = ""
        params = []
        while True:
            message = self.clientsocket.recv(128)
            message = self.onlyAllowedCharacter(message)
            message = message.lower()
            message = message.split(" ")
            #if message[0] != "end":
            #    agentNumber = self.convertStringToId(message[1])
            #    params.append(float(message[2]))
            #    params.append(float(message[3]))
            print "recibido el mensaje " + str(message)
            if message[0] == "obstacles" and len(message) == 1:
                self.lock.acquire(True)
                self.outSocket.send("ob" + "_" + str(self.initInfo["obstacles"]) + "\n")
                self.lock.release()
            elif message[0] == "agents" and len(message) == 1:
                self.lock.acquire(True)
                self.outSocket.send("ag" + "_" + str(self.initInfo["agents"]) + "\n")
                self.lock.release()
            elif message[0] == "agent" and len(message) == 2:
                idEntero = self.convertStringToId(message[1])
                if idEntero != -1:
                        obs = json.loads(self.agents[idEntero].getWorldState().observations[-1].text)
                        position = "ag_["+str(float(obs[u'XPos'])) + ", " + str(float(obs[u'YPos'])) + ", " + str(float(obs[u'ZPos'])) + "]\n"
                        self.lock.acquire(True)
                        self.outSocket.send(position)
                        self.lock.release()
                else:
                    self.lock.acquire(True)
                    self.outSocket.send("Error: id de agente no identificado\n")
                    self.lock.release()
            elif message[0] == "apples" and len(message) == 1:
                self.lock.acquire(True)
                self.outSocket.send("ap" + "_" + str(self.initInfo["apples"]) + "\n")
                self.lock.release()
            elif message[0] == "move" and len(message) == 4:
                ##Llamar a la funcion aqui
                idEntero = self.convertStringToId(message[1])
                if idEntero != -1:
                    self.lock.acquire(True)
                    self.outSocket.send("Ack\n")
                    self.lock.release()
                    self.orderDispatcher[idEntero].dispatch(Command(self.index[message[0]], [self.outSocketAck, self.lock, message[2], message[3], self.initInfo]))
                else:
                    self.lock.acquire(True)
                    self.outSocket.send("Error: id de agente no identificado\n")
                    self.lock.release()
            elif message[0] == "eval" and len(message) == 4:
                ##Llamar a la funcion aqui
                idEntero = self.convertStringToId(message[1])
                if idEntero != -1:
                    self.orderDispatcher[idEntero].dispatch(Command(self.index[message[0]], [self.outSocket, self.lock, message[2], message[3], self.initInfo]))
                else:
                    self.lock.acquire(True)
                    self.outSocket.send("Error: id de agente no identificado\n")
                    self.lock.release()
            elif message[0] == "end" and len(message) == 1:
                break
            else: 
                self.lock.acquire(True)
                self.outSocket.send("No command found\n")
                self.lock.release()
            #message = message[0]
            #if message != "end" and message in self.commandList and agentNumber < len(self.orderDispatcher) and agentNumber != -1:
            #    self.orderDispatcher[agentNumber].dispatch(Command(self.index[message], params))
        self.inSocket.close()
        for obj in self.orderDispatcher:
            obj.stopExecution()


class Command(object):
    
    def __init__(self, command, params):
        self.command = command
        self.finish = False
        self.params = params

    def action(self, index):
        print "on action function"
        self.finish = self.command(index, self.params)
        print "on end action function"
        print str(self.finish)
    
    def isFinished(self):
        return self.finish



def up(index):
    print("prueba ejecutada correctamente.")
    print str(index)
    finish = True
    #agents_pos[index][0] += 1
    #index.sendCommand("tpx " + agents_pos[index][0])
    index.sendCommand("movenorth 1")
    #index.sendCommand("movenorth 1")
    print str(finish)
    return finish

def down(index):
    print("prueba ejecutada correctamente.")
    print str(index)
    finish = True
    #agents_pos[index][0] -= 1
    #index.sendCommand("tpx " + agents_pos[index][0])
    index.sendCommand("movesouth 1")
    print str(finish)
    return finish
	
def right(index):
    print("prueba ejecutada correctamente.")
    print str(index)
    finish = True
    #agents_pos[index][2] += 1
    #index.sendCommand("tpz " + agents_pos[index][2])
    index.sendCommand("moveeast 1")
    print str(finish)
    return finish

def left(index):
    print("prueba ejecutada correctamente.")
    print str(index)
    finish = True
    #agents_pos[index][2] -= 1
    #index.sendCommand("tpz " + agents_pos[index][2])
    index.sendCommand("movewest 1")
    print str(finish)
    return finish

def stop(index):
    print("prueba ejecutada correctamente.")
    print str(index)
    finish = True
    index.sendCommand("move 0")
    index.sendCommand("strafe 0")
    print str(finish)
    return finish

def move(index,args):
    obs = json.loads(index.getWorldState().observations[-1].text)
    xini = obs["XPos"]
    zini = obs["ZPos"]
    correctObstacles = []
    for o in args[4]["obstacles"]:
        if o[1] == 226:
            correctObstacles.append((o[0]+0.5,o[2]+0.5))
    c = CalculoRutas((xini,zini),correctObstacles,args[4]["width"],args[4]["height"])
    route = c.calculaRuta(float(args[2]),float(args[3]))
    for (newX,newZ) in route:
        if newZ < zini:
            zini = newZ
            up(index)
            time.sleep(0.75)
        elif newZ > zini:
            zini = newZ
            down(index)
            time.sleep(0.75)
        if newX > xini:
            xini = newX
            right(index)
            time.sleep(0.75)
        elif newX < xini:
            xini = newX
            left(index)
            time.sleep(0.75)
    return True
def eval(index,args):
    obs = json.loads(index.getWorldState().observations[-1].text)
    xini = obs["XPos"]
    zini = obs["ZPos"]
    correctObstacles = []
    for o in args[4]["obstacles"]:
        if o[1] == 226:
            correctObstacles.append((o[0]+0.5,o[2]+0.5))
    c = CalculoRutas((xini,zini),correctObstacles,args[4]["width"],args[4]["height"])
    route = c.calculaRuta(float(args[2]),float(args[3]))
    message = "eval %s %i\n"%(obs['Name'],len(route))
    outSocket = args[0]
    args[1].acquire(True)
    outSocket.send(message)
    args[1].release()
    return True
    

#def agent(index, params):
#    obs = json.loads(index.getWorldState().observations[-1].text)
#    position = "ag_[["+str(float(obs[u'XPos'])) + ", " + str(float(obs[u'YPos'])) + ", " + str(float(obs[u'ZPos'])) + "]]\n"
#    print str(position)
#    params[1].acquire(True)
#    params[0].send(position)
#    params[1].release()
#    return True

def initDispatcher(world_items, agent_host):
    #TODO se supone que tenemos el numero de agentes
    #cogemos el numero de agentes
    amountAgents = len(world_items["agents"])
    #Cogemos la posicion de todos los agentes.
    cont = 0;
    for id in world_items["agentsId"]:
        agents_pos[str(cont)] = world_items["agents"][cont]
        cont+=1
    #amountAgents = 3;
    #iniciamos la lista de despachadores de ordenes
    dispatches = []
    #para el numero de agentes
    print str(amountAgents)
    for contador in range(amountAgents):
        #Iniciamos el despachador de ordenes
        #dispatch = OrderDispatcher(contador)
        dispatch = OrderDispatcher(agent_host[contador])
        #lo metemos en la lista
        dispatches.append(dispatch)
    #creamos la clase que recibe y apunta ordenes
    o = OrderServer(9288, [("up", up),("down", down),("right",right),("left",left),("stop",stop),("move",move),("eval",eval)], dispatches, world_items, agent_host)
    #print Aceptamos la conexion con la clase de java
    #o.startConnection()
    #print Como ya hay conexion establecemos un hilo para que vaya pasando las ordenes al despachador
    thread = threading.Thread(target=o.receiveOrder)
    thread.start()
    #iniciando la ejecucion de ordenes
    #thread2 = threading.Thread(target=dispatch.execution)
    #thread2.start()
    thread2 = []
    for contador in range(amountAgents):
        thread2.append(threading.Thread(target=dispatches[contador].execution))
        thread2[contador].start()
    #esperamos hasta que los threads acabemos
    #thread.join()
    #for contador in range(amountAgents):
    #    thread2[contador].join()