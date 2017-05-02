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


class TabQAgent:
    """Tabular Q-learning agent for discrete state/action spaces."""

    def __init__(self):
        self.epsilon = 0.01  # chance of taking a random action instead of the best

        self.logger = logging.getLogger(__name__)
        if False:  # True if you want to see more information
            self.logger.setLevel(logging.DEBUG)
        else:
            self.logger.setLevel(logging.INFO)
        self.logger.handlers = []
        self.logger.addHandler(logging.StreamHandler(sys.stdout))

        self.actions = ["movenorth 1", "movesouth 1", "movewest 1", "moveeast 1"]
        self.q_table = {}
        self.canvas = None
        self.root = None

    def updateQTable(self, reward, current_state):
        """Change q_table to reflect what we have learnt."""
        
        # retrieve the old action value from the Q-table (indexed by the previous state and the previous action)
        old_q = self.q_table[self.prev_s][self.prev_a]
        
        # TODO: what should the new action value be?
        new_q = reward
        
        # assign the new action value to the Q-table
        self.q_table[self.prev_s][self.prev_a] = new_q
        
    def updateQTableFromTerminatingState(self, reward):
        """Change q_table to reflect what we have learnt, after reaching a terminal state."""
        
        # retrieve the old action value from the Q-table (indexed by the previous state and the previous action)
        old_q = self.q_table[self.prev_s][self.prev_a]
        
        # TODO: what should the new action value be?
        new_q = reward
        
        # assign the new action value to the Q-table
        self.q_table[self.prev_s][self.prev_a] = new_q
        
    def act(self, world_state, agent_host, current_r):
        """take 1 action in response to the current world state"""
        
        obs_text = world_state.observations[-1].text
        obs = json.loads(obs_text)  # most recent observation
        self.logger.debug(obs)
        if not u'XPos' in obs or not u'ZPos' in obs:
            self.logger.error("Incomplete observation received: %s" % obs_text)
            return 0
        current_s = "%d:%d" % (int(obs[u'XPos']), int(obs[u'ZPos']))
        self.logger.debug("State: %s (x = %.2f, z = %.2f)" % (current_s, float(obs[u'XPos']), float(obs[u'ZPos'])))
        if not self.q_table.has_key(current_s):
            self.q_table[current_s] = ([0] * len(self.actions))

        # update Q values
        if self.prev_s is not None and self.prev_a is not None:
            self.updateQTable(current_r, current_s)

        self.drawQ(curr_x=int(obs[u'XPos']), curr_y=int(obs[u'ZPos']))

        # select the next action
        rnd = random.random()
        if rnd < self.epsilon:
            a = random.randint(0, len(self.actions) - 1)
            self.logger.info("Random action: %s" % self.actions[a])
        else:
            m = max(self.q_table[current_s])
            self.logger.debug("Current values: %s" % ",".join(str(x) for x in self.q_table[current_s]))
            l = list()
            for x in range(0, len(self.actions)):
                if self.q_table[current_s][x] == m:
                    l.append(x)
            y = random.randint(0, len(l) - 1)
            a = l[y]
            self.logger.info("Taking q action: %s" % self.actions[a])

        # try to send the selected action, only update prev_s if this succeeds
        try:
            agent_host.sendCommand(self.actions[a])
            self.prev_s = current_s
            self.prev_a = a

        except RuntimeError as e:
            self.logger.error("Failed to send command: %s" % e)

        return current_r

    def run(self, agent_host):
        """run the agent on the world"""

        total_reward = 0
        
        self.prev_s = None
        self.prev_a = None
        
        is_first_action = True
        
        # main loop:
        world_state = agent_host.getWorldState()
        while world_state.is_mission_running:

            current_r = 0
            
            if is_first_action:
                # wait until have received a valid observation
                while True:
                    time.sleep(0.1)
                    world_state = agent_host.getWorldState()
                    for error in world_state.errors:
                        self.logger.error("Error: %s" % error.text)
                    for reward in world_state.rewards:
                        current_r += reward.getValue()
                    if world_state.is_mission_running and len(world_state.observations) > 0 and not world_state.observations[-1].text == "{}":
                        total_reward += self.act(world_state, agent_host, current_r)
                        break
                    if not world_state.is_mission_running:
                        break
                is_first_action = False
            else:
                # wait for non-zero reward
                while world_state.is_mission_running and current_r == 0:
                    time.sleep(0.1)
                    world_state = agent_host.getWorldState()
                    for error in world_state.errors:
                        self.logger.error("Error: %s" % error.text)
                    for reward in world_state.rewards:
                        current_r += reward.getValue()
                # allow time to stabilise after action
                while True:
                    time.sleep(0.1)
                    world_state = agent_host.getWorldState()
                    for error in world_state.errors:
                        self.logger.error("Error: %s" % error.text)
                    for reward in world_state.rewards:
                        current_r += reward.getValue()
                    if world_state.is_mission_running and len(world_state.observations) > 0 and not world_state.observations[-1].text == "{}":
                        total_reward += self.act(world_state, agent_host, current_r)
                        break
                    if not world_state.is_mission_running:
                        break

        # process final reward
        self.logger.debug("Final reward: %d" % current_r)
        total_reward += current_r

        # update Q values
        if self.prev_s is not None and self.prev_a is not None:
            self.updateQTableFromTerminatingState(current_r)
            
        self.drawQ()
    
        return total_reward
        
    def drawQ(self, curr_x=None, curr_y=None):
        scale = 40
        world_x = 6
        world_y = 14
        if self.canvas is None or self.root is None:
            self.root = tk.Tk()
            self.root.wm_title("Q-table")
            self.canvas = tk.Canvas(self.root, width=world_x * scale, height=world_y * scale, borderwidth=0, highlightthickness=0, bg="black")
            self.canvas.grid()
            self.root.update()
        self.canvas.delete("all")
        action_inset = 0.1
        action_radius = 0.1
        curr_radius = 0.2
        action_positions = [ (0.5, action_inset), (0.5, 1 - action_inset), (action_inset, 0.5), (1 - action_inset, 0.5) ]
        # (NSWE to match action order)
        min_value = -20
        max_value = 20
        for x in range(world_x):
            for y in range(world_y):
                s = "%d:%d" % (x, y)
                self.canvas.create_rectangle(x * scale, y * scale, (x + 1) * scale, (y + 1) * scale, outline="#fff", fill="#000")
                for action in range(4):
                    if not s in self.q_table:
                        continue
                    value = self.q_table[s][action]
                    color = 255 * (value - min_value) / (max_value - min_value)  # map value to 0-255
                    color = max(min(color, 255), 0)  # ensure within [0,255]
                    color_string = '#%02x%02x%02x' % (255 - color, color, 0)
                    self.canvas.create_oval((x + action_positions[action][0] - action_radius) * scale,
                                             (y + action_positions[action][1] - action_radius) * scale,
                                             (x + action_positions[action][0] + action_radius) * scale,
                                             (y + action_positions[action][1] + action_radius) * scale,
                                             outline=color_string, fill=color_string)
        if curr_x is not None and curr_y is not None:
            self.canvas.create_oval((curr_x + 0.5 - curr_radius) * scale,
                                     (curr_y + 0.5 - curr_radius) * scale,
                                     (curr_x + 0.5 + curr_radius) * scale,
                                     (curr_y + 0.5 + curr_radius) * scale,
                                     outline="#fff", fill="#fff")
        self.root.update()
            
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


world_items = dict(apples=[[20, 227, 20], [12, 227, 32], [5, 227, 10], [27, 227, 20], [20, 227, 25]], enemies=[[46, 227, 46]], agents=[[22, 227, 23]], obstacles=[], width = zTop, height = xTop)

generateObstacles()


# agent = TabQAgent()
# Create default Malmo objects:
agent_host = []

agent_host.append(MalmoPython.AgentHost())
try:
    agent_host[0].parse(sys.argv)
except RuntimeError as e:
    print 'ERROR:', e
    print agent_host[0].getUsage()
    exit(1)
if agent_host[0].receivedArgument("help"):
    print agent_host[0].getUsage()
    exit(0)

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
</Mission>'''

my_mission = MalmoPython.MissionSpec(mission_xml, True)
#my_mission.removeAllCommandHandlers()
#my_mission.allowAllDiscreteMovementCommands()
#my_mission.allowAllAbsoluteMovementCommands()
#my_mission.allowAllContinuousMovementCommands()
generateAgentsIds()
print "Comenzando zona pruebas"
initDispatcher(world_items, agent_host)
print "fin zona de pruebas"
time.sleep(2)
#thread = threading.Thread(target=provideInitialInfo,args = [world_items])
#thread.start()
    
my_mission_record = MalmoPython.MissionRecordSpec()

print "Comenzando mision"

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
            time.sleep(2)

print "esperando hasta que la mision comience"

# Loop until mission starts:
print "Waiting for the mission to start ",
world_state = agent_host[0].getWorldState()
while not world_state.has_mission_begun:
    sys.stdout.write(".")
    time.sleep(0.1)
    world_state = agent_host[0].getWorldState()
    for error in world_state.errors:
        print "Error:", error.text

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
    sys.stdout.write(".")
    time.sleep(0.1)
    world_state = agent_host[0].getWorldState()
    for error in world_state.errors:
        print "Error:", error.text
    if world_state.number_of_observations_since_last_state > 0:  # Have any observations come in?
        msg = world_state.observations[-1].text  # Yes, so get the text
        observations = json.loads(msg)  # and parse the JSON
        grid = observations.get(u'floor3x3', 0)  # and get the grid we asked for
        # ADD SOME CODE HERE TO SAVE YOUR AGENT

print "Mission ended"
# Mission has ended.
#thread.join()
