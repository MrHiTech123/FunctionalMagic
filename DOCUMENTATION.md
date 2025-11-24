# Runecraft Documentation

## Basic Syntax

Runecraft is an entirely LL(1) language. This means that with a few exceptions, the parser automatically detects when one expression ends and the next begins, so parentheses and spaces are redundant.

It is recommended to type Runecraft using the [Runecraft Keyboard](https://github.com/MrHiTech123/RunecraftKeyboard), which is made (and installed) using [Keyman](https://github.com/keymanapp/keyman).


## Simple Literals
### Substances
A **substance** is a real-world material, such as wood, stone, or blood. There are six basic substances: fire, water, air, earth, mind, and flesh, which are signified by the following literals.
| Basic Substance | Literal | Key to type on keyboard |
| --------------- | ------- | ----------------------- |
| Fire | 🜂 | f |
| Water | 🜄 | w |
| Air | 🜁 | a |
| Earth | 🜃 | e |
| Mind | 🜍 | m |
| Flesh | ♀ | M |

### Integers
Integers in Runecraft are written in binary, reversed (meaning that the least significant digit is on the left). 🝰 represents 0, and 🝯 represents 1.
For example, 🝰🝰🝯🝰🝯 translates to 00101, reversed is 10100, which is 20 in binary. That means that 🝰🝰🝯🝰🝯 represents the number 20.

Note: The parser can usually tell when an integer literal ends, but you can explicitly end a binary literal with a `.`. This is useful when it is ambiguous when an integer literal ends, say, when two integer literals are written next to one another.

For example, to call the `⊢` (add) function, you need to write the arguments next to one another, like so: `⊢🝰🝯.🝰🝰🝯`. Note how the `.` between the two literals indicates where one ends and the next one begins.

Every number in Runecraft is an integer—there are no fractions or decimals.

## Functions
### Syntax
Functions in Runecraft are called by writing the function's name, followed immediately by all its arguments in order. Once again, no spaces are used.
For example, to use the `🜑` (combine) function on `🜄` and `🜂`, you would simply write `🜑🜄🜂`.

### Function descriptions:
#### Combine
| | |
| ------ | - |
| Symbol | 🜑 |
| Typed with key | C |
| Signature | (Substance baseSubstance, Substance modifierSubstance) -> Substance |
| Description | Returns a new substance, which is baseSubstance, but modified with or made more similar to modifierSubstance. |
| Examples | `🜑🜄🜂` will return steam, `🜑🜃🜃` will return stone. |








## Data Types




