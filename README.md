# TogglePvP v0.2
## STILL IN DEVELOPMENT
## A Spigot Plugin to Toggle PvP per player. 

## Commands
* /togglepvp [player] [on/off] <duration>
  - Description: Toggles PvP on/off for a player, with an optional duration.
    * Example: /togglepvp Worf2340 off 30m would disable PvP for Worf2340 for 30m.
* /pvpstatus <player>
  - Description: Checks whether a player has PvP protection, and how much longer the protection will apply if applicable. If no player is specified, the sender of the command will be checked. 
 
## Features 
* Warnings at 30m, 15m, 5m, and 1m for PvP protection expiration.
* Keep PvP status when player's logout or the server restarts.
  
## Planned additions
* Add protection for lingering potions and other potion effects.
* Add permissions for each command.


