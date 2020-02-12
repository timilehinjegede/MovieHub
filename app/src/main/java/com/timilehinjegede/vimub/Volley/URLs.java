package com.timilehinjegede.vimub.Volley;

public class URLs {

    public static final String BASE_URL_MOVIE = "https://api.themoviedb.org/3/movie";
    public static final String BASE_URL_TV = "https://api.themoviedb.org/3/tv";

    public static final String POPULAR_MOVIE_URL = BASE_URL_MOVIE + "/popular";
    public static final String TOP_RATED_MOVIE_URL = BASE_URL_MOVIE + "/top_rated";
    public static final String UPCOMING = BASE_URL_MOVIE + "/upcoming";
    public static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185";
    public static final String BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w780";
    public static final String PROFILE_PATH_BASE_URL = "https://image.tmdb.org/t/p/h632";
    public static final String CASTS = "casts";
    public static final String TRAILERS = "trailers";

    //    Youtube Trailer
    public static final String YOUTUBE_BASE = "https://www.youtube.com/watch?v=";
    public static final String YOUTUBE_THUMBNAIL = "https://img.www.youtube.com/vi/";

    //For TV Series
    public static final String TOP_RATED_TV_URL = BASE_URL_TV + "/top_rated";
    public static final String POPULAR_TV_URL = BASE_URL_TV + "/popular";

    public static final String API_KEY = "e7e9e4ba03bc22a6bcfa6ea40882715b";
    public static final int PAGE_NUMBER = 1 ;
}