package com.jakobzeise.documentreader.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jakobzeise.documentreader.R
import com.jakobzeise.documentreader.modell.Projects
import kotlinx.android.synthetic.main.item_recycler.view.*


class RecyclerAdapter(private var listOfProjects: MutableList<Projects>) :
    RecyclerView.Adapter<RecyclerAdapter.ProjectViewHolder>() {

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
            val intentOpenReadingActivity = Intent(holder.itemView.context, ReadingActivity::class.java)
            intentOpenReadingActivity.putExtra("fileName", fileName)
            intentOpenReadingActivity.putExtra("uri", uri.toString())
            intentOpenReadingActivity.putExtra("fileContent", fileContent)
            holder.itemView.context.startActivity(intentOpenReadingActivity)
        }

        holder.itemView.textViewProjectNameItemRecycler.setOnClickListener {
            val intentOpenReadingActivity = Intent(holder.itemView.context, ReadingActivity::class.java)
            intentOpenReadingActivity.putExtra("fileName", fileName)
            intentOpenReadingActivity.putExtra("uri", uri.toString())
            intentOpenReadingActivity.putExtra("fileContent", fileContent)
            holder.itemView.context.startActivity(intentOpenReadingActivity)
        }

    }

    override fun getItemCount(): Int {
        return listOfProjects.size
    }



    class ProjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}