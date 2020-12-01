package com.example.codinginflow.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.codinginflow.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    class Callback @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dao = database.get().taskDao()
            applicationScope.launch {
                dao.insert(Task(name = "Learn Python"))
                dao.insert(Task(name = "Learn Java", important = true, completed = true))
                dao.insert(Task(name = "Learn C++"))
                dao.insert(Task(name = "Learn AWS", important = true))
                dao.insert(Task(name = "Learn Android", completed = true))
                dao.insert(Task(name = "Learn iOS"))
                dao.insert(Task(name = "Forgot Every Thing ;)"))
            }
        }
    }
}