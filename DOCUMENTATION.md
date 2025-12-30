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

#### Create
| | |
| ------ | - |
| Symbol | 🝧 |
| Typed with key | c |
| Signature | (Object toCreate) -> Object |
| Description | Instantiates an object in the level, marks that object as "existing", returns the object.|
| Examples | `🝧🝏🜂🝯` creates a bolt of fire that deals 1 damage|

#### Yeet
| | |
| ------ | - |
| Symbol | 🜳 |
| Typed with key | y |
| Signature | (Object toYeet, Integer speed, Integer angleRadians) -> Object |
| Description | Cause an object to move at speed speed, at an angle angleRadians (where an angle of 0 radians is directly away from the player). |
| Examples | `🜳🝧🝏🜂🝯.🝰🝰🝰🝰🝯.🝰` Creates a bolt of fire that deals 1 damage, and yeets it directly away from the player at a speed of 32|

#### Diverge
| | |
| ------ | - |
| Symbol | 🜼 |
| Typed with key | d |
| Signature | (Expr expr1, Expr expr2) -> Null |
| Description | Runs expr1, then runs expr2. |
| Examples | `🜼🜳🝧🝏🜂🝯.🝯.🝯🜳🝧🝏🜑🜂🜄🝰🝯🝰🝯.🝰🝯.🝰` will create a bolt of fire that deals 1 damage, and then yeet it at speed 1 and angle 1, and then create a bolt of acid that deals 10 damage, and shoot it at speed 2 and in direction 0 |


### Objects
#### Bolt
| | |
| ------ | - |
| Symbol | 🝏 |
| Typed with key | b |
| Signature | (Substance material, Integer damage) -> Bolt |
| Description | Returns a bolt made of material, that deals damage damage on hitting a target (damage system to be implemented if this ever becomes a game). |
| Examples | `🝏🜂🝯` returns a bolt of fire that deals 1 damage, `🝏🜑🜂🜄🝰🝯🝰🝯` returns a bolt of acid that deals 10 damage. |

## Data Structures
### Sets
The set is the only data structure that exists natively in Runecraft. It is written using `⳺` and `⳻` as delimiters. Elements inside are written inside the set, one after the other. No commas are needed. Sets cannot hold duplicate elements, but elements may be of different types. Sets may also be stored in other sets.
Sets are unordered, and attempts to iterate through them (documentation coming soon!) will never have a consistent order.


#### Examples:
- `⳺🜄🜃🜂🜁⳻` is the set `{WATER, EARTH, FIRE, AIR}`.
- `⳺🜑🜄🜂🜑🜃🜃🜃🜂⳻` is literally the set `{combine(WATER, FIRE), combine(EARTH, EARTH), EARTH, WATER}`, which simplifies to `{ACID, STONE, EARTH, WATER}`.
- `⳺🝰🝰🝯.🝰🝯.🝯.🝰🝰🝯🝰🝯.🝰🝰🝰🝰🝰🝯🜂🜄⳻` is the set `{4, 2, 1, 20, FIRE, WATER}`
- `⳺⳺🜂🜄⳻⳺🜂⳻⳺🝯🝰🝰🝯⳻🝯⳻` is the set `{{FIRE, WATER}, {FIRE}, {9}, 1}`. Note that the first three elements in this set are sets, while the element that was written last is an integer.








