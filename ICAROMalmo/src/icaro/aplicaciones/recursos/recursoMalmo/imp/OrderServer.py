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
from time import sleep

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
            if not self.queue.empty() and finishCondition == True:
                obj = self.queue.get()
                obj.action(self.number)
                finishCondition = obj.isFinished()
                
                '''thread = threading.Thread(target=obj.action, args=[self.number])
                thread.start();'''


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

    def initializeStructures(self, list, dispatcher, initInfo, agents, agent_routes):
        self.index = {}
        self.commandList = []
        for element in list:
            self.index[element[0].lower()] = element[1]
            self.commandList.append(element[0].lower())
        self.orderDispatcher = dispatcher
        self.initInfo = initInfo
        self.agentRoutes = agent_routes
        self.lock = threading.Lock()
        self.agents = agents

    def __init__(self, port, actionList, dispatcher, initInfo, agents, agent_routes):
        self.initializeStructures(actionList, dispatcher, initInfo, agents, agent_routes)
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
            if '' in message:
                message.remove('')
            print message
            if message[0] == "end" and len(message) == 1:
                break
            else:
                #thread = threading.Thread(target=self.parseMessage, args=[message])
                #thread.start()
                self.parseMessage(message)
        self.inSocket.close()
        for obj in self.orderDispatcher:
            obj.stopExecution()

    def parseMessage(self, message):
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
                        obs = json.loads(self.agents[idEntero].peekWorldState().observations[-1].text)
                        position = "ag_" + message[1] + "_["+str(float(obs[u'XPos'])) + ", " + str(float(obs[u'YPos'])) + ", " + str(float(obs[u'ZPos'])) + "]\n"
                        self.lock.acquire(True)
                        self.outSocket.send(position)
                        self.lock.release()
                else:
                    self.lock.acquire(True)
                    self.outSocket.send("Error: id de agente no identificado\n")
                    self.lock.release()
            elif message[0] == "enemies" and len(message) == 1:
                self.lock.acquire(True)
                self.outSocket.send("en" + "_" + str(self.initInfo["enemies"]) + "\n")
                self.lock.release()
            elif message[0] == "apples" and len(message) == 1:
                self.lock.acquire(True)
                self.outSocket.send("ap" + "_" + str(self.initInfo["apples"]) + "\n")
                self.lock.release()
            elif message[0] == "move" and len(message) == 4:
                idEntero = self.convertStringToId(message[1])
                if idEntero != -1:
                    self.orderDispatcher[idEntero].dispatch(Command(self.index[message[0]], [self.outSocket, self.lock, message[2], message[3], self.initInfo, self.agentRoutes]))
                else:
                    self.lock.acquire(True)
                    self.outSocket.send("Error: id de agente no identificado\n")
                    self.lock.release()
            elif "buildriver" in message[0] and len(message) == 7:
                idEntero = self.convertStringToId(message[1])
                self.orderDispatcher[idEntero].dispatch(Command(self.index["buildriver"],
                                                        [(message[2], message[3]), (message[4], message[5]), message[6], self.outSocket, self.lock, self.initInfo, self.agentRoutes]))
            elif "pickstone" in message[0] and len(message) == 7:
                idEntero = self.convertStringToId(message[1])
                self.orderDispatcher[idEntero].dispatch(Command(self.index["pickstone"],
                                                        [(message[2], message[3]), (message[4], message[5]), message[6], self.outSocket, self.lock, self.initInfo, self.agentRoutes]))

            elif message[0] == "eval" and len(message) == 4:
                ##Llamar a la funcion aqui
                idEntero = self.convertStringToId(message[1])
                if idEntero != -1:
                    self.orderDispatcher[idEntero].dispatch(Command(self.index[message[0]], [self.outSocket, self.lock, message[2], message[3], self.initInfo, self.agentRoutes]))
                else:
                    self.lock.acquire(True)
                    self.outSocket.send("Error: id de agente no identificado\n")
                    self.lock.release()
            else: 
                self.lock.acquire(True)
                self.outSocket.send("No command found\n")
                self.lock.release()
            #message = message[0]
            #if message != "end" and message in self.commandList and agentNumber < len(self.orderDispatcher) and agentNumber != -1:
            #    self.orderDispatcher[agentNumber].dispatch(Command(self.index[message], params))

class Command(object):
    
    def __init__(self, command, params):
        self.command = command
        self.finish = False
        self.params = params

    def action(self, index):
        #print "on action function"
        self.finish = self.command(index, self.params)
        #print "on end action function"
        #print str(self.finish)
    
    def isFinished(self):
        return self.finish



def up(index):
    index.sendCommand("movenorth 1")
    return True

def down(index):
    index.sendCommand("movesouth 1")
    return True
	
def right(index):
    index.sendCommand("moveeast 1")
    return True

def left(index):
    index.sendCommand("movewest 1")
    return True

def giraAgente(agente, oriActual,oriNueva):
    #print("OrientacionActual: %i, OrientacionNueva: %i"%(oriActual,oriNueva))
    if oriActual>oriNueva:
        n = (oriActual-oriNueva)/90
        for i in range(int(n)):
            agente.sendCommand("turn -1")
            sleep(0.1)
    elif oriActual<oriNueva:
        n = (oriNueva-oriActual)/90
        for i in range(int(n)):
            agente.sendCommand("turn 1")
            sleep(0.1)
    return oriNueva

def miraSiHayObstaculos(obs,world_items):
    alrededor = obs['floor3x3']
    actualesObstaculos = world_items['obstacles']
    actualesRios = world_items["rivers"]
    xini = obs['XPos'] -1
    yini = obs['YPos'] -1
    zini = obs['ZPos'] -1
    pos = 0
    obstaculosDetectados = []
    piedrasDetectadas = []
    riosDetectados = []
    for block in alrededor:
        if yini == (obs['YPos'] -1) and (block == 'agua' or block == 'lava') :
                if (xini,zini) not in actualesObstaculos and (xini,zini) not in obstaculosDetectados:
                    obstaculosDetectados.append((xini,zini))
                    riosDetectados.append((xini, zini))
        if yini > (obs['YPos'] -1) and (block == 'stone') :
            if (xini,zini) not in actualesObstaculos and (xini,zini) not in obstaculosDetectados:
                obstaculosDetectados.append((xini,zini))
                piedrasDetectadas.append((xini, zini))
                print piedrasDetectadas
        elif yini > (obs['YPos'] -1):
            if block != 'air':
                if (xini,zini) not in actualesObstaculos and (xini,zini) not in obstaculosDetectados:
                    obstaculosDetectados.append((xini,zini))
        pos = pos + 1
        if pos % 9 == 0:
            xini = obs['XPos'] -1
            yini = yini + 1
            zini = obs['ZPos'] -1
        elif pos % 3 == 0:
            xini = obs['XPos'] - 1
            zini = zini + 1
        else:
            xini = xini + 1

    return obstaculosDetectados,riosDetectados, piedrasDetectadas
    
def getObservations(agent):
    obs = json.loads(agent.peekWorldState().observations[-1].text)
    obs = json.loads(agent.peekWorldState().observations[-1].text)#Importante dos veces, si no,no se actualiza el Yaw
    return obs

#arg[0] --> posicion del puente
#arg[1] --> posicion a la que ir de la manzana
#arg[2]--> yaw
#arg[3] --> socket
#arg[4] --> lock
#arg[5] --> world_items
def buildriver(index, args):
    (posX, posZ) = args[0]
    (manX, manZ) = args[1]
    arg = [args[3], args[4], posX, posZ, args[5], args[6]]
    obs = getObservations(index)
    yaw = obs["Yaw"]
    move(index, arg)
    yaw = giraAgente(index, yaw,int(args[2]))
    index.sendCommand("move -1")
    time.sleep(0.4);
    index.sendCommand("move -1")
    useBlock(index, 2)
    index.sendCommand("move 1")
    time.sleep(0.4)
    index.sendCommand("move 1")
    time.sleep(0.4)
    index.sendCommand("move 1")
    time.sleep(0.4)
    index.sendCommand("move 1")
    time.sleep(0.4)
    arg[2] = manX
    arg[3] = manZ
    move(index, arg)
    return True

#arg[0] --> posicion de la piedra
#arg[1] --> posicion a la que ir de la manzana
#arg[2]--> yaw
#arg[3] --> socket
#arg[4] --> lock
#arg[5] --> world_items
def pickstone(index, args):
    (posX, posZ) = args[0]
    (manX, manZ) = args[1]
    arg = [args[3], args[4], posX, posZ, args[5], args[6]]
    obs = getObservations(index)
    yaw = obs["Yaw"]
    move(index, arg)
    yaw = giraAgente(index, yaw,int(args[2]))

    usePick(index, 3)
    time.sleep(0.4)
    index.sendCommand("move 1")
    time.sleep(0.4)
    index.sendCommand("move 1")
    time.sleep(0.4)
    index.sendCommand("move 1")
    time.sleep(0.4)
    index.sendCommand("move 1")
    time.sleep(0.4)
    arg[2] = manX
    arg[3] = manZ
    move(index, arg)
    return True

def usePick(index, hotbarIndex):
    hb1 = ("hotbar.%d 1")%(int(hotbarIndex))
    hb2 = "hotbar.1 1"
    use = "attack 1"
    index.sendCommand(hb1)
    time.sleep(0.1)
    index.sendCommand(use)
    time.sleep(0.1)
    index.sendCommand(hb2)
 

def useBlock(index, hotbarIndex):
    hb1 = ("hotbar.%d 1")%(int(hotbarIndex))
    hb2 = "hotbar.1 1"
    use = "use 1"
    index.sendCommand(hb1)
    time.sleep(0.1)
    index.sendCommand(use)
    time.sleep(0.1)
    index.sendCommand(hb2)

#args[0] --> socket
#args[1] --> lock
#args[2] --> X
#args[3] --> Z
#args[4] --> world_items
def move(index,args):
    obs = getObservations(index)
    xini = obs["XPos"]
    zini = obs["ZPos"]
    name = obs["Name"]
    c = CalculoRutas((xini,zini),args[4]["obstacles"],args[4]["width"],args[4]["height"])
    route = c.calculaRuta(float(args[2]),float(args[3]))
    args[5][obs['Name']] = len(route)
    
    print args[5][obs['Name']] 
    yaw = obs["Yaw"]
    #norte es la -Z y Este es la +x.
    for (newX,newZ) in route:
        print str(newX) + " " + str(newZ)
        if newZ < zini:
            yaw = giraAgente(index, yaw,180)
            nuevosObstaculos,riosNuevos, piedrasNuevas = miraSiHayObstaculos(json.loads(index.peekWorldState().observations[-1].text),args[4])
            if len(nuevosObstaculos) > 0:
                for pos in nuevosObstaculos:
                    args[4]['obstacles'].append(pos)
                    if pos in riosNuevos:
                        args[4]['rivers'].append(pos)
                    if pos in piedrasNuevas:
                        args[4]['stones_detected'].append(pos)
                if (xini,newZ) in args[4]['obstacles']:
                    if (xini,newZ) in args[4]['rivers']:
                        sendRiverFound(name, (xini, zini), (float(args[2]), float(args[3])), yaw, args[1], args[0])
                        return True
                    if (xini,newZ) in args[4]['stones_detected']:
                        sendStoneFound(name, (xini, zini), (float(args[2]), float(args[3])), yaw, args[1], args[0])
                        return True
                    else:    
                        return move(index,args)
                    break
            zini = newZ
            print args[5][obs['Name']] 
            index.sendCommand("move 1")
            time.sleep(0.4)
            
        elif newZ > zini:
            yaw = giraAgente(index, yaw,0)
            nuevosObstaculos,riosNuevos, piedrasNuevas = miraSiHayObstaculos(json.loads(index.peekWorldState().observations[-1].text),args[4])
            if len(nuevosObstaculos) > 0:
                for pos in nuevosObstaculos:
                    args[4]['obstacles'].append(pos)
                    if pos in riosNuevos:
                        args[4]['rivers'].append(pos)
                    if pos in piedrasNuevas:
                        args[4]['stones_detected'].append(pos)
                if (xini,newZ) in args[4]['obstacles']:
                    if (xini,newZ) in args[4]['rivers']:
                        sendRiverFound(name, (xini, zini), (float(args[2]), float(args[3])), yaw, args[1], args[0])
                        return True
                    if (xini,newZ) in args[4]['stones_detected']:
                        sendStoneFound(name, (xini, zini), (float(args[2]), float(args[3])), yaw, args[1], args[0])
                        return True
                    else:
                        return move(index,args)
                    break
            zini = newZ
            print args[5][obs['Name']]
            
            index.sendCommand("move 1")
            time.sleep(0.4)
        obs = getObservations(index)
            
        if newX > xini:
            yaw = giraAgente(index, yaw ,270)
            nuevosObstaculos,riosNuevos, piedrasNuevas = miraSiHayObstaculos(json.loads(index.peekWorldState().observations[-1].text),args[4])
            if len(nuevosObstaculos) > 0:
                for pos in nuevosObstaculos:
                    args[4]['obstacles'].append(pos)
                    if pos in riosNuevos:
                        args[4]['rivers'].append(pos)
                    if pos in piedrasNuevas:
                        args[4]['stones_detected'].append(pos)
                if (newX,zini) in args[4]['obstacles']:
                    if (newX,zini) in args[4]['rivers']:
                        sendRiverFound(name, (xini, zini), (float(args[2]), float(args[3])), yaw, args[1], args[0])
                        return True
                    if (newX,zini) in args[4]['stones_detected']:
                        sendStoneFound(name, (xini, zini), (float(args[2]), float(args[3])), yaw, args[1], args[0])
                        return True
                    else:
                        return move(index,args)
                    break
            xini = newX
            print args[5][obs['Name']]
            index.sendCommand("move 1")
            time.sleep(0.4)
        elif newX < xini:
            yaw = giraAgente(index, yaw ,90)
            nuevosObstaculos,riosNuevos, piedrasNuevas = miraSiHayObstaculos(json.loads(index.peekWorldState().observations[-1].text),args[4])
            if len(nuevosObstaculos) > 0:
                for pos in nuevosObstaculos:
                    args[4]['obstacles'].append(pos)
                    if pos in riosNuevos:
                        args[4]['rivers'].append(pos)
                    if pos in piedrasNuevas:
                        args[4]['stones_detected'].append(pos)
                if (newX,zini) in args[4]['obstacles']:
                    if (newX,zini) in args[4]['rivers']:
                        sendRiverFound(name, (xini, zini), (float(args[2]), float(args[3])), yaw, args[1], args[0])
                        return True
                    if (newX,zini) in args[4]['stones_detected']:
                        sendStoneFound(name, (xini, zini), (float(args[2]), float(args[3])), yaw, args[1], args[0])
                        return True
                    else:
                        return move(index,args)
                    break
            xini = newX
            print args[5][obs['Name']]
            index.sendCommand("move 1")
            time.sleep(0.4)
        obs = getObservations(index)
        args[5][obs['Name']] = args[5][obs['Name']] - 1
    print str(xini) + " " + str(zini)
    for coords in args[4]['apples']:
        if (coords[0]) == xini and (coords[2])  == zini:
            time.sleep(0.4)
            args[1].acquire(True)
            message = "success_%s_%f_%f\n"%(obs['Name'], xini, zini)
            args[0].send(message)
            args[1].release()
    return True

def sendRiverFound(agentId, posIni, posDest, yaw, lock, outSocket):
    (xini, zini) = posIni
    (xdest, zdest) = posDest
    message = "river_%s_%f_%f_%i_%f_%f\n"%(agentId, xini, zini, yaw, xdest, zdest)
    lock.acquire(True)
    outSocket.send(message)
    lock.release()

def sendStoneFound(agentId, posIni, posDest, yaw, lock, outSocket):
    (xini, zini) = posIni
    (xdest, zdest) = posDest
    message = "stone_%s_%f_%f_%i_%f_%f\n"%(agentId, xini, zini, yaw, xdest, zdest)
    lock.acquire(True)
    outSocket.send(message)
    lock.release()


def eval(index,args):
    outSocket = args[0]
    obs = getObservations(index)
    xini = obs["XPos"]
    zini = obs["ZPos"]
    c = CalculoRutas((xini,zini),args[4]["obstacles"],args[4]["width"],args[4]["height"])
    route = c.calculaRuta(float(args[2]),float(args[3]))
    print len(route)+args[5][obs['Name']]
    if route is not None:
        message = "eval_%s_%i\n"%(obs['Name'],len(route)+args[5][obs['Name']])
    else:
        message = "eval_%s_-1\n"%obs['Name']
    args[1].acquire(True)
    outSocket.send(message)
    args[1].release()
    return True
    
def initDispatcher(world_items, agent_routes, agent_host):
    #TODO se supone que tenemos el numero de agentes
    #cogemos el numero de agentes
    amountAgents = len(world_items["agents"])
    #Cogemos la posicion de todos los agentes.
    cont = 0;
    for id in world_items["agentsId"]:
        agents_pos[str(cont)] = world_items["agents"][cont]
        cont+=1
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
    o = OrderServer(9288, [("up", up),("down", down),("right",right),("left",left),("move",move),("eval",eval), ("buildriver",buildriver), ("pickstone", pickstone)], dispatches, world_items, agent_host, agent_routes)
    #print Aceptamos la conexion con la clase de java
    #print Como ya hay conexion establecemos un hilo para que vaya pasando las ordenes al despachador
    thread = threading.Thread(target=o.receiveOrder)
    thread.start()
    #iniciando la ejecucion de ordenes
    thread2 = []
    for contador in range(amountAgents):
        thread2.append(threading.Thread(target=dispatches[contador].execution))
        thread2[contador].start()
    
    while thread.is_alive() or thread2[0].is_alive():
        pass