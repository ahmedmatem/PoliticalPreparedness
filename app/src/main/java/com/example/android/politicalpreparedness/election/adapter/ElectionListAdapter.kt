package com.example.android.politicalpreparedness.election.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.ListItemElectionBinding
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.utils.exts.DefaultDateFormat
import com.example.android.politicalpreparedness.utils.exts.dateToString

class ElectionListAdapter(private val clickListener: ElectionListener) :
    ListAdapter<Election, ElectionListAdapter.ElectionViewHolder>(ElectionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        return ElectionViewHolder.from(parent)
    }

    //TODO: Bind ViewHolder
    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
        val election = getItem(position)
        holder.bind(election, clickListener)
    }

    //TODO: Create ElectionViewHolder
    //TODO: Add companion object to inflate ViewHolder (from)
    class ElectionViewHolder private constructor(val binding: ListItemElectionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(election: Election, listener: ElectionListener) {
            binding.electionName.text = election.name
            binding.electionDate.text = election.electionDay.dateToString(DefaultDateFormat)
        }

        companion object {
            fun from(parent: ViewGroup): ElectionViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemElectionBinding.inflate(layoutInflater, parent, false)
                return ElectionViewHolder(binding)
            }
        }
    }
}

//TODO: Create ElectionDiffCallback

//TODO: Create ElectionListener
class ElectionListener(val clickListener: (electionId: Int) -> Unit) {
    fun onClick(election: Election) = clickListener(election.id)
}