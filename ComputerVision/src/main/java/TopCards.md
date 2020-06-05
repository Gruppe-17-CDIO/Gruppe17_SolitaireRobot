# Top Cards

##Usage

TopCards is a data object for the top 'fully visible' cards that computer vision registers. The data structure is 
returned to Control and handled there.

Set the drawn card with:

```setDrawnCard(Card)```

Set foundations (length 4) with:

```setFoundations(List<Card>)```

Set piles (columns, length 7) with:

 ```setPiles(List<Card>)```

The two last methods throw an exception if there is the wrong number of cards in the list.

Cards can be null during the game, because there may be empty piles / foundations.

Not to be confused with the final state object, that has lists of cards, this object has only one card per visible pile.

> Blame: Erlend & Rasmus