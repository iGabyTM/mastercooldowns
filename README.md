## Master Cooldowns
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/53eb57fc49234a5ca081a2388bf874f2)](https://www.codacy.com/manual/iGabyTM/MasterCooldowns?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=iGabyTM/MasterCooldowns&amp;utm_campaign=Badge_Grade)

## Download
- [SpigotMC]()
- [Mc-Market]()
- [McPlugins]()

## Commands
| Name | usage | Description |
| --- | --- | --- |
| `mcd add [player] [id] [duration]` | `mcd add GabyTM daily_reward 86400` | Add a new cooldown, if the id is already in use the cooldown will be overridden. The duration is in seconds |
| `mcd remove [player] [id]` | `mcd remove GabyTM daily_reward` | Remove a cooldown |
| `mcd check [player] [id]` | `mcd check GabyTM daily_reward` | Check the time left of a cooldown |
|  `mcd list [player]` | `mcd list GabyTM` | A list of the available cooldowns |
| `mcd help` | `mcd help` | Display the commands list |

> ```
> Note: To use the commands you need the 'mastercooldowns.access' permission.
> Aliases: cd, mcd, mcooldowns, mcooldown.
> ```
 
## Placeholders
The plugin add the following placeholders to [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/).    

| Placeholder | Usage | Output | Description |
| --- | --- | --- | --- |
| `%mcd_left_COOLDOWN%` | `%mcd_left_daily_reward%` | 86400 | Return the time left of a cooldown as an integer |
| `%mcd_left_formatted_COOLDOWN%` | `%mcd_left_formatted_daily_reward%` | 24h | Same as above but formatted using PlaceholderAPI simple date format |
 
## Statistics
[![BStats](https://bstats.org/signatures/bukkit/MasterCooldowns.svg)](https://bstats.org/plugin/bukkit/MasterCooldowns)
  
[![Discord](https://i.imgur.com/O1vSizn.png)](https://gabytm.me)