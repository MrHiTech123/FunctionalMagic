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

### Numbers
Numbers in Runecraft are written in binary, reversed (meaning that the least significant digit is on the left). 🝰 represents 0, and 🝯 represents 1.
For example, 🝰🝰🝯🝰🝯 translates to 00101, reversed is 10100, which is 20 in binary. That means that 🝰🝰🝯🝰🝯 represents the number 20.

Note: The parser can usually tell when a binary number ends because another token will be, but you can explicitly end a binary literal with a `.`. This is useful when it is ambiguous when a number ends, say, when two numbers are written next to one another.

Everything in Runecraft is an integer—there are no fractions or decimals.

### Sets

## Functions


## Data Types

