package org.ohmstheresistance.sleepdatatracker.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.ohmstheresistance.sleepdatatracker.database.SleepNight
import org.ohmstheresistance.sleepdatatracker.databinding.SleepItemViewBinding

class SleepNightAdapter(val clickListener: SleepNightClickListener) :
    ListAdapter<SleepNight, SleepNightAdapter.SleepNightViewHolder>(SleepNightDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepNightViewHolder {

        return SleepNightViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SleepNightViewHolder, position: Int) {

        val item = getItem(position)

        holder.bind(clickListener,item)
    }

    class SleepNightViewHolder private constructor(val binding: SleepItemViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: SleepNightClickListener, item: SleepNight) {
            binding.sleep = item
            binding.clickListener = clickListener
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

    class SleepNightClickListener(val clickListener: (sleepId: Long) -> Unit) {
        fun onClick(night: SleepNight) = clickListener(night.nightId)
    }
}
