/**
 * ListInsertion.java
 * 
 *  This file implements insertion sort on lists
 *
 * @author Thomas VanDrunen
 * Wheaton College, CSCI 235
 * 
 * 
 */

public class ListInsertion {

    public static IList sort(IList unsorted) {
        
        IList sorted = IList.newSortList();

        while (!unsorted.isEmpty()) {

	    //for debug
	    //System.out.println("sorted [before]");
	    //sorted.printList();

	    //actual code
            int nextNumber = unsorted.removeFront();
            sorted.insertSorted(nextNumber);

	    //for debug
	    //System.out.println("sorted [after]");
	    //sorted.printList();
        }

        return sorted;
    }
}
