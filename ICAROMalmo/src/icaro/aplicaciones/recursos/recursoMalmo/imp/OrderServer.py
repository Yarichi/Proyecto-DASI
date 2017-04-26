# coding=utf-8
import string
import socket as socket
from collections import defaultdict
import heapq
import Queue as Queue
import threading

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
        self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.socket.bind(('localhost', port))
        self.socket.listen(1)

    def initializeStructures(self, list, dispatcher, agentsId):
        self.index = {}
        self.commandList = []
        for element in list:
            self.index[element[0]] = element[1]
            self.commandList.append(element[0])
        self.orderDispatcher = dispatcher
        self.agentsId = agentsId

    def __init__(self, port, actionList, dispatcher, agentsId):
        self.initializeStructures(actionList, dispatcher, agentsId)
        self.initializeConnection(port)
		
    def convertStringToId(self, queryId):
        for counter in range(len(self.agentsId)):
            if self.agentsId[counter].lower() == queryId.lower():
                return counter
        return -1
        
    def onlyAllowedCharacter(self, message):
        newMessage = ''
        for character in message:
            if (character >= 'a' and character <= 'z') or (character >= 'A' and character <= 'Z') or character == " " or (character >= '0' and character <= '9'):
                newMessage = newMessage + character
        return newMessage

    def startConnection(self):
        (self.clientsocket, self.clientAddress) = self.socket.accept()

    def receiveOrder(self):
        message = ""
        while message != "end":
            message = self.clientsocket.recv(128)
            print "\nmensaje recibido" + " = " + message
            message = self.onlyAllowedCharacter(message)
            message = message.split(" ")
            if message[0] != "end":
                print message
                print message[1]
                print str(self.convertStringToId(message[1]))
                agentNumber = self.convertStringToId(message[1])
            message = message[0]
            if message != "end" and message in self.commandList and agentNumber < len(self.orderDispatcher) and agentNumber != -1:
                self.orderDispatcher[agentNumber].dispatch(Command(self.index[message]))
        self.socket.close()
        for obj in self.orderDispatcher:
            obj.stopExecution()

class Command(object):
    
    def __init__(self, command):
        self.command = command
        self.finish = False

    def action(self, index):
        print "on action function"
        self.finish = self.command(index)
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
    index.sendCommand("move 1")
    #index.sendCommand("movenorth 1")
    print str(finish)
    return finish

def down(index):
    print("prueba ejecutada correctamente.")
    print str(index)
    finish = True
    #agents_pos[index][0] -= 1
    #index.sendCommand("tpx " + agents_pos[index][0])
    index.sendCommand("move -1")
    print str(finish)
    return finish
	
def right(index):
    print("prueba ejecutada correctamente.")
    print str(index)
    finish = True
    #agents_pos[index][2] += 1
    #index.sendCommand("tpz " + agents_pos[index][2])
    index.sendCommand("strafe 1")
    print str(finish)
    return finish

def left(index):
    print("prueba ejecutada correctamente.")
    print str(index)
    finish = True
    #agents_pos[index][2] -= 1
    #index.sendCommand("tpz " + agents_pos[index][2])
    index.sendCommand("strafe -1")
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
    o = OrderServer(9288, [("up", up),("down", down),("right",right),("left",left),("stop",stop)], dispatches, world_items["agentsId"])
    #print Aceptamos la conexion con la clase de java
    o.startConnection()
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