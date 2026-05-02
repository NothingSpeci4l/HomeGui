# 🏠 HomeGui

**A lightweight, feature-rich home plugin for Minecraft SMP servers.**
Set, manage and teleport to your homes through a clean GUI or simple commands.

https://modrinth.com/plugin/homes-gui

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
Customize the prefix, maximum homes per player, unsafe teleport behavior, and confirmation expiry delay — all from `config.yml`. Reload without restarting with `/homereload`.

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

---

## 🔐 Permissions

| Permission | Description |
|---|---|
| `hg.admin` | Access to `/homeadmin`, `/delhomeadmin` and `/homereload` |

---

## ⚙️ Config

```yaml
# Maximum number of homes per player
max-homes: 3

# Plugin prefix
prefix: "HomeGui "

# Allow unsafe teleportation
# If true, players will be asked to confirm with /confirmhome
allow-unsafe-teleport: false

# Confirmation expiry delay in seconds
confirm-expiry: 60
```

---

## 📦 Installation
Drop the `.jar` into your `plugins/` folder and restart your server. No dependencies required.

---

## 🔧 Compatibility
- ✅ Paper / Spigot
- ✅ Minecraft 1.21.x

---

*Made with ❤️ by Gregwll*
