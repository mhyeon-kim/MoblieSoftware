package dduwcom.mobile.finalreport

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log

class CourseDBHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
    val TAG = "CourseDBHelper"

    companion object {
        const val DB_NAME = "course_db"
        const val TABLE_NAME = "course_table"
        const val COL_IMAGE = "image"
        const val COL_TITLE = "title"
        const val COL_PROFESSOR = "professor"
        const val COL_SEMESETER = "semester"
        const val COL_CREDIT = "credit"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE =
            "CREATE TABLE ${TABLE_NAME} (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${COL_IMAGE} INTEGER, ${COL_TITLE} TEXT, ${COL_PROFESSOR} TEXT, ${COL_SEMESETER} TEXT, ${COL_CREDIT} INTEGER)"
        Log.d(TAG, CREATE_TABLE)

        db?.execSQL(CREATE_TABLE)
        db?.execSQL("INSERT INTO ${TABLE_NAME} VALUES (null, ${R.mipmap.class01}, '모바일소프트웨어', '최윤석','3-1', 3)")
        db?.execSQL("INSERT INTO ${TABLE_NAME} VALUES (null, ${R.mipmap.class02}, '네트워크', '임성채','3-1', 3)")
        db?.execSQL("INSERT INTO ${TABLE_NAME} VALUES (null, ${R.mipmap.class03}, '데이터베이스개론', '박창섭','2-2', 3)")
        db?.execSQL("INSERT INTO ${TABLE_NAME} VALUES (null, ${R.mipmap.class04}, '시스템프로그래밍','이완연','3-1', 3)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS ${TABLE_NAME}"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }
}