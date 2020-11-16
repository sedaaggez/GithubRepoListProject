package com.info.githubrepo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.info.githubrepo.R
import com.info.githubrepo.databinding.ItemRepoBinding
import com.info.githubrepo.model.Repo
import com.info.githubrepo.util.CustomSharedPreferences
import com.info.githubrepo.view.ReposFragmentDirections
import kotlinx.android.synthetic.main.item_repo.view.*

class RepoAdapter(val repoList: ArrayList<Repo>): RecyclerView.Adapter<RepoAdapter.RepoViewHolder>(), RepoClickListener {
    class RepoViewHolder(var view: ItemRepoBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemRepoBinding>(inflater, R.layout.item_repo, parent, false)
        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.view.repo = repoList[position]
        holder.view.listener = this
    }

    override fun getItemCount(): Int {
        return repoList.size
    }

    fun updateRepoList(newRepoList: List<Repo>) {
        repoList.clear()
        repoList.addAll(newRepoList)
        notifyDataSetChanged()
    }

    override fun onCountryClicked(v: View) {
        val uuid = v.repoUuidText.text.toString().toInt()
        val action = ReposFragmentDirections.actionReposFragmentToRepoDetailFragment(uuid)
        Navigation.findNavController(v).navigate(action)
    }

}