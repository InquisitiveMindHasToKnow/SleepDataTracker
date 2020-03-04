package org.ohmstheresistance.sleepdatatracker.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.ohmstheresistance.sleepdatatracker.R
import org.ohmstheresistance.sleepdatatracker.convertDurationToFormatted
import org.ohmstheresistance.sleepdatatracker.convertNumericQualityToString
import org.ohmstheresistance.sleepdatatracker.database.SleepNight
import org.ohmstheresistance.sleepdatatracker.databinding.SleepItemViewBinding

class SleepNightAdapter :
    ListAdapter<SleepNight, SleepNightAdapter.SleepNightViewHolder>(SleepNightDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepNightViewHolder {

        return SleepNightViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SleepNightViewHolder, position: Int) {

        val item = getItem(position)
        holder.bind(item)
    }

    class SleepNightViewHolder private constructor(val binding: SleepItemViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SleepNight) {
            binding.sleep = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SleepNightViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = SleepItemViewBinding.inflate(layoutInflater, parent, false)

                return SleepNightViewHolder(binding)
            }
        }
    }

    class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNight>() {
        override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {

            return oldItem.nightId == newItem.nightId
        }

        override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {

            return oldItem == newItem
        }
    }
}
