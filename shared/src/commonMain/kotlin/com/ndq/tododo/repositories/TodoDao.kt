package com.ndq.tododo.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ndq.tododo.models.Todo
import com.ndq.tododo.models.TodoStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo ORDER BY createdAt DESC")
    fun getAllFlow(): Flow<List<Todo>>

    @Query(
        """
        SELECT * FROM Todo 
        WHERE 
            CASE :status 
                WHEN 'TODO' THEN doneAt IS NULL AND cancelledAt IS NULL
                WHEN 'DONE' THEN doneAt IS NOT NULL
                WHEN 'CANCELLED' THEN cancelledAt IS NOT NULL
            END
        ORDER BY createdAt DESC
        """
    )
    fun getAllByStatus(status: TodoStatus): Flow<List<Todo>>

    @Query("SELECT * FROM todo WHERE id = :id")
    suspend fun getById(id: Long): Todo?

    @Insert
    suspend fun insert(todo: Todo)

    @Update
    suspend fun update(todo: Todo)

    @Query("DELETE FROM todo WHERE id = :id")
    suspend fun delete(id: Long)
}
