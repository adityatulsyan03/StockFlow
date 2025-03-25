package com.example.stockflow.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stockflow.BuildConfig
import com.example.stockflow.common.UiState
import com.example.stockflow.data.model.AccessTokenBody
import com.example.stockflow.presentation.navigation.Screens
import com.example.stockflow.presentation.viewmodel.UserDetailViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import `in`.iotkiit.nexterdayevents.view.components.login.rememberFirebaseAuthLauncher

@Composable
fun LoginScreen(
    viewModel: UserDetailViewModel,
    navController: NavController
) {
    val authUserState by viewModel.adduser.collectAsState()
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }
    val context = LocalContext.current
    val token = BuildConfig.FIREBASE_TOKEN

    // Handle authentication states
    when (authUserState) {
        is UiState.Loading -> Log.d("LoginScreen", "Loading")
        is UiState.Success -> {
            Log.d("LoginScreen", "Success")
            LaunchedEffect(authUserState) {
                navController.navigate(Screens.DashBoardScreen.route)
            }
        }
        is UiState.Failed -> Log.d("LoginScreen", "Failed")
        else -> {}
    }

    val launcher = rememberFirebaseAuthLauncher(onAuthComplete = { result ->
        user = result.user
        val userCurr = FirebaseAuth.getInstance().currentUser
        userCurr?.getIdToken(true)
            ?.addOnSuccessListener { tokenResult ->
                val idToken = tokenResult.token
                if (idToken != null) {
                    Log.d("Google Auth", "Token: $idToken")
                    viewModel.addUser(AccessTokenBody(token = idToken))
                } else {
                    Toast.makeText(context, "Login Failed!", Toast.LENGTH_LONG).show()
                }
            }
            ?.addOnFailureListener {
                Toast.makeText(context, "Failed to fetch ID token!", Toast.LENGTH_LONG).show()
            }
    }, onAuthError = {
        user = null
        Toast.makeText(context, "Login Failed!", Toast.LENGTH_LONG).show()
    })

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF121212) // Hardcoded Background Color
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome Onboard!",
                color = Color(0xFFB0BEC5), // Hardcoded Text Color
                textAlign = TextAlign.Center,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(token)
                        .requestEmail()
                        .build()

                    val googleSignInClient = GoogleSignIn.getClient(context, gso)

                    googleSignInClient.signOut().addOnCompleteListener {
                        googleSignInClient.revokeAccess().addOnCompleteListener {
                            Log.d("LoginScreen", "Signed out and access revoked.")
                            launcher.launch(googleSignInClient.signInIntent)
                        }
                    }
                },
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1E1E1E), // Hardcoded Button Color
                    contentColor = Color.White
                )
            ) {
                Text(text = "Log In")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (user != null) {

                Button(
                    onClick = {
                        Firebase.auth.signOut()
                        Toast.makeText(context, "Logged out successfully!", Toast.LENGTH_SHORT).show()
                    },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1E1E1E), // Hardcoded Button Color
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Logout")
                }
            }
        }
    }
}