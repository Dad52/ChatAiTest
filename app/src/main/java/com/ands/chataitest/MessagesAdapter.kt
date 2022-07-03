package com.ands.chataitest

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.ands.chataitest.databinding.BotMessageItemBinding
import com.ands.chataitest.databinding.UserMessageItemBinding

/**
 * Created by Dad52(Sobolev) on 7/2/2022.
 */
class MessagesAdapter: ListAdapter<Message, MessagesAdapter.BaseViewHolder>(ItemComparator()) {

    abstract class BaseViewHolder(binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            messageText().text = message.message
        }
        abstract fun messageText(): TextView
    }

    class ItemComparator: DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.message == newItem.message
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }

    }

    class MessagesUserViewHolder(private val binding: UserMessageItemBinding): BaseViewHolder(binding) {
        override fun messageText() = binding.message
    }

    class MessagesBotViewHolder(private val binding: BotMessageItemBinding): BaseViewHolder(binding) {
        override fun messageText() = binding.message
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        return when(viewType) {
            Message.USER_ID -> MessagesUserViewHolder(UserMessageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
            Message.BOT_ID -> MessagesBotViewHolder(BotMessageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
            else -> throw IllegalArgumentException("argument is not found")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {

        val item = getItem(position)

        if (item.user == Message.USER_ID) return Message.USER_ID
        if (item.user == Message.BOT_ID) return Message.BOT_ID

        return super.getItemViewType(position)
    }

}