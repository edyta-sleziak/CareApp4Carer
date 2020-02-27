package org.wit.careapp4carer.models.firebase

import android.content.Context
import org.jetbrains.anko.AnkoLogger
import org.wit.careapp4carer.models.AccountInfoModel
import org.wit.careapp4carer.models.AccountInfoStore

class AccountInfoFireStore(val context: Context) : AccountInfoStore, AnkoLogger {

    val accounts = ArrayList<AccountInfoModel>()

    override fun add(accountInfo: AccountInfoModel) {

    }

    override fun getAccountInfo(id: Long): AccountInfoModel? {
        val userInfo: AccountInfoModel? = accounts.find { a -> a.id == id }
        return userInfo
    }

}