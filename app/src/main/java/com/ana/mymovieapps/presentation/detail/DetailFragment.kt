package com.ana.mymovieapps.presentation.detail

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ana.core.data.source.Resource
import com.ana.core.domain.model.Movie
import com.ana.core.presentation.adapter.MovieTrailerAdapter
import com.ana.mymovieapps.R
import com.ana.mymovieapps.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var movie: Movie
    private var isFavoriteMovie by Delegates.notNull<Boolean>()
    private val movieTrailerAdapter: MovieTrailerAdapter by lazy {
        MovieTrailerAdapter{}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Movie>(MOVIE_KEY)?.let { data ->
            movie = data
            setupUi(movie)
            observeFav(data.id)
            data.id?.let {
                viewModel.getMovieTrailerById(it)
                observeMovieTrailer()
                initRv()
            }
            favOnPress()
        }
        backOnPress()
    }

    private fun backOnPress() {
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun favOnPress() {
        binding.btnFavorite.setOnClickListener {
            if (isFavoriteMovie){
                viewModel.deleteFavoriteFromDb(movie)
            }else{
                viewModel.insertFavoriteToDb(movie)
            }
            isFavoriteMovie = !isFavoriteMovie
            favButtonUpdate()
        }
    }

    private fun initRv() {
        binding.rvMovieTrailer.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)
            adapter = movieTrailerAdapter
        }
    }

    private fun observeMovieTrailer() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.movieTrailerResponse.collect{
                    when(it){
                        is Resource.Success ->{
                            binding.rvMovieTrailer.visibility = View.VISIBLE
                            it.data?.let { movieTrailers ->
                                movieTrailerAdapter.setItems(movieTrailers)
                            }
                        }
                        is Resource.Loading -> {
                            binding.rvMovieTrailer.visibility = View.GONE
                        }
                        is Resource.Error -> {
                            binding.rvMovieTrailer.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun observeFav(movie: Int?) {
        if (movie != null){
            viewModel.isFavorite(movie).observe(viewLifecycleOwner){ fav ->
                isFavoriteMovie = fav
                favButtonUpdate()
            }
        }
    }

    private fun favButtonUpdate() {
        val (color, text) = if (isFavoriteMovie) {
            ContextCompat.getColor(requireContext(), R.color.red) to getString(R.string.remove_favorite)
        } else {
            ContextCompat.getColor(requireContext(), R.color.background_button) to getString(R.string.add_favorite)
        }
        binding.btnFavorite.apply { 
            backgroundTintList = ColorStateList.valueOf(color)
            setText(text)
        }
    }

    private fun setupUi(movie: Movie) {
        binding.apply {
            Glide.with(requireContext()).load(movie.img).into(ivMoviePoster)
            tvRelease.text = movie.releaseDate?.substring(0,4)
            tvRating.text = movie.voteAverage.toString()
            tvNameDetail.text = movie.name
            tvAboutSinopsis.text = movie.overview
        }
    }

    companion object{
        const val MOVIE_KEY = "MOVIE"
    }
}