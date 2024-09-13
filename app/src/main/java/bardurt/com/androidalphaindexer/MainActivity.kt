package bardurt.com.androidalphaindexer

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var alphaViews: ArrayList<View>
    private lateinit var mapIndex: MutableMap<String, Int?>
    private lateinit var scrollIndex: MutableMap<String, Int>
    private var modelList: MutableList<MyModel> = mutableListOf()
    private lateinit var modelAdapter: ModelAdapter
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prepareListView()
    }

    private fun setHeaders() {
        modelList[0].header = true

        for (i in 0 until modelList.size - 1) {
            val a = modelList[i].name.substring(0, 1)
            val b = modelList[i + 1].name.substring(0, 1)

            if (!a.equals(b, ignoreCase = true)) {
                modelList[i + 1].header = true
            }
        }

        listView.adapter = modelAdapter
    }

    private fun getIndexList(countries: MutableList<MyModel>) {
        alphaViews = ArrayList()
        mapIndex = LinkedHashMap()
        scrollIndex = LinkedHashMap()
        var position = 0

        for (i in countries.indices) {
            val item = countries[i].name
            val index = item.substring(0, 1)

            if (mapIndex[index] == null) {
                mapIndex[index] = position
                position++
            }

            scrollIndex.putIfAbsent(index, i)
        }
    }

    private fun displayIndex() {
        val indexLayout = findViewById<View>(R.id.side_index) as LinearLayout
        val indexList: List<String> = ArrayList(
            mapIndex.keys
        )

        // set the weight sum for the index container
        indexLayout.weightSum = indexList.size.toFloat()

        var textView: TextView

        for (index in indexList) {
            textView = layoutInflater.inflate(R.layout.alphabet_indicator, null) as TextView
            textView.text = index

            val llp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)
            llp.weight = 1f

            textView.layoutParams = llp

            textView.setOnClickListener { view: View ->
                val selectedTextView = view as TextView
                val alpha = selectedTextView.text.toString()
                val position = scrollIndex[alpha]!!
                listView.smoothScrollToPosition(position)
            }

            indexLayout.addView(textView)
            alphaViews.add(textView)
        }
    }

    private fun highLightAlphabetAt(alpha: ArrayList<String>) {
        try {
            // remove highlight

            for (i in alphaViews.indices) {
                (alphaViews[i] as TextView).setTextColor(
                    ContextCompat.getColor(applicationContext, R.color.black)
                )

                alphaViews[i].setBackgroundColor(
                    ContextCompat.getColor(applicationContext, R.color.white)
                )
            }

            // set highlight
            for (j in alpha.indices) {
                // highlight the indices which are currently in view

                (alphaViews[mapIndex[alpha[j]]!!] as TextView).setTextColor(
                    ContextCompat.getColor(applicationContext, R.color.colorAccent)
                )

                // highlight the background of displayed indices, to make them pop
                alphaViews[mapIndex[alpha[j]]!!].setBackgroundColor(
                    ContextCompat.getColor(applicationContext, R.color.colorHighLight)
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "Some error", e)
        }
    }

    private fun prepareListView() {
        // fill the list with models
        modelList.add(MyModel(name = "Albania"))
        modelList.add(MyModel(name = "Denmark"))
        modelList.add(MyModel(name = "Norway"))
        modelList.add(MyModel(name = "Russia"))
        modelList.add(MyModel(name = "Romania"))
        modelList.add(MyModel(name = "USA"))
        modelList.add(MyModel(name = "Peru"))
        modelList.add(MyModel(name = "Canada"))
        modelList.add(MyModel(name = "Spain"))
        modelList.add(MyModel(name = "Sweden"))
        modelList.add(MyModel(name = "Iceland"))
        modelList.add(MyModel(name = "Greenland"))
        modelList.add(MyModel(name = "China"))
        modelList.add(MyModel(name = "Japan"))
        modelList.add(MyModel(name = "Germany"))
        modelList.add(MyModel(name = "Chile"))
        modelList.add(MyModel(name = "Scotland"))
        modelList.add(MyModel(name = "Poland"))
        modelList.add(MyModel(name = "Australia"))
        modelList.add(MyModel(name = "Ukraine"))
        modelList.add(MyModel(name = "Pakistan"))
        modelList.add(MyModel(name = "India"))
        modelList.add(MyModel(name = "Argentina"))
        modelList.add(MyModel(name = "Brazil"))
        modelList.add(MyModel(name = "France"))
        modelList.add(MyModel(name = "Finland"))
        modelList.add(MyModel(name = "Serbia"))
        modelList.add(MyModel(name = "Slovenia"))

        modelAdapter = ModelAdapter(
            this@MainActivity,
            R.layout.list_item_model,
            R.id.model_name_tv,
            modelList
        )

        listView = findViewById(R.id.model_lv)

        listView.adapter = modelAdapter

        listView.onItemClickListener =
            OnItemClickListener { adapterView: AdapterView<*>?, view: View?, i: Int, l: Long -> }

        listView.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
            }

            override fun onScroll(
                view: AbsListView, firstVisibleItem: Int,
                visibleItemCount: Int, totalItemCount: Int
            ) {
                var end = firstVisibleItem + visibleItemCount

                if (end > modelList.size) {
                    end = modelList.size
                }

                // list of indices which should be highlighted
                val alphas = ArrayList<String>()

                for (i in firstVisibleItem until end) {
                    val alpha = modelList[i].name.substring(0, 1)
                    if (!alphas.contains(alpha)) {
                        alphas.add(alpha)
                    }
                }

                highLightAlphabetAt(alphas)
            }
        })

        // sort the list by alphabet
        modelList.sortWith(ModelComparator())

        setHeaders()

        // get the indices for the list
        getIndexList(modelList)

        displayIndex()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
