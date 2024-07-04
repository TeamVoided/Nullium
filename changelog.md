# WARNING the config was re-written so the old config files will not work

### New config system:
- Now the modules can be enabled/disabled in the main config
- Old config files will not work (this should be the last update where that is the case)
### Holder Man Module:
- Moved `#enderman_placable` tag to this module
- When enderman spawn they have a chance to already be holding a block (from a loot table)
### Blacksmith Module:
- Changed how item repairing works:
  - Repair now doesn't add to `Repair Cost` data component.
  - A single repair item can repair a tool fully.
  - Repair cost is now calculated from tools material and all the enchantments it has. (This is highly configurable, through tags and the config file)
- Chain armor can now be repaired with chains.
- Most Vanilla tools, weapons and armor repair materials are now a tag. (This doesn't include things like the mace or shears)
- Netherite equipment now is repaired with Netherite scrap instead of Netherite ingots. (Can be changed with a tag see the line above)
### Big Salmon / Mob Scaling Module:
- Renamed to Mob Scaling
- Rewrote the config (There are now more scaling options)
- 1/6 Squids will be a baby
- Glow squid now always are 30% smaller
### Compostable Module:
- Golden apples and carrots can now be composted, they will always adda layer to the composter
- Composable items now can be added through the config file, this also allows you to override vanilla chances
- Existing compostable items can be removed via the config
### Miscellaneous features
- Stackable potions, non throwable potions stack to 16
- Glow Berries now give you the glowing status effect
- Cakes can be picked up if they have no bites takes from them
- Upgrade Smiting Templates now can be gotten from piglin bartering (The chance is about 1 per 3.3 stacks of gold
  ingots)
- Copper bulbs now work how they did in snapshots