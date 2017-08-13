package bardurt.com.androidalphaindexer;

/**
 * Created by Bardur on 13/08/2017.
 */

public class MyModel {

    private boolean header;
    private String  name;

    public MyModel(){
        this.header = false;
        this.name   = "";
    }

    public MyModel(String name){
        this.header = false;
        this.name   = name;
    }

    public boolean hasHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
