package com.example.captainsclub2.repository

import com.example.captainsclub2.data.database.AppDatabase
import com.example.captainsclub2.data.models.Matros
import com.example.captainsclub2.data.models.Operation
import com.example.captainsclub2.data.models.Report
import com.example.captainsclub2.data.models.Shift
import kotlinx.coroutines.flow.Flow

class ShiftRepository(private val db: AppDatabase) {

    // Shift operations
    suspend fun getOpenShift() = db.shiftDao().getOpenShift()
    suspend fun saveShift(shift: Shift) = db.shiftDao().insert(shift)
    suspend fun closeShift(shift: Shift) {
        shift.status = false
        db.shiftDao().update(shift)
    }

    // Matros operations
    suspend fun getAllMatros() = db.matrosDao().getAllMatros()
    suspend fun addMatros(matros: Matros) = db.matrosDao().insert(matros)
    suspend fun removeMatros(matros: Matros) = db.matrosDao().delete(matros)


    // Operation operations
    suspend fun getActiveOperations() = db.operationDao().getActiveOperations()
    suspend fun saveOperation(operation: Operation) = db.operationDao().insert(operation)
    suspend fun updateOperation(operation: Operation) = db.operationDao().update(operation)

    // Report operations
    suspend fun getRecentReports() = db.reportDao().getRecentReports()
    suspend fun saveReport(report: Report) = db.reportDao().insert(report)
}