package com.indialone.notesappusingsqlite.sqlitedb

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.indialone.notesappusingsqlite.utils.Constants
import java.lang.Exception

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(Constants.CREATE_NOTE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(Constants.UPGRADE_NOTE_TABLE)
        onCreate(db)
    }

    fun addNote(note: NoteEntity) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(Constants.TABLE_TITLE, note.title)
        values.put(Constants.TABLE_DESCRIPTION, note.description)

        db.insert(Constants.TABLE_NAME, null, values)
//        db.close()
    }

    fun getAllNotes(): ArrayList<NoteEntity> {
        val db = this.writableDatabase
        val cursor: Cursor = db.rawQuery(Constants.GET_ALL_NOTES_QUERY, null)
        val list = ArrayList<NoteEntity>()
        if (cursor.moveToFirst()) {
            try {
                do {
                    val note = NoteEntity()
                    note.id = cursor.getString(0).toInt()
                    note.title = cursor.getString(1)
                    note.description = cursor.getString(2)
                    list.add(note)
                } while (cursor.moveToNext())
            } catch (e: Exception) {
            }
        }
        return list
    }

    fun updateNote(note: NoteEntity) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(Constants.TABLE_TITLE, note.title)
        values.put(Constants.TABLE_DESCRIPTION, note.description)

        db.update(
            Constants.TABLE_NAME,
            values,
            "${Constants.TABLE_ID} =?",
            arrayOf("${note.id}")
        )
    }

    fun deleteNote(note: NoteEntity) {
        val db = this.writableDatabase
        db.delete(
            Constants.TABLE_NAME, "${Constants.TABLE_ID} =?", arrayOf("${note.id}")
        )
    }

    fun getNotesCount(): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery(Constants.GET_ALL_NOTES_QUERY, null)
        return cursor.count
    }

}