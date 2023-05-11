package modelPackage;

import java.util.ArrayList;
import java.util.ListIterator;

public class TagList extends ArrayList<String> {
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        ListIterator<String> itemIterator = super.listIterator();
        while(itemIterator.hasNext()) {
            builder.append(itemIterator.next() + ", ");
        }
        
        return builder.substring(0, builder.length() - 2);
    }
}
