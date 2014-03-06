// This class defines the a group of parking lots, specified
// by the start number and the number of lots in the group.
// This class is given and is not supposed to be modified or submitted.

class Lots {

    /****** Attributes *********/
    int lotStart; // start lot number in the group
    int lotSize; // how many lots are there in this group?

    /******* Constructors ********/
    public Lots() {
    }

    public Lots(int start, int size) {
        setLotStart(start);
        setLotSize(size);
    }

    /******** Accessors *******/
    public int getLotStart() {
        return lotStart;
    }

    public int getLotSize() {
        return lotSize;
    }

    /********* Mutators ********/
    public void setLotStart(int start) {
        lotStart = start;
    }

    public void setLotSize(int size) {
        lotSize = size;
    }

    public String toString() {
        return "[" + getLotStart() + ";" + getLotSize() + "]";
    }
}
