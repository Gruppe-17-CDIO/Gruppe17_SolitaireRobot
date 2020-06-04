# Top Cards

##Usage

TopCards are the top 'fully visible' cards that computer vision registers, and returns to logic.

Set the drawn card with:
```setDrawnCard(Card)```

Set foundations (max 4) with:
```setFoundations(List<Card>)```

Set piles (columns, max 7) with:
 ```setPiles(List<Card>)```

The two last methods throw an exception if there are too many cards in the list.

Null values are OK, because there may be empty piles / foundations.