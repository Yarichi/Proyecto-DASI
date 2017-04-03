# coding=utf-8
import string
import socket as socket
from collections import defaultdict
import heapq
import Queue as Queue
import threading

class OrderDispatcher(object):
    def __init__(self):
        self.queue = Queue.Queue()
        self.stop = False
        
    def dispatch(self, action):
        self.queue.put(action)
        
    def execution(self):
	    ##if(!self.queue.empty)
        ##threading.Thread(target=self.queue.get())
        while self.stop == False:
            if self.queue.empty() == False:
                fun = self.queue.get()
                fun()

    def stopExecution(self):
        self.stop = True

class OrderServer(object):

    def initializeConnection(self, port):
        self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.socket.bind(('127.0.0.1', port))
        self.socket.listen(1)

    def initializeStructures(self, list, dispatcher):
        self.index = {}
        #self.index = defaultdict(lambda: None, self.index)
        for element in list:
            self.index[element[0]] = element[1]
        self.orderDispatcher = dispatcher

    def __init__(self, port, actionList, dispatcher):
        self.initializeStructures(actionList, dispatcher)
        self.initializeConnection(port)
		
    def onlyAllowedCharacter(self, message):
        newMessage = ''
        for character in message:
            if (character >= 'a' and character <= 'z') or (character >= 'A' and character <= 'Z'):
                newMessage = newMessage + character
        return newMessage

    def startConnection(self):
        (self.clientsocket, self.clientAddress) = self.socket.accept()

    def receiveOrder(self):
        message = ""
        while message != "end":
            message = self.clientsocket.recv(128)
            message = self.onlyAllowedCharacter(message)
            if message != "end":
                self.orderDispatcher.dispatch(self.index[message])
        self.socket.close()
        self.orderDispatcher.stopExecution()

def prueba():
    print("prueba ejecutada correctamente.")
	
#Iniciando el despachador de ordenes
dispatch = OrderDispatcher()
#creamos la clase que recibe y apunta ordenes
o = OrderServer(17999, [("prueba", prueba)], dispatch)
#print Aceptamos la conexion con la clase de java
o.startConnection()
#print Como ya hay conexion establecemos un hilo para que vaya pasando las ordenes al despachador
thread = threading.Thread(target=o.receiveOrder)
thread.start()
#iniciando la ejecucion de ordenes
dispatch.execution()
thread.join()
