package com.huiiro.ncn.component.tab.index.content.fn2CrowToken

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huiiro.ncn.domain.TokenEntity
import com.huiiro.ncn.domain.common.Response
import com.huiiro.ncn.http.repository.CrowRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TokenCrowViewModel : ViewModel() {

    private val _data = MutableStateFlow(Response<List<TokenEntity>>())
    val data: StateFlow<Response<List<TokenEntity>>> get() = _data

    fun loadData() {
        viewModelScope.launch {
            val result = CrowRepository.token()
            _data.emit(result)
        }
    }
}