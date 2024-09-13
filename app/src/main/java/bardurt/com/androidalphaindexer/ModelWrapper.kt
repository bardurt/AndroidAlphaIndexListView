package bardurt.com.androidalphaindexer

import android.view.View
import android.widget.TextView

class ModelWrapper internal constructor(row: View?) {
    private var name: TextView? = null
    private var header: TextView? = null
        get() {
            if (field == null) {
                field = row!!.findViewById(R.id.model_header_tv)
            }

            return field
        }
    private var row: View? = null

    init {
        this.row = row
    }


    private val nameView: TextView?
        get() {
            if (name == null) {
                name = row!!.findViewById(R.id.model_name_tv)
            }

            return name
        }

    fun populateFrom(model: MyModel) {
        nameView!!.text = model.name

        // display a index header
        if (model.header) {
            header!!.visibility = View.VISIBLE
            val first = model.name[0]
            val alpha = "" + first
            header!!.text = alpha
        } else {
            header!!.visibility = View.GONE
        }
    }
}