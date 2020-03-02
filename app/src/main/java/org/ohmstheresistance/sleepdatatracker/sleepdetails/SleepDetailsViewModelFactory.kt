package org.ohmstheresistance.sleepdatatracker.sleepdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ohmstheresistance.sleepdatatracker.database.SleepDatabaseDao

class SleepDetailsViewModelFactory(
    private val sleepNightKey: Long,
    private val dataSource: SleepDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepDetailsViewModel::class.java)) {
            return SleepDetailsViewModel(sleepNightKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}