package dduwcom.mobile.finalreport

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dduwcom.mobile.finalreport.databinding.ListItemBinding

class CourseAdapter (val courses : ArrayList<CourseDto>)
    : RecyclerView.Adapter<CourseAdapter.CourseViewHoler>() {
    val TAG = "CourseAdapter"

    override fun getItemCount(): Int = courses.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHoler {
        val itemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHoler(itemBinding, listener, lcListener)
    }

    override fun onBindViewHolder(holder: CourseViewHoler, position: Int) {
        holder.itemBinding.imgPhoto.setImageResource(courses[position].photo)
        holder.itemBinding.tvTitle.text = courses[position].title
        holder.itemBinding.tvProf.text = courses[position].professor
        holder.itemBinding.tvSem.text = courses[position].semester
    }


    class CourseViewHoler(val itemBinding: ListItemBinding,
        listener: OnItemClickListener?, lcListener: OnItemLongClickListener?)
        : RecyclerView.ViewHolder(itemBinding.root) {
        init {
            itemBinding.root.setOnClickListener {
                listener?.onItemClick(it, adapterPosition)
            }
            itemBinding.root.setOnLongClickListener{
                lcListener?.onItemLongClick(it, adapterPosition)
                true
            }
        }
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int)
    }

    var lcListener : OnItemLongClickListener? = null

    fun setOnItemLongClickListener(listener: OnItemLongClickListener?) {
        this.lcListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(view : View, position : Int)
    }

    var listener : OnItemClickListener? = null  // listener 를 사용하지 않을 때도 있으므로 null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }
}