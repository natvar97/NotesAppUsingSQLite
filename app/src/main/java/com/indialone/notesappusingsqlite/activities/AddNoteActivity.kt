package com.indialone.notesappusingsqlite.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.indialone.notesappusingsqlite.viewmodels.NoteViewModel
import com.indialone.notesappusingsqlite.viewmodels.ViewModelFactory
import com.indialone.notesappusingsqlite.databinding.ActivityAddNoteBinding
import com.indialone.notesappusingsqlite.sqlitedb.NoteEntity
import com.indialone.notesappusingsqlite.utils.Constants

class AddNoteActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityAddNoteBinding
    private lateinit var notesViewModel: NoteViewModel
    private var noteDetails: NoteEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        if (intent.hasExtra(Constants.NOTE_DATA)) {
            noteDetails = intent.getParcelableExtra(Constants.NOTE_DATA)!!
        }

        notesViewModel =
            ViewModelProvider(this, ViewModelFactory(this)).get(NoteViewModel::class.java)

        noteDetails?.let { note ->
            if (note.id != 0) {
                mBinding.btnSave.text = "Update"
                mBinding.etTitle.setText(note.title)
                mBinding.etDescription.setText(note.description)
            }
        }

        mBinding.btnSave.setOnClickListener {
            var tableId = 0

            noteDetails?.let {
                tableId = it.id!!
            }

            val note = NoteEntity(
                tableId,
                mBinding.etTitle.text.toString(),
                mBinding.etDescription.text.toString()
            )

            if (tableId == 0) {
                notesViewModel.addNote(note)
            } else {
                notesViewModel.updateNote(note)
            }
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }
}