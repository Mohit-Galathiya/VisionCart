package com.example.visioncart.presentation.Screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.visioncart.R
import com.example.visioncart.domain.di.models.UserData
import com.example.visioncart.domain.di.models.UserDataParent
import com.example.visioncart.presentation.Utils.LogOutAlertDialog
import com.example.visioncart.presentation.navigation.SubNavigation
import com.example.visioncart.presentation.viewModels.ShoppingAppViewModel
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    firebaseAuth: FirebaseAuth,
    navController: NavController
) {

    LaunchedEffect(key1 = true) {
        viewModel.getUserById(firebaseAuth.currentUser!!.uid)
    }

    val profileScreenState by viewModel.profileScreenState.collectAsStateWithLifecycle()
    val upDateScreenState by viewModel.upDateScreenState.collectAsStateWithLifecycle()
    val userProfileImageState by viewModel.userProfileImageState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var imageUrl by remember { mutableStateOf("") }

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    LaunchedEffect(profileScreenState.userData) {
        profileScreenState.userData?.userdata?.let { userData ->
            firstName = userData.firstName
            lastName = userData.lastName
            email = userData.email
            phoneNumber = userData.phoneNumber
            address = userData.address
            imageUrl = userData.profileImage
        }
    }

    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.userProfileImage(uri)
            imageUri = uri
        }
    }

    LaunchedEffect(upDateScreenState.userData) {
        if (upDateScreenState.userData != null) {
            Toast.makeText(context, upDateScreenState.userData, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(upDateScreenState.errorMessage) {
        if (upDateScreenState.errorMessage != null) {
            Toast.makeText(context, upDateScreenState.errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(userProfileImageState.userData) {
        if (userProfileImageState.userData != null) {
            imageUrl = userProfileImageState.userData.toString()
        }
    }

    LaunchedEffect(userProfileImageState.errorMessage) {
        if (userProfileImageState.errorMessage != null) {
            Toast.makeText(context, userProfileImageState.errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "ACCOUNT",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(R.drawable.back),
                            contentDescription = "back button"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HorizontalDivider(
                color = Color.LightGray,
                thickness = 0.5.dp
            )

            when {
                profileScreenState.isLoading || upDateScreenState.isLoading || userProfileImageState.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                profileScreenState.errorMessage != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = profileScreenState.errorMessage!!)
                    }
                }

                profileScreenState.userData != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .align(Alignment.CenterHorizontally)
                        ) {
                            SubcomposeAsyncImage(
                                model = if (isEditing && imageUri != null) imageUri else imageUrl,
                                contentDescription = "Profile Picture",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, colorResource(id = R.color.blue), CircleShape)
                            ) {
                                when (painter.state) {
                                    is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
                                    is AsyncImagePainter.State.Error -> Icon(
                                        Icons.Default.Person,
                                        contentDescription = null,
                                        modifier = Modifier.size(80.dp)
                                    )
                                    else -> SubcomposeAsyncImageContent()
                                }
                            }

                            if (isEditing) {
                                IconButton(
                                    onClick = {
                                        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                    },
                                    modifier = Modifier
                                        .size(40.dp)
                                        .align(Alignment.BottomEnd)
                                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                                ) {
                                    Icon(
                                        Icons.Default.Add,
                                        contentDescription = "Change Picture",
                                        tint = Color.White
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            OutlinedTextField(
                                value = firstName,
                                onValueChange = { firstName = it },
                                modifier = Modifier.weight(1f),
                                readOnly = !isEditing,
                                label = { Text("First Name") },
                                shape = RoundedCornerShape(10.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            OutlinedTextField(
                                value = lastName,
                                onValueChange = { lastName = it },
                                modifier = Modifier.weight(1f),
                                readOnly = !isEditing,
                                label = { Text("Last Name") },
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = !isEditing,
                            label = { Text("Email") },
                            shape = RoundedCornerShape(10.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = phoneNumber,
                            onValueChange = { phoneNumber = it },
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = !isEditing,
                            label = { Text("Phone Number") },
                            shape = RoundedCornerShape(10.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = address,
                            onValueChange = { address = it },
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = !isEditing,
                            label = { Text("Address") },
                            shape = RoundedCornerShape(10.dp)
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        if (!isEditing) {
                            Button(
                                onClick = { isEditing = true },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blue))
                            ) {
                                Text("Edit Profile")
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedButton(
                                onClick = { showDialog = true },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Log Out", color = Color.Red)
                            }
                        } else {
                            Button(
                                onClick = {
                                    val updatedUserData = UserData(
                                        firstName = firstName,
                                        lastName = lastName,
                                        email = email,
                                        phoneNumber = phoneNumber,
                                        address = address,
                                        profileImage = imageUrl
                                    )
                                    val userDataParent = UserDataParent(
                                        nodeId = profileScreenState.userData!!.nodeId,
                                        userdata = updatedUserData
                                    )
                                    viewModel.upDateUserData(userDataParent)
                                    isEditing = false
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                            ) {
                                Text("Save Profile", color = Color.White)
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            TextButton(onClick = { isEditing = false }) {
                                Text("Cancel")
                            }
                        }

                        if (showDialog) {
                            LogOutAlertDialog(
                                onDismiss = { showDialog = false },
                                onConfirm = {
                                    showDialog = false
                                    firebaseAuth.signOut()
                                    navController.navigate(SubNavigation.LoginSignUpScreen) {
                                        popUpTo(0)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
