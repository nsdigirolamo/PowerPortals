# Power-Portals

A Spigot plugin for Minecraft that adds interconnected portals. This plugin is still under development and should not be depended on for stable use on a    public server. This plugin is heavily inspired by the now abandoned <a href="https://github.com/WolfNetDevelopment/Wormhole-X-Treme">Wormhole-X-Treme</a> Bukkit plugin for Minecraft.

Players can build powerportals in-game and teleport between portals. Each powerportal has its own unique name, determined by the player that built it. Players are limited to owning a maximum of 20 powerportals at any given time, but this maxmimum can be increased via the plugin's config file.

<img src="https://i.imgur.com/8XaQ3xG.png" alt="an example of a powerportal" width=40%/>

Above is an example of a powerportal. The player builds this structure and the name of the portal will be automatically set as the first line of the sign. The powerportal will be registered once the player uses the lever. After the powerportal is registered, a player can use the lever again and link their portal to any other portal using `/link <portalName`. After two portals are linked the player can teleport between them.

## Short Term Goals
* Add powerportal passwords as an option for players</li>
* Add powerportal statistics so players can see the number of visitors to their portal</li>

## Long Term Goals
* Allow for custom powerportal designs
* Shorten the time it takes to load the powerportals.json file or just use a database for storing powerportals instead of a file
* Maybe allow for entities to pass through powerportals?
* Actually get the plugin to a releasable state

## Commands
* `/link <portalName>` ... Link two PowerPortals.
* `/portals <pageNumber>|all]` ... List owned PowerPortals.
* `/pwarp <portalName>` ... Warp to a PowerPortal.

## Permissions
* `powerportals.portals.byPassMax` ... Allows a player to own more than the maximum number of portals.
* `powerportals.commands.link` ... Allow use of the /link command.
* `powerportals.commands.portals` ... Allow use of the /portals command.
* `powerportals.commands.portals.all` ... Allow use of the /portals all command.
* `powerportals.commands.pwarp` ... Allow use of the /pwarp command.
