package org.wit.careapp4carer.ui.sosNotification

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.activity_sos_notification.*
import org.jetbrains.anko.intentFor
import org.wit.careapp4carer.R
import org.wit.careapp4carer.models.firebase.AccountInfoFireStore
import org.wit.careapp4carer.models.firebase.SosFireStore
import org.wit.careapp4carer.ui.MainActivity
import org.wit.careapp4carer.ui.home.HomeFragment
import org.wit.careapp4carer.ui.login.LoginActivity

class SosNotificationView : AppCompatActivity() {

    var accountFirebase = AccountInfoFireStore()
    var sosFirestore = SosFireStore()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sos_notification)

        val alertId = intent.getStringExtra("id")

        val viewModel = SosViewModel()
        viewModel.getData(alertId!!)
            .observe(this, Observer { data ->

                last_hr.setText("No data")
                source.setText(data!!.source)
                Log.d("data view", data.toString())
                date.setText(data!!.alertDate)
                Log.d("time", data.alertTime)
                time.setText(data!!.alertTime)
            })


    }

    override fun onBackPressed() {
        if (accountFirebase.getUser() != null) {
            startActivity(Intent(baseContext, MainActivity::class.java))
        } else {
            startActivityForResult(intentFor<LoginActivity>(), 0)
        }

    }

}