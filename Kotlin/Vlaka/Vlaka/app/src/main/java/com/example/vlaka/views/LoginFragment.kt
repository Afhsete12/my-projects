package com.example.vlaka.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.vlaka.HomeActivity
import androidx.navigation.fragment.findNavController
import com.example.vlaka.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {


    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("MyApp", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("MyApp", "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        activity?.let { activity ->
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("MyApp", "signInWithCredential:success")
                        val user = auth.currentUser

                        activity.let {
                            findNavController().navigate(R.id.action_loginFragment_to_homeActivity)
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("MyApp", "signInWithCredential:failure", task.exception)
                        //mostrar error al usuario
                    }
                }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth

        val btnStart = view.findViewById<Button>(R.id.btnIniciarSesion)
        val btnBack = view.findViewById<Button>(R.id.btnRegistrarse)

        val email = view.findViewById<TextInputLayout>(R.id.emailInputIniciar)
        val pass = view.findViewById<TextInputLayout>(R.id.passInputIniciar)

        val inWithGoogle = view.findViewById<Button>(R.id.inWithGoogle)

        initGoogle()

        inWithGoogle.setOnClickListener {
            loginGoogle()
        }

        btnStart.setOnClickListener {
            if (email.editText?.text.toString().isEmpty()){
                email.editText?.error = "Don't let email box empty"
            }else if (pass.editText?.text.toString().isEmpty()){
                activity?.let { it1 ->
                    Snackbar.make(
                        it1.findViewById(R.id.passInputIniciar),
                        "Don't let pass box empty", Snackbar.LENGTH_LONG).show()
                }
            }else{
                loginUser(email.editText?.text.toString(), pass.editText?.text.toString())
            }
        }

        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun loginGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun initGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        activity?.let { googleSignInClient = GoogleSignIn.getClient(it, gso) }
        auth = Firebase.auth
    }

    private fun loginUser(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_loginFragment_to_homeActivity)
                } else {
                    Toast.makeText(context, "Error en login", Toast.LENGTH_LONG).show()
                }
            }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
}