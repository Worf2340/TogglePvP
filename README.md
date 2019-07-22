# TogglePvP 
## STILL IN DEVELOPMENT
## A Spigot Plugin to Toggle PvP per player. 

## Commands
* /togglepvp [player] [on/off] <duration>
  - Description: Toggles PvP on/off for a player, with an optional duration.
    * Example: `/togglepvp Worf2340 off 30m` would disable PvP for Worf2340 for 30m.
* /pvpstatus <player>
  - Description: Checks whether a player has PvP protection, and how much longer the protection will apply if applicable. If no player is specified, the sender of the command will be checked. 
* /pvplist 
  - Description: Lists all online PvP protected players. Names in green have permanent protection. Names in yellow have temporary protection, with the duration of the protection in parantheses. 
  
## Permissions
* `togglepvp.*`: Gives access to all /togglepvp commands.
* `togglepvp.pvpstatus`: Allows players to check their own PvP protection status.
* `togglepvp.pvpstatus.others`: Allows players to check other player's PvP protection status.
* `togglepvp.pvplist`: Allows players to use /pvplist.
* `togglepvp.togglepvp`: Necessary to allow players to use the /togglepvp command, depended on by further permission listed below.
* `togglepvp.togglepvp.on`: Allows players to turn PvP on for themselves (disabling protection).
* `togglepvp.togglepvp.off`: Allows players to turn PvP off for themselves (enabling protection).
* `togglepvp.togglepvp.others.on`: Allows players to turn PvP on for other players (disabling protection).
* `togglepvp.togglepvp.others.off`: Allows players to turn PvP off for other players (enabling protection).
* `togglepvp.togglepvp.*`:  Gives access to all /togglepvp commands. 

 
## Features 
* Protects against normal PvP, spalsh potions of harming, and lingering potions of harming, and all projectiles.
* Warnings at 30m, 15m, 5m, and 1m for PvP protection expiration.
* Keep PvP status when player's logout or the server restarts.
  
## Planned additions
* Add protection for potions of weakness, slowness, and poison.
* Increased protection against losing protection data in a server crash.
* View /pvpstatus information for offline players.
* MySQL support?

