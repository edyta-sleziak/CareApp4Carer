package org.wit.careapp4carer.ui.reports

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.careapp4carer.models.HrModel
import org.wit.careapp4carer.models.firebase.HrFireStore

class ReportsViewModel : ViewModel() {

    val hrFireStore = HrFireStore()
    val mItemsList: LiveData<ArrayList<HrModel>> get() = hrFireStore.getHrHistory()

    fun getData(): LiveData<ArrayList<HrModel>> {
        return mItemsList
    }
}