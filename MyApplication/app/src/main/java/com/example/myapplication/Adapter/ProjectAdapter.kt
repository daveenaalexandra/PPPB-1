package com.example.myapplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.model.ProjectData

class ProjectAdapter(
    private var projectList: List<ProjectData>,
    private val onItemClick: (ProjectData) -> Unit
) :
    RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProjectViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_project, parent, false)

        return ProjectViewHolder(view)

    }

    override fun onBindViewHolder(
        holder: ProjectViewHolder,
        position: Int
    ) {
        val project: ProjectData = projectList[position]


        holder.tvProjectName.text = project.title

        val imageurl = "http://192.168.1.2:8000/storage/" + project?.cover_image_url

        Glide.with(holder.itemView.context)
            .load(imageurl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(android.R.drawable.ic_dialog_alert)
            .into(holder.imgProject)

        holder.itemView.setOnClickListener {
            onItemClick(project)
        }

    }

    override fun getItemCount(): Int {
        return projectList.size
    }

    fun updateData(newProjectList: List<ProjectData>) {
        projectList = newProjectList
        notifyDataSetChanged()
    }



    class ProjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProject: ImageView = itemView.findViewById(R.id.imgProject)
        val tvProjectName: TextView = itemView.findViewById(R.id.tvProjectName)

    }

    }