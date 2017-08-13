package bardurt.com.androidalphaindexer;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by Bardur on 14/05/2017.
 *
 * Model adapter for Android ListView
 */
public class ModelAdapter extends ArrayAdapter<MyModel> implements SectionIndexer {

    private final HashMap<String, Integer>    mAlphaIndexer;
    private final String[]                    mSections;

    public ModelAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<MyModel> objects) {
        super(context, resource, textViewResourceId, objects);

        mAlphaIndexer = new HashMap<>();
        int size = objects.size();

        for (int x = 0; x < size; x++) {
            String s = objects.get(x).getName();


            String ch = s.substring(0, 1);

            ch = ch.toUpperCase();

            if (!mAlphaIndexer.containsKey(ch)) {
                mAlphaIndexer.put(ch, x);
            }

        }

        Set<String> sectionLetters = mAlphaIndexer.keySet();

        // create a list from the set to sort
        ArrayList<String> sectionList = new ArrayList<>(
                sectionLetters);

        Collections.sort(sectionList);

        mSections = new String[sectionList.size()];

        sectionList.toArray(mSections);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ModelWrapper wrapper;

        if(convertView==null){
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.list_item_model, parent,false);
            wrapper =  new ModelWrapper(convertView);
            convertView.setTag(wrapper);
        }else {

            wrapper = (ModelWrapper) convertView.getTag();
        }

        wrapper.populateFrom(getItem(position));

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        if (getCount() != 0)
            return getCount();

        return 1;
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public Object[] getSections() {
        return mSections;
    }

    @Override
    public int getPositionForSection(int i) {
        return mAlphaIndexer.get(mSections[i]);
    }

    @Override
    public int getSectionForPosition(int i) {
        return 0;
    }
}