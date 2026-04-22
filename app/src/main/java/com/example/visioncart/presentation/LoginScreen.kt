//package com.example.visioncart.presentation
//
//import android.widget.Toast
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Email
//import androidx.compose.material.icons.filled.Lock
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.material3.OutlinedButton
//import androidx.compose.material3.TextButton
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.visioncart.R
//import com.example.visioncart.presentation.Utils.CustomTextField
//
//@Preview(showSystemUi = true)
//@Composable
//fun LoginScreen() {
//
//    val context = LocalContext.current
//
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//
//        Text(
//            text = "Login",
//            fontSize = 24.sp,
//            style = TextStyle(fontWeight = FontWeight.Bold),
//            modifier = Modifier
//                .padding(bottom = 16.dp)
//                .align(Alignment.CenterHorizontally)
//        )
//
//        CustomTextField(
//            value = email,
//            onValueChange = { email = it },
//            label = "Email",
//            leadingIcon = Icons.Default.Email,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 4.dp)
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        CustomTextField(
//            value = password,
//            onValueChange = { password = it },
//            label = "Password",
//            leadingIcon = Icons.Default.Lock,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp),
//            visualTransformation = PasswordVisualTransformation()
//        )
//
//        Spacer(modifier = Modifier.height(4.dp))
//
//        Text(
//            text = "Forgot Password?",
//            modifier = Modifier.align(Alignment.End)
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(
//            onClick = {
//                if (email.isNotBlank() && password.isNotBlank()) {
//                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(context, "Please fill all details", Toast.LENGTH_SHORT).show()
//                }
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 16.dp),
//            shape = RoundedCornerShape(8.dp),
//            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue)
//            ),
//            border = BorderStroke(1.dp, colorResource(id = R.color.blue))
//        ) {
//            Text(text = "Login", color = colorResource(id = R.color.white))
//        }
//        Row(verticalAlignment = Alignment.CenterVertically) {
//
//            Text(text = "Don't have an account?")
//            TextButton(onClick = {
//                //navigate to login screen
//
//
//            }) {
//
//                Text(text = "Signup", color = colorResource(id = R.color.blue))
//
//            }
//
//
//        }
//
//
//        Row(modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//
//            HorizontalDivider(modifier = Modifier.weight(1f))
//
//            Text(text = "OR", modifier = Modifier.padding(horizontal = 8.dp))
//
//            HorizontalDivider(modifier = Modifier.weight(1f))
//
//
//        }
//
//        OutlinedButton(
//            onClick = { },
//
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp),
//            shape = RoundedCornerShape(8.dp),
//        ) {
//
//            Image(
//                painter = painterResource(id = R.drawable.google), contentDescription = null,
//
//                modifier = Modifier.size(24.dp)
//            )
//
//            Spacer(modifier = Modifier.size(8.dp))
//            Text("Login with Google")
//
//
//        }
//
//
//    }
//}

package com.example.visioncart.presentation.Screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.visioncart.R
import com.example.visioncart.domain.di.models.UserData
import com.example.visioncart.presentation.Utils.CustomTextField
import com.example.visioncart.presentation.Utils.SuccessAlertDialog
import com.example.visioncart.presentation.navigation.Routes
import com.example.visioncart.presentation.navigation.SubNavigation
import com.example.visioncart.presentation.viewModels.ShoppingAppViewModel



@Composable
fun LoginScreenUI(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.loginScreenState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    } else if (state.errorMessage != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = state.errorMessage!!)
        }
    } else if (state.userData != null) {
        SuccessAlertDialog(
            onClick = {
                navController.navigate(SubNavigation.MainHomeScreen)
            }
        )
    } else {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "LogIn",
                fontSize = 24.sp,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            CustomTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                leadingIcon = Icons.Default.Email,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            )
            Spacer(modifier = Modifier.height(8.dp))

            CustomTextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                leadingIcon = Icons.Default.Lock,
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Forgot Password?",
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { /* Handle Forgot Password */ },
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (email.isNotBlank() && password.isNotBlank()) {
                        val userData = UserData(
                            email = email,
                            password = password
                        )
                        viewModel.loginUserWithEmailAndPassword(userData)
                    } else {
                        Toast.makeText(context, "Please Fill All Fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.teal_700))
            ) {
                Text("Login", color = Color.White)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Don't have an account?")
                TextButton(onClick = {
                    navController.navigate(Routes.SignUpScreen)
                }) {
                    Text("Signup", color = colorResource(id = R.color.teal_700))
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f))
                Text("OR", modifier = Modifier.padding(horizontal = 8.dp))
                HorizontalDivider(modifier = Modifier.weight(1f))
            }

            OutlinedButton(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Log in with Google")
            }
        }
    }
}
