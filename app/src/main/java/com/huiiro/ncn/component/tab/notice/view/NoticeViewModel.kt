package com.huiiro.ncn.component.tab.notice.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huiiro.ncn.domain.NoticeEntity
import com.huiiro.ncn.domain.common.Response
import com.huiiro.ncn.http.repository.CrowRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoticeViewModel : ViewModel() {

    private val _data = MutableStateFlow(Response<List<NoticeEntity>>())
    val data: StateFlow<Response<List<NoticeEntity>>> get() = _data

    fun loadData() {
        viewModelScope.launch {
            val result = CrowRepository.notice()
            _data.emit(result)
        }
    }
}