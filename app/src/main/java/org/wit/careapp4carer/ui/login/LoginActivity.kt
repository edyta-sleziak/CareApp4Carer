package org.wit.careapp4carer.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import org.wit.careapp4carer.R
import org.wit.careapp4carer.models.AccountInfoModel
import org.wit.careapp4carer.models.LocationModel
import org.wit.careapp4carer.models.firebase.AccountInfoFireStore
import org.wit.careapp4carer.ui.MainActivity

class LoginActivity : AppCompatActivity() {

    lateinit var context: Context
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var fireStore = AccountInfoFireStore()
    var accountFirebase = AccountInfoFireStore()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (accountFirebase.getUser() != null) {
            startActivity(Intent(baseContext, MainActivity::class.java))
        } else {
            context = this
            setContentView(R.layout.activity_login)
            progressBar.visibility = View.GONE

            login.setOnClickListener() {
                val email = email.text.toString()
                val password = password.text.toString()

                if (email.isEmpty() && password.isEmpty()) {
                    toast(R.string.enter_credentials)
                } else {
                    doLogin(email, password)
                }
            }
            signup.setOnClickListener() {
                val email = email.text.toString()
                val password = password.text.toString()

                if (email.isEmpty() || password.isEmpty()) {
                    toast(R.string.enter_credentials)
                } else {
                    doSignup(email, password)
                }
            }
        }
    }
    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    private fun doLogin(email: String, password: String) {
        showProgress()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                hideProgress()
                startActivity(Intent(baseContext, MainActivity::class.java))
            } else {
                hideProgress()
                toast("Login Failed: ${task.exception?.message}")
            }
        }
    }

    private fun doSignup (email: String, password: String) {
        showProgress()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val newAccount =
                AccountInfoModel(email, "Carer", "Patient", "112", "130", "70", "Not set", "Not set", "Not set", "","", LocationModel(0.0, 0.0, 6f,""))
            doLogin(email,password)
            fireStore.add(newAccount)
            Thread.sleep(4000)
            } else {
                toast("Sign Up Failed: ${task.exception?.message}")
                hideProgress()
            }
        }
    }
}
