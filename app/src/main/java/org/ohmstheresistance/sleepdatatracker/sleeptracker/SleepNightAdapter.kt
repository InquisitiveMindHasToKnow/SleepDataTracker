package org.ohmstheresistance.sleepdatatracker.sleeptracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.ohmstheresistance.sleepdatatracker.R
import org.ohmstheresistance.sleepdatatracker.convertDurationToFormatted
import org.ohmstheresistance.sleepdatatracker.convertNumericQualityToString
import org.ohmstheresistance.sleepdatatracker.database.SleepNight

class SleepNightAdapter : RecyclerView.Adapter<SleepNightAdapter.SleepNightViewHolder>() {

    var data = listOf<SleepNight>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepNightViewHolder {

        return SleepNightViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SleepNightViewHolder, position: Int) {

        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class SleepNightViewHolder private constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var sleepLength: TextView = itemView.findViewById(R.id.sleep_length)
        var quality: TextView = itemView.findViewById(R.id.quality_string)
        val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)

        fun bind(item: SleepNight) {

            val res = itemView.context.resources

            sleepLength.text =
                convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
            quality.text = convertNumericQualityToString(item.sleepQuality, res)
            qualityImage.setImageResource(
                when (item.sleepQuality) {
                    0 -> R.drawable.ic_sleep_0
                    1 -> R.drawable.ic_sleep_1
                    2 -> R.drawable.ic_sleep_2
                    3 -> R.drawable.ic_sleep_3
                    4 -> R.drawable.ic_sleep_4
                    5 -> R.drawable.ic_sleep_5
                    else -> R.drawable.ic_sleep_active
                }
            )
        }

        companion object {
            fun from(parent: ViewGroup): SleepNightViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.sleep_item_view, parent, false)

                return SleepNightViewHolder(view)
            }
        }
    }
}
