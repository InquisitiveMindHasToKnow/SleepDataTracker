package org.ohmstheresistance.sleepdatatracker.sleepdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import org.ohmstheresistance.sleepdatatracker.R
import org.ohmstheresistance.sleepdatatracker.database.SleepDatabase
import org.ohmstheresistance.sleepdatatracker.databinding.SleepDetailsFragmentBinding

class SleepDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: SleepDetailsFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.sleep_details_fragment, container, false
        )

        val application = requireNotNull(this.activity).application
        val arguments = SleepDetailsFragmentArgs.fromBundle(requireArguments())

        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SleepDetailsViewModelFactory(arguments.sleepNightKey, dataSource)

        val sleepDetailsViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SleepDetailsViewModel::class.java)

        binding.sleepDetailsViewModel = sleepDetailsViewModel

        binding.setLifecycleOwner(this)

        sleepDetailsViewModel.navigateToSleepTracker.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    SleepDetailsFragmentDirections.actionSleepDetailFragmentToSleepTrackerFragment()
                )

                sleepDetailsViewModel.doneNavigating()
            }
        })

        return binding.root
    }
}
