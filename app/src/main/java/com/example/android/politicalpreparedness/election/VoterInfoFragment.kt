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
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.example.android.politicalpreparedness.utils.exts.setDisplayHomeAsUpEnabled
import com.example.android.politicalpreparedness.utils.exts.setTitle

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
            this, VoterInfoViewModel.Factory(
                args, ElectionDatabase.getInstance(requireContext()).electionDao
            )
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
            displayVoterInfo(hasInfo, binding)
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

    private fun displayVoterInfo(hasInfo: Boolean, binding: FragmentVoterInfoBinding) {
        if(hasInfo) {
            binding.addressGroup.visibility = View.VISIBLE
        } else {
            binding.addressGroup.visibility = View.GONE
        }
    }

}