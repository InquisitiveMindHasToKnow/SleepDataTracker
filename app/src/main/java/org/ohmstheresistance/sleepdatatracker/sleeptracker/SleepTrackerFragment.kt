package org.ohmstheresistance.sleepdatatracker.sleeptracker

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager

import org.ohmstheresistance.sleepdatatracker.R
import org.ohmstheresistance.sleepdatatracker.database.SleepDatabase
import org.ohmstheresistance.sleepdatatracker.databinding.SleepTrackerFragmentBinding

class SleepTrackerFragment : Fragment() {

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: SleepTrackerFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.sleep_tracker_fragment, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao

        val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)

        val sleepTrackerViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(SleepTrackerViewModel::class.java)

        binding.sleepTrackerViewModel = sleepTrackerViewModel

        val manager = GridLayoutManager(activity, 3)
        binding.sleepTrackerRecyclerView.layoutManager = manager

        val sleepAdapter = SleepNightAdapter()
        binding.sleepTrackerRecyclerView.adapter = sleepAdapter
        sleepTrackerViewModel.nights.observe(viewLifecycleOwner, Observer {
            it?.let {
                sleepAdapter.submitList(it)
            }
        })

        binding.setLifecycleOwner(this)

        sleepTrackerViewModel.showSnackBarEvent.observe(viewLifecycleOwner, Observer {

            if (it == true) {
                Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    getString(R.string.cleared_message),
                    Snackbar.LENGTH_SHORT
                ).show()

                sleepTrackerViewModel.doneShowingSnackbar()
            }
        })

        sleepTrackerViewModel.navigateToSleepQuality.observe(viewLifecycleOwner, Observer { night ->
            night?.let {

                this.findNavController().navigate(
                    SleepTrackerFragmentDirections
                        .actionSleepTrackerFragmentToSleepQualityFragment(night.nightId))

                sleepTrackerViewModel.doneNavigating()
            }
        })

        return binding.root
    }
}
