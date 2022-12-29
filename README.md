## Master Cooldowns

## Download
- [Mc-Market](https://www.mc-market.org/resources/12592/)
- [SpigotMC](https://www.spigotmc.org/resources/72145/)

## Commands
| Name | Usage | Description |
| --- | --- | --- |
| `mcd add [player] [id] [duration]` | `mcd add GabyTM daily_reward 86400` | Add a new cooldown, if the id is already in use the cooldown will be overridden. The duration is in seconds |
| `mcd remove [player┃*┃**] [id┃all]` | `mcd remove GabyTM daily_reward` | Use `*` as player argument to remove cooldowns from all online players, `**` for offline players and `all` as id to remove all cooldowns |
| `mcd check [player] [id]` | `mcd check GabyTM daily_reward` | Check the time left of a cooldown |
| `mcd list [player]` | `mcd list GabyTM` | A list of the available cooldowns |
| `mcd reload` | `mcd reload` | Reload the config |
| `mcd help` | `mcd help` | Display the commands list |

> ```
> Note: To use the commands you need the 'mastercooldowns.access' permission.
> Aliases: cd, mcd, mcooldowns, mcooldown.
> ```
 
## Placeholders
The plugin add the following placeholders to [PlaceholderAPI](https://www.spigotmc.org/resources/6245/).    

| Placeholder                             | Usage                                        | Output | Description                                                         |
|-----------------------------------------|----------------------------------------------|--------|---------------------------------------------------------------------|
| `%mcd_left_<cooldown>%`                 | `%mcd_left_daily_reward%`                    | 86400  | Return the time left of a cooldown as an integer                    |
| `%mcd_left_formatted_<cooldown>%`       | `%mcd_left_formatted_daily_reward%`          | 24h    | Same as above but formatted using PlaceholderAPI simple date format |
| `%mcd_active_<space separated list>%`   | `%mcd_active_daily_reward monthly_reward%`   | 2      | Return how many of the provided cooldowns are active                |
| `%mcd_inactive_<space separated list>%` | `%mcd_inactive_daily_reward monthly_reward%` | 2      | Return how many of the provided cooldowns are inactive              |
| `%mcd_isactive_<cooldown>%`             | `%mcd_isactive_daily_reward%`                | Yes    | Whether a cooldown is active                                        |
| `%mcd_isinactive_<cooldown>%`           | `%mcd_isinactive_daily_reward%`              | No     | Whether a cooldown is inactive                                      |

## Config
The default config can be found [here](iGabyTM/MasterCooldowns/tree/master/src/main/resources/config.yml).

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
      material: STORAGE_MINECART
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
      click_commands:
        - '[console] mcd add %player_name% daily_reward 86400'
        - '[console] eco give %player_name% 10000'
        - '[close]'
        - '[message] &2&l[!] &aYou have claimed your daily reward, come back tomorrow for more!'
    on_cooldown:
      material: MINECART
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