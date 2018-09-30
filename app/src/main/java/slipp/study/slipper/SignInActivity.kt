package slipp.study.slipper

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

var isSignedIn = false

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val emailAfterSignUp = intent.getStringExtra("email")
        email.setText(emailAfterSignUp, TextView.BufferType.EDITABLE)

        sign_in.setOnClickListener {
            performSignIn()
        }

        sign_up.setOnClickListener {
            intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    private fun performSignIn() {
        val email = email.text.toString()
        val password = password.text.toString()
        Log.d("click", "email : $email")
        Log.d("click", "Password : $password")
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "이메일과 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener
                    isSignedIn = true
                    intent = Intent(this, CategoryTabActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Log.d("LogIn", "Failed to LogIn: ${it.message}")
                    Toast.makeText(this, "Failed to LogIn: ${it.message}", Toast.LENGTH_SHORT).show()
                }
    }
}