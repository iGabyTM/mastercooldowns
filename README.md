## Master Cooldowns
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/53eb57fc49234a5ca081a2388bf874f2)](https://www.codacy.com/manual/iGabyTM/MasterCooldowns?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=iGabyTM/MasterCooldowns&amp;utm_campaign=Badge_Grade)

## Download
- [SpigotMC](https://www.spigotmc.org/resources/72145/)
- [Mc-Market](https://www.mc-market.org/resources/12592/)
- [McPlugins](https://mcplugins.io/resources/46/)

## Commands
| Name | Usage | Description |
| --- | --- | --- |
| `mcd add [player] [id] [duration]` | `mcd add GabyTM daily_reward 86400` | Add a new cooldown, if the id is already in use the cooldown will be overridden. The duration is in seconds |
| `mcd remove [player] [id]` | `mcd remove GabyTM daily_reward` | Remove a cooldown |
| `mcd check [player] [id]` | `mcd check GabyTM daily_reward` | Check the time left of a cooldown |
| `mcd list [player]` | `mcd list GabyTM` | A list of the available cooldowns |
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
 
## Examples
<details>
  <summary>DeluxeMenus Rewards GUI</summary>
  
  ```yaml
menu_title: '&rDaily Rewards'
inventory_type: HOPPER
open_command: rewards
update_interval: 1
items:
  glass:
    material: STAINED_GLASS_PANE
    data: 7
    slots:
      - 0
      - 1
      - 3
      - 4
    display_name: ' '
  available:
    material: 'STORAGE_MINECART'
    slot: 2
    priority: 1
    view_requirement:
      requirements:
        cooldown:
          type: '=='
          input: '%mcd_left_daily_reward%'
          output: '0'
    display_name: '&aDaily Reward'
    lore:
      - ''
      - '&aRight Click &7to claim!'
    right_click_commands:
      - '[console] mcd add %player_name% daily_reward 86400'
      - '[console] eco give %player_name% 10000'
      - '[close]'
      - '[message] &2&l[!] &aYou have claimed your daily reward, come back tomorrow for more!'
  on_cooldown:
    material: 'MINECART'
    slot: 2
    priority: 2
    update: true
    display_name: '&cDaily Reward'
    lore:
      - ''
      - '&7Available on &c%mcd_left_formatted_daily_reward%'
```
</details>
 
## Statistics
[![BStats](https://bstats.org/signatures/bukkit/MasterCooldowns.svg)](https://bstats.org/plugin/bukkit/MasterCooldowns)
  
[![Discord](https://i.imgur.com/O1vSizn.png)](https://gabytm.me)