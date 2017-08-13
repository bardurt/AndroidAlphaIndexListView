package bardurt.com.androidalphaindexer;

import java.util.Comparator;

/**
 * Created by Bardur on 13/08/2017.
 */

public class ModelComparator implements Comparator<MyModel>
{
    public int compare(MyModel left, MyModel right) {
        return left.getName().compareTo(right.getName());
    }
}