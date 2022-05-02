# PowerPortals

A lightweight Spigot plugin for Minecraft that adds interconnected portals. This plugin is heavily inspired by the now outdated <a href="https://github.com/Wormhole-X-Treme/Wormhole-X-Treme">Wormhole-X-Treme</a> Bukkit plugin for Minecraft.

Players can build powerportals in-game and teleport between portals. Each powerportal has its own unique name, determined by the player that built it. See the <a href="https://github.com/nsdigirolamo/PowerPortals/wiki/PowerPortals-Home">wiki page on github</a> for more information.

## Goals
* Allow for custom powerportal designs.

## Commands
* `/link <portalName> [<password>]` ... Link two PowerPortals.
* `/portals [<pageNumber>|all]` ... List owned PowerPortals.
* `/pwarp <portalName>` ... Warp to a PowerPortal.
* `/ppassword <portalName> [set|delete] [<password>]` ... Set or delete a PowerPortal's password. 

## Permissions
* `powerportals.portals.byPassMax` ... Allows a player to own more than the maximum number of portals.
* `powerportals.commands.link` ... Allow use of the /link command.
* `powerportals.commands.portals` ... Allow use of the /portals command.
* `powerportals.commands.portals.all` ... Allow use of the /portals all command.
* `powerportals.commands.pwarp` ... Allow use of the /pwarp command.
* `powerportals.commands.ppassword` ... Allow use of the /ppassword command
