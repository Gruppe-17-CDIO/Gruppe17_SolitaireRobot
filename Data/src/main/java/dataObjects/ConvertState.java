package dataObjects;

public class ConvertState {

    Card draw;
    Card foundation1, foundation2, foundation3, foundation4;
    Card row1;
    Card row2;
    Card row3;
    Card row4;
    Card row5;
    Card row6;
    Card row7;

    public Card getDraw() {
        return draw;
    }

    public void setDraw(Card draw) {
        this.draw = draw;
    }

    public Card getFoundation1() {
        return foundation1;
    }

    public void setFoundation1(Card foundation, int foundationColumn)
    {
        switch (foundationColumn){
            case 0:this.foundation1=foundation;
            break;
            case 1:this.foundation2=foundation;
                break;
            case 2:this.foundation3=foundation;
                break;
            case 3:this.foundation4=foundation;
                break;
        }
    }

    public Card getFoundation2() {
        return foundation2;
    }


    public Card getFoundation3() {
        return foundation3;
    }


    public Card getFoundation4() {
        return foundation4;
    }


    public void setRow(Card rowCard, int rowColumn)
    {
        switch (rowColumn){
            case 0:this.row1=rowCard;
                break;
            case 1:this.row2=rowCard;
                break;
            case 2:this.row3=rowCard;
                break;
            case 3:this.row4=rowCard;
                break;
            case 4:this.row5=rowCard;
                break;
            case 5:this.row6=rowCard;
                break;
            case 6:this.row7=rowCard;
                break;
        }
    }

    public Card getRow1() {
        return row1;
    }

    public void setRow1(Card row1) {
        this.row1 = row1;
    }

    public Card getRow2() {
        return row2;
    }

    public void setRow2(Card row2) {
        this.row2 = row2;
    }

    public Card getRow3() {
        return row3;
    }

    public void setRow3(Card row3) {
        this.row3 = row3;
    }

    public Card getRow4() {
        return row4;
    }

    public void setRow4(Card row4) {
        this.row4 = row4;
    }

    public Card getRow5() {
        return row5;
    }

    public void setRow5(Card row5) {
        this.row5 = row5;
    }

    public Card getRow6() {
        return row6;
    }

    public void setRow6(Card row6) {
        this.row6 = row6;
    }

    public Card getRow7() {
        return row7;
    }

    public void setRow7(Card row7) {
        this.row7 = row7;
    }

}
