package com.info.githubrepo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.info.githubrepo.R
import com.info.githubrepo.databinding.FragmentRepoDetailBinding
import com.info.githubrepo.util.CustomSharedPreferences
import com.info.githubrepo.viewmodel.RepoDetailViewModel


class RepoDetailFragment : Fragment() {

    private lateinit var viewModel: RepoDetailViewModel
    private var repoUuid = 0;
    private lateinit var dataBinding: FragmentRepoDetailBinding
    private var customPreferences = CustomSharedPreferences()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.let {
            repoUuid = RepoDetailFragmentArgs.fromBundle(it).repoUuid
        }

        viewModel = ViewModelProvider(this).get(RepoDetailViewModel::class.java)
        viewModel.getDataFromRoom(repoUuid)

        observeLiveData()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_repo_detail,
            container,
            false
        )
        return dataBinding.root
    }

    private fun observeLiveData() {
        viewModel.repoLiveData.observe(viewLifecycleOwner, Observer { repo ->
            repo?.let {
                dataBinding.repoDetail = repo
                (activity as AppCompatActivity?)!!.supportActionBar!!.title = repo.repoName

            }
        })
    }

}