package com.indialone.notesappusingsqlite.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListItemViewModel : ViewModel() {

    private val listSize = MutableLiveData<String>()

    fun setSize(size: String) {
        listSize.postValue(size)
    }

    fun getSize(): LiveData<String> = listSize

}