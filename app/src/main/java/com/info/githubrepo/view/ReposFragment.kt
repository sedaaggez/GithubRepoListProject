package com.info.githubrepo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.info.githubrepo.R
import com.info.githubrepo.adapter.RepoAdapter
import com.info.githubrepo.viewmodel.ReposViewModel
import kotlinx.android.synthetic.main.fragment_repos.*

class ReposFragment : Fragment() {

    private lateinit var viewModel: ReposViewModel
    private val repoAdapter = RepoAdapter(arrayListOf())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ReposViewModel::class.java)

        viewModel.init()

        buttonSubmit.setOnClickListener(View.OnClickListener {
            viewModel.getRepos(editTextName.text.toString())
        })

        recyclerViewRepo.layoutManager = LinearLayoutManager(context)
        recyclerViewRepo.adapter = repoAdapter

        observeLiveData()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repos, container, false)
    }

    private fun observeLiveData() {
        viewModel.repos.observe(viewLifecycleOwner, Observer {repos ->
            repos?.let {
                recyclerViewRepo.visibility = View.VISIBLE
                repoAdapter.updateRepoList(repos)
            }
        })

        viewModel.repoError.observe(viewLifecycleOwner, Observer {error ->
            error?.let {
                if(it) {
                    textViewRepoError.visibility = View.VISIBLE
                    progressBarRepoLoading.visibility = View.GONE
                    recyclerViewRepo.visibility = View.GONE
                } else {
                    textViewRepoError.visibility = View.GONE

                }
            }
        })

        viewModel.repoLoading.observe(viewLifecycleOwner, Observer {loading ->
            loading?.let {
                if(it) {
                    progressBarRepoLoading.visibility = View.VISIBLE
                    textViewRepoError.visibility = View.GONE
                    recyclerViewRepo.visibility = View.GONE

                } else {
                    progressBarRepoLoading.visibility = View.GONE

                }
            }
        })
    }


}