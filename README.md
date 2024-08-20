![Icon-Bild](resources/images/title-github.png)

![Latest Release](https://img.shields.io/github/v/release/Dev7ex/MultiWarp)
![SpigotMC Downloads](https://img.shields.io/spiget/downloads/118996?label=Downloads)
![Spiget Rating](https://img.shields.io/spiget/rating/118996?label=Rating&style=flat-square)
![Java](https://img.shields.io/badge/Java-17+-orange)
![Spigot](https://img.shields.io/badge/Spigot-1.16--1.20-red)
[![CodeFactor](https://www.codefactor.io/repository/github/dev7ex/multiwarp/badge)](https://www.codefactor.io/repository/github/dev7ex/multiwarp)
![Last Commit](https://img.shields.io/github/last-commit/Dev7ex/MultiWarp)
![GitHub](https://img.shields.io/github/license/dev7ex/multiwarp)
![Discord](https://img.shields.io/discord/834580308543668264)
![Modrinth Followers](https://img.shields.io/modrinth/followers/multiwarp)

---

# Contents

1. [Overview](#overview)
2. [Features](#features)
3. [Commands](#commands)
4. [Installation](#installation)
5. [Requirements](#requirements)
6. [Configuration](#configuration)
7. [Contributing](#contributing)
8. [License](#license)
9. [Contact](#contact)

# Overview

- MultiWarp is a versatile plugin designed for Minecraft servers to streamline warp creation and management. With MultiWarp, server administrators can easily set up and organize multiple warp points throughout their world, providing players with a convenient way to travel quickly and efficiently.

# Features

* This plugin is highly customizable.
* Create Multiple Warps: Define numerous warp locations across your server to facilitate smooth navigation.
* Manage Warps: Administrators can effortlessly add, edit, and delete warp points using simple commands.
* Player Accessibility: Allow players to access and teleport to available warps, enhancing their gameplay experience.
* Permissions Control: Customize who can create, use, and manage warps with configurable permission settings.
* User-Friendly Commands: Utilize intuitive commands to manage warps with ease, ensuring a seamless experience for both admins and players.

# Commands
```
* /warp                                                                 [multiwarp.command.warp]
* /warp create <Name>                                                   [multiwarp.command.warp.create]
* /warp delete <Warp>                                                   [multiwarp.command.warp.delete]
* /warp flag <Warp> <Property> <Value>                                  [multiwarp.command.warp.flag]
* /warp help                                                            [multiwarp.command.warp]
* /warp info <Warp>                                                     [multiwarp.command.warp.info]
* /warp list                                                            [multiwarp.command.warp.list]
* /warp lock <Warp>                                                     [multiwarp.command.warp.lock]
* /warp teleport <Warp>                                                 [multiwarp.command.warp.teleport]
```

# Installation

1. Download the latest version of `MultiWarp` from [GitHub Releases](https://github.com/Dev7ex/MultiWarp/releases).
2. Download the required version of `FacilisCommon`
   from [GitHub Releases](https://github.com/Dev7ex/FacilisCommon/releases).
3. Copy the downloaded `.jar` file into the `plugins` directory of your Spigot server.
4. Restart the server to activate the plugin.

# Requirements

- Minecraft Version: 1.16 - 1.21
- Java Version: 17 or higher
- Spigot Server

# Configuration

- After installation, a configuration file will be created in the `plugins/MultiWarp` directory. Here, you can make
  various settings.

<details>
<summary>config.yml</summary>

```yaml
#   __  __       _ _   ___          __
# |  \/  |     | | | (_) \        / /
# | \  / |_   _| | |_ _ \ \  /\  / /_ _ _ __ _ __
# | |\/| | | | | | __| | \ \/  \/ / _` | '__| '_ \
# | |  | | |_| | | |_| |  \  /\  / (_| | |  | |_) |
# |_|  |_|\__,_|_|\__|_|   \/  \/ \__,_|_|  | .__/
#                                           | |
#                                           |_|
# Copyright (c) 2024 by Dev7ex
# Version: ${project.version}
config-version: ${project.version}
# General
prefix: '§8[§eMultiWarp§8]§r'
# Settings
settings:
  # Enables or disables the monitoring system. If true, the system will be active.
  monitoring-system-enabled: true
  # Defines the format in which date and time information is displayed.
  # Format: day.month.year hour:minute:second
  time-format: dd.MM.yyyy HH:mm:ss
```

</details>

# Contributing

We welcome contributions to MultiWarp! If you'd like to contribute, please follow these guidelines:

1. Fork the repository and clone it to your local machine.
2. Create a new branch for your feature or bug fix.
3. Make your changes and ensure the code passes any existing tests.
4. Commit your changes and push them to your fork.
5. Submit a pull request, explaining the changes you've made and why they should be merged.
6. Ensure your pull request adheres to the code style and guidelines of the project.

Thank you for contributing to MultiWarp!

# License

The MultiWarp project is licensed under the GNU General Public License v3.0. See the [LICENSE](LICENSE) file for
details.

# Contact

If you have any questions or need support, you can reach out to Dev7ex via:

- Twitter: [@Dev7ex](https://twitter.com/Dev7ex)
- Discord: [Dev7ex's Discord Server](https://discord.gg/ta33bbA8eF)