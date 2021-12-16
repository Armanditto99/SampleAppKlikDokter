package com.example.sampleappkd.adapter.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sampleappkd.R
import com.example.sampleappkd.base.AuthHelper
import com.example.sampleappkd.model.Doctor
import kotlinx.android.synthetic.main.adapter_doctor_item.view.*


class DoctorItemAdapter : RecyclerView.Adapter<DoctorItemAdapter.DoctorViewHolder>() {

    inner class DoctorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private var onItemClickListener: ((Doctor) -> Unit)? = null

    var onDeleteIconClickListener: ((Doctor) -> Unit)? = null

    private val differCallback = object : DiffUtil.ItemCallback<Doctor>() {
        override fun areItemsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        return DoctorViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.adapter_doctor_item,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = differ.currentList[position]
        holder.itemView.apply {
            txt_doctor_name.text = doctor.doctor
            txt_available_time.text = doctor.availableUntil
            txt_doctor_fee.text = doctor.consultationPrice
            txt_bitcoin_address.text = doctor.btcAddress
            Glide.with(this).load(doctor.avatar).into(img_avatar)

            expanded_view.visibility = if (doctor.isExpanded) View.VISIBLE else View.GONE
            img_arrow_up.visibility = if (doctor.isExpanded) View.VISIBLE else View.GONE
            img_arrow_down.visibility = if (doctor.isExpanded) View.GONE else View.VISIBLE

            btn_delete.setOnClickListener {
                onDeleteIconClickListener?.invoke(doctor)
                Toast.makeText(context, "ID: ${doctor.id}", Toast.LENGTH_SHORT).show()
            }

            setOnClickListener {
                onItemClickListener?.let { it(doctor) }
                Toast.makeText(context, "ID: ${doctor.id}", Toast.LENGTH_SHORT).show()
                doctor.isExpanded = !doctor.isExpanded
                notifyDataSetChanged()
            }
        }
    }
}