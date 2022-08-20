package com.example.abbreviation.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.abbreviation.R
import com.example.abbreviation.data.model.LfModel
import com.example.abbreviation.data.model.LongFormItemModel
import com.example.abbreviation.databinding.ActivityMainBinding
import com.example.abbreviation.databinding.FragmentSearchBinding
import com.example.abbreviation.util.UiState
import com.example.abbreviation.util.observeOnce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)

        val searchViewModel by lazy{
            ViewModelProvider(this).get(SearchViewModel::class.java)
        }


        recyclerView = binding.rvSearchFragment
        recyclerView.layoutManager = LinearLayoutManager(context)


        val sfRadioButton = binding.sfRadioButton
        val lfRadioButton = binding.lfRadioButton
        val searchButton = binding.searchButton

        searchButton.setOnClickListener{
            val searchTerm = binding.searchTerm.text.toString()
            if(searchTerm.isNullOrBlank()){
                Toast.makeText(context,"Enter search term",Toast.LENGTH_LONG).show()
                recyclerView.adapter = SearchAdapter("", listOf())
            }else{
                searchViewModel.getMeaningsFromDb(searchTerm)
                searchViewModel.readLongForm.observeOnce(viewLifecycleOwner){ database ->
                    if(database.isEmpty()){

                        if(searchViewModel.hasInternetConnection()){
                            searchViewModel.getMeaningsfromApi(searchTerm)
                            searchViewModel.meaningLiveData.observe(viewLifecycleOwner) { state ->
                                when (state) {
                                    is UiState.Loading -> {
                                        Log.d("Meaning", "Loading")
                                    }
                                    is UiState.Error -> {
                                        Log.d("Meaning", "Error -> ${state.error.message}")
                                        Toast.makeText(context,"Invalid search term",Toast.LENGTH_LONG).show()
                                        updateUi(LongFormItemModel(listOf(),""))
                                    }
                                    is UiState.Success<*> -> {
                                        var result = state.meaningResponse as ArrayList<LongFormItemModel>
                                        Log.d("SearchResult","Data from Api -> $result")
                                        for(res in result){
                                            updateUi(res)
                                        }
                                    }
                                }
                            }
                        }else{
                            Toast.makeText(context, "No Internet connection and no database content", Toast.LENGTH_LONG).show()
                            recyclerView.adapter = SearchAdapter("", listOf())
                        }
                    }else{updateUi(LongFormItemModel(database[0].lfs,database[0].sf))
                        Log.d("SearchResult", "Data from Db -> ${database[0]}")
                    }
                }
            }
        }
        return binding.root
    }

    private fun updateUi(longFormItemModel: LongFormItemModel) {
        val sf = longFormItemModel.sf
        val lfs = longFormItemModel.lfs
        recyclerView.adapter = SearchAdapter(sf,lfs)
    }


}