package org.wit.careapp4carer.models

interface NotificationsStore {
    fun displayNote(noteId: Long)
    fun markAsDone(noteId: Long)
    fun displayLater(noteId: Long)
}