package ba.example.instagramclone.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import ba.example.instagramclone.ui.MainActivity
import ba.example.instagramclone.R
import ba.example.instagramclone.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.email
import kotlinx.android.synthetic.main.activity_register.password

class RegisterActivity : AppCompatActivity() {

    private  lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        button_register.setOnClickListener {
            val emailText = email.text.toString()
            val passwordText = password.text.toString()

            if (emailText.isEmpty()){
                Toast.makeText(this, "Molimo unesite email!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (passwordText.isEmpty()){
                Toast.makeText(this, "Molimo unesite password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else{
                progress_bar.visibility = View.VISIBLE
                auth.createUserWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(this) { task ->
                        progress_bar.visibility = View.GONE
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information

                            val user = auth.currentUser

                            if (user != null){
                                writeNewUser(user.uid, user.displayName ?: "", user.email ?: "")
                            }
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            progress_bar.visibility = View.GONE
                            // If sign in fails, display a message to the user.
                            Toast.makeText(baseContext, "Authentication failed.${task.exception.toString()}",
                                Toast.LENGTH_SHORT).show()
                        }
                        // ...
                    }
            }
        }
    }

    private fun writeNewUser(userId: String, userName: String, email: String) {
        val user = User(userId, userName, email)
        database.child("users").child(userId).setValue(user)
    }
}
