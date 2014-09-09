#OOGASalad - Public Static Final Fantasy 

The goal of our project is to create a 2-D RPG Game Maker. We aim to provide the user some basic tools to create a simple RPG within our game authoring environment, as well as play the newly created game in our game player. The games we aim to allow the user to create are most similar to Pokemon or Harvest Moon. Basic elements include moving around in a 2-D environment with a player avatar, talking to NPCs, picking up and using items, battling enemies, and leveling up. Naturally, the user can specify these aspects.

Users can create maps, characters, set NPCs, set stats for battles, and define how to move between maps. They can set particular abilities to tiles in the background (like walkable), NPCs (whether they can talk and set dialogue trees), and item (pick-up-able). This allows the user to define how the player will interact with various objects or the environment.  After editing the game to their satisfaction, the user can publish their game to a json file, meaning the game is ready for playing.

The game player allows the user to pick a game to play from a menu. Upon loading the file, the game window displays the game. The right side of the window has a variety of tabs. Here, the player can see the status of various aspects of the game. For example, the player will see informative messages in the notifications tab, check the inventory in the inventory tab, and see the stats for all the characters in their party. The tabs also will change to suit the needs of the game state. For example, in the battle state, the notification panel will have buttons appear to allow the user to choose either to fight or use an item. If the user encounters an talkable NPC, the panel will display potential responses, where the NPC will display the appropriate text. 