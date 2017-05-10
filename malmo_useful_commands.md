# To enable discrete movement, inventory  commands and observations from grid, put this inside <AgentHandlers>
<DiscreteMovementCommands/>
  * then you can send actions = ["movenorth 1", "movesouth 1", "movewest 1", "moveeast 1"]
  * All the commands available:Commands for moving and turning in discrete increments. Some examples:
    -"move 1" - move the agent one block forwards (1 = forwards, -1 = backwards).
    -"jumpmove 1" - move the agent one block up and forwards (1 = forwards, -1 = backwards).
    -"strafe 1" - move the agent one block sideways (1 = right, -1 = left).
    -"jumpstrafe 1" - move the agent one block up and sideways (1 = right, -1 = left).
    -"turn 1" - rotate the agent 90 degrees right (1 = right, -1 = left).
    -"movenorth 1" - move the agent one block north.
    -"moveeast 1" - move the agent one block east.
    -"movesouth 1" - move the agent one block south.
    -"movewest 1" - move the agent one block west.
    -"jumpnorth 1" - move the agent one block up and north.
    -"jumpeast 1" - move the agent one block up and east.
    -"jumpsouth 1" - move the agent one block up and south.
    -"jumpwest 1" - move the agent one block up and west.
    -"jump 1" - move the agent one block up.
    -"look 1" - look down by 45 degrees (-ve = up, +ve = down).
    -"attack 1" - destroy the block that has focus.
    -"use 1" - place the held block item on the block face that has focus.
    -"jumpuse" - simultaneously jump and place the held block on the block face that has focus.
<InventoryCommands/>
  *agent_host.sendCommand("hotbar." + str(hotkey) + " 1")  # press
  *agent_host.sendCommand("hotbar." + str(hotkey) + " 0")  # release
<ObservationFromGrid>
<Grid name="floor3x3">
<min x="-1" y="-1" z="-1"/>
<max x="1" y="-1" z="1"/>
</Grid>
</ObservationFromGrid>

# Load xml from file
* -- set up the mission:
mission_file = './tutorial_6.xml'
with open(mission_file, 'r') as f:
    print "Loading mission from %s" % mission_file
    mission_xml = f.read()
    my_mission = MalmoPython.MissionSpec(mission_xml, True)

# Draw an item
<DrawItem type="apple" x="-6" y="250" z="-35"/>
Items types:
iron_shovel, iron_pickaxe, iron_axe, flint_and_steel, apple, bow, arrow, coal, diamond, iron_ingot, gold_ingot, iron_sword, wooden_sword, wooden_shovel, wooden_pickaxe, wooden_axe, stone_sword, stone_shovel, stone_pickaxe, stone_axe, diamond_sword, diamond_shovel, diamond_pickaxe, diamond_axe, stick, bowl, mushroom_stew, golden_sword, golden_shovel, golden_pickaxe, golden_axe, string, feather, gunpowder, wooden_hoe, stone_hoe, iron_hoe, diamond_hoe, golden_hoe, wheat_seeds, wheat, bread, leather_helmet, leather_chestplate, leather_leggings, leather_boots, chainmail_helmet, chainmail_chestplate, chainmail_leggings, chainmail_boots, iron_helmet, iron_chestplate, iron_leggings, iron_boots, diamond_helmet, diamond_chestplate, diamond_leggings, diamond_boots, golden_helmet, golden_chestplate, golden_leggings, golden_boots, flint, porkchop, cooked_porkchop, painting, golden_apple, sign, wooden_door, bucket, bucket, water_bucket, lava_bucket, minecart, saddle, iron_door, redstone, snowball, boat, leather, milk_bucket, brick, clay_ball, reeds, paper, book, slime_ball, chest_minecart, furnace_minecart, egg, compass, fishing_rod, clock, glowstone_dust, fish, cooked_fish, dye, bone, sugar, cake, bed, repeater, cookie, filled_map, shears, melon, pumpkin_seeds, melon_seeds, beef, cooked_beef, chicken, cooked_chicken, rotten_flesh, ender_pearl, blaze_rod, ghast_tear, gold_nugget, nether_wart, potion, glass_bottle, spider_eye, fermented_spider_eye, blaze_powder, magma_cream, brewing_stand, cauldron, ender_eye, speckled_melon, spawn_egg, experience_bottle, fire_charge, writable_book, written_book, emerald, item_frame, flower_pot, carrot, potato, baked_potato, poisonous_potato, map, golden_carrot, skull, carrot_on_a_stick, nether_star, pumpkin_pie, fireworks, firework_charge, enchanted_book, comparator, netherbrick, quartz, tnt_minecart, hopper_minecart, prismarine_shard, prismarine_crystals, rabbit, cooked_rabbit, rabbit_stew, rabbit_foot, rabbit_hide, armor_stand, iron_horse_armor, golden_horse_armor, diamond_horse_armor, lead, name_tag, command_block_minecart, mutton, cooked_mutton, banner, spruce_door, birch_door, jungle_door, acacia_door, dark_oak_door, record_13, record_cat, record_blocks, record_chirp, record_far, record_mall, record_mellohi, record_stal, record_strad, record_ward, record_11, record_wait.


* Cuboids and lines types:
air, stone, grass, dirt, cobblestone, planks, sapling, bedrock, flowing_water, water, flowing_lava, lava, sand, gravel, gold_ore, iron_ore, coal_ore, log, leaves, sponge, glass, lapis_ore, lapis_block, dispenser, sandstone, noteblock, bed, golden_rail, detector_rail, sticky_piston, web, tallgrass, deadbush, piston, piston_head, wool, piston_extension, yellow_flower, red_flower, brown_mushroom, red_mushroom, gold_block, iron_block, double_stone_slab, stone_slab, brick_block, tnt, bookshelf, mossy_cobblestone, obsidian, torch, fire, mob_spawner, oak_stairs, chest, redstone_wire, diamond_ore, diamond_block, crafting_table, wheat, farmland, furnace, lit_furnace, standing_sign, wooden_door, ladder, rail, stone_stairs, wall_sign, lever, stone_pressure_plate, iron_door, wooden_pressure_plate, redstone_ore, lit_redstone_ore, unlit_redstone_torch, redstone_torch, stone_button, snow_layer, ice, snow, cactus, clay, reeds, jukebox, fence, pumpkin, netherrack, soul_sand, glowstone, portal, lit_pumpkin, cake, unpowered_repeater, powered_repeater, stained_glass, trapdoor, monster_egg, stonebrick, brown_mushroom_block, red_mushroom_block, iron_bars, glass_pane, melon_block, pumpkin_stem, melon_stem, vine, fence_gate, brick_stairs, stone_brick_stairs, mycelium, waterlily, nether_brick, nether_brick_fence, nether_brick_stairs, nether_wart, enchanting_table, brewing_stand, cauldron, end_portal, end_portal_frame, end_stone, dragon_egg, redstone_lamp, lit_redstone_lamp, double_wooden_slab, wooden_slab, cocoa, sandstone_stairs, emerald_ore, ender_chest, tripwire_hook, tripwire, emerald_block, spruce_stairs, birch_stairs, jungle_stairs, command_block, beacon, cobblestone_wall, flower_pot, carrots, potatoes, wooden_button, skull, anvil, trapped_chest, light_weighted_pressure_plate, heavy_weighted_pressure_plate, unpowered_comparator, powered_comparator, daylight_detector, redstone_block, quartz_ore, hopper, quartz_block, quartz_stairs, activator_rail, dropper, stained_hardened_clay, stained_glass_pane, leaves2, log2, acacia_stairs, dark_oak_stairs, slime, barrier, iron_trapdoor, prismarine, sea_lantern, hay_block, carpet, hardened_clay, coal_block, packed_ice, double_plant, standing_banner, wall_banner, daylight_detector_inverted, red_sandstone, red_sandstone_stairs, double_stone_slab2, stone_slab2, spruce_fence_gate, birch_fence_gate, jungle_fence_gate, dark_oak_fence_gate, acacia_fence_gate, spruce_fence, birch_fence, jungle_fence, dark_oak_fence, acacia_fence, spruce_door, birch_door, jungle_door, acacia_door, dark_oak_door. 

  <FlatWorldGenerator generatorString="3;7,220*1,5*3,2;3;,biome_1"/>
      <DrawingDecorator>
        <!-- coordinates for cuboid are inclusive -->
        <DrawCuboid x1="-2" y1="46" z1="-2" x2="7" y2="50" z2="18" type="air" />            <!-- limits of our arena -->
        <DrawCuboid x1="-2" y1="45" z1="-2" x2="7" y2="45" z2="18" type="lava" />           <!-- lava floor -->
        <DrawCuboid x1="1"  y1="45" z1="1"  x2="3" y2="45" z2="12" type="sandstone" />      <!-- floor of the arena -->
        <DrawBlock   x="4"   y="45"  z="1"  type="cobblestone" />                           <!-- the starting marker -->
        <DrawBlock   x="4"   y="45"  z="12" type="lapis_block" />                           <!-- the destination marker -->
        <DrawItem    x="4"   y="46"  z="12" type="diamond" />                               <!-- another destination marker -->
      </DrawingDecorator>
...
       <Name>Cristina</Name>
    <AgentStart>
      <Placement x="4.5" y="46.0" z="1.5" pitch="30" yaw="0"/>
    </AgentStart>

# Draw an item
Combining raw XML with the API:
Having loaded the raw XML, it’s possible to modify it using the API, as here:
# add 20% holes for interest
for x in range(1,4):
for z in range(1,13):
if random.random()<0.1:
my_mission.drawBlock( x,45,z,"lava")

# Meter bichos
# In animation_test.py you have examples for defining velocity and limits to entities

    <DrawEntity
x="xs:decimal [1]"
y="xs:decimal [1]"
z="xs:decimal [1]"
type="SpawnableTypes [1]" 
yaw="xs:decimal [0..1]"
pitch="xs:decimal [0..1]"
xVel="xs:decimal [0..1]"
yVel="xs:decimal [0..1]"
zVel="xs:decimal [0..1]"/> 

Entity types: Creeper, Skeleton, Spider, Giant, Zombie, Slime, Ghast, PigZombie, Enderman, CaveSpider, Silverfish, Blaze, LavaSlime, EnderDragon, WitherBoss, Bat, Witch, Endermite, Guardian, Pig, Sheep, Cow, Chicken, Squid, Wolf, MushroomCow, SnowMan, Ozelot, VillagerGolem, EntityHorse, Rabbit, Villager, EnderCrystal

# Set up agent recorder
<AgentSection mode="Survival">
        <Name>Watcher#''' + str(i) + '''</Name>
        <AgentStart>
          <Placement ''' + placement + '''/>
          <Inventory>
            <InventoryObject type="leather_helmet" slot="39" quantity="1"/>
          </Inventory>
        </AgentStart>
        <AgentHandlers>
          <VideoProducer>
            <Width>''' + str(width) + '''</Width>
            <Height>''' + str(height) + '''</Height>
          </VideoProducer>
        </AgentHandlers>
      </AgentSection>'''

# Set up a recording
recording = False
my_mission_record = MalmoPython.MissionRecordSpec()
recordingsDirectory = agent_host.getStringArgument("recordingDir")
if len(recordingsDirectory) > 0:
    recording = True
    try:
        os.makedirs(recordingsDirectory)
    except OSError as exception:
        if exception.errno != errno.EEXIST: # ignore error if already existed
            raise
    my_mission_record.recordRewards()
    my_mission_record.recordObservations()
    my_mission_record.recordCommands()

Iluminar: 
* lit_furnace
* lit_pumpkin
* lit_redstone_lamp

// reward_far_items_test.py
Para obtener información en tiempo real de entidades (enemigos) se necesita activar
primero en el XML que configura el servidor la opción de que se cargen las observaciones 
de este tipo con un rango determinado del espacio del mapa. Por ejemplo, de la siguiente manera
se obtendrían en la colección de observaciones dos propiedades "close_entities" y "far_entities",
que contendrán las entidades existentes en el mapa (cercanas y lejanas):

<ObservationFromNearbyEntities>
    <Range name="close_entities" xrange="2" yrange="2" zrange="2" />
    <Range name="far_entities" xrange="10" yrange="2" zrange="10" update_frequency="100"/>
</ObservationFromNearbyEntities>

from collections import namedtuple
# Esta tupla representa la estructura de la informacion que podemos obtener de las entidades
# del mapa a traves de agent_host.getWorldState()
EntityInfo = namedtuple('EntityInfo', 'x, y, z, name, quantity')
EntityInfo.__new__.__defaults__ = (0, 0, 0, "", 1)

Cuando se obtiene la información del mundo ya se puede 
#world_state = agent_host.getWorldState()
# Peek world state tiene mejor eficiencia (no vacia los buffer)
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