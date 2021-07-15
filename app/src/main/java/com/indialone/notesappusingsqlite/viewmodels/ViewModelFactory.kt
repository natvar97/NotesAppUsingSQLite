package com.indialone.notesappusingsqlite.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.indialone.notesappusingsqlite.repository.NoteDatabaseRepository
import com.indialone.notesappusingsqlite.sqlitedb.DatabaseHelper
import java.lang.IllegalArgumentException

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            return NoteViewModel(NoteDatabaseRepository(DatabaseHelper(context))) as T
        }
        if (modelClass.isAssignableFrom(ListItemViewModel::class.java)) {
            return ListItemViewModel() as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}