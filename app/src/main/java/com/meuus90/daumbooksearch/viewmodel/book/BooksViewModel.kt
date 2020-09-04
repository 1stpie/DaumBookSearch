package com.meuus90.daumbooksearch.viewmodel.book

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.meuus90.base.util.customDebounce
import com.meuus90.daumbooksearch.data.model.book.BookSchema
import com.meuus90.daumbooksearch.data.repository.book.inDb.BooksRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BooksViewModel
@Inject
constructor(private val repository: BooksRepository) : ViewModel() {
    val org = MutableLiveData<BookSchema>()

    init {
        viewModelScope.launch {
            org.asFlow()
                .customDebounce(500L)
                .collect {
                    postBookSchema(it)
                }
        }
    }

    private val schemaLiveData = MutableLiveData<BookSchema>()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val books = schemaLiveData.asFlow()
        .distinctUntilChangedBy {
            Timber.e("BooksViewModel bookSchema distinctUntilChangedBy hash ${it.hashCode()}")
            it.hashCode()
        }
        .flatMapLatest {
            repository.execute(it)
        }

    fun postBookSchema(bookSchema: BookSchema) {
        schemaLiveData.value = bookSchema
    }

    fun postBookSchemaWithDebounce(bookSchema: BookSchema) {
        org.value = bookSchema
    }
}