package com.example.captainsclub2.data.database

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import com.example.captainsclub2.data.models.Matros
import com.example.captainsclub2.data.models.Operation
import com.example.captainsclub2.data.models.Report
import com.example.captainsclub2.data.models.Shift

@Database(entities = [Shift::class, Matros::class, Operation::class, Report::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shiftDao(): ShiftDao
    abstract fun matrosDao(): MatrosDao
    abstract fun operationDao(): OperationDao
    abstract fun reportDao(): ReportDao
}

// DAO for Shift
@Dao
interface ShiftDao {
    @Query("SELECT * FROM Shift WHERE status = 1 LIMIT 1")
    suspend fun getOpenShift(): Shift?

    @Insert
    suspend fun insert(shift: Shift)

    @Update
    suspend fun update(shift: Shift)
}

// DAO for Matros
@Dao
interface MatrosDao {
    @Query("SELECT * FROM Matros")
    suspend fun getAllMatros(): List<Matros>

    @Insert
    suspend fun insert(matros: Matros)

    @Delete
    suspend fun delete(matros: Matros)
}

// DAO for Operation
@Dao
interface OperationDao {
    @Query("SELECT * FROM Operation WHERE status = 'ACTIVE'")
    suspend fun getActiveOperations(): List<Operation>

    @Insert
    suspend fun insert(operation: Operation)

    @Update
    suspend fun update(operation: Operation)
}

// DAO for Report
@Dao
interface ReportDao {
    @Query("SELECT * FROM Report ORDER BY id DESC LIMIT 220")
    suspend fun getRecentReports(): List<Report>

    @Insert
    suspend fun insert(report: Report)
}