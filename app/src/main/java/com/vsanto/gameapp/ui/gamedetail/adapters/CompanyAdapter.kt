package com.vsanto.gameapp.ui.gamedetail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vsanto.gameapp.R
import com.vsanto.gameapp.domain.model.InvolvedCompany

class CompanyAdapter(
    private var companies: List<InvolvedCompany> = emptyList(),
    private val onItemSelected: (Int) -> Unit
) : RecyclerView.Adapter<CompanyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        return CompanyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_company, parent, false)
        )
    }

    override fun getItemCount() = companies.size

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.bind(companies[position], onItemSelected)
    }


}