package bardurt.com.androidalphaindexer;

import android.view.View;
import android.widget.TextView;

/*
 * Created by Bardur on 14/05/2017.
 *
 * Wrapper to wrap MyModels in Android ListView
 */

public class ModelWrapper {
    private TextView        name            = null;
    private TextView        header          = null;
    private View            row             = null;

    ModelWrapper(View row){
        this.row = row;}


    private TextView getNameView(){
        if(name == null){
            name = row.findViewById(R.id.model_name_tv);
        }

        return name;
    }

    private TextView getHeader(){
        if(header == null){
            header = row.findViewById(R.id.model_header_tv);
        }

        return header;
    }

    void populateFrom(MyModel model){


        getNameView().setText(model.getName());

        // display a index header
        if(model.hasHeader()){
            getHeader().setVisibility(View.VISIBLE);
            char first = model.getName().charAt(0);
            String alpha = ""+first;
            getHeader().setText(alpha);
        }else {
            getHeader().setVisibility(View.GONE);
        }
    }
}