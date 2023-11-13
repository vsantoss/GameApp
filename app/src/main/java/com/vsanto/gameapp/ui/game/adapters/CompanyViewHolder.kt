package com.vsanto.gameapp.ui.game.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vsanto.gameapp.R
import com.vsanto.gameapp.databinding.ItemCompanyBinding
import com.vsanto.gameapp.domain.model.InvolvedCompany

class CompanyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemCompanyBinding.bind(view)

    fun bind(company: InvolvedCompany, onItemSelected: (Int) -> Unit) {
        if (company.logo != null) {
            loadLogo(company.logo.url)
        } else {
            hideLogo()
        }

        binding.tvName.text = company.name
        loadJob(company)

        binding.root.setOnClickListener { onItemSelected(company.id) }
    }

    private fun hideLogo() {
        val cardView = binding.cvLogo
        val params = cardView.layoutParams
        params.height = 0
        cardView.layoutParams = params
    }

    private fun loadLogo(url: String) {
        Picasso.get().isLoggingEnabled = true
        Picasso.get().load(url).into(binding.ivLogo)
    }

    private fun loadJob(company: InvolvedCompany) {
        val jobs: MutableList<String> = ArrayList()
        if (company.developer) jobs.add(binding.tvJob.context.getString(R.string.developer))
        if (company.supporting) jobs.add(binding.tvJob.context.getString(R.string.supporter))
        if (company.publisher) jobs.add(binding.tvJob.context.getString(R.string.publisher))
        if (company.porting) jobs.add(binding.tvJob.context.getString(R.string.porter))

        binding.tvJob.text = jobs.joinToString(separator = ", ")
    }
}