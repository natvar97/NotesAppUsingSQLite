package com.indialone.notesappusingsqlite.sqlitedb

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteEntity(
    var id: Int? = null,
    var title: String? = null,
    var description: String? = null
) : Parcelable
