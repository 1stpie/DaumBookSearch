package com.meuus90.daumbooksearch.presentation.book.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.meuus90.base.util.NumberTools
import com.meuus90.base.util.TimeTools
import com.meuus90.base.util.TimeTools.Companion.ISO8601
import com.meuus90.base.util.TimeTools.Companion.YMD
import com.meuus90.base.view.util.BaseViewHolder
import com.meuus90.daumbooksearch.R
import com.meuus90.daumbooksearch.data.model.book.BookDoc
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_book.view.*

class BookListAdapter(val doOnClick: (item: BookDoc, sharedView: View) -> Unit) :
    PagedListAdapter<BookDoc, BaseViewHolder<BookDoc>>(DIFF_CALLBACK) {
    companion object {
        private val PAYLOAD_TITLE = Any()

        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<BookDoc>() {
                override fun areItemsTheSame(oldItem: BookDoc, newItem: BookDoc): Boolean =
                    oldItem.isbn == newItem.isbn

                override fun areContentsTheSame(oldItem: BookDoc, newItem: BookDoc): Boolean =
                    oldItem == newItem

                override fun getChangePayload(oldItem: BookDoc, newItem: BookDoc): Any? {
                    return if (sameExceptTitle(oldItem, newItem)) PAYLOAD_TITLE
                    else null
                }
            }

        private fun sameExceptTitle(
            oldItem: BookDoc,
            newItem: BookDoc
        ): Boolean {
            return oldItem.copy(isbn = newItem.isbn) == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<BookDoc> {
        val inflater = LayoutInflater.from(parent.context.applicationContext)
        val view = inflater.inflate(R.layout.item_book, parent, false)
        return BookItemHolder(view, this)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<BookDoc>,
        position: Int
    ) {
        val item = getItem(position)

        item?.let {
            if (holder is BookItemHolder) {
                holder.bindItemHolder(holder, it, position)
            }
        }
    }

    class BookItemHolder(
        override val containerView: View,
        private val adapter: BookListAdapter
    ) : BaseViewHolder<BookDoc>(containerView), LayoutContainer {
        @SuppressLint("SetTextI18n")
        override fun bindItemHolder(
            holder: BaseViewHolder<BookDoc>,
            item: BookDoc,
            position: Int
        ) {
            containerView.apply {
                Glide.with(context).asDrawable().clone()
                    .load(item.thumbnail)
                    .centerCrop()
                    .dontAnimate()
                    .error(R.drawable.ic_b)
                    .into(iv_thumbnail)

                iv_thumbnail.transitionName = position.toString()

                tv_title.text = item.title

                tv_author.text = item.authors.joinToString()
                tv_publisher.text = item.publisher
                tv_date.text = TimeTools.convertDateFormat(item.datetime, ISO8601, YMD)
                tv_price.text = NumberTools.convertToString(item.price.toBigDecimal())
                tv_status.text = item.status

                v_root.setOnClickListener {
                    val positioned = item
                    positioned.position = position
                    adapter.doOnClick(item, iv_thumbnail)
                }
            }
        }

        override fun onItemSelected() {
            containerView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            containerView.setBackgroundColor(0)
        }
    }
}