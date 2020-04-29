# Card Calculator

Calculates NEXT state based on the move.

###Input
1. Image state from Computer Vision, array
2. Current state
3. Move. 

### Output
1. Saves state
2. Returns state to controller.
3. Errors 

If no previous state or move are given, it generates a new one based on a starter state and assumes this is a new game.

Card array is a required argument, move and state is required except in new game.

Suggested classes and methods:

```
class CardTracker
Solitairestate generateState(List<Card> cardArray, State prevState, Move move)
Solitairestate initiateState(List<Card> cardArray)
```

## Flow Chart
![](../../resources/card-tracker-flow.png)

First draft for card tracker. This has changed a bit, since the computer calculates NEXT state, before the user knows it...
The order has to be worked out, probably best if the computer does not settle on a fixed state, in case the user gets a choice.
