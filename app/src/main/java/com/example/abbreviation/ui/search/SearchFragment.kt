package com.example.abbreviation.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.abbreviation.R
import com.example.abbreviation.data.model.LongFormItemModel
import com.example.abbreviation.databinding.FragmentSearchBinding
import com.example.abbreviation.util.UiState
import com.example.abbreviation.util.checkForInternet
import com.example.abbreviation.util.observeOnce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchBinding.bind(view)

        val searchViewModel by lazy{
            ViewModelProvider(this).get(SearchViewModel::class.java)
        }

        recyclerView = binding.rvSearchFragment
        recyclerView.layoutManager = LinearLayoutManager(context)

        val searchView = binding.svSearchFragment

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val searchTerm = searchView.query.toString()
                if(searchTerm.isNullOrBlank()){
                    Toast.makeText(context,"Enter search term",Toast.LENGTH_LONG).show()
                    recyclerView.adapter = SearchAdapter("", listOf())
                }else{
                    searchViewModel.getMeaningsFromDb(searchTerm)
                    searchViewModel.readLongForm.observeOnce(viewLifecycleOwner){ database ->
                        if(database.isEmpty()){
                            if(checkForInternet(requireContext())){
                                searchViewModel.getMeaningsfromApi(searchTerm)
                                searchViewModel.meaningLiveData.observe(viewLifecycleOwner) { state ->
                                    when (state) {
                                        is UiState.Loading -> {
                                            Log.d("SearchResult", "Loading")
                                        }
                                        is UiState.Error -> {
                                            Log.d("SearchResult", "Error -> ${state.error.message}")
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
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                recyclerView.adapter = SearchAdapter("", listOf())
                return false
            }
        })

    }

  /*  override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


       // val searchButton = binding.searchButton

    /*    searchButton.setOnClickListener{
            val searchTerm = binding.searchTerm.text.toString()
            if(searchTerm.isNullOrBlank()){
                Toast.makeText(context,"Enter search term",Toast.LENGTH_LONG).show()
                recyclerView.adapter = SearchAdapter("", listOf())
            }else{
                searchViewModel.getMeaningsFromDb(searchTerm)
                searchViewModel.readLongForm.observeOnce(viewLifecycleOwner){ database ->
                    if(database.isEmpty()){
                        if(checkForInternet(requireContext())){
                            searchViewModel.getMeaningsfromApi(searchTerm)
                            searchViewModel.meaningLiveData.observe(viewLifecycleOwner) { state ->
                                when (state) {
                                    is UiState.Loading -> {
                                        Log.d("SearchResult", "Loading")
                                    }
                                    is UiState.Error -> {
                                        Log.d("SearchResult", "Error -> ${state.error.message}")
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
        }  */
        return binding.root
    }*/

    private fun updateUi(longFormItemModel: LongFormItemModel) {
        val sf = longFormItemModel.sf
        val lfs = longFormItemModel.lfs
        recyclerView.adapter = SearchAdapter(sf,lfs)
    }



}