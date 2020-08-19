package org.wit.careapp4carer.ui.sosNotification

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_sos_notification.*
import org.jetbrains.anko.intentFor
import org.wit.careapp4carer.R
import org.wit.careapp4carer.models.Location
import org.wit.careapp4carer.models.LocationModel
import org.wit.careapp4carer.models.firebase.AccountInfoFireStore
import org.wit.careapp4carer.models.firebase.SosFireStore
import org.wit.careapp4carer.ui.MainActivity
import org.wit.careapp4carer.ui.home.HomeFragment
import org.wit.careapp4carer.ui.login.LoginActivity

class SosNotificationView : AppCompatActivity(), OnMapReadyCallback {

    var accountFirebase = AccountInfoFireStore()
    var sosFirestore = SosViewModel()
    private lateinit var map: GoogleMap
    var location = LocationModel()

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
                date.setText(data.alertDate)
                Log.d("time", data.alertTime)
                time.setText(data.alertTime)

            })

    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latestLocation = sosFirestore.getLatestLocation()
        val location = Location(latestLocation.value!!.latitude, latestLocation.value!!.longitude, latestLocation.value!!.zoom)

        map = googleMap
        val loc = LatLng(location.lat, location.lng)
        val options = MarkerOptions()
            .title("Latest location")
            .snippet("GPS : " + loc.toString())
            .draggable(false)
            .position(loc)
        map.addMarker(options)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
    }

    override fun onBackPressed() {
        if (accountFirebase.getUser() != null) {
            startActivity(Intent(baseContext, MainActivity::class.java))
        } else {
            startActivityForResult(intentFor<LoginActivity>(), 0)
        }

    }

}