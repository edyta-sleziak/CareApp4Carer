package org.wit.careapp4carer.ui.sosNotification

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_sos_notification.*
import org.jetbrains.anko.intentFor
import org.wit.careapp4carer.R
import org.wit.careapp4carer.models.HrModel
import org.wit.careapp4carer.models.LocationModel
import org.wit.careapp4carer.models.firebase.AccountInfoFireStore
import org.wit.careapp4carer.models.firebase.SosFireStore
import org.wit.careapp4carer.ui.MainActivity
import org.wit.careapp4carer.ui.home.HomeFragment
import org.wit.careapp4carer.ui.login.LoginActivity
import org.wit.careapp4carer.ui.map.MapFragment
import com.google.android.gms.maps.SupportMapFragment
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class SosNotificationView : AppCompatActivity(), OnMapReadyCallback {

    var accountFirebase = AccountInfoFireStore()
    var sosFirestore = SosViewModel()
    private lateinit var map: GoogleMap
    var location = LocationModel()
    var hr = HrModel()
    private var loc = LatLng(location.latitude, location.longitude)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sos_notification)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        val alertId = intent.getStringExtra("id")

        val viewModel = SosViewModel()

        viewModel.getData(alertId!!)
            .observe(this, Observer { data ->
                source.setText(data!!.source)
                Log.d("data view", data.toString())
                date.setText(data.alertDate)
                time.setText(data.alertTime)
         })

        viewModel.getLatestLocation()
            .observe(this, Observer { data ->
                location = LocationModel(data.latitude,data.longitude,data.zoom)
                map.clear()
                loc = LatLng(location.latitude, location.longitude)
                val options = MarkerOptions()
                    .snippet("GPS : " + loc.toString())
                    .draggable(false)
                    .position(loc)
                map.addMarker(options)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
            })

        viewModel.getLatestHR()
            .observe(this, Observer { data ->
                hr = HrModel(data.hrValue,data.dateTime)
                last_hr.setText(hr.hrValue.toString())
            })

    }

    override fun onBackPressed() {
        if (accountFirebase.getUser() != null) {
            startActivity(Intent(baseContext, MainActivity::class.java))
        } else {
            startActivityForResult(intentFor<LoginActivity>(), 0)
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }

}