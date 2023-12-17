//과제명: 강의 관리 앱
//분반: 02
//학번: 20210752 성명: 김미현
//제출일 2023년 6월 2일

package dduwcom.mobile.finalreport

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import dduwcom.mobile.finalreport.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    val REQ_ADD = 431
    val REQ_UPDATE = 2321

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: CourseAdapter
    lateinit var courses : ArrayList<CourseDto>
    lateinit var dao : CourseDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dao = CourseDao(this)
        courses = dao.getAllCourse()
        adapter = CourseAdapter(courses)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        val onClickListener = object : CourseAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, UpdateActivity::class.java)
                intent.putExtra("dto", courses.get(position) )
                startActivityForResult(intent, REQ_UPDATE)
            }
        }

        adapter.setOnItemClickListener(onClickListener)

        val onLongClickListener = object : CourseAdapter.OnItemLongClickListener {
            override fun onItemLongClick(view: View, position: Int) {
                AlertDialog.Builder(this@MainActivity).run {
                    setTitle("강의 삭제")
                    setMessage("${courses[position].title}을 삭제하시겠습니까?")

                    setPositiveButton("삭제", object: DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            if (dao.deleteCourse(courses.get(position).id.toInt()) > 0) {
                                refreshList(RESULT_OK)
                            }
                        }
                    })

                    setNegativeButton("취소", null)
                    show()
                }
            }
        }

        adapter.setOnItemLongClickListener(onLongClickListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.addItem -> {
                addCourse()
            }
            R.id.info -> {
                AlertDialog.Builder(this).run {
                    setTitle("모바일소프트웨어 02분반")
                    setIcon(R.drawable.profile)
                    setMessage("20210752 김미현")
                    setPositiveButton("확인", null)
                    show()
                }
            }
            R.id.exit -> {
                AlertDialog.Builder(this).run {
                    setTitle("앱 종료")
                    setMessage("앱을 종료하시겠습니까?")

                    setPositiveButton("종료", object: DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            finish()
                        }
                    })

                    setNegativeButton("취소", null)
                    show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun addCourse() {
        val intent = Intent(this, AddActivity::class.java)
        startActivityForResult(intent, REQ_ADD)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            REQ_ADD -> {
                refreshList(resultCode)
            }
            REQ_UPDATE -> {
                refreshList(resultCode)
            }
        }
    }

    private fun refreshList(resultCode: Int) {
        if (resultCode == RESULT_OK) {
            courses.clear()
            courses.addAll(dao.getAllCourse())
            adapter.notifyDataSetChanged()
        }
        else {
            Toast.makeText(this@MainActivity, "취소됨", Toast.LENGTH_SHORT).show()
        }
    }
}