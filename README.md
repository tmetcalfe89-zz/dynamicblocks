# dynamicblocks
Add blocks that age when right-clicked with specified items to Minecraft.

It adds a folder in the Minecraft directory called "objects". Create a JSON file in there with any name you'd like and add entries matching the following:

```
[
  {
    "name": "<name>",                   // The name of the block to be added.
    "stages": "<x>",                    // The number of stages the block will have.
    "dropsItems": "<true/false",        // Whether or not this block drops aging items it's received.
    "handItems": [
      "<namespace>:<item>:<metadata>",
      ...
    ]                                   // A list of items required to be in the players hand to advance each stage.
  }
]
```

# Examples

Here's an example that adds a block called "ironpile" that has 9 stages and advances every time it's right-clicked with an iron ingot:

```
[
  {
    "name": "ironpile",
    "stages": "9",
    "handItems": [
      "minecraft:iron_ingot:0",
      "minecraft:iron_ingot:0",
      "minecraft:iron_ingot:0",
      "minecraft:iron_ingot:0",
      "minecraft:iron_ingot:0",
      "minecraft:iron_ingot:0",
      "minecraft:iron_ingot:0",
      "minecraft:iron_ingot:0"
    ]
  }
]
```
