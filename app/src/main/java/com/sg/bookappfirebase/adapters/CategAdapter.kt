package com.sg.bookappfirebase.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sg.bookappfirebase.R
import com.sg.bookappfirebase.interfaces.CategoryInterface
import com.sg.bookappfirebase.model.Categ

class CategAdapter(
    val categoryArrayList: ArrayList<Categ>, val categoryInterface: CategoryInterface
    ) : RecyclerView.Adapter<CategAdapter.CategoryHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
            val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.row_category, parent, false)
            return CategoryHolder((view))
        }

        override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
            holder?.bindCategory(categoryArrayList[position])
        }

        override fun getItemCount() = categoryArrayList.size

        inner class CategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val categoryTv = itemView?.findViewById<TextView>(R.id.category_Tv_row_category)
            val deleteBtn = itemView?.findViewById<ImageButton>(R.id.deleteBtn_row_category)

            fun bindCategory(category: Categ) {
                categoryTv?.text = category.categoryName
                deleteBtn.setOnClickListener {
                    categoryInterface.categoryInterfaceListenet(category)
                }
            }
        }







}