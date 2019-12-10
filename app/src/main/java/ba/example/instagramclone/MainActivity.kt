package ba.example.instagramclone

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ba.example.instagramclone.ui.MainPagerAdapter
import ba.example.instagramclone.ui.profile.AddFragment
import ba.example.instagramclone.ui.profile.HomeFragment
import ba.example.instagramclone.ui.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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
    }
}
