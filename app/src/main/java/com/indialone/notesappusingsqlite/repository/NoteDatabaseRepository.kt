package com.indialone.notesappusingsqlite.repository

import com.indialone.notesappusingsqlite.sqlitedb.DatabaseHelper
import com.indialone.notesappusingsqlite.sqlitedb.NoteEntity

class NoteDatabaseRepository(
    private val databaseHelper: DatabaseHelper
) {

    fun addNote(note: NoteEntity) = databaseHelper.addNote(note)

    fun updateNote(note: NoteEntity) = databaseHelper.updateNote(note)

    fun deleteNote(note : NoteEntity) = databaseHelper.deleteNote(note)

//    fun getAllNotes(): LiveData<ArrayList<NoteEntity>> = databaseHelper.getAllNotes()

    fun getAllNotes() : ArrayList<NoteEntity> = databaseHelper.getAllNotes()

    fun getNotesCount() = databaseHelper.getNotesCount()

}