package bardurt.com.androidalphaindexer

class ModelComparator : Comparator<MyModel> {
    override fun compare(left: MyModel, right: MyModel): Int {
        return left.name.compareTo(right.name)
    }
}