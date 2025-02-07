package com.ana.mymovieapps.presentation.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ana.core.data.source.Resource
import com.ana.core.presentation.adapter.MovieAdapter
import com.ana.mymovieapps.R
import com.ana.mymovieapps.databinding.FragmentHomeBinding
import com.ana.mymovieapps.presentation.detail.DetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private val movieAdapter: MovieAdapter by lazy {
        MovieAdapter{data ->
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment,
                Bundle().apply {
                    putParcelable(DetailFragment.MOVIE_KEY,data)
                })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        favOnPress()
        initRv()
    }

    private fun initRv() {
        binding.rvMovie.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun favOnPress() {
        binding.ivFavorite.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
        }
    }

    private fun observeData() {
        viewModel.getMovie.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {
                    binding.rvMovie.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE

                }
                is Resource.Success ->{
                    binding.rvMovie.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { movie ->
                        movieAdapter.setItems(movie)
                    }
                }
                is Resource.Error ->{
                    binding.rvMovie.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvMovie.adapter = null
        _binding = null
    }
}