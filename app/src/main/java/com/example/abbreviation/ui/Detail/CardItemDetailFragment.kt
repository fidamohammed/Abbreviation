package com.example.abbreviation.ui.Detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.abbreviation.R
import com.example.abbreviation.data.room.LongFormEntity
import com.example.abbreviation.databinding.FragmentCardItemDetailBinding
import com.example.abbreviation.ui.history.SearchHistoryViewModel
import com.example.abbreviation.ui.search.SearchAdapter
import com.example.abbreviation.ui.search.SearchViewModel
import com.example.abbreviation.util.observeOnce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardItemDetailFragment : Fragment() {

    private lateinit var binding: FragmentCardItemDetailBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCardItemDetailBinding.inflate(inflater)

        recyclerView = binding.rvSearchFragment
        recyclerView.layoutManager = LinearLayoutManager(context)

        val viewModel =
            ViewModelProvider(this)[SearchViewModel::class.java]


        val word = requireArguments().get("word") as LongFormEntity
        recyclerView.adapter = SearchAdapter(word.sf,word.lfs)

        //val word = requireArguments().get("word").toString()
        /*viewModel.getMeaningsFromDb(word)
        viewModel.readLongForm.observeOnce(viewLifecycleOwner){database->
            if(database.isEmpty()){

            }else{
                recyclerView.adapter = SearchAdapter(database[0].sf,database[0].lfs)
            }
        }*/

        Log.d("Detail", "$word")
       // binding.tvShortForm.text = word


        return binding.root
    }


}