package com.abnamro.apps.referenceandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abnamro.apps.referenceandroid.model.Comment
import com.abnamro.apps.referenceandroid.network.NetworkResult
import com.abnamro.apps.referenceandroid.repository.CommentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CommentsUIState(
    val comments: List<Comment> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false
)

class CommentsViewModel(
    private val repository: CommentsRepository
) : ViewModel() {

    private val _commentsUIState = MutableStateFlow(CommentsUIState())
    val commentsUIState: StateFlow<CommentsUIState> = _commentsUIState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun getComments() {
        viewModelScope.launch(Dispatchers.IO) {
            clearComment()

            val result = repository.getComments()
            when (result) {

                is NetworkResult.Success -> {
                    _commentsUIState.update {
                        it.copy(
                            comments = result.data,
                            isLoading = false,
                        )
                    }
                    _uiEvent.emit(UIEvent.ShowSnackBar("Comments loaded!"))
                }

                is NetworkResult.Error -> {
                    _commentsUIState.update {
                        it.copy(
                            isLoading = false, error = result.message
                        )
                    }
                }
            }
        }
    }

    fun clearComment() {
        _commentsUIState.update {
            it.copy(
                isLoading = true,
                comments = emptyList()
            )
        }
    }
    sealed class UIEvent {
        data class ShowSnackBar(val message: String) : UIEvent()
    }
}
