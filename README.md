# 🏠 HomeGui

<p align="center"> 
  <img src="https://img.shields.io/badge/Version-1.0.2-blue?style=for-the-badge">
  <img src="https://img.shields.io/badge/License-MIT-green?style=for-the-badge">
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Author-Gregwll-blue?style=flat-square">
  <img src="https://img.shields.io/badge/Written%20In-Java-orange?style=flat-square">
  <img src="https://img.shields.io/badge/lines%20of%20code-1139-blue">
</p>

# 🏠 HomeGui

**A lightweight, feature-rich home plugin for Minecraft SMP servers.**
Set, manage and teleport to your homes through a clean GUI or simple commands.

---

## ✨ Features

### 🏡 Home System
Set named homes and teleport to them instantly via command or through an interactive GUI. A maximum number of homes per player is configurable.

### 🖥️ Interactive GUI
Open your homes list with `/home` — navigate through pages, and click a home to teleport. Each home displays its coordinates and world.

### ⚠️ Safe Teleport System
The plugin checks whether a home destination is safe before teleporting. If unsafe teleport is enabled in the config, the player must confirm the teleportation with `/confirmhome`. Pending confirmations expire after a configurable delay.

### 🛡️ Admin Commands
Operators and players with the `hg.admin` permission can teleport to or delete any player's home using `player:home` syntax with full tab-completion.

### ⚙️ Full Config Support
Customize the prefix, maximum homes per player, teleportation cooldown, teleportation cancelation, unsafe teleport behavior, and confirmation expiry delay — all from `config.yml`. Reload without restarting with `/homereload`.

---

## 📋 Commands

| Command | Description |
|---|---|
| `/home` | Open the homes GUI |
| `/home <name>` | Teleport to a specific home |
| `/sethome <name>` | Set a home at your current location |
| `/delhome <name>` | Delete one of your homes |
| `/confirmhome` | Confirm teleportation to an unsafe home |
| `/homeadmin <player>:<home>` | Teleport to any player's home |
| `/delhomeadmin <player>:<home>` | Delete any player's home |
| `/homereload` | Reload the config |
| `/back` | Return back to the last position |

---

## 🔐 Permissions

| Permission | Description |
|---|---|
| `hg.admin` | Access to `/homeadmin`, `/delhomeadmin` and `/homereload` |

---

## ⚙️ Config

```yaml
# HomeGui Configuration

# Maximum number of homes per player (default: 3)
max-homes: 3

# Plugin prefix
prefix: "§x§0§0§8§D§F§F§l§nH§x§2§1§7§6§F§F§l§no§x§4§3§5§E§F§F§l§nm§x§6§4§4§7§F§F§l§ne§x§8§5§2§F§F§F§l§nG§x§A§7§1§8§F§F§l§nU§x§C§8§0§0§F§F§l§nI "

# Allow unsafe teleportation (teleporting to dangerous locations like inside blocks)
# If false, the plugin will check if the destination is safe before teleporting
allow-unsafe-teleport: false

# Pending teleport expiry delay in seconds (default: 60) // ONLY IF "allow-unsafe-teleport" IS SET ON FALSE.
confirm-expiry: 60

# Cooldown between teleportations in seconds (0 = disabled)
teleport-cooldown: 5

# Warmup delay before teleporting in seconds (0 = disabled)
# The player must stand still during this delay
teleport-warmup: 3

# Cancel the warmup if the player moves
cancel-on-move: true

# Cancel the warmup if the player takes damage
cancel-on-damage: true

# Enable /back command (returns to position before last home teleport)
enable-back: true
```

---

## 📦 Installation
Drop the `.jar` into your `plugins/` folder and restart your server. No dependencies required.

---

## 🔧 Compatibility
- ✅ Paper / Spigot
- ✅ Minecraft 1.21.x // 26.x.x

---

*Made with ❤️ by Gregwll*
