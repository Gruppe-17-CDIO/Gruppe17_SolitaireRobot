# Data Objects

## SolitaireState
This object is used to store the whole deck of cards as displayed on the table. All the following fields must be set:

| Field | Type | Use |
|---|---|---|
| stockEmpty | boolean | To check if there are cards left to draw.
| drawnCards | List of Cards. Max size: 3. | The 0-3 cards currently drawn. |
| foundations | List of Cards. Max size: 3.| Goal for the sorted cards. Values can be null|
| piles | List of Lists of Cards. Size: 7. | The 7 columns. Values can be empty lists.|

There are getters and setters for all fields. Some basic tests for validity are built-in. For example if null values are OK, and the size ranges are enforced.

The `toString()` method returns the ID of the SolitaireState. 

To get a printable State in readable format, call `getPrintFormat()`.

## Card
This object represents a card in the game. The constructor is overloaded, so to versions exist: One for visible cards, and one for cards that are lying face down. 

### Constructors

Visible cards are created like this:
```
Card myCard = new Card(Card.Suit.SPADE, 12);
```
The suits are stored in a static enum in the card class. Legal ranks are 1 through 13.

Cards lying face down are created like this:
```
Card someCard = new Card(Card.Status.FACEDOWN)
```
Status is a static enum in the Card class, just like suit.  The default value is Status.FACEUP, which is why it must be specified in the constructor.

### Status
Before you ask for a cards suit or rank, it's a good idea to check if Status is set to FACEUP.
```
if (someCard.getStatus() == Card.Status.FACEUP{
    // Use card
} 
```
### Suit vs. Color
Cards have suits. Suits come in two colors, red and black. To get the color of a card, call ```getColor()```.

## Move
Do it!

>Author:  Erlend