package dduwcom.mobile.finalreport

import android.content.ContentValues
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dduwcom.mobile.finalreport.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    val TAG = "AddActivity"
    lateinit var addBinding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(addBinding.root)

        val dao = CourseDao(this)

        addBinding.btnAdd.setOnClickListener {
            val photo = R.mipmap.class05
            val title = addBinding.etNewTitle.text.toString()
            val professor = addBinding.etNewProf.text.toString()
            val semester = addBinding.etNewSem.text.toString()
            val credit = addBinding.etNewCre.text.toString().toInt()
            val newDto = CourseDto(5, photo, title, professor, semester, credit)

            if (dao.addCourse(newDto) > 0) {
                setResult(RESULT_OK)
            }
            else {
                setResult(RESULT_CANCELED)
            }

            finish()
        }

        addBinding.btnAddCancel.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}