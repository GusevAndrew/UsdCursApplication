package ru.gusev.usdcursapplication.ui.curs_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.gusev.usdcursapplication.R
import ru.gusev.usdcursapplication.data.model.CursInfoModel
import ru.gusev.usdcursapplication.databinding.ItemCursLayoutBinding
import ru.gusev.usdcursapplication.utils.getDateInServerFormat

class CursAdapter(private val mDataSet: MutableList<CursInfoModel> = mutableListOf()): RecyclerView.Adapter<CursAdapter.CursViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CursViewHolder {
        return CursViewHolder(ItemCursLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CursViewHolder, position: Int) {
       holder.bind(mDataSet[position])
    }

    override fun getItemCount(): Int = mDataSet.size


    fun setNewData(newData: List<CursInfoModel>) {
        mDataSet.clear()
        if(newData.isNotEmpty()) {
            mDataSet.addAll(newData)
        }
        notifyDataSetChanged()
    }

    inner class CursViewHolder(private var binding: ItemCursLayoutBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(cursInfo: CursInfoModel) {
            binding.date.text = cursInfo.date.getDateInServerFormat()
            binding.usdValue.text = binding.root.context.getString(R.string.curs_list_format_usd, cursInfo.value)
        }
    }

}