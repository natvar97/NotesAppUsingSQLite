package com.indialone.notesappusingsqlite.adapters

import android.app.Activity
import android.content.Intent
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.indialone.notesappusingsqlite.viewmodels.ListItemViewModel
import com.indialone.notesappusingsqlite.R
import com.indialone.notesappusingsqlite.viewmodels.ViewModelFactory
import com.indialone.notesappusingsqlite.activities.AddNoteActivity
import com.indialone.notesappusingsqlite.activities.MainActivity
import com.indialone.notesappusingsqlite.databinding.ListItemLayoutBinding
import com.indialone.notesappusingsqlite.sqlitedb.NoteEntity
import com.indialone.notesappusingsqlite.utils.Constants

class NoteItemAdapter(private val activity: Activity) :
    RecyclerView.Adapter<NoteItemAdapter.NoteViewHolder>() {

    private val notesList: ArrayList<NoteEntity> = ArrayList()

    private var isEnabled: Boolean = false
    private var isSelectedAll: Boolean = false
    private var selectedItems = ArrayList<NoteEntity>()
    private lateinit var listItemViewModel: ListItemViewModel


    class NoteViewHolder(itemView: ListItemLayoutBinding) : RecyclerView.ViewHolder(itemView.root) {
        private val tvTitle = itemView.tvTitle
        private val tvDescription = itemView.tvDescription
        val ivChecked = itemView.ivChecked

        fun bind(note: NoteEntity) {
            tvTitle.text = note.title
            tvDescription.text = note.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = ListItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        listItemViewModel =
            ViewModelProvider(activity as FragmentActivity, ViewModelFactory(activity)).get(
                ListItemViewModel::class.java
            )

        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notesList[position])

//        holder.ivDelete.setOnClickListener {
//            if (activity is MainActivity) {
//                activity.deleteNote(notesList[position])
//            }
//        }


        holder.itemView.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                if (!isEnabled) {
                    val callback = object : ActionMode.Callback {
                        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                            val menuInflater = mode?.menuInflater
                            menuInflater?.inflate(R.menu.action_menu, menu)
                            return true
                        }

                        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                            isEnabled = true
                            clickItem(holder)

                            listItemViewModel.getSize()
                                .observe(activity as FragmentActivity) { size ->
                                    mode?.title = "Selected $size"
                                }

                            return true
                        }

                        override fun onActionItemClicked(
                            mode: ActionMode?,
                            item: MenuItem?
                        ): Boolean {
                            when (item?.itemId) {
                                R.id.menu_delete -> {
                                    val restoreList = ArrayList<NoteEntity>()
                                    if (activity is MainActivity) {
                                        for (note in selectedItems) {
                                            activity.deleteNote(note)
                                            restoreList.add(note)
                                        }
                                        activity.showSnackBar(restoreList)
                                        activity.fetchData()
                                    }

//                                    if (notesList.size == 0) {
//                                        if (activity is MainActivity) {
//                                            activity.showNoNotesText()
//                                        }
//                                    }
                                    mode?.finish()
                                }

                                R.id.menu_select_all -> {
                                    if (selectedItems.size == notesList.size) {
                                        isSelectedAll = false
                                        selectedItems.clear()
                                    } else {
                                        isSelectedAll = true
                                        selectedItems.clear()
                                        selectedItems.addAll(notesList)
                                    }
                                    listItemViewModel.setSize("${selectedItems.size}")
                                    notifyDataSetChanged()
                                }
                            }
                            return true
                        }

                        override fun onDestroyActionMode(mode: ActionMode?) {
                            isEnabled = false
                            isSelectedAll = false
                            selectedItems.clear()
                            notifyDataSetChanged()
                        }

                    }
                    (v?.context as AppCompatActivity).startActionMode(callback)
                } else {
                    clickItem(holder)
                }
                return true
            }
        })

        holder.itemView.setOnClickListener {
            if (isEnabled) {
                clickItem(holder)
            } else {
                val intent = Intent(holder.itemView.context, AddNoteActivity::class.java)
                intent.putExtra(Constants.NOTE_DATA, notesList[position])
                it.context.startActivity(intent)
            }
        }

        if (isSelectedAll) {
            holder.ivChecked.visibility = View.VISIBLE
        } else {
            holder.ivChecked.visibility = View.GONE
        }

    }

    private fun clickItem(holder: NoteViewHolder) {
        val selected = notesList[holder.adapterPosition]

        if (holder.ivChecked.visibility == View.GONE) {
            holder.ivChecked.visibility = View.VISIBLE
            selectedItems.add(selected)
        } else {
            holder.ivChecked.visibility = View.GONE
            selectedItems.remove(selected)
        }
        listItemViewModel.setSize("${selectedItems.size}")
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun setData(list: ArrayList<NoteEntity>) {
        notesList.clear()
        notesList.addAll(list)
        notifyDataSetChanged()
    }

    fun restoreNote(position: Int, note: NoteEntity) {
        notesList.add(position, note)
        notifyItemInserted(position)
    }

}