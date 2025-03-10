package com.ndq.tododo

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.ndq.tododo.models.Preferences
import com.ndq.tododo.models.Todo
import com.ndq.tododo.repositories.PreferencesDao
import com.ndq.tododo.repositories.TodoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO


@Database(entities = [Todo::class, Preferences::class], version = 2)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getTodoDao(): TodoDao
    abstract fun getPreferencesDao(): PreferencesDao
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .fallbackToDestructiveMigration(false)
        .build()
}

// Room compiler generates the `actual` implementations
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
