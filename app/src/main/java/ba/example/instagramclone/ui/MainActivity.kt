package ba.example.instagramclone.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ba.example.instagramclone.R
import ba.example.instagramclone.ui.login.LoginActivity
import ba.example.instagramclone.ui.add.AddFragment
import ba.example.instagramclone.ui.home.HomeFragment
import ba.example.instagramclone.ui.profile.ProfileFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private  lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = MainPagerAdapter(supportFragmentManager)

        adapter.addFragment(HomeFragment())
        adapter.addFragment(AddFragment())
        adapter.addFragment(ProfileFragment())

        view_pager.adapter = adapter

        nav_view.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home ->{
                    view_pager.setCurrentItem(0)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_dashboard ->{
                    view_pager.setCurrentItem(1)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications ->{
                    view_pager.setCurrentItem(2)
                    return@setOnNavigationItemSelectedListener true
                }
                else ->{
                    return@setOnNavigationItemSelectedListener true
                }
            }
        }
        auth = FirebaseAuth.getInstance()

        auth.addAuthStateListener{
            if (it.currentUser == null){
                val user = auth.currentUser
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}
