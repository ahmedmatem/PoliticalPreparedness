package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener
import com.example.android.politicalpreparedness.network.models.Election

class ElectionsFragment : Fragment() {

    //TODO: Declare ViewModel
    private lateinit var viewModel: ElectionsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //TODO: Add ViewModel values and create ViewModel
        viewModel = ViewModelProvider(this, ElectionsViewModel.Factory(requireContext())).get(
            ElectionsViewModel::class.java
        )

        //TODO: Add binding values
        val binding = FragmentElectionBinding.inflate(inflater)
        binding.lifecycleOwner = this

        //TODO: Link elections to voter info
        viewModel.navigateToSelectedElectionDetails.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(
                    ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(
                        it.id,
                        it.division
                    )
                )
            }
        })

        //TODO: Initiate recycler adapters
        val adapter = ElectionListAdapter(ElectionListener {
            it?.let {
                viewModel.displaySelectedElectionInfo(it)
            }
        })
        binding.upcomingElectionsRecycler.adapter = adapter

        //TODO: Populate recycler adapters
        viewModel.upcomingElections.observe(viewLifecycleOwner, Observer<List<Election>> {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root

    }

    //TODO: Refresh adapters when fragment loads

}