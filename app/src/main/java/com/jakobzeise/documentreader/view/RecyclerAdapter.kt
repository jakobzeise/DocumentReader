package com.jakobzeise.documentreader.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jakobzeise.documentreader.modell.Projects


class RecyclerAdapter(private var listOfProjects: MutableList<Projects>) :
    RecyclerView.Adapter<RecyclerAdapter.ProjectViewHolder>(){

class ProjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return listOfProjects.size
    }
}