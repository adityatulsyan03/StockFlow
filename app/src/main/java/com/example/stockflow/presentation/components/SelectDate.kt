package com.example.stockflow.presentation.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerField(
    label: String = "Select Date",
    selectedDate: MutableState<LocalDate?>,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, day ->
            val newDate = LocalDate.of(year, month + 1, day)
            selectedDate.value = newDate
            onDateSelected(newDate)
        },
        selectedDate.value?.year ?: LocalDate.now().year,
        selectedDate.value?.monthValue?.minus(1) ?: (LocalDate.now().monthValue - 1),
        selectedDate.value?.dayOfMonth ?: LocalDate.now().dayOfMonth
    )

    Box(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedDate.value?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                ?: "",
            onValueChange = {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                selectedDate.value = try {
                    LocalDate.parse(it, formatter)
                } catch (e: Exception) {
                    null
                }
            },
            label = { Text(label) },
            readOnly = true,
            enabled = false,
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledTextColor = Color.White,
                disabledLabelColor = Color.White,
                disabledBorderColor = Color.White,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable { datePickerDialog.show() }
        )
    }
}