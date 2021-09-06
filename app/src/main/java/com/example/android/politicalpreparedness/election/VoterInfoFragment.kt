package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.example.android.politicalpreparedness.utils.exts.setDisplayHomeAsUpEnabled
import com.example.android.politicalpreparedness.utils.exts.setTitle
import com.google.android.material.snackbar.Snackbar

class VoterInfoFragment : Fragment() {

    private lateinit var viewModel: VoterInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val args: VoterInfoFragmentArgs by navArgs()

        //TODO: Add ViewModel values and create ViewModel
        viewModel = ViewModelProvider(
            this, VoterInfoViewModel.Factory(args, requireContext())
        ).get(VoterInfoViewModel::class.java)

        //TODO: Add binding values
        val binding = FragmentVoterInfoBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

//        setDisplayHomeAsUpEnabled(false)

        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
         */
        viewModel.hasVoterInfo.observe(viewLifecycleOwner, Observer { hasInfo ->
            if (hasInfo) {
                showVoterInfo(binding)
            } else {
                hideVoterInfo(binding)
                Snackbar.make(requireView(), R.string.voter_info_error, Snackbar.LENGTH_LONG).show()
            }
        })

        //TODO: Handle loading of URLs
        viewModel.votingLocationsUrl.observe(viewLifecycleOwner, Observer { url ->
            url?.let {
                loadUrl(url)
            }
        })
        viewModel.ballotInformationUrl.observe(viewLifecycleOwner, Observer { url ->
            url?.let {
                loadUrl(url)
            }
        })

        //TODO: Handle save button UI state
        //TODO: cont'd Handle save button clicks

        return binding.root

    }

    //TODO: Create method to load URL intents
    private fun loadUrl(url: String) {
        startActivity(Intent(ACTION_VIEW, Uri.parse(url)))
    }

    private fun showVoterInfo(binding: FragmentVoterInfoBinding) {
        binding.stateHeader.visibility = View.VISIBLE
        binding.stateLocations.visibility = View.VISIBLE
        binding.stateBallot.visibility = View.VISIBLE
        binding.followElectionButton.visibility = View.VISIBLE
    }

    private fun hideVoterInfo(binding: FragmentVoterInfoBinding) {
        binding.stateHeader.visibility = View.GONE
        binding.stateLocations.visibility = View.GONE
        binding.stateBallot.visibility = View.GONE
        binding.followElectionButton.visibility = View.GONE
    }

}