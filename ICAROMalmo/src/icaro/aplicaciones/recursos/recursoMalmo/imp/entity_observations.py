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

# Tutorial sample #5: Observations
'''
Para obtener informacion en tiempo real de entidades (enemigos) se necesita activar
primero en el XML que configura el servidor la opcion de que se cargen las observaciones 
de este tipo con un rango determinado del espacio del mapa. Por ejemplo, de la siguiente manera
se obtendrian en la coleccion de observaciones dos propiedades "close_entities" y "far_entities",
que contendran las entidades existentes en el mapa (cercanas y lejanas):

<ObservationFromNearbyEntities>
    <Range name="close_entities" xrange="2" yrange="2" zrange="2" />
    <Range name="far_entities" xrange="10" yrange="2" zrange="10" update_frequency="100"/>
</ObservationstionFromNearbyEntities>

Cuando se obtiene la informacion del mundo ya se puede 
world_state = agent_host.peekWorldState()
if world_state.number_of_observations_since_last_state > 0:
    msg = world_state.observations[-1].text
    ob = json.loads(msg)
    if "close_entities" in ob:
        entities = [EntityInfo(**k) for k in ob["close_entities"]]
        for ent in entities:
            print ent.name, ent.x, ent.z, ent.quantity
    
    if "far_entities" in ob:
        far_entities = [EntityInfo(**k) for k in ob["far_entities"]]
        for ent in far_entities:
            print ent.name, ent.quantity
'''
import MalmoPython
import os
import sys
import time
import json

from collections import namedtuple
# Esta tupla representa la estructura de la informacion que podemos obtener de las entidades
# del mapa a traves de agent_host.getWorldState()
EntityInfo = namedtuple('EntityInfo', 'x, y, z, name, quantity')
EntityInfo.__new__.__defaults__ = (0, 0, 0, "", 1)

sys.stdout = os.fdopen(sys.stdout.fileno(), 'w', 0)  # flush print output immediately

world_items = dict(obstacles=[[23,226,22,26,236,26]],apples=[[20,227,20],[12,227,32],[5,227,10],[27,227,20],[20,227,25]], enemies=[[2,229,2],[42,229,7],[41,229,47],[40,229,30]])

xTop = 50
zTop = 50

def drawEntity(x,y,z,tipo,yaw,pitch,xVel,yVel,zVel):
    return '''<DrawEntity x="%f" y="%f" z="%f" type="%s" yaw="%f" pitch="%f" xVel="%f" yVel="%f" zVel="%f"/>''' \
          % (x,y,z,tipo,yaw,pitch,xVel,yVel,zVel)

def drawItem(x,y,z,tipo):
    return '''<DrawItem type="%s" x="%d" y="%d" z="%d"/>''' % (tipo,x,y,z)

def drawLine(tipo,x1,y1,z1,x2,y2,z2):
    return '''<DrawLine type="%s"  x1="%d" y1="%d" z1="%d" x2="%d" y2="%d" z2="%d"/>''' \
          % (tipo,x1,y1,z1,x2,y2,z2)

def drawMap():
    world = ""
    world += drawCuboid(0, 227, 0, xTop, 227, zTop, "air")
    world += drawCuboid(0, 227, 0, xTop, 227, zTop, "glowstone")
    world += drawCuboid(0, 227, 0, 0, 237, zTop, "barrier")
    world += drawCuboid(0, 227, 0, xTop, 237, 0, "barrier")
    world += drawCuboid(xTop, 227, zTop, xTop, 237, 0, "barrier")
    world += drawCuboid(xTop, 227, zTop, 0, 237, zTop, "barrier")
    for coords in world_items["obstacles"]:
        world += drawCuboid(coords[0],coords[1],coords[2],coords[3],coords[4],coords[5],"lapis_block")
        world += drawCuboid(coords[0],coords[4],coords[2],coords[3],coords[4],coords[5],"beacon")
    return world

def drawItems():
    items = ""
    items += drawMap()
    for coords in world_items["apples"]:
        items += drawItem(coords[0],coords[1],coords[2],"apple")

    for coords in world_items["enemies"]:
        items += drawEntity(coords[0],coords[1],coords[2],"Zombie",90,45,1,0,1)

    items += drawLine("lava",38,227,0,38,227,50)
    items += drawLine("lava",-1,227,-1,-1,227,zTop+1)
    items += drawLine("lava",-1,227,-1,xTop+1,227,-1)
    items += drawLine("lava",xTop+1,227,zTop+1,xTop+1,227,-1)
    items += drawLine("lava",xTop+1,227,zTop+1,-1,227,zTop+1)

    return items

def drawCuboid(x1, y1, z1, x2, y2, z2, blocktype):
    return '<DrawCuboid x1="' + str(x1) + '" y1="' + str(y1) + '" z1="' + str(z1) + '" x2="' + str(x2) + '" y2="' + str(y2) + '" z2="' + str(z2) + '" type="' + blocktype + '"/>'

def drawCuboidWithVariant(x1, y1, z1, x2, y2, z2, blocktype, variant):
    return '<DrawCuboid x1="' + str(x1) + '" y1="' + str(y1) + '" z1="' + str(z1) + '" x2="' + str(x2) + '" y2="' + str(y2) + '" z2="' + str(z2) + '" type="' + blocktype + '" variant="' + variant + '"/>'
    
missionXML='''<?xml version="1.0" encoding="UTF-8" standalone="no" ?>
            <Mission xmlns="http://ProjectMalmo.microsoft.com" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            
              <About>
                <Summary>DASI project</Summary>
              </About>
              
            <ServerSection>
              <ServerInitialConditions>
                <Time>
                    <StartTime>18500</StartTime>
                    <AllowPassageOfTime>false</AllowPassageOfTime>
                </Time>
                <Weather>clear</Weather>
              </ServerInitialConditions>

            <ServerHandlers>
                <FlatWorldGenerator destroyAfterUse="1" forceReset="1" generatorString="3;7,220*1,5*3,2;3;,biome_1" />
                <DrawingDecorator>
                    ''' + drawMap() + drawItems() + '''
                </DrawingDecorator>
                <ServerQuitFromTimeUp timeLimitMs="150000"/>
                <ServerQuitWhenAnyAgentFinishes />
            </ServerHandlers>
            </ServerSection>

              <AgentSection mode="Creative">
                <Name>AgenteRecolector</Name>
                <AgentStart>
                    <Placement x="7" y="229" z="36" yaw="-135"/>
                    <Inventory>
                        <InventoryItem slot="8" type="diamond_pickaxe"/>
                    </Inventory>
                </AgentStart>
                <AgentHandlers>
                  <ObservationFromFullStats/>
                  <ObservationFromGrid>
                      <Grid name="floor3x3">
                        <min x="-1" y="-1" z="-1"/>
                        <max x="1" y="-1" z="1"/>
                      </Grid>
                  </ObservationFromGrid>
                  <ObservationFromNearbyEntities>
                    <Range name="enemies" xrange="50" yrange="2" zrange="50"/>
                  </ObservationFromNearbyEntities>
                  <ContinuousMovementCommands turnSpeedDegs="180"/>
                  <InventoryCommands/>
                  <AgentQuitFromTouchingBlockType>
                      <Block type="diamond_block" />
                  </AgentQuitFromTouchingBlockType>
                </AgentHandlers>
              </AgentSection>
            </Mission>'''

# Create default Malmo objects:
agent_host = MalmoPython.AgentHost()
try:
    agent_host.parse( sys.argv )
except RuntimeError as e:
    print 'ERROR:',e
    print agent_host.getUsage()
    exit(1)
if agent_host.receivedArgument("help"):
    print agent_host.getUsage()
    exit(0)

my_mission = MalmoPython.MissionSpec(missionXML, True)
my_mission_record = MalmoPython.MissionRecordSpec()

# Attempt to start a mission:
max_retries = 3
for retry in range(max_retries):
    try:
        agent_host.startMission( my_mission, my_mission_record )
        break
    except RuntimeError as e:
        if retry == max_retries - 1:
            print "Error starting mission:",e
            exit(1)
        else:
            time.sleep(2)

# Loop until mission starts:
print "Waiting for the mission to start ",
world_state = agent_host.getWorldState()
while not world_state.has_mission_begun:
    sys.stdout.write(".")
    time.sleep(0.1)
    world_state = agent_host.getWorldState()
    for error in world_state.errors:
        print "Error:",error.text

print
print "Mission running ",

while world_state.is_mission_running:
    sys.stdout.write(".")
    time.sleep(1)
    world_state = agent_host.peekWorldState()
    for error in world_state.errors:
        print "Error:",error.text
    if world_state.number_of_observations_since_last_state > 0: # Have any observations come in?
        msg = world_state.observations[-1].text                 # Yes, so get the text
        observations = json.loads(msg)                          # and parse the JSON
        grid = observations.get(u'floor3x3', 0)                 # and get the grid we asked for
        entities = [EntityInfo(**k) for k in observations["enemies"]]
        enemy_positions = []
        for ent in entities:
            if ent.name == "Zombie":
                enemy_positions.append([ent.x,ent.z])
        world_items["enemies"] = enemy_positions
        print world_items["enemies"]
print "Mission ended"
# Mission has ended.