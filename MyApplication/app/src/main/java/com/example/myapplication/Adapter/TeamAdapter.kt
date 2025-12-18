package com.example.myapplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.model.UserTeam

class TeamAdapter(
    private var teamList: List<UserTeam>,
    private val onItemClick: (UserTeam) -> Unit
) : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    // 1. Inflate the layout (item_team_member.xml)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.teamcard, parent, false)
        return TeamViewHolder(view)
    }

    // 2. Bind data to the views
    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val user = teamList[position]

        // Set Text
        holder.tvName.text = user.username
        holder.tvRole.text = user.role

        // Set Image (Handle null profile pictures)
        // Note: Change IP to match your local Laravel server
        if (user.profile_picture != null) {
            val fullUrl = "http://192.168.1.2:8000/storage/" + user.profile_picture

            Glide.with(holder.itemView.context)
                .load(fullUrl)
                .placeholder(R.drawable.ic_launcher_background) // Default if loading
                .into(holder.imgUser)
        } else {
            // If API returns null, show a default user icon
            holder.imgUser.setImageResource(R.drawable.ic_launcher_background)
        }

        holder.itemView.setOnClickListener {
            onItemClick(user)
        }
    }

    // 3. Return total count
    override fun getItemCount(): Int = teamList.size

    // 4. Helper function to update data from Activity
    fun updateData(newTeamList: List<UserTeam>) {
        teamList = newTeamList
        notifyDataSetChanged()
    }

    // 5. ViewHolder Class (Connects to IDs in XML)
    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgUser: ImageView = itemView.findViewById(R.id.Img_GambarUser)
        val tvName: TextView = itemView.findViewById(R.id.tv_NamaUser)
        val tvRole: TextView = itemView.findViewById(R.id.tv_role)
    }
}