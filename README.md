# PowerPortals

A Spigot plugin for Minecraft that adds interconnected portals. This plugin is still under development and should not be depended on for stable use on a    public server. This plugin is heavily inspired by the now outdated <a href="https://github.com/Wormhole-X-Treme/Wormhole-X-Treme">Wormhole-X-Treme</a> Bukkit plugin for Minecraft.

Players can build powerportals in-game and teleport between portals. Each powerportal has its own unique name, determined by the player that built it. Players are limited to owning a maximum of 20 powerportals at any given time, but this maxmimum can be increased via the plugin's config file.

<img src="https://i.imgur.com/8XaQ3xG.png" alt="an example of a powerportal" width=40%/>

Above is an example of a powerportal. The player builds this structure and the name of the portal will be automatically set as the first line of the sign. The powerportal will be registered once the player uses the lever. After the powerportal is registered, a player can use the lever again and link their portal to any other portal using `/link <portalName> [<password>]`. After two portals are linked the player can teleport between them.

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
