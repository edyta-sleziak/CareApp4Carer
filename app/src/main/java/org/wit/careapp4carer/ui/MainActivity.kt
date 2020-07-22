package org.wit.careapp4carer.ui

import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import org.wit.careapp4carer.R
import android.net.Uri
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import org.wit.careapp4carer.ui.login.LoginActivity
import org.wit.careapp4carer.ui.map.MapFragment
import org.wit.careapp4carer.ui.notes.AddNote
import org.wit.careapp4carer.ui.notes.NoteDetailsFragment
import org.wit.careapp4carer.ui.notifications.AddNotificationFragment
import org.wit.careapp4carer.ui.notifications.history.NotificationHistoryFragment
import org.wit.careapp4carer.ui.todo.history.toDoHistoryFragment
import org.wit.careapp4carer.ui.todo.toDoItemEditFragment


class MainActivity : AppCompatActivity(),
        AddNotificationFragment.OnFragmentInteractionListener,
        NotificationHistoryFragment.OnFragmentInteractionListener,
        AddNote.OnFragmentInteractionListener,
        NoteDetailsFragment.OnFragmentInteractionListener,
        toDoItemEditFragment.OnFragmentInteractionListener,
        toDoHistoryFragment.OnFragmentInteractionListener,
        MapFragment.OnFragmentInteractionListener
{

    private lateinit var appBarConfiguration: AppBarConfiguration

    private var user = FirebaseAuth.getInstance().currentUser!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        var auth: FirebaseAuth = FirebaseAuth.getInstance()

        val navView: NavigationView = findViewById(R.id.nav_view)
        val header = navView.getHeaderView(0)
        val accountName: TextView = header.findViewById(R.id.accountName)
        accountName.setText(user.email)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        //val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_todo,
                R.id.nav_notes,
                R.id.nav_settings,
                R.id.nav_notifications,
                R.id.nav_reports
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.menu.findItem(R.id.logout).setOnMenuItemClickListener {
        auth.signOut()
            startActivity(Intent(baseContext, LoginActivity::class.java))
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onFragmentInteraction(uri: Uri) {
    }
}
