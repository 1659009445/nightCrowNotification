package com.huiiro.ncn.component.tab.index.content.fn3DigitalToken

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huiiro.ncn.domain.WemixEntity
import com.huiiro.ncn.domain.common.Response
import com.huiiro.ncn.http.repository.CrowRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DigitalTokenViewModel : ViewModel() {

    private val _data = MutableStateFlow(Response<WemixEntity>())
    val data: StateFlow<Response<WemixEntity>> get() = _data

    fun loadData() {
        viewModelScope.launch {
            val result = CrowRepository.wemix()
            _data.emit(result)
        }
    }
}