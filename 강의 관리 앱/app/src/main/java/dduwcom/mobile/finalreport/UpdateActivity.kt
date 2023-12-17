package dduwcom.mobile.finalreport

import android.content.ContentValues
import android.os.Bundle
import android.provider.BaseColumns
import androidx.appcompat.app.AppCompatActivity
import dduwcom.mobile.finalreport.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {
    lateinit var updateBinding : ActivityUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateBinding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(updateBinding.root)

        val dao = CourseDao(this)
        val dto = intent.getSerializableExtra("dto") as CourseDto

        updateBinding.imageView.setImageResource(dto.photo)
        updateBinding.etModifyTitle.setText(dto.title)
        updateBinding.etModifyProf.setText(dto.professor)
        updateBinding.showSem.text = dto.semester
        updateBinding.showCred.text = dto.credit.toString()
        updateBinding.showId.text = dto.id.toString()

        updateBinding.btnModify.setOnClickListener {
            dto.title = updateBinding.etModifyTitle.text.toString()
            dto.professor = updateBinding.etModifyProf.text.toString()

            if (dao.updateCourse(dto) > 0) {
                setResult(RESULT_OK)
            }
            else {
                RESULT_CANCELED
            }
            finish()
        }

        updateBinding.btnModifyCancel.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}