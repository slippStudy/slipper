package slipp.study.slipper

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        sign_up.setOnClickListener {
            performRegister()
        }

    }

    private fun performRegister() {
        val email = email.text.toString()
        val password = password.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "이메일과 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener
                    Log.d("SignUp", "Successfully created user with uid: ${it.result.user.uid}")
                    intent = Intent(this, SignInActivity::class.java)
                    intent.putExtra("email", email)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Log.d("SignUp", "Failed to create user: ${it.message}")
                    Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT).show()
                }
    }
}