# Nullium
A collection of many "small" server-side features

# What is this mod about
As the text above says, this is just a collection of small server-side only features that we thought would be cool.
Everything is configurable, so you can disable any of the features you don't want.

# The Things in the mod
### Holder Man Module:
- Enderman can only place down block in the tag `#enderman_placable`. (Default is \[#enderman_holdable])
- When enderman spawn they have a chance to already be holding a block, which is determined by a loot table.
### Blacksmith Module:
- Changed how item repairing works:
  - Repair now doesn't add to `Repair Cost` data component.
  - A single repair item can repair a tool fully.
  - Repair cost is now calculated from tools material and all the enchantments it has. (This is highly configurable, through tags and the config file)
- Chain armor can now be repaired with chains.
- Most Vanilla tools, weapons and armor repair materials are now a tag. (This doesn't include things like the mace or shears)
- Netherite equipment now is repaired with Netherite scrap instead of Netherite ingots. (Can be changed with a tag see the line above)
### Mob Scaling Module:
- Adds a big salmon and cod
- 1/6 Squids will be a baby
- Glow squid now always are 30% smaller
- all of this applies to already existing mobs, meaning if you add this to a world made without nullium, the mobs will be re-scaled.
- Any `LivingEntity` can be scaled with a config file. Check default config for more info.
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
- Copper bulbs now work how they did in snapshots, meaning they have a one game tick delay
- Eyes of Ender can be used in The End to find End Cities (Will be removed in the future)
- Sugarcane can be planted on blocks in tag `#cane_support` (Default is \[#dirt, #sand])
- Sugarcane can be placed next to blocks in tag `#cane_hydration` (Default is \[ice, frosted_ice]) And next to
  liquids in the tag `#cane_hydration` (Default is \[#water])
- Wither rose can be planted on any block in tag `#wither_rose_support` (Default is \[#dirt, #sand, netherrack,
  soul_sand, soul_soil])
- Buttons and leavers (WallMountedBlock's) can be placed on top of blocks in the tag `#support/small/bottom` (
  Default is \[#walls, #fences]) And bellow the blocks in the tag `#support/small/top` (Default is \[#walls,
  #fences])

## Commands:
- `/Nullium reload`
    - This command will reload the all the module config files. This is useful because it means you don't have to
      restart the
      game. *Unlike other mods which don't do this. >:)*
