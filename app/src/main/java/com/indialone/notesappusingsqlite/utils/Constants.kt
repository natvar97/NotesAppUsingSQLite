package com.indialone.notesappusingsqlite.utils

object Constants {

    const val DATABASE_NAME = "NotesDatabase"
    const val DATABASE_VERSION = 1

    const val TABLE_NAME = "NotesTable"
    const val TABLE_TITLE = "title"
    const val TABLE_ID = "id"
    const val TABLE_DESCRIPTION = "description"

    const val CREATE_NOTE_TABLE =
        "CREATE TABLE $TABLE_NAME($TABLE_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,$TABLE_TITLE TEXT,$TABLE_DESCRIPTION TEXT)"

    const val UPGRADE_NOTE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

    const val GET_ALL_NOTES_QUERY = "SELECT * FROM $TABLE_NAME"

    const val DELETE_BY_ID = "DELETE FROM $TABLE_NAME WHERE $TABLE_ID="

    const val NOTE_DATA = "note"

}