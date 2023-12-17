package dduwcom.mobile.finalreport

class CourseDto (val id: Long, val photo: Int, var title : String, var professor: String, val semester : String, val credit : Int) : java.io.Serializable {
    override fun toString() = "$title $professor"
}