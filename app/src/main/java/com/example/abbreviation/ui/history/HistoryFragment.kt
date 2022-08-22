package com.example.abbreviation.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.abbreviation.R
import com.example.abbreviation.data.room.LongFormEntity
import com.example.abbreviation.databinding.FragmentHistoryBinding
import com.example.abbreviation.util.observeOnce
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import com.lorentzos.flingswipe.SwipeFlingAdapterView.onFlingListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var arrayAdapter: ArrayAdapter<String>
    lateinit var data: MutableList<String?>
    private lateinit var flingAdapterView: SwipeFlingAdapterView
    private lateinit var detailData: List<LongFormEntity>
    lateinit var dataItem: LongFormEntity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater)

        flingAdapterView = binding.swipe1

        val searchHistoryViewModel =
            ViewModelProvider(this)[SearchHistoryViewModel::class.java]

        data = mutableListOf("")

        searchHistoryViewModel.getWordsFromDb()
        searchHistoryViewModel.readLongForm.observe(viewLifecycleOwner){database->
            if(database.isEmpty()){
                Toast.makeText(context,"No Search History", Toast.LENGTH_SHORT).show()
                data = mutableListOf("")
            }else{
                detailData=database
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
                Toast.makeText(requireContext(), "Left swipe", Toast.LENGTH_SHORT).show()
            }

            override fun onRightCardExit(o: Any) {
                Toast.makeText(requireContext(), "Right Swipe", Toast.LENGTH_SHORT).show()
            }

            override fun onAdapterAboutToEmpty(i: Int) {}
            override fun onScroll(v: Float) {}

        })

        flingAdapterView.setOnItemClickListener(SwipeFlingAdapterView.OnItemClickListener { itemPosition, dataObject ->
            //Toast.makeText(context,"Item clicked", Toast.LENGTH_LONG).show()
            //var word = dataObject
            //getDataObject(dataObject,detailData)
            dataItem = LongFormEntity(0, listOf(),"")
            var bundle = Bundle()
            bundle.putSerializable("word",getDataObject(dataObject,detailData))
            //bundle.putString("word", dataObject.toString())

            findNavController().navigate(R.id.action_histroyFragment_to_cardItemDetailFragment, bundle)
        })

        return binding.root


    }

    fun getDataObject(dataObject: Any?, detailData: List<LongFormEntity>): LongFormEntity {
        for(element in detailData){
            if(element.sf == dataObject.toString()){
                dataItem=element
                break
            }
        }
        return dataItem
    }


    private fun insertToArray(longFormEntity: List<LongFormEntity>) {
        for(element in longFormEntity){
            data.add(element.sf)
        }
    }

}