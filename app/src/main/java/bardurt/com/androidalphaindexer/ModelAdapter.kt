package bardurt.com.androidalphaindexer

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SectionIndexer
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import java.util.Locale

class ModelAdapter(
    context: Context,
    @LayoutRes resource: Int,
    @IdRes textViewResourceId: Int,
    objects: List<MyModel>
) : ArrayAdapter<MyModel?>(context, resource, textViewResourceId, objects), SectionIndexer {

    private val mAlphaIndexer = HashMap<String, Int>()
    private val mSections: Array<String>

    init {
        val size = objects.size

        for (x in 0 until size) {
            val s = objects[x].name


            var ch = s.substring(0, 1)

            ch = ch.uppercase(Locale.getDefault())

            if (!mAlphaIndexer.containsKey(ch)) {
                mAlphaIndexer[ch] = x
            }
        }

        val sectionLetters: Set<String> = mAlphaIndexer.keys

        // create a list from the set to sort
        val sectionList = ArrayList(
            sectionLetters
        )

        sectionList.sort()

        mSections = arrayOf()

        sectionList.toArray(mSections)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val wrapper: ModelWrapper

        if (view == null) {
            view = (context as Activity).layoutInflater.inflate(
                R.layout.list_item_model,
                parent,
                false
            )
            wrapper = ModelWrapper(view)
            view.tag = wrapper
        } else {
            wrapper = view.tag as ModelWrapper
        }

        wrapper.populateFrom(getItem(position)!!)

        return view!!
    }

    override fun getViewTypeCount(): Int {
        if (count != 0) return count

        return 1
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getSections(): Array<String> {
        return mSections
    }

    override fun getPositionForSection(i: Int): Int {
        return mAlphaIndexer[mSections[i]]!!
    }

    override fun getSectionForPosition(i: Int): Int {
        return 0
    }
}