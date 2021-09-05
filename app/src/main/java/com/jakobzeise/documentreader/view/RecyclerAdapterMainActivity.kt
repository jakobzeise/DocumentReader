package com.jakobzeise.documentreader.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jakobzeise.documentreader.R
import com.jakobzeise.documentreader.modell.Projects
import kotlinx.android.synthetic.main.item_recycler.view.*

class RecyclerAdapterMainActivity(
    private var listOfProjects: MutableList<Projects>,
    private var deleteListener: DeleteInterface
) :
    RecyclerView.Adapter<RecyclerAdapterMainActivity.ProjectViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return ProjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        //Gets the current item
        val itemProject = listOfProjects.elementAt(position)

        //Sets the ProjectName
        holder.itemView.textViewProjectNameItemRecycler.text = itemProject.fileName

        holder.itemView.itemRecyclerLayout.setOnClickListener {
            val intentOpenSectionActivity =
                Intent(holder.itemView.context, SectionActivity::class.java)

            intentOpenSectionActivity.putExtra(
                "projectNumber", position
            )
            intentOpenSectionActivity.putExtra(
                "fileName", listOfProjects[position].fileName
            )
            intentOpenSectionActivity.putExtra(
                "fileContent", listOfProjects[position].fileContent
            )


            holder.itemView.context.startActivity(intentOpenSectionActivity)
        }

        holder.itemView.imageViewDeleteButton.setOnClickListener {
            listOfProjects.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return listOfProjects.size
    }

    inner class ProjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.imageViewDeleteButton.setOnClickListener {
                deleteListener.deleteItem(adapterPosition)
            }
        }
    }

    interface DeleteInterface {
        fun deleteItem(position: Int)
    }


}


