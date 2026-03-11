package com.praisetechzw.phoneguard.core.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "phone_reports")
data class PhoneReportEntity(
    @PrimaryKey val imei: String,
    val manufacturer: String,
    val model: String,
    val status: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isUserOwned: Boolean = false
)

@Dao
interface PhoneReportDao {
    @Query("SELECT * FROM phone_reports ORDER BY timestamp DESC")
    fun getReportsFlow(): Flow<List<PhoneReportEntity>>

    @Query("SELECT * FROM phone_reports")
    suspend fun getAllReports(): List<PhoneReportEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: PhoneReportEntity)

    @Delete
    suspend fun deleteReport(report: PhoneReportEntity)
}

@Database(entities = [PhoneReportEntity::class], version = 1)
abstract class PhoneGuardDatabase : RoomDatabase() {
    abstract fun phoneReportDao(): PhoneReportDao
}
