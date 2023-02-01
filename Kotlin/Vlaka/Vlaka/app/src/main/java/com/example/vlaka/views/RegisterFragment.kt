package com.example.vlaka.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
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

class RegisterFragment : Fragment() {


    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001
    val TAG = "miapp"

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        val btnRegister = view.findViewById<Button>(R.id.btnRegistrar)
        val yaTengocuenta = view.findViewById<Button>(R.id.btnIniciarSesion)
        val btnGoogle = view.findViewById<Button>(R.id.authGoogle)


        yaTengocuenta.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        val email = view.findViewById<TextInputLayout>(R.id.emailInputRegistrar)
        val pass = view.findViewById<TextInputLayout>(R.id.passInputRegistrar)

        btnRegister.setOnClickListener {
            if (email.editText?.text.toString().isEmpty()) {
                email.editText?.error = "Don't let email box empty"
            } else if (pass.editText?.text.toString().isEmpty()) {
                activity?.let { it1 ->
                    Snackbar.make(
                        it1.findViewById(R.id.passInputIniciar),
                        "Don't let pass box empty", Snackbar.LENGTH_LONG
                    ).show()
                }
            } else {
                registerUser(email.editText!!.text.toString(), pass.editText!!.text.toString())
            }

        }
        initGoogle()

        btnGoogle.setOnClickListener() {
            authGoogle()
        }


    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Registrado", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_registerFragment_to_homeActivity)
                } else {
                    Toast.makeText(context, "Error de registro", Toast.LENGTH_LONG).show()
                }
            }

    }

    private fun initGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        activity?.let { GoogleSignIn.getClient(it, gso) }.also {
            if (it != null) {
                googleSignInClient = it
            }
        }
        auth = Firebase.auth
    }

    private fun authGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        activity?.let {
            auth.signInWithCredential(credential)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")
                        findNavController().navigate(R.id.action_registerFragment_to_homeActivity)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                    }
                }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }
}


