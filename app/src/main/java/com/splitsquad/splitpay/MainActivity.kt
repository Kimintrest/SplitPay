package com.splitsquad.splitpay


//real
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig
import com.firebase.ui.auth.AuthUI.IdpConfig.*
import com.google.android.material.navigation.NavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.splitsquad.splitpay.databinding.ActivityMainBinding


fun isUserAuthenticated(): Boolean {
    val firebaseAuth = FirebaseAuth.getInstance()
    return firebaseAuth.currentUser != null
}


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    companion object {
        private const val MY_REQUEST_CODE = 7372007 // Any number you want
    }

    private lateinit var binding: ActivityMainBinding


    private lateinit var userInformation: DatabaseReference
    private lateinit var providers: List<IdpConfig>


    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Initialize Firebase
        userInformation = FirebaseDatabase.getInstance()
            .getReference(com.splitsquad.splitpay.Utils.Common.USER_INFORMATION)


        // Initialize Providers
        listOf(
            EmailBuilder().build(),
            GoogleBuilder().build()
        ).also { providers = it }


        //main
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nav_view)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//com.splitsquad.splitpay.Utils
        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle =
            ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.email, R.string.email)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }

    }

    private fun showSignInOptions() {
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            MY_REQUEST_CODE
        )
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MY_REQUEST_CODE) {
            val firebaseUser = FirebaseAuth.getInstance().currentUser

            // Check if user exists in the database
            firebaseUser?.let { user ->
                userInformation.orderByKey()
                    .equalTo(user.uid)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(error: DatabaseError) {
                            // Handle error if needed
                        }

                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (!dataSnapshot.hasChild(user.uid)) {
                                // User does not exist, add user to the database
                                com.splitsquad.splitpay.Utils.Common.loggedUser =
                                    com.splitsquad.splitpay.Model.User(user.uid, user.email ?: "")
                                userInformation.child(com.splitsquad.splitpay.Utils.Common.loggedUser.uid!!)
                                    .setValue(com.splitsquad.splitpay.Utils.Common.loggedUser)
                            }
                        }
                    })
            }
        }

        fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.nav_home -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment()).commit()

                R.id.nav_event -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, EventFragment()).commit()

                R.id.nav_group -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, GroupFragment()).commit()

                R.id.nav_search -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, SearchFragment()).commit()

                R.id.nav_bill -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, BillFragment()).commit()
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            return true
        }

        fun onBackPressed() {
            super.onBackPressed()
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

}
