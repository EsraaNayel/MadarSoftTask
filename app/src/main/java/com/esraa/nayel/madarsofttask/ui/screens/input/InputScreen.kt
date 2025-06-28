package com.esraa.nayel.madarsofttask.ui.screens.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.esraa.nayel.madarsofttask.ui.viewmodel.UserViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun InputScreen(
    viewModel: UserViewModel,
    onNavigateToDisplay: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var jobTitle by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }

    val inputState by viewModel.inputState.collectAsState()
    val genderOptions = listOf("Male", "Female")

    LaunchedEffect(inputState.successMessage) {
        if (inputState.successMessage != null) {
            name = ""
            age = ""
            jobTitle = ""
            selectedGender = ""
            kotlinx.coroutines.delay(2000)
            viewModel.clearMessages()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "User Information",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !inputState.isLoading
        )

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            enabled = !inputState.isLoading
        )

        OutlinedTextField(
            value = jobTitle,
            onValueChange = { jobTitle = it },
            label = { Text("Job Title") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !inputState.isLoading
        )

        Text(
            text = "Gender",
            style = MaterialTheme.typography.bodyLarge
        )

        Column {
            genderOptions.forEach { option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (selectedGender == option),
                            onClick = { if (!inputState.isLoading) selectedGender = option }
                        )
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (selectedGender == option),
                        onClick = { if (!inputState.isLoading) selectedGender = option },
                        enabled = !inputState.isLoading
                    )
                    Text(
                        text = option,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        // Error/Success Messages
        inputState.errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        inputState.successMessage?.let { success ->
            Text(
                text = success,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    viewModel.insertUser(name, age, jobTitle, selectedGender)
                },
                modifier = Modifier.weight(1f),
                enabled = !inputState.isLoading
            ) {
                if (inputState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp))
                } else {
                    Text("Save User")
                }
            }

            OutlinedButton(
                onClick = onNavigateToDisplay,
                modifier = Modifier.weight(1f),
                enabled = !inputState.isLoading
            ) {
                Text("View Users")
            }
        }
    }
}
