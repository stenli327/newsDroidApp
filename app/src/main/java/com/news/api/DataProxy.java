package com.news.api;

import java.security.InvalidParameterException;

import retrofit2.Call;

public class DataProxy extends BaseProxyApi {

    /***
     * Provides a list of the news sources and blogs available on News API.
     * You will need this to programmatically locate the identifier for the source you want articles from when querying the /articles endpoint.
     *
     * @param category (optional) - The category you would like to get sources for.
     *                 Possible options: business, entertainment, gaming, general, music, politics, science-and-nature, sport, technology.
     *                 Default: empty (all sources returned)
     * @param language (optional) - The 2-letter ISO-639-1 code of the language you would like to get sources for.
     *                 Default: empty (all sources returned)
     * @param country (optional) - The 2-letter ISO 3166-1 code of the country you would like to get sources for.
     *                Default: empty (all sources returned)
     * @return SourceResponse
     */
    public SourceResponse loadSources(ESourceCategory category, ELanguage language, ECountry country){

        IDataProxy client = createService(IDataProxy.class);

        String c = category.getLabel();


        Call<SourceResponse> call = client.getSources("", "en", "us");
        SourceResponse response = null;
        try {
            response = call.execute().body();
        } catch (Exception e) {
            return new SourceResponse();
        }

        return response;
    }

    /***
     * Provides a list of live article metadata from a news source or blog (99% of the time this is the one you want!).
     * @param source The identifer for the news source or blog you want headlines from. Use the /sources endpoint to locate this or use the sources index.
     * @param sortBy (optional) - Specify which type of list you want. The possible options are top, latest and popular. Note: not all options are available for all sources. Default: top.
     *     top	    - Requests a list of the source's headlines sorted in the order they appear on its homepage.
     *     latest	    - Requests a list of the source's headlines sorted in chronological order, newest first.
     *     popular    - 	Requests a list of the source's current most popular or currently trending headlines.
     * @return ArticleResponse
     */
    public ArticleResponse loadArticles(String source, String sortBy){

        if(ObjectUtil.isEmptyOrNull(source)){
            throw new InvalidParameterException("source");
        }

        IDataProxy client = createService(IDataProxy.class);

        Call<ArticleResponse> call = client.getArticles(source, sortBy);
        ArticleResponse response = null;
        try {
            response = call.execute().body();
        } catch (Exception e) {
            return new ArticleResponse();
        }

        return response;
    }
}
