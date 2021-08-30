package com.jakobzeise.documentreader.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jakobzeise.documentreader.R
import com.jakobzeise.documentreader.modell.Projects
import kotlinx.android.synthetic.main.item_recycler.view.*


class RecyclerAdapterMainActivity(private var listOfProjects: MutableSet<Projects>) :
    RecyclerView.Adapter<RecyclerAdapterMainActivity.ProjectViewHolder>() {

    private var listener: BookReader? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return ProjectViewHolder(view)
    }

    fun initaliseListener(listener: BookReader) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        //Gets the current item
        val itemProject = listOfProjects.elementAt(position)

        //Sets the ProjectName
        holder.itemView.textViewProjectNameItemRecycler.text = itemProject.fileName

        holder.itemView.itemRecyclerLayout.setOnClickListener {
            val intentOpenSectionActivity =
                Intent(holder.itemView.context, SectionActivity::class.java)
            intentOpenSectionActivity.putExtra("projectNumberKey", "projectNumber$position")
            intentOpenSectionActivity.putExtra("fileNameKey", "fileName$position")
            intentOpenSectionActivity.putExtra("fileContentKey", "fileContent$position")
            intentOpenSectionActivity.putExtra("projectNumber$position", position)
            intentOpenSectionActivity.putExtra("fileName$position", fileNames.elementAt(position))
            intentOpenSectionActivity.putExtra(
                "fileContent$position", fileContents.elementAt(position)
            )


            holder.itemView.context.startActivity(intentOpenSectionActivity)
        }
        holder.itemView.imageViewDeleteButton.setOnClickListener {
            Log.d(TAG, "onBindViewHolder: fileName: ${fileNames.elementAt(position)}")
            fileNames.remove(fileName)
            fileContents.remove(fileContent)
            val mainActivity = MainActivity()
            val sharedPreferences: SharedPreferences? =
                mainActivity.getSharedPreferences("documentReader", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
            editor?.clear()
            editor?.putStringSet("fileNames", fileNames.toMutableSet())
            editor?.putStringSet("fileContents", fileContents.toMutableSet())
            editor?.apply()
            notifyItemChanged(position)
        }

    }

    override fun getItemCount(): Int {
        return listOfProjects.size
    }

    class ProjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

interface BookReader {
    fun readBook(fileName1: String, fileContent1: String, projectNumber1: Int)
}