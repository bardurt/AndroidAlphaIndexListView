package bardurt.com.androidalphaindexer;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ArrayList<View>         alphaViews;
    private Map<String, Integer>    mapIndex;
    private Map<String, Integer>    scrollIndex;
    private ArrayList<MyModel>      modelList;
    private ModelAdapter            modelAdapter;
    private ListView                listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepareListView();
    }

    private void setHeaders(){

        modelList.get(0).setHeader(true);

        for(int i = 0; i < modelList.size()-1; i++){

            String a = modelList.get(i).getName().substring(0,1);
            String b = modelList.get(i+1).getName().substring(0,1);

            if(!a.equalsIgnoreCase(b)){
                modelList.get(i+1).setHeader(true);
            }
        }

        listView.setAdapter(modelAdapter);
    }

    private void getIndexList(ArrayList<MyModel> countries)
    {
        alphaViews = new ArrayList<>();
        mapIndex = new LinkedHashMap<>();
        scrollIndex = new LinkedHashMap<>();
        int position = 0;

        for(int i=0; i<countries.size(); i++)
        {
            String item =  countries.get(i).getName();
            String index = item.substring(0,1);

            if(mapIndex.get(index) == null) {
                mapIndex.put(index, position);
                position++;
            }

            if(scrollIndex.get(index) == null){
                scrollIndex.put(index,i);
            }
        }
    }

    private void displayIndex() {
        final LinearLayout indexLayout = (LinearLayout)findViewById(R.id.side_index);
        final List<String> indexList = new ArrayList<>(mapIndex.keySet());

        // set the weight sum for the index container
        indexLayout.setWeightSum(indexList.size());

        TextView textView;

        for(String index : indexList) {

            textView = (TextView) getLayoutInflater().inflate(R.layout.alphabet_indicator,null);
            textView.setText(index);

            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0);
            llp.weight = 1;

            textView.setLayoutParams(llp);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView selectedTextView  = (TextView) view;
                    String alpha = selectedTextView.getText().toString();
                    int position = scrollIndex.get(alpha);
                    listView.smoothScrollToPosition(position);
                }
            });

            indexLayout.addView(textView);
            alphaViews.add(textView);
        }
    }

    private void highLightAlphabetAt(ArrayList<String> alpha){
        try {

            // remove highlight
            for (int i = 0; i < alphaViews.size(); i++) {
                ((TextView) alphaViews.get(i)).setTextColor(
                        ContextCompat.getColor(getApplicationContext(), R.color.black));

                alphaViews.get(i).setBackgroundColor(
                        ContextCompat.getColor(getApplicationContext(), R.color.white));
            }

            // set highlight
            for(int j = 0; j < alpha.size(); j++) {

                // highlight the indices which are currently in view
                ((TextView) alphaViews.get(mapIndex.get(alpha.get(j)))).setTextColor(
                        ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));

                // highglight the background of displayed indices, to make them pop
                alphaViews.get(mapIndex.get(alpha.get(j))).setBackgroundColor(
                        ContextCompat.getColor(getApplicationContext(), R.color.colorHighLight));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void prepareListView(){

        modelList = new ArrayList<>();

        // fill the list with models
        modelList.add(new MyModel("Albania"));
        modelList.add(new MyModel("Denmark"));
        modelList.add(new MyModel("Norway"));
        modelList.add(new MyModel("Russia"));
        modelList.add(new MyModel("Romania"));
        modelList.add(new MyModel("USA"));
        modelList.add(new MyModel("Peru"));
        modelList.add(new MyModel("Canada"));
        modelList.add(new MyModel("Spain"));
        modelList.add(new MyModel("Sweden"));
        modelList.add(new MyModel("Iceland"));
        modelList.add(new MyModel("Greenland"));
        modelList.add(new MyModel("China"));
        modelList.add(new MyModel("Japan"));
        modelList.add(new MyModel("Germany"));
        modelList.add(new MyModel("Chile"));
        modelList.add(new MyModel("Scotland"));
        modelList.add(new MyModel("Poland"));
        modelList.add(new MyModel("Australia"));
        modelList.add(new MyModel("Ukraine"));

        modelAdapter = new ModelAdapter(MainActivity.this,
                R.layout.list_item_model,
                R.id.model_name_tv,
                modelList);

        listView = (ListView) findViewById(R.id.model_lv);

        listView.setAdapter(modelAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {


                int start = firstVisibleItem;
                int end = firstVisibleItem + visibleItemCount;

                if (end > modelList.size()) {
                    end = modelList.size();
                }

                // list of indices which should be highlighted
                ArrayList<String> alphas = new ArrayList<>();

                for (int i = start; i < end; i++) {
                    String alpha = modelList.get(i).getName().substring(0, 1);
                    if (!alphas.contains(alpha)) {
                        alphas.add(alpha);
                    }
                }

                highLightAlphabetAt(alphas);

            }
        });

        // sort the list by alphabet
        Collections.sort(modelList, new ModelComparator());

        setHeaders();

        // get the indices for the list
        getIndexList(modelList);

        displayIndex();
    }
}
