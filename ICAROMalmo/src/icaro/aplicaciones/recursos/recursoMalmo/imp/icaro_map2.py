# ------------------------------------------------------------------------------------------------
# Copyright (c) 2016 Microsoft Corporation
# 
# Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
# associated documentation files (the "Software"), to deal in the Software without restriction,
# including without limitation the rights to use, copy, modify, merge, publish, distribute,
# sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
# 
# The above copyright notice and this permission notice shall be included in all copies or
# substantial portions of the Software.
# 
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
# NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
# NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
# DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
# ------------------------------------------------------------------------------------------------

# Tutorial sample #6: Discrete movement, rewards, and learning

# The "Cliff Walking" example using Q-learning.
# From pages 148-150 of:
# Richard S. Sutton and Andrews G. Barto
# Reinforcement Learning, An Introduction
# MIT Press, 1998

import MalmoPython
import json
import logging
import os
import random
import sys
import time
import Tkinter as tk
from OrderServer import initDispatcher
import threading
import re
import uuid
            
def generateObstacles():
    for i in range(48):
        world_items["obstacles"].append([38, 227, i + 3])
    for i in [23, 24, 25, 26]:
        for j in [22, 23, 24, 25, 26]:
            world_items["obstacles"].append([i, 226, j])

def generateAgentsIds():
    #obtenemos en una lista de cadenas los nombres de los agentes con las etiquetas
    modified = re.findall("<Name>.*</Name>", mission_xml)
    print modified
    #quitamos las etiquetas
    for counter in range(len(modified)):
        element = modified[counter]
        element = re.sub("<Name>", "", element)
        element = re.sub("</Name>", "", element)
        modified[counter] = element
    print modified
    #Una vez quitado las etiquetas se meten en el diccionario
    world_items["agentsId"] = modified
    print world_items

def drawEntity(x, y, z, tipo, yaw, pitch, xVel, yVel, zVel):
    return '''<DrawEntity x="%f" y="%f" z="%f" type="%s" yaw="%f" pitch="%f" xVel="%f" yVel="%f" zVel="%f"/>''' \
          % (x, y, z, tipo, yaw, pitch, xVel, yVel, zVel)

def drawItem(x, y, z, tipo):
    return '''<DrawItem type="%s" x="%d" y="%d" z="%d"/>''' % (tipo, x, y, z)

def drawLine(tipo, x1, y1, z1, x2, y2, z2):
    return '''<DrawLine type="%s"  x1="%d" y1="%d" z1="%d" x2="%d" y2="%d" z2="%d"/>''' \
          % (tipo, x1, y1, z1, x2, y2, z2)

def drawItems():
    items = ""
    for coords in world_items["apples"]:
        items += drawItem(coords[0], coords[1], coords[2], "apple")

    for coords in world_items["enemies"]:
        items += drawEntity(coords[0], coords[1], coords[2], "Zombie", 90, 45, 1, 0, 1)

    items += drawLine("lava", 38, 227, 3, 38, 227, 50)

    return items

def GenCuboid(x1, y1, z1, x2, y2, z2, blocktype):
    return '<DrawCuboid x1="' + str(x1) + '" y1="' + str(y1) + '" z1="' + str(z1) + '" x2="' + str(x2) + '" y2="' + str(y2) + '" z2="' + str(z2) + '" type="' + blocktype + '"/>'

def GenCuboidWithVariant(x1, y1, z1, x2, y2, z2, blocktype, variant):
    return '<DrawCuboid x1="' + str(x1) + '" y1="' + str(y1) + '" z1="' + str(z1) + '" x2="' + str(x2) + '" y2="' + str(y2) + '" z2="' + str(z2) + '" type="' + blocktype + '" variant="' + variant + '"/>'
    
    
sys.stdout = os.fdopen(sys.stdout.fileno(), 'w', 0)  # flush print output immediately



xTop = 50
zTop = 50
numAgentes = 2

world_items = dict(apples=[[20, 227, 20], [12, 227, 32], [5, 227, 10], [27, 227, 20], [20, 227, 25]], enemies=[[46, 227, 46]], agents=[[22, 227, 22],[26, 227, 26]], obstacles=[], width = zTop, height = xTop)

generateObstacles()


# agent = TabQAgent()
# Create default Malmo objects:
agent_hosts = []
    
for i in range(numAgentes):
    agent_hosts.append(MalmoPython.AgentHost())



# -- set up the mission -- #

mission_xml = '''<?xml version="1.0" encoding="UTF-8" standalone="no" ?>
<Mission xmlns="http://ProjectMalmo.microsoft.com" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

    <About>
        <Summary>Hello world!</Summary>
    </About>

    <ServerSection>
        <ServerInitialConditions>
            <Time>
                <StartTime>1000</StartTime>
                <AllowPassageOfTime>false</AllowPassageOfTime>
            </Time>
            <Weather>clear</Weather>
        </ServerInitialConditions>

        <ServerHandlers>
            <FlatWorldGenerator generatorString="3;7,220*1,5*3,2;3;,biome_1" />
            <DrawingDecorator>
                <DrawCuboid x1="0" y1="227" z1="0" x2="''' + str(xTop) + '''"
                    y2="227" z2="''' + str(zTop) + '''" type="air" />   <!-- to clear old items -->
                <DrawCuboid x1="0" y1="227" z1="0" x2="''' + str(xTop) + '''"
                    y2="227" z2="''' + str(zTop) + '''" type="obsidian" />
                <DrawCuboid x1="0" y1="227" z1="0" x2="0" y2="237"
                    z2="''' + str(zTop) + '''" type="glass" />
                <DrawCuboid x1="0" y1="227" z1="0" x2="''' + str(xTop) + '''"
                    y2="237" z2="0" type="glass" />
                <DrawCuboid x1="''' + str(xTop) + '''" y1="227" z1="''' + str(zTop) + '''"
                    x2="''' + str(xTop) + '''" y2="237" z2="0" type="glass" />
                <DrawCuboid x1="''' + str(xTop) + '''" y1="227" z1="''' + str(zTop) + '''"
                    x2="0" y2="237" z2="''' + str(zTop) + '''" type="glass" />
                <DrawCuboid x1="23" y1="226" z1="22" x2="26" y2="236" z2="26"
                    type="lapis_block" />
                <DrawCuboid x1="23" y1="227" z1="22" x2="26" y2="227" z2="26"
                    type="red_flower" variant="blue_orchid" /> <!-- yes, blue orchids are indeed a type of red flower. -->
                ''' + drawItems() + '''
            </DrawingDecorator>
        </ServerHandlers>
    </ServerSection>


    <AgentSection mode="Survival">
        <Name>robot1Recolector</Name>
        <AgentStart>
            <Placement x="22.5" y="228.0" z="22.5" pitch="30" yaw="0" />
            <Inventory>
                <InventoryItem slot="8" type="diamond_pickaxe" />
            </Inventory>
        </AgentStart>
        <AgentHandlers>
            <DiscreteMovementCommands/>
            <ObservationFromFullStats />
            <ObservationFromGrid>
                <Grid name="floor3x3">
                    <min x="-1" y="-1" z="-1" />
                    <max x="1" y="-1" z="1" />
                </Grid>
            </ObservationFromGrid>
            <InventoryCommands />
            <AgentQuitFromTouchingBlockType>
                <Block type="diamond_block" />
            </AgentQuitFromTouchingBlockType>
        </AgentHandlers>
    </AgentSection>
    <AgentSection mode="Survival">
        <Name>robot2Recolector</Name>
        <AgentStart>
            <Placement x="22.5" y="228.0" z="34.5" pitch="30" yaw="0" />
            <Inventory>
                <InventoryItem slot="8" type="diamond_pickaxe" />
            </Inventory>
        </AgentStart>
        <AgentHandlers>
            <DiscreteMovementCommands/>
            <ObservationFromFullStats />
            <ObservationFromGrid>
                <Grid name="floor3x3">
                    <min x="-1" y="-1" z="-1" />
                    <max x="1" y="-1" z="1" />
                </Grid>
            </ObservationFromGrid>
            <InventoryCommands />
            <AgentQuitFromTouchingBlockType>
                <Block type="diamond_block" />
            </AgentQuitFromTouchingBlockType>
        </AgentHandlers>
    </AgentSection>
</Mission>'''

my_mission = MalmoPython.MissionSpec(mission_xml, True)
#my_mission.removeAllCommandHandlers()
#my_mission.allowAllDiscreteMovementCommands()
#my_mission.allowAllAbsoluteMovementCommands()
#my_mission.allowAllContinuousMovementCommands()
generateAgentsIds()

#thread = threading.Thread(target=provideInitialInfo,args = [world_items])
#thread.start()

client_pool = MalmoPython.ClientPool()
for x in xrange(10000, 10000 + numAgentes):
    client_pool.add( MalmoPython.ClientInfo('127.0.0.1', x) )


print "Comenzando mision"
'''
# Attempt to start a mission:
max_retries = 3
for retry in range(max_retries):
    try:
        agent_host[0].startMission(my_mission, my_mission_record)
        break
    except RuntimeError as e:
        if retry == max_retries - 1:
            print "Error starting mission:", e
            exit(1)
        else:
            time.sleep(2)'''
            
            
    


print "esperando hasta que la mision comience"




#agent_host.startMission( my_mission, my_client_pool, my_mission_record, role, expId )
for i in range(len(agent_hosts)):
    print "iniciando el robot " + str(i)
    retry = 0
    max_retries= 3
    for retry in range(max_retries):
        try:
        # Attempt to start the mission:
            agent_hosts[i].startMission( my_mission, client_pool, MalmoPython.MissionRecordSpec(), i, '') #str(uuid.uuid4())
            break
        except RuntimeError as e:
            if retry == max_retries - 1:
                print "Error starting mission",e
                print "Is the game running?"
                exit(1)
            else:
                # In a multi-agent mission, startMission will fail if the integrated server
                # hasn't yet started - so if none of our clients were available, that may be the
                # reason. To catch this specifically we could check the results for "MALMONOSERVERYET",
                # but it should be sufficient to simply wait a bit and try again.
                time.sleep(5)


print "Comenzando zona pruebas"
initDispatcher(world_items, agent_hosts)
print "fin zona de pruebas"
time.sleep(2)


# Loop until mission starts:
print "Waiting for the mission to start ",
hasBegun = False
hadErrors = False
while not hasBegun and not hadErrors:
    sys.stdout.write(".")
    time.sleep(0.1)
    #print "miramos a los agentes"
    for ah in agent_hosts:
        #print "tomamos el estado del mundo del agente " + str(ah)
        world_state = ah.getWorldState()
        #print "mirando si la mision empezo"
        if world_state.has_mission_begun:
            hasBegun = True
            #print "empezo"
        if len(world_state.errors):
            hadErrors = True
            #print "hubo error"
            print "Errors from agent " + agentName(agent_hosts.index(ah))
            for error in world_state.errors:
                print "Error:",error.text
if hadErrors:
    print "ABORTING"
    exit(1)
print "Mission running "

# Possible solution for challenge set in tutorial_4.py:

#agent_host.sendCommand("hotbar.9 1") #Press the hotbar key
#agent_host.sendCommand("hotbar.9 0") #Release hotbar key - agent should now be holding diamond_pickaxe

#agent_host.sendCommand("pitch 0.2") #Start looking downward slowly
#time.sleep(1)                        #Wait a second until we are looking in roughly the right direction
#agent_host.sendCommand("pitch 0")    #Stop tilting the camer
#agent_host[0].sendCommand("move 1")     #And start running...
#agent_host.sendCommand("attack 1")   #Whilst flailing our pickaxe!

# Loop until mission ends:
while world_state.is_mission_running:
    pass
'''sys.stdout.write(".")
    time.sleep(0.1)
    for i in range(numAgentes):
        world_state = agent_hosts[i].getWorldState()
        for error in world_state.errors:
            print "Error:", error.text
        if world_state.number_of_observations_since_last_state > 0:  # Have any observations come in?
            pass
            #msg = world_state.observations[-1].text  # Yes, so get the text
            #observations = json.loads(msg)  # and parse the JSON
            #grid = observations.get(u'floor3x3', 0)  # and get the grid we asked for
            # ADD SOME CODE HERE TO SAVE YOUR AGENT
'''
print "Mission ended"
# Mission has ended.
#thread.join()
