package com.example.footballapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.footballapp.data.entity.Team
import com.example.footballapp.databinding.RecyclerViewItemBinding

class RecyclerAdapter(
    private val teams: List<Team>,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerViewItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemView.setOnClickListener {
            onClickListener.onItemClick(teams[i])
        }

        viewHolder.view.name.text = teams[i].name

        Glide.with(viewHolder.itemView.context)
            .load(teams[i].emblem)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_baseline_image_24)
                    .error(R.drawable.ic_baseline_image_not_supported_24)
            )
            .into(viewHolder.view.emblem)
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    class ViewHolder(val view: RecyclerViewItemBinding) : RecyclerView.ViewHolder(view.root)

    class OnClickListener(val clickListener: (team: Team) -> Unit) {
        fun onItemClick(team: Team) = clickListener(team)
    }
}
