package edu.farmingdale.pizzapartybottomnavbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlin.math.ceil
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// ToDo 6: Add another level of hunger that is Hungry that is in between Medium and Very hungry

// ToDo 7: Using the ViewModel class, create a new ViewModel class called PizzaPartyViewModel as
// a subclass of ViewModel. Add the following properties to the PizzaPartyViewModel - see Brightspace

class PizzaPartyViewModel : ViewModel() {
    var numPeopleInput by mutableStateOf("")
        private set

    var hungerLevel by mutableStateOf("Medium")
        private set

    var totalPizzas by mutableStateOf(0)
        private set

    fun updateNumPeople(input: String) {
        numPeopleInput = input
    }

    fun updateHungerLevel(level: String) {
        hungerLevel = level
    }

    fun calculatePizzas() {
        viewModelScope.launch {
            val numPeople = numPeopleInput.toIntOrNull() ?: 0
            totalPizzas = calculateNumPizzas(numPeople, hungerLevel)
        }
    }

    private fun calculateNumPizzas(numPeople: Int, hungerLevel: String): Int {
        val slicesPerPizza = 8
        val slicesPerPerson = when (hungerLevel) {
            "Light" -> 2
            "Medium" -> 3
            else -> 5
        }
        return ceil(numPeople * slicesPerPerson / slicesPerPizza.toDouble()).toInt()
    }
}

@Composable
fun PizzaPartyScreen(
    viewModel: PizzaPartyViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(10.dp)
    ) {
        Text(
            text = "Pizza Party",
            fontSize = 38.sp,
            modifier = modifier.padding(bottom = 16.dp)
        )
        NumberField(
            labelText = "Number of people?",
            textInput = viewModel.numPeopleInput,
            onValueChange = { viewModel.updateNumPeople(it) },
            modifier = modifier.padding(bottom = 16.dp).fillMaxWidth()
        )
        RadioGroup(
            labelText = "How hungry?",
            radioOptions = listOf("Light", "Medium", "Hungry", "Very hungry"),
            selectedOption = viewModel.hungerLevel,
            onSelected = { viewModel.updateHungerLevel(it) },
            modifier = modifier
        )
        Text(
            text = "Total pizzas: ${viewModel.totalPizzas}",
            fontSize = 22.sp,
            modifier = modifier.padding(top = 16.dp, bottom = 16.dp)
        )
        Button(
            onClick = { viewModel.calculatePizzas() },
            modifier = modifier.fillMaxWidth()
        ) {
            Text("Calculate")
        }
    }
}

@Composable
fun NumberField(
    labelText: String,
    textInput: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = textInput,
        onValueChange = onValueChange,
        label = { Text(labelText) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        modifier = modifier
    )
}

@Composable
fun RadioGroup(
    labelText: String,
    radioOptions: List<String>,
    selectedOption: String,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isSelectedOption: (String) -> Boolean = { selectedOption == it }

    Column {
        Text(labelText)
        radioOptions.forEach { option ->
            Row(
                modifier = modifier
                    .selectable(
                        selected = isSelectedOption(option),
                        onClick = { onSelected(option) },
                        role = Role.RadioButton
                    )
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = isSelectedOption(option),
                    onClick = null,
                    modifier = modifier.padding(end = 8.dp)
                )
                Text(
                    text = option,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
    }
}