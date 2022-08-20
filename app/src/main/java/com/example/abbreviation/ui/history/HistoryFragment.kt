package com.example.abbreviation.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.abbreviation.R
import com.example.abbreviation.data.room.LongFormEntity
import com.example.abbreviation.databinding.FragmentHistoryBinding
import com.example.abbreviation.ui.search.SearchViewModel
import com.example.abbreviation.util.observeOnce
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import com.lorentzos.flingswipe.SwipeFlingAdapterView.onFlingListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var arrayAdapter: ArrayAdapter<String>
    lateinit var data: MutableList<String>
    lateinit var flingAdapterView: SwipeFlingAdapterView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(layoutInflater)

        flingAdapterView = binding.swipe1

        val searchHistoryViewModel =
            ViewModelProvider(this)[SearchHistoryViewModel::class.java]

        data = mutableListOf("")

        searchHistoryViewModel.getWordsFromDb()
        searchHistoryViewModel.readLongForm.observe(viewLifecycleOwner){database->
            if(database.isEmpty()){
                data = mutableListOf("java","python","php","html","kotlin")
            }else{
                insertToArray(database)
            }
        }


        arrayAdapter = ArrayAdapter(requireContext(), R.layout.card_items, R.id.data, data)
        flingAdapterView.adapter = arrayAdapter

        flingAdapterView.setFlingListener(object : onFlingListener {
            override fun removeFirstObjectInAdapter() {
                data.add(data[0])
                data.removeAt(0)
                arrayAdapter.notifyDataSetChanged()
            }

            override fun onLeftCardExit(o: Any) {
                Toast.makeText(requireContext(), "Left swipe", Toast.LENGTH_LONG).show()
            }

            override fun onRightCardExit(o: Any) {
                Toast.makeText(requireContext(), "Right Swipe", Toast.LENGTH_LONG).show()
            }

            override fun onAdapterAboutToEmpty(i: Int) {}
            override fun onScroll(v: Float) {}

        })

       // flingAdapterView.setOnItemClickListener(SwipeFlingAdapterView.OnItemClickListener())


        return binding.root


    }

    private fun insertToArray(longFormEntity: List<LongFormEntity>) {
        for(element in longFormEntity){
            data.add(element.sf)
        }
    }

}