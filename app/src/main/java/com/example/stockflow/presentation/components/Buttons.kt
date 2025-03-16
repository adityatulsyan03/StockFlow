package com.example.stockflow.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.RateReview
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    contentDescription: String? = null,
    icon: ImageVector?= null,
    extended: Boolean
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        elevation = elevation
    ) {

        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription
                )
            }

            AnimatedVisibility(visible = extended) {
                Text(
                    modifier = Modifier.padding(12.dp),
                    text = text
                )
            }
        }
    }
}