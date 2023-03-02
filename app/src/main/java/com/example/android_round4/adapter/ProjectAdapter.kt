package com.example.android_round4.adapter;

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_round4.DetailActivity
import com.example.android_round4.R
import com.example.android_round4.entity.Project
import com.example.android_round4.entity.ProjectList

public class ProjectAdapter(val projectList: ProjectList, val fragment: Fragment) : RecyclerView.Adapter<ProjectAdapter.ViewHolder>()
{
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val title: TextView = view.findViewById(R.id.projectTitle)
        val image: ImageView = view.findViewById(R.id.projectImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_project,parent,false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener{
            val position = viewHolder.bindingAdapterPosition
            val project = projectList[position]
            val intent = Intent(view.context,DetailActivity::class.java)
            intent.putExtra("project.title",project.title)
            intent.putExtra("project.imageurl",project.imageurl)
            intent.putExtra("project.content",project.content)
            intent.putExtra("project.price",project.price)
            intent.putExtra("project.telephone",project.telephone)
            intent.putExtra("project.id",project.id)
            intent.putExtra("user.id",project.user_id)
            view.context.startActivity(intent)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val project = projectList[position]
        holder.title.text = project.title
        //holder.image.setImageResource(project.imageUrl)
        Glide.with(this.fragment).load(project.imageurl).into(holder.image)
        //Picasso.with(fragment.context).load(project.imageUrl).into(holder.image)
    }

    override fun getItemCount() = projectList.size
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}
