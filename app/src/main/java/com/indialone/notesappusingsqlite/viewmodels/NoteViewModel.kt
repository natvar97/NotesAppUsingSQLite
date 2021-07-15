package com.indialone.notesappusingsqlite.viewmodels

import androidx.lifecycle.*
import com.indialone.notesappusingsqlite.repository.NoteDatabaseRepository
import com.indialone.notesappusingsqlite.sqlitedb.NoteEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

class NoteViewModel(
    private val notesRepository: NoteDatabaseRepository
) : ViewModel() {


    fun addNote(note: NoteEntity) = viewModelScope.launch {
        notesRepository.addNote(note)
    }

    fun updateNote(note: NoteEntity) = viewModelScope.launch {
        notesRepository.updateNote(note)
    }

    fun deleteNote(note: NoteEntity) = viewModelScope.launch {
        notesRepository.deleteNote(note)
    }

    private var allNotes = MutableLiveData<ArrayList<NoteEntity>>()
    private var notesCount = MutableLiveData<Int>()

    // notesRepository.getAllNotes()

    init {
        fetchNotes()
        fetchNotesCount()
    }

    fun fetchNotesCount() {
        viewModelScope.launch {
            try {
                coroutineScope {
                    val count = async {
                        notesRepository.getNotesCount()
                    }
                    notesCount.postValue(count.await())
                }
            } catch (e: Exception) {
            }
        }
    }

    fun fetchNotes() {
        viewModelScope.launch {
            try {
                coroutineScope {
                    val list = async {
                        notesRepository.getAllNotes()
                    }
                    allNotes.postValue(list.await())
                }
            } catch (e: Exception) {
            }
        }
    }

    fun getAllNotes() = allNotes

    fun getNotesCount() = notesCount

}