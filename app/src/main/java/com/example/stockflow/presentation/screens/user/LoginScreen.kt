package com.example.stockflow.presentation.screens.user

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
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
import com.example.stockflow.utils.safeNavigateOnce
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import `in`.iotkiit.nexterdayevents.view.components.login.rememberFirebaseAuthLauncher

@Composable
fun LoginScreen(
    viewModel: UserDetailViewModel,
    navController: NavController
) {

    Log.d("Screen","Login Screen")

    val authUserState by viewModel.adduser.collectAsState()
    val context = LocalContext.current
    val token = BuildConfig.FIREBASE_TOKEN
    when (authUserState) {
        is UiState.Loading -> {
            Log.d("LoginScreen", "Loading")
            LoadingScreen()
        }
        is UiState.Success -> {
            Log.d("LoginScreen", "Success")
            LaunchedEffect(authUserState) {
                navController.safeNavigateOnce(Screens.DashBoardScreen.route)
            }
        }
        is UiState.Failed -> {
            Log.d("LoginScreen", "Failed")
            ErrorScreen()
        }
        else -> {
            Log.d("LoginScreen", "Else")
            LoadingScreen()
        }
    }

    val launcher = rememberFirebaseAuthLauncher(onAuthComplete = { result ->
        val userCurr = FirebaseAuth.getInstance().currentUser
        userCurr?.getIdToken(true)
            ?.addOnSuccessListener { tokenResult ->
                val idToken = tokenResult.token
                Log.d("Token", idToken.toString())
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
        Toast.makeText(context, "Login Failed!", Toast.LENGTH_LONG).show()
    })

    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color(0xFF121212))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome Onboard!",
                color = Color(0xFFB0BEC5),
                textAlign = TextAlign.Center,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    Log.d("LoginScreen", "Login Button Clicked")
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
                    containerColor = Color(0xFF1E1E1E),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Log In")
            }
        }
    }
}