# ASPGamerule

ASPGamerule is a versatile Minecraft plugin designed for server owners using AdvancedSlimePaper. This plugin provides easy management of game rules across multiple worlds, supporting both regular and regex-based world names. It ensures that game rules are automatically applied when new worlds are created or loaded. Additionally, ASPGamerule is compatible with SSB-SlimeWorldManager, enhancing its flexibility and usability for various server setups.

[![Build and Release](https://github.com/Arknesia/ASPGamerule/actions/workflows/build-and-release.yml/badge.svg)](https://github.com/Arknesia/ASPGamerule/actions/workflows/build-and-release.yml)

Command:
```
/aspg reload
```
Config:
```yml
worlds:
  # support regex
  world-user-.*:
    keepInventory: true
  lobby:
    doDaylightCycle: false
    fallDamage: false
    keepInventory: true
    randomTickSpeed: 0
```