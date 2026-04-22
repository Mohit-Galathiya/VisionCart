package com.example.visioncart.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.visioncart.R
import com.example.visioncart.presentation.navigation.Routes

@Composable
fun SearchBarForSearchScreen(navController: NavController) {

    var query by remember {
        mutableStateOf("")
    }

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(44.dp)
        .padding(horizontal = 8.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(Color.White)
        .border(1.dp, color = Color.LightGray, shape = RoundedCornerShape(16.dp))
        .clickable {
            navController.navigate(Routes.SearchBarScreen)
        },
        verticalAlignment = Alignment.CenterVertically) {
        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            imageVector = Icons.Default.Search,
            tint = Color.Gray,
            contentDescription = "Search Icon",
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        BasicTextField(
            value = query,
            onValueChange = { query = it },
            textStyle = TextStyle(
                fontSize = 15.sp,
                color = Color.Gray
            ),
            enabled = true,
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp),
            decorationBox = { innerTextField ->
                if (query.isEmpty()) {
                    Text(
                        text = "Search by Keyword or Product ID",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Note: microphone and camera drawables must exist in res/drawable
        // If they don't exist, you should add them or use Material Icons
        Icon(
            painter = painterResource(id = R.drawable.microphone), // Placeholder if microphone is missing
            contentDescription = "Voice Search",
            tint = Color.Gray,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Icon(
            painter = painterResource(id = R.drawable.camera), // Placeholder if camera is missing
            contentDescription = "Camera Search",
            tint = Color.Gray,
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))
    }
}
