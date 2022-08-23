package com.example.abbreviation.ui.history

import com.example.abbreviation.data.room.LongFormEntity
import org.junit.Assert.*
import org.junit.Test

class HistoryFragmentTest{

    val historyFragment = HistoryFragment()

    @Test
    fun `get data object`(){
        val dataObject = LongFormEntity("test", listOf())
        val detailData = listOf<LongFormEntity>(LongFormEntity("test", listOf()),LongFormEntity("sample", listOf()))
        val result = historyFragment.getDataObject(dataObject.sf,detailData)
        assertEquals(result,dataObject)
    }

}