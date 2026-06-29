package com.aakash.android.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.aakash.android.presentation.viewmodel.PokedexViewModel

@Composable
fun PokemonDetailScreen(
    name: String,
    viewModel: PokedexViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(name) {
        viewModel.getPokemonDetails(name)
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header Row: Back button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Back",
                    color = Color(0xFF5B71A6), // Standard iOS/Simple back button blue
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clickable { onBack() }
                        .padding(end = 16.dp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                when {
                    uiState.isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    uiState.error.isNotEmpty() -> {
                        Text(
                            text = uiState.error,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    uiState.info != null -> {
                        val detail = uiState.info!!
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Circular background container for the main image
                            Surface(
                                modifier = Modifier.size(160.dp),
                                shape = CircleShape,
                                color = Color(0xFFEAE7ED) // Light lavender/gray circle
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    AsyncImage(
                                        model = detail.imgUrl,
                                        contentDescription = detail.name,
                                        modifier = Modifier.size(120.dp)
                                    )
                                }
                            }
                            
                            Text(
                                text = detail.name.replaceFirstChar { it.uppercase() },
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            
                            // Info Card
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFEBE7EE)), // Lavender Card
                                shape = RoundedCornerShape(24.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(20.dp),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Text(
                                        text = "Info",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                    
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = "Height", color = Color.DarkGray, fontSize = 15.sp)
                                        Text(text = detail.height.toString(), fontWeight = FontWeight.Medium, color = Color.Black, fontSize = 15.sp)
                                    }
                                    
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = "Base Experience", color = Color.DarkGray, fontSize = 15.sp)
                                        Text(text = detail.baseExperience.toString(), fontWeight = FontWeight.Medium, color = Color.Black, fontSize = 15.sp)
                                    }
                                    
                                    Spacer(modifier = Modifier.height(4.dp))
                                    
                                    Text(
                                        text = "Abilities",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                    
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        detail.abilities.forEach { ability ->
                                            Text(
                                                text = "• ${ability.name.replaceFirstChar { it.uppercase() }}",
                                                fontSize = 15.sp,
                                                color = Color.DarkGray
                                            )
                                        }
                                    }

                                    if (detail.sprites.isNotEmpty()) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        
                                        LazyRow(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                                        ) {
                                            items(detail.sprites.size) { index ->
                                                val spriteUrl = detail.sprites[index]
                                                Card(
                                                    modifier = Modifier.size(110.dp),
                                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                                    shape = RoundedCornerShape(12.dp),
                                                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                                                ) {
                                                    Box(
                                                        contentAlignment = Alignment.Center,
                                                        modifier = Modifier.fillMaxSize()
                                                    ) {
                                                        AsyncImage(
                                                            model = spriteUrl,
                                                            contentDescription = "Alternative sprite",
                                                            modifier = Modifier.size(96.dp)
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
