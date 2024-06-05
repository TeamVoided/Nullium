# Nullium

A collection of many small server-side features

# What is this mod about

As the text above says, this is just a collection of small server-side only features that we thought would be cool.
As of this current moment, the mod only contains one feature. There are like 10 more that we have planned, so look out
for the future updates!

# The Things in the mod

- Sugarcane tags
    - Block:
        - `cane_support` (Defines the block that sugarcane can be planted on. Default is \[#dirt, #sand, soul_sand,
          soul_soil])
        - `cane_hydration` (Defines the block that sugarcane can be placed next to. Default is \[ice, frosted_ice])
    - Fluid:
        - `cane_hydration`  (Defines the fluid that sugarcane can be placed next to. Default is \[#water])
- Big Salmon module.
  - This module adds a big salmon. But more specifically, it adds the ability to control mob scale
    with a config
    file. The config format requires a `LivingEntity` mob id (You can fund this by trying to /summon a mob), a min and max
    scale values. This will randomly pick between the min and max
    scale values when spawn the entity. The mod will also rescale existing mobs if added to a new world.

    To disable this feature, just remove all entries from the config file.
- `/Nullium reload`
  - This command will reload the all the module config files. This is useful because it means you don't have to restart the
    game. *Unlike other mods which don't do this. >:)*
    