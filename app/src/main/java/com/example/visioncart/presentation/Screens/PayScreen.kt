package com.example.visioncart.presentation.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.visioncart.R
import com.example.visioncart.presentation.Utils.OrderPlacedDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PayScreen(navController: NavController) {

    var showOrderDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                title = { Text("REVIEW YOUR ORDER",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(
                        onClick = {navController.popBackStack()}
                    )  {
                        Icon(
                            painter = painterResource(R.drawable.back),
                            contentDescription = "back button"
                        )
                    }
                }
            )
        },
        bottomBar = {
            Column {
                HorizontalDivider(
                    color = Color.LightGray,
                    thickness = 0.5.dp
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Rs. 9000", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("VIEW PRICE DETAILS", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = colorResource(R.color.blue))
                    }

                    Button(
                        onClick = { showOrderDialog = true },
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.blue))
                    ) {
                        Text("Place Order",
                            fontSize = 16.sp,
                            color = Color.White)
                    }
                }
            }
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {

            HorizontalDivider(
                color = Color.LightGray,
                thickness = 0.5.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier.fillMaxWidth().background(color = Color.White)
                    .padding(vertical = 8.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)
                ) {
                    Box(
                        modifier = Modifier.size(150.dp, 150.dp).padding(horizontal = 12.dp)
                    )  {

                        Image(painter = painterResource(R.drawable.kurti_pink),
                            contentDescription = "Women image",
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            "Kurti Light full Neck",
                            fontSize = 18.sp, fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            "Rs. 10000",
                            fontSize = 20.sp, fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            "No Returns only Exchange",
                            fontSize = 16.sp, color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row {
                            Text("Size M", fontSize = 16.sp, color = Color.Gray)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Qty : 1", fontSize = 16.sp, color = Color.Gray)
                        }
                    }
                }

                HorizontalDivider(
                    color = Color.LightGray,
                    thickness = 0.5.dp
                )

                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {

                    Text("sold by:", color = Color.Gray)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("GROWTH TEXTILE PRIVATE LIMITED", color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(color = Color.White)
                    .padding(vertical = 8.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text("Price Details", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    // Assuming 'application' is a valid drawable since 'upload' wasn't in list earlier but similar exists
                    Icon(painter = painterResource(R.drawable.category),
                        tint = Color.Black,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp))
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text("Total Product Price", color = Color.Gray, fontSize = 14.sp)
                    Text("+ Rs. 10000", color = Color.Gray, fontSize = 14.sp)
                }
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text("Total Discount", color = Color.Gray, fontSize = 14.sp)
                    Text("- Rs. 500", color = Color.Gray, fontSize = 14.sp)
                }
                
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    color = Color.Gray,
                    thickness = 0.5.dp
                )

                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text("Order Total", color = Color.Gray, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Text("Rs. 9500", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
                
            }
        }
    }

    if (showOrderDialog) {
        OrderPlacedDialog(
            onDismiss = { showOrderDialog = false },
            onConfirm = {
                showOrderDialog = false
                navController.popBackStack()
            }
        )
    }
}


@Composable
@Preview(showBackground = true)
fun PayPreview() {
    PayScreen(navController = rememberNavController())
}
