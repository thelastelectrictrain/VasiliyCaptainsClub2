package com.example.captainsclub2.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.captainsclub2.data.models.Shift
import com.example.captainsclub2.repository.ShiftRepository
import com.example.captainsclub2.utils.EmailUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(private val repository: ShiftRepository) : ViewModel() {

    private val _currentShift = MutableStateFlow<Shift?>(null)
    val currentShift: StateFlow<Shift?> = _currentShift

    init {
        viewModelScope.launch {
            _currentShift.value = repository.getOpenShift()
        }
    }

    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date())
    }

    fun openShift() {
        viewModelScope.launch {
            val shift = Shift(date = getCurrentDate(), status = true, matrosOnShift = emptyList())
            repository.saveShift(shift)
            _currentShift.value = shift
        }
    }

    fun closeShift() {
        viewModelScope.launch {
            val shift = _currentShift.value
            if (shift != null) {
                repository.closeShift(shift)
                _currentShift.value = null
            }
        }
    }

    fun generateReport(): String {
        val shift = currentShift.value ?: return ""
        val operations = repository.getActiveOperations()

        // Подсчет выручки
        val duckRevenue = operations.filter { it.type == "RENTAL" && it.itemType == "DUCK" && it.status == "COMPLETED" }
            .sumOf { 300 } // 300₽ за каждую успешную аренду утки
        val yachtRevenue = operations.filter { it.type == "RENTAL" && it.itemType == "YACHT" && it.status == "COMPLETED" }
            .sumOf { 400 } // 400₽ за каждую успешную аренду яхты
        val souvenirRevenue = operations.filter { it.type == "SALE" && it.status == "COMPLETED" }
            .sumOf {
                when (it.itemType) {
                    "HAT" -> 500
                    "KEYCHAIN" -> 300
                    "ROPE" -> 100
                    "DUCK_SALE" -> 2500
                    "STATUETTE" -> 500
                    else -> 0
                }
            }
        val fineRevenue = operations.filter { it.type == "FINE" && it.status == "COMPLETED" }
            .sumOf {
                when (it.itemType) {
                    "FINE_DUCK" -> 1000
                    "FINE_YACHT" -> 3000
                    else -> 0
                }
            }

        val totalRevenue = duckRevenue + yachtRevenue + souvenirRevenue + fineRevenue

        // Подсчет количества операций
        val totalRentals = operations.count { it.type == "RENTAL" && it.status == "COMPLETED" }
        val duckRentals = operations.count { it.type == "RENTAL" && it.itemType == "DUCK" && it.status == "COMPLETED" }
        val yachtRentals = operations.count { it.type == "RENTAL" && it.itemType == "YACHT" && it.status == "COMPLETED" }
        val fines = operations.count { it.type == "FINE" && it.status == "COMPLETED" }
        val souvenirs = operations.groupBy { it.itemType }.mapValues { it.value.size }

        // Расчет зарплаты
        val salaryPerRental = 75
        val matrosesOnShift = shift.matrosOnShift.size
        val totalSalary = (salaryPerRental * totalRentals).toDouble() / matrosesOnShift

        return """
        #отчет 
        Смена: ${shift.date}

        Общая выручка: ${totalRevenue}₽

        Выручка по сдачам: ${duckRevenue + yachtRevenue}₽

        Наличка: 5000₽
        Сдач: $totalRentals
        Сдач: Яхт: $yachtRentals (${yachtRevenue}₽)
        Сдач: Уток: $duckRentals (${duckRevenue}₽)
        Штрафы: $fines (-${fineRevenue}₽)
        Сувенирка: ${souvenirRevenue}₽
        Шапки: ${souvenirs["HAT"] ?: 0}
        Брелки: ${souvenirs["KEYCHAIN"] ?: 0}
        Веревки: ${souvenirs["ROPE"] ?: 0}
        Утки (продажа): ${souvenirs["DUCK_SALE"] ?: 0}
        Утка статуэтка: ${souvenirs["STATUETTE"] ?: 0}
        
        Зп общее: ${totalSalary.toInt()}₽
        ${shift.matrosOnShift.joinToString("\n") { "зп ${repository.getMatrosName(it)}: ${totalSalary.toInt()}₽" }}

        Оборудование
        Работает
        Яхты: 9
        Утки: 18
        Не работает
        Утиль: 
        Батарейки
        Работает: 378
    """.trimIndent()
    }
}