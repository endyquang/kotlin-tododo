package com.ndq.tododo.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.ndq.tododo.models.Preferences
import kotlinx.coroutines.flow.Flow

@Dao
interface PreferencesDao {
    @Query("SELECT * FROM preferences LIMIT 1")
    fun getFlow(): Flow<Preferences?>

    @Query("SELECT * FROM preferences LIMIT 1")
    suspend fun get(): Preferences?

    @Update
    suspend fun update(preferences: Preferences)

    @Insert
    suspend fun insert(preferences: Preferences)

    @Transaction
    suspend fun upsert(preferences: Preferences) {
        val existing = get()
        if (existing == null) {
            insert(preferences)
        } else {
            update(preferences)
        }
    }
}
