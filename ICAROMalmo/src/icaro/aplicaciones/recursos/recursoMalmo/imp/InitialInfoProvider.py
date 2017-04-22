# coding=utf-8
import string
import socket as socket
import time

def provideInitialInfo(world_items):
    #iniciamos conexion con la clase java 
    outSck = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    outSck.connect(("127.0.0.1", 9289))
    #esperamos la conexion desde la clase java
    inSck = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    inSck.bind(("127.0.0.1", 9290))
    inSck.listen(1)
    (clientsocket, clientAddress) = inSck.accept()
    message = ""
    message = clientsocket.recv(128)
    while message != "end":    
        #enviamos la informacion
        string = "ap" + "_" + str(world_items["apples"]) + "\n"
        outSck.send(string)
        time.sleep(0.3)
        string = "ag" + "_" + str(world_items["agents"]) + "\n"
        outSck.send(string)
        time.sleep(0.3)
        string = "ob" + "_" + str(world_items["obstacles"]) + "\n"
        outSck.send(string)
        time.sleep(0.3)
        outSck.send("end" + "\n")
        message = clientsocket.recv(128)
    outSck.close()
    inSck.close()
