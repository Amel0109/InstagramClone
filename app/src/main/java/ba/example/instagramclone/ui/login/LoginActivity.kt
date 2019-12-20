package ba.example.instagramclone.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import ba.example.instagramclone.ui.MainActivity
import ba.example.instagramclone.R
import ba.example.instagramclone.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    // Declare FirebaseAuth
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Initialize firebase
        auth = FirebaseAuth.getInstance()

        // Check does current user exists
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null){
            // If user exists navigate him to main
            val user = auth.currentUser
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        textView_register.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        log_in.setOnClickListener {
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
                auth.signInWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(this) { task ->
                        progress_bar.visibility = View.GONE
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information

                            val user = auth.currentUser
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            progress_bar.visibility = View.GONE
                            Toast.makeText(baseContext, "Authentication failed.${task.exception.toString()}",
                                Toast.LENGTH_SHORT).show()

                        }

                        // ...
                    }
            }
        }
    }
}
