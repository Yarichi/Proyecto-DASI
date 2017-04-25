# coding=utf-8
import string
import socket as socket
import time

def onlyAllowedCharacter(message):
    newMessage = ''
    for character in message:
        if (character >= 'a' and character <= 'z') or (character >= 'A' and character <= 'Z') or character == " " or (character >= '0' and character <= '9'):
            newMessage = newMessage + character
    return newMessage

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
    message = onlyAllowedCharacter(clientsocket.recv(128))
    while message != "end":    
        #enviamos la informacion
        print "envio 1"
        string = "ap" + "_" + str(world_items["apples"]) + "\n"
        outSck.send(string)
        time.sleep(0.3)
        print "envio 2"
        string = "ag" + "_" + str(world_items["agents"]) + "\n"
        outSck.send(string)
        time.sleep(0.3)
        print "envio 3"
        string = "ob" + "_" + str(world_items["obstacles"]) + "\n"
        outSck.send(string)
        time.sleep(0.3)
        print "envio 4"
        string = "id" + "_" + str(world_items["agentsId"]) + "\n"
        outSck.send(string)
        time.sleep(0.3)
        print "envio de fin de info"
        outSck.send("end" + "\n")
        print "a la espera de mas o cierre"
        message = onlyAllowedCharacter(clientsocket.recv(128))
        print "recibido"
        print message
    outSck.close()
    inSck.close()
    print "cerrando sockets del info provider"
