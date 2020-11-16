package com.info.githubrepo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.info.githubrepo.R
import com.info.githubrepo.databinding.FragmentRepoDetailBinding
import com.info.githubrepo.util.CustomSharedPreferences
import com.info.githubrepo.viewmodel.RepoDetailViewModel
import kotlinx.android.synthetic.main.fragment_repo_detail.*
import kotlinx.android.synthetic.main.toolbar_custom.view.*


class RepoDetailFragment : Fragment() {

    private lateinit var viewModel: RepoDetailViewModel
    private var repoUuid = 0;
    private lateinit var dataBinding: FragmentRepoDetailBinding
    private var customPreferences = CustomSharedPreferences()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarRepoDetail.imageViewStar.visibility = View.GONE

        toolbarRepoDetail.imageViewStarOutline.setOnClickListener(View.OnClickListener {
            toolbarRepoDetail.imageViewStar.visibility = View.VISIBLE
            toolbarRepoDetail.imageViewStarOutline.visibility = View.GONE
        })

        toolbarRepoDetail.imageViewStar.setOnClickListener(View.OnClickListener {
            toolbarRepoDetail.imageViewStar.visibility = View.GONE
            toolbarRepoDetail.imageViewStarOutline.visibility = View.VISIBLE
        })

        arguments?.let {
            repoUuid = RepoDetailFragmentArgs.fromBundle(it).repoUuid
        }

        toolbarRepoDetail.imageViewStarOutline.setOnClickListener(View.OnClickListener {
            toolbarRepoDetail.imageViewStar.visibility = View.VISIBLE
            toolbarRepoDetail.imageViewStarOutline.visibility = View.GONE
            customPreferences.saveFavoriteRepo(repoUuid.toString())
        })

        toolbarRepoDetail.imageViewStar.setOnClickListener(View.OnClickListener {
            toolbarRepoDetail.imageViewStar.visibility = View.GONE
            toolbarRepoDetail.imageViewStarOutline.visibility = View.VISIBLE
        })

        viewModel = ViewModelProvider(this).get(RepoDetailViewModel::class.java)
        viewModel.getDataFromRoom(repoUuid)

        observeLiveData()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater ,R.layout.fragment_repo_detail, container, false)
        return dataBinding.root
    }

    private fun observeLiveData() {
        viewModel.repoLiveData.observe(viewLifecycleOwner, Observer { repo ->
            repo?.let {
                dataBinding.repoDetail = repo
                toolbarRepoDetail.textViewTitle.text = repo.repoName

            }
        })
    }

}