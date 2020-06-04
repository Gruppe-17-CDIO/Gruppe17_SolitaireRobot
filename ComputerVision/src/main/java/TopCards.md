# Top Cards

##Usage

TopCards is a data object for the top 'fully visible' cards that computer vision registers. The data strucure is returned to Control and handled there.

Set the drawn card with:

```setDrawnCard(Card)```

Set foundations (max 4) with:

```setFoundations(List<Card>)```

Set piles (columns, max 7) with:

 ```setPiles(List<Card>)```

The two last methods throw an exception if there are too many cards in the list.

Null values are OK during the game, because there may be empty piles / foundations.

Not to be confused with the final state object, that has lists of cards, this object has only one card per visible pile.

> Blame: Erlend & Rasmus