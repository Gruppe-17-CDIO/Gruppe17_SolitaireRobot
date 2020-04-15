# Computer Vision Simulation

## StateGenerator

StateGenerator has a static method to generate a SolitaireState object. This can be used for test and simulation. 

```
public static SolitaireState getState(int id) throws Exception
```
The argument 'id' must correspond to the ending of a file in the folder 'builderFiles'. For example the argument '0' would return the result of the file `resources/builderFiles/build-a-state_0`.

## builderFiles?

These files are made for copying the layout of cards from a table, and mapping it to a SolitaireCard object. The syntax is described in the file itself, and can be seen below. 

#### To test a new case: 
- Copy the file 'build-a-state_x'.
- Rename the file (id instead of x).
- Edit the file to mirror real cards or an idea for a case.
- Create State from file at runtime by calling ```StateGenerator.getState(id)```.



This is a temporary solution until Computer Vision works. The original build-a-state is included here, in case it gets lost. Hopefully, the syntax makes sense.

## build-a-state_x template

```
|‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾|
|  Build-a-state  |
|_________________|

A Hashtag tells the compiler that the next line should be read and parsed.
Cards are comma separated, and the exact spelling is important for parsing.
Note that an empty pile is notated with a blank line.

# Stock is empty (draw pile)
true

# Cards drawn
DIAMOND 7

# Cards in foundation
HEART 1, DIAMOND 2

# Pile 1 (from left)
HEART 5

# Pile 2
FACEDOWN, DIAMOND 9

# Pile 3
FACEDOWN, FACEDOWN, CLUB 11, HEART 10

# Pile 4
FACEDOWN, FACEDOWN, FACEDOWN, SPADE 12, DIAMOND 11, CLUB 10, HEART 9

# Pile 5
FACEDOWN, FACEDOWN, FACEDOWN, FACEDOWN, CLUB 5, HEART 4

# Pile 6


# Pile 7
FACEDOWN, FACEDOWN, FACEDOWN, FACEDOWN, FACEDOWN, FACEDOWN

```
## Example of usage
See the `CV_Simulation/StateGeneratorTest.java` in the test folder of this module.

>Author: Erlend