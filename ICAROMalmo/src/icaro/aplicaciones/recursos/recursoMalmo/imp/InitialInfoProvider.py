# coding=utf-8
import string
import socket as socket
import time

def provideInitialInfo(world_items):
    sck = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sck.connect(("127.0.0.1", 9289))
    string = "ap" + "_" + str(world_items["apples"]) + "\n"
    sck.send(string)
    time.sleep(0.3)
    string = "ag" + "_" + str(world_items["agents"]) + "\n"
    sck.send(string)
    time.sleep(0.3)
    string = "ob" + "_" + str(world_items["obstacles"]) + "\n"
    sck.send(string)
    time.sleep(0.3)
    sck.send("end")
    sck.close()