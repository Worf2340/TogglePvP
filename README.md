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
 
## Features 
* Protects against normal PvP, spalsh potions of harming, and lingering potions of harming. 
* Warnings at 30m, 15m, 5m, and 1m for PvP protection expiration.
* Keep PvP status when player's logout or the server restarts.
  
## Planned additions
* Add protection for potions of weakness, slowness, and poison.
* Add protection for bows.
* Add permissions for each command.
* Increased protection against losing protection data in a server crash.
* View /pvpstatus information for offline players.

