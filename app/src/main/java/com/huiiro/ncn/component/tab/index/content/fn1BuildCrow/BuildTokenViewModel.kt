package com.huiiro.ncn.component.tab.index.content.fn1BuildCrow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huiiro.ncn.domain.CrowEntity
import com.huiiro.ncn.domain.common.Response
import com.huiiro.ncn.http.repository.CrowRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BuildTokenViewModel : ViewModel() {

    private val _data = MutableStateFlow(Response<CrowEntity>())
    val data: StateFlow<Response<CrowEntity>> get() = _data

    fun loadData() {
        viewModelScope.launch {
            val result = CrowRepository.crow()
            _data.emit(result)
        }
    }
}