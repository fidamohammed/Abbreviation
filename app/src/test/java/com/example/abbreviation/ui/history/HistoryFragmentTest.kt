package com.example.abbreviation.ui.history

import com.example.abbreviation.data.room.LongFormEntity
import org.junit.Assert.*
import org.junit.Test

class HistoryFragmentTest{

    val historyFragment = HistoryFragment()

    @Test
    fun `get data object`(){
        val dataObject = LongFormEntity(0, listOf(),"test")
        val detailData = listOf<LongFormEntity>(LongFormEntity(0, listOf(),"test"),LongFormEntity(1, listOf(),"sample"))
        val result = historyFragment.getDataObject(dataObject.sf,detailData)
        assertEquals(result,dataObject)
    }

}