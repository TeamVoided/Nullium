# Nullium

A collection of many small server-side features

# What is this mod about

As the text above says, this is just a collection of small server-side only features that we thought would be cool.
Everything is configurable, so you can disable any of the features you don't want.

# The Things in the mod

- General:
    - Eyes of Ender can be used in The End to find End Cities
    - Sugarcane can be planted on blocks in tag `#cane_support` (Default is \[#dirt, #sand])
    - Sugarcane can be placed next to blocks in tag `#cane_hydration` (Default is \[ice, frosted_ice]) And next to
      liquids in the tag `#cane_hydration` (Default is \[#water])
    - Wither rose can be planted on any block in tag `#wither_rose_support` (Default is \[#dirt, #sand, netherrack,
      soul_sand, soul_soil])
    - Buttons and leavers (WallMountedBlock's) can be placed on top of blocks in the tag `#support/small/bottom` (
      Default is \[#walls, #fences]) And bellow the blocks in the tag `#support/small/top` (Default is \[#walls,
      #fences])
    - Enderman can only place down block in the tag `#enderman_placable` (Default is \[#enderman_holdable])
- Big Salmon module.
    - This module adds a big salmon. But more specifically, it adds the ability to control mob scale with a config file.
      The config format requires a `LivingEntity` mob id (You can fund this by trying to /summon a mob), a min and max
      scale values. This will randomly pick between the min and max scale values when spawn the entity. The mod will
      also rescale existing mobs if added to a new world.

      To disable this feature, just remove all entries from the config file.
- `/Nullium reload`
    - This command will reload the all the module config files. This is useful because it means you don't have to
      restart the
      game. *Unlike other mods which don't do this. >:)*
