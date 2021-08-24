package com.jakobzeise.documentreader.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jakobzeise.documentreader.R
import kotlinx.android.synthetic.main.item_recycler_sections.view.*

class RecyclerAdapterSectionActivity(private var listOfSections: MutableList<Int>) :
    RecyclerView.Adapter<RecyclerAdapterSectionActivity.SectionViewHolder>() {




    class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_sections, parent, false)
        return RecyclerAdapterSectionActivity.SectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        holder.itemView.textViewSectionNumber.text = listOfSections[position].toString()

        holder.itemView.setOnClickListener {
            val intentOpenReadingActivity = Intent(holder.itemView.context, ReadingActivity::class.java)
            intentOpenReadingActivity.putExtra("fileName", fileName)
            intentOpenReadingActivity.putExtra("uri", uri.toString())
            intentOpenReadingActivity.putExtra("fileContent", fileContent)
            intentOpenReadingActivity.putExtra("section", holder.itemView.textViewSectionNumber.text)
            intentOpenReadingActivity.putStringArrayListExtra("sectionList", ArrayList(sectionList))
            holder.itemView.context.startActivity(intentOpenReadingActivity)
        }

        holder.itemView.textViewSectionNumber.setOnClickListener {
            val intentOpenReadingActivity = Intent(holder.itemView.context, ReadingActivity::class.java)
            intentOpenReadingActivity.putExtra("fileName", fileName)
            intentOpenReadingActivity.putExtra("uri", uri.toString())
            intentOpenReadingActivity.putExtra("fileContent", fileContent)
            intentOpenReadingActivity.putExtra("section", holder.itemView.textViewSectionNumber.text)
            intentOpenReadingActivity.putStringArrayListExtra("sectionList", ArrayList(sectionList))
            holder.itemView.context.startActivity(intentOpenReadingActivity)
        }
    }

    override fun getItemCount(): Int {
        return listOfSections.size
    }


}