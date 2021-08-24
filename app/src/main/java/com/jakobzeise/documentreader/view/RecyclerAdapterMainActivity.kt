package com.jakobzeise.documentreader.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jakobzeise.documentreader.R
import com.jakobzeise.documentreader.modell.Projects
import kotlinx.android.synthetic.main.item_recycler.view.*


class RecyclerAdapterMainActivity(private var listOfProjects: MutableList<Projects>) :
    RecyclerView.Adapter<RecyclerAdapterMainActivity.ProjectViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return ProjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        //Gets the current item
        val itemProject = listOfProjects[position]

        //Sets the ProjectName
        holder.itemView.textViewProjectNameItemRecycler.text = itemProject.fileName

        holder.itemView.itemRecyclerLayout.setOnClickListener {
            val intentOpenSectionActivity =
                Intent(holder.itemView.context, SectionActivity::class.java)
            intentOpenSectionActivity.putExtra("fileName", fileName)
            intentOpenSectionActivity.putExtra("uri", uri.toString())
            intentOpenSectionActivity.putExtra("fileContent", fileContent)
            intentOpenSectionActivity.putStringArrayListExtra("sectionList", ArrayList(sectionList))
            holder.itemView.context.startActivity(intentOpenSectionActivity)
        }

        holder.itemView.textViewProjectNameItemRecycler.setOnClickListener {
            val intentOpenSectionActivity =
                Intent(holder.itemView.context, SectionActivity::class.java)
            intentOpenSectionActivity.putExtra("fileName", fileName)
            intentOpenSectionActivity.putExtra("uri", uri.toString())
            intentOpenSectionActivity.putExtra("fileContent", fileContent)
            intentOpenSectionActivity.putStringArrayListExtra("sectionList", ArrayList(sectionList))
            holder.itemView.context.startActivity(intentOpenSectionActivity)
        }
    }

    override fun getItemCount(): Int {
        return listOfProjects.size
    }

    class ProjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}