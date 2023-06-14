package com.animebiru.kerjaaja.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.animebiru.kerjaaja.data.sources.local.entity.HistoryProjectEntity
import com.animebiru.kerjaaja.data.sources.local.entity.ProjectCategoryEntity
import com.animebiru.kerjaaja.data.sources.local.entity.ProjectEntity

@Database(entities = [ProjectEntity::class, ProjectCategoryEntity::class, HistoryProjectEntity::class], version = 5, exportSchema = false)
abstract class MainDatabase: RoomDatabase() {
    abstract val dao: MainDao
}