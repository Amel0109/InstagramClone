package ba.example.instagramclone.ui.profile


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ba.example.instagramclone.R
import ba.example.instagramclone.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sign_out.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
        }
        database = FirebaseDatabase.getInstance().reference
        val currentUser = FirebaseAuth.getInstance().currentUser

        currentUser?.let {
            database.child("users").child(it.uid).addValueEventListener(object :
                ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    Log.d("Tag","On Cancelled")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("Tag", "On data change")
                    val user = dataSnapshot.getValue(User::class.java)
                    user?.let {
                        username.setText(it.username)
                        followers.setText(it.followers)
                        following.setText(it.following)
                        posts.setText(it.posts)
                        bio.setText(it.bio)
                    }
                }
            })
        }
    }


    private fun writeNewUser(userId: String, userName: String, email: String) {
        val user = User(userId, userName, email)
        database.child("users").child(userId).setValue(user)
    }


}
