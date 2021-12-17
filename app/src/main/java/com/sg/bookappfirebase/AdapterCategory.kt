package com.sg.bookappfirebase

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class AdapterCategory(
    val categoryArrayList: ArrayList<ModelCategory>
) : RecyclerView.Adapter<AdapterCategory.CateroryHolder>() {


    private var filterList = categoryArrayList
    private var filter: FilterCategory? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CateroryHolder {
        val view = LayoutInflater.from(parent?.context)
            .inflate(R.layout.row_category, parent, false)
        return CateroryHolder((view))
    }

    override fun onBindViewHolder(holder: CateroryHolder, position: Int) {
        holder?.bindCategory(categoryArrayList[position])
    }

    override fun getItemCount() = categoryArrayList.size

    inner class CateroryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryTv = itemView?.findViewById<TextView>(R.id.category_Tv_row_category)
        val deletBtn = itemView?.findViewById<ImageButton>(R.id.deleteBtn_row_category)

        fun bindCategory(category: ModelCategory) {
            categoryTv?.text = category.category

            /*deletBtn.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Delete")
                    .setMessage("Are you sure you want to delete this category?")
                    .setPositiveButton("Confirm") { a, d ->
                        Toast.makeText(context, "Deleting ...", Toast.LENGTH_LONG).show()
                        deletCategory(category)
                    }.setNegativeButton("Cancel") { a, d ->
                        a.dismiss()
                    }

                    .show()
            }*/

        }

        /*private fun deletCategory(model: ModelCategory) {
       val id = model.id
       val ref = FirebaseDatabase.getInstance().getReference("Categories")
       ref.child(id)
           .removeValue()
           .addOnSuccessListener {
               Toast.makeText(context, "Deleting ...", Toast.LENGTH_LONG).show()
           }
           .addOnFailureListener {
               Toast.makeText(
                   context,
                   "Unable to delete category ->${it.message} ...",
                   Toast.LENGTH_LONG
               ).show()
           }
   }*/


    }

    /* override fun getFilter(): Filter {
         if (filter==null){
             filter= FilterCategory(filterList,this)
         }
         return filter as FilterCategory
     }*/


}


/*
* class AdapterCategory(
    val categoryArrayList: ArrayList<ModelCategory>
) : RecyclerView.Adapter<AdapterCategory.CateroryHolder>(),Filterable {

    private lateinit var context: Context
    //private var filterList=ArrayList<ModelCategory>()

    private var filterList=categoryArrayList
    private var filter:FilterCategory?=null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CateroryHolder {
        context = parent.context
        val view =
            LayoutInflater.from(parent?.context).inflate(R.layout.row_category, parent, false)
        return CateroryHolder((view))
    }

    override fun onBindViewHolder(holder: CateroryHolder, position: Int) {
        holder?.bindCategory(categoryArrayList[position])
    }

    private fun deletCategory(model: ModelCategory) {
        val id = model.id
        val ref = FirebaseDatabase.getInstance().getReference("Categories")
        ref.child(id)
            .removeValue()
            .addOnSuccessListener {
                Toast.makeText(context, "Deleting ...", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(
                    context,
                    "Unable to delete category ->${it.message} ...",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    override fun getItemCount() = categoryArrayList.size

    inner class CateroryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryTv = itemView?.findViewById<TextView>(R.id.category_Tv_row_category)
        val deletBtn = itemView?.findViewById<ImageButton>(R.id.deleteBtn_row_category)

        fun bindCategory(category: ModelCategory) {
            categoryTv?.text = category.category

            deletBtn.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Delete")
                    .setMessage("Are you sure you want to delete this category?")
                    .setPositiveButton("Confirm") { a, d ->
                        Toast.makeText(context, "Deleting ...", Toast.LENGTH_LONG).show()
                        deletCategory(category)
                    }.setNegativeButton("Cancel") { a, d ->
                        a.dismiss()
                    }

                    .show()
            }

        }


    }

    override fun getFilter(): Filter {
        if (filter==null){
            filter= FilterCategory(filterList,this)
        }
        return filter as FilterCategory
    }




}*/


/*override fun getFilter(): Filter {
      if (filterList== null){
          val li=FilterCategory(filterList ,this)
          filterList=li
      }
      return filterList
  }*/

/*class CommentAdapter(
    private val comments: ArrayList<Comment>,
    val commentOptionListener: CommentsOptionClickListener
) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent?.context).inflate(R.layout.comment_list_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.bindComment(comments[position])
    }

    override fun getItemCount() = comments.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val commentUsername = itemView?.findViewById<TextView>(R.id.commentListUserName)
        val commentTimestamp = itemView?.findViewById<TextView>(R.id.commentListTimestap)
        val commentTxt = itemView?.findViewById<TextView>(R.id.commentListCommentText)
        val optionImage = itemView?.findViewById<ImageView>(R.id.commentOptionImage)

        fun bindComment(comment: Comment) {
            optionImage?.visibility = View.INVISIBLE
            commentUsername?.text = comment.username
            commentTimestamp?.text = comment.timestamp?.toDate().toString()
            commentTxt?.text = comment.commentTxt

            if (FirebaseAuth.getInstance().currentUser?.uid == comment.userID) {
                optionImage?.visibility = View.VISIBLE
            }

            optionImage?.setOnClickListener {
                        commentOptionListener.optionMenuClicked(comment)
            }

        }
    }
}
*/