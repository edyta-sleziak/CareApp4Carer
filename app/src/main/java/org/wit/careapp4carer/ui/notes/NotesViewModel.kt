package org.wit.careapp4carer.ui.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.careapp4carer.models.NotesModel
import org.wit.careapp4carer.models.firebase.NotesFireStore

class NotesViewModel : ViewModel() {

    val notesFireStore = NotesFireStore()
    val mItemsList: LiveData<ArrayList<NotesModel>> get() = notesFireStore.getActiveNotes()
    val mRemovedItemsList: LiveData<ArrayList<NotesModel>> get() = notesFireStore.getRemovedNotes()

    fun getNotesList(): LiveData<ArrayList<NotesModel>> {
        return mItemsList
    }
}