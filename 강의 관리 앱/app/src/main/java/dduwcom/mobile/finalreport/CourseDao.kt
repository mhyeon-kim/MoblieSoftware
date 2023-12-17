package dduwcom.mobile.finalreport

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.provider.BaseColumns
import androidx.core.app.ActivityCompat.startActivityForResult

class CourseDao (val context : Context){
    val REQ_ADD = 431
    val REQ_UPDATE = 2321

    @SuppressLint("Range")
    fun getAllCourse() : ArrayList<CourseDto> {
        val helper = CourseDBHelper(context)
        val db = helper.readableDatabase

        val cursor = db.query(CourseDBHelper.TABLE_NAME, null, null, null, null, null, null, null)
        val courses = arrayListOf<CourseDto>()

        with(cursor) {
            while(moveToNext()) {
                val id = getLong(getColumnIndex(BaseColumns._ID))
                val photo = getInt(getColumnIndex(CourseDBHelper.COL_IMAGE))
                val title = getString(getColumnIndex(CourseDBHelper.COL_TITLE))
                val professor = getString(getColumnIndex(CourseDBHelper.COL_PROFESSOR))
                val semester = getString(getColumnIndex(CourseDBHelper.COL_SEMESETER))
                val credit = getInt(getColumnIndex(CourseDBHelper.COL_CREDIT))

                val dto = CourseDto(id, photo, title, professor, semester, credit) //photo 뺌
                courses.add(dto)
            }
        }

        cursor.close()
        helper.close()

        return courses
    }

    fun addCourse(newDto : CourseDto) : Long {
        val helper = CourseDBHelper(context)
        val db = helper.writableDatabase

        val newValues = ContentValues()

        newValues.put(CourseDBHelper.COL_IMAGE, newDto.photo)
        newValues.put(CourseDBHelper.COL_TITLE, newDto.title)
        newValues.put(CourseDBHelper.COL_PROFESSOR, newDto.professor)
        newValues.put(CourseDBHelper.COL_SEMESETER, newDto.semester)
        newValues.put(CourseDBHelper.COL_CREDIT, newDto.credit)

        val result = db.insert(CourseDBHelper.TABLE_NAME, null, newValues)

        helper.close()
        return result
    }

    fun updateCourse(dto: CourseDto): Int {
        val helper = CourseDBHelper(context)
        val db = helper.writableDatabase
        val updateValue = ContentValues()

        updateValue.put(CourseDBHelper.COL_TITLE, dto.title)
        updateValue.put(CourseDBHelper.COL_PROFESSOR, dto.professor)

        val whereCaluse = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(dto.id.toString())

        val result = db.update(CourseDBHelper.TABLE_NAME,
            updateValue, whereCaluse, whereArgs)

        helper.close()
        return result
    }

    fun deleteCourse(id : Int) : Int {
        val helper = CourseDBHelper(context)
        val db = helper.writableDatabase

        val whereClause = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(id.toString())

        val result = db.delete(CourseDBHelper.TABLE_NAME, whereClause, whereArgs)

        helper.close()
        return result
    }
}