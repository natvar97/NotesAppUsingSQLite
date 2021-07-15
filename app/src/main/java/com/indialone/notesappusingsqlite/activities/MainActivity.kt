package com.indialone.notesappusingsqlite.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.indialone.notesappusingsqlite.adapters.NoteItemAdapter
import com.indialone.notesappusingsqlite.viewmodels.NoteViewModel
import com.indialone.notesappusingsqlite.R
import com.indialone.notesappusingsqlite.viewmodels.ViewModelFactory
import com.indialone.notesappusingsqlite.databinding.ActivityMainBinding
import com.indialone.notesappusingsqlite.sqlitedb.NoteEntity

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteItemAdapter: NoteItemAdapter
    private var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        noteViewModel =
            ViewModelProvider(this, ViewModelFactory(this)).get(NoteViewModel::class.java)

        noteItemAdapter = NoteItemAdapter(this)

        noteViewModel.getAllNotes().observe(this) { list ->
//            if (list.size == 0) {
//                mBinding.tvNoNotes.visibility = View.VISIBLE
//                mBinding.recyclerView.visibility = View.GONE
//            } else {
//                mBinding.tvNoNotes.visibility = View.GONE
                mBinding.recyclerView.visibility = View.VISIBLE
                noteItemAdapter.setData(list)
                noteItemAdapter.notifyDataSetChanged()
                count = list.size
                Toast.makeText(this, "Total Notes: $count", Toast.LENGTH_SHORT).show()
//            }
        }

//        noteViewModel.getNotesCount().observe(this) {
//            count = it
//
//        }

        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mBinding.recyclerView.adapter = noteItemAdapter

    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(this, "Total Notes: $count", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "Total Notes: $count", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = MenuInflater(this)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_note -> {
                val intent = Intent(this, AddNoteActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    fun deleteNote(note: NoteEntity) {
        noteViewModel.deleteNote(note)
        noteViewModel.fetchNotes()
        noteViewModel.fetchNotesCount()

    }

    fun showSnackBar(notes: ArrayList<NoteEntity>) {
        val snackBar =
            Snackbar.make(mBinding.layout, "Selected notes removed !!", Snackbar.LENGTH_LONG)
        snackBar.setAction("UNDO") {
            notes.forEachIndexed { position, note ->
                noteViewModel.addNote(note)
                noteItemAdapter.restoreNote(position, note)
                mBinding.recyclerView.scrollToPosition(position)
            }
        }
        snackBar.setActionTextColor(Color.YELLOW)
        snackBar.show()
    }

    fun fetchData() {
        noteViewModel.fetchNotes()
        noteViewModel.fetchNotesCount()
        Toast.makeText(this, "Total Notes: $count", Toast.LENGTH_SHORT).show()
    }

//    fun showNoNotesText() {
//        mBinding.tvNoNotes.visibility = View.VISIBLE
//    }

}