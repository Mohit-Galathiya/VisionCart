package com.example.visioncart.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.visioncart.domain.di.models.BannerDataModels

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorizontalCarousel(banners: List<BannerDataModels>) {
    
    if (banners.isEmpty()) return

    val state = rememberCarouselState { banners.count() }
    
    HorizontalMultiBrowseCarousel(
        state = state,
        modifier = Modifier
            .width(412.dp)
            .height(221.dp),
        preferredItemWidth = 186.dp,
        itemSpacing = 8.dp,
        contentPadding = PaddingValues(horizontal = 16.dp) 
    ) { i -> 
        
        val banner = banners[i]
        AsyncImage(
            model = banner.image,
            contentDescription = banner.name,
            modifier = Modifier
                .height(205.dp)
                .clip(MaterialTheme.shapes.extraLarge),
            contentScale = ContentScale.Crop
        )
    }
}
