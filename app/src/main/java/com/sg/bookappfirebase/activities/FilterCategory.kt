package com.sg.bookappfirebase.activities

import android.widget.Filter
import com.sg.bookappfirebase.model.ModelCategory
import com.sg.bookappfirebase.adapters.AdapterCategory

class FilterCategory(val filterList:ArrayList<ModelCategory>, var adapterCategory: AdapterCategory):Filter(){

    override fun performFiltering(p0: CharSequence?): FilterResults {
        var constrain=p0
        val result= FilterResults()

        val filterModels:ArrayList<ModelCategory> = ArrayList()


        if (constrain!=null && constrain.isNotEmpty()){

            constrain=constrain.toString().uppercase()

            for (index in 0 until filterList.size){
                if (filterList[index].category.uppercase().contains(constrain)){
                    filterModels.add(filterList[index])
                }
            }
            result.count=filterModels.size
            result.values=filterModels
        }
        else{
            result.count=filterList.size
            result.values=filterList
        }
        return result
    }

    override fun publishResults(constrain: CharSequence?, result: FilterResults?) {

        val list= result?.values as ArrayList<ModelCategory>
        adapterCategory= AdapterCategory(list)
        adapterCategory.notifyDataSetChanged()
    }





}






/*
class FilterCategory:Filter{
    private var filterList:ArrayList<ModelCategory>
     var adapterCategory:AdapterCategory

    constructor(filterList: ArrayList<ModelCategory>, adapterCategory: AdapterCategory) : super() {
        this.filterList = filterList
        this.adapterCategory = adapterCategory
    }

    override fun performFiltering(p0: CharSequence?): FilterResults {
        var constrain=p0
        val result= FilterResults()
        if (constrain!=null && constrain.isNotEmpty()){

            constrain=constrain.toString().uppercase()
            val filterModels:ArrayList<ModelCategory> = ArrayList()
            for (i in 0 until filterList.size){
                if (filterList[i].category.uppercase().contains(constrain)){
                    filterModels.add(filterList[i])
                }
            }
            result.count=filterModels.size
            result.values=filterModels
        }
        else{
            result.count=filterList.size
            result.values=filterList
        }
        return result
    }

    override fun publishResults(constrain: CharSequence?, result: FilterResults?) {
        val list= result?.values as ArrayList<ModelCategory>
        adapterCategory= AdapterCategory(list)
        adapterCategory.notifyDataSetChanged()
    }


}*/
