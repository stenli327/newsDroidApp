package com.news.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseProxyApi {

    private static final String CLOUD_URL = "https://newsapi.org/v1/";

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'";
    private static final int MAX_IDLE_CONNECTIONS = 100;
    private static final int TIMEOUT = 30;

    private static final String API_KEY = "faed10400b454658a235e1d7e3c0a5e9";

    protected final static Gson gson = registerTypeFromMapper();

    private final static boolean verbose = true;

    protected static <S> S createService(Class<S> serviceClass) {

        Interceptor interceptor = new Interceptor() {

            private Date actual = new Date();
            private final Timestamp actualTimeStampDate = new Timestamp(actual.getTime());

            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                okhttp3.Request original = chain.request();

                okhttp3.Request.Builder requestBuilder = original.newBuilder()
                        .header("X-Api-Key", API_KEY)
                        .header("Accept", "application/json")
                        .method(original.method(), original.body());

                okhttp3.Request request = requestBuilder.build();

                if(verbose){
                    //log request
                    System.out.println("\n===================================================================");
                    System.out.println(String.format("Starting: %s ", actualTimeStampDate));
                    System.out.println(String.format("%s", original.url().host()));
                    System.out.println(String.format("%s %s", original.method(), original.url().encodedPath()));
                }

                okhttp3.Response response = chain.proceed(request);

                if(!verbose){
                    return response;
                }

                try {
                    String jsonResponse = response.body().string();

                    //log response
                    String prettyJsonString = prettyJson(jsonResponse);
                    System.out.print(String.format("ArticleResponse: \n%s", prettyJsonString));

                    long responseTimeInMilis = new Timestamp(new Date().getTime()).getTime() - actualTimeStampDate.getTime();
                    System.out.println(String.format("\nRespons time: %sms", responseTimeInMilis));
                    return response.newBuilder()
                            .body(ResponseBody.create(response.body().contentType(), jsonResponse)).build();
                } catch (Exception e) {
                    System.out.println("\nError: ");
                    System.out.print(String.format("Message: %s", e.getMessage()));
                    System.out.print(String.format("StackTrace: %s", e.getStackTrace().toString()));
                }finally {
                    //close the resources
                    response.close();
                }
                return response;
            }
        };

        Dispatcher dispatcher = new Dispatcher(Executors.newSingleThreadExecutor());
        dispatcher.setMaxRequests(5);
        dispatcher.setMaxRequestsPerHost(1);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .dispatcher(dispatcher)
                .connectionPool(new ConnectionPool(
                        MAX_IDLE_CONNECTIONS,
                        TIMEOUT,
                        TimeUnit.SECONDS));

        builder.interceptors().add(interceptor);

        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CLOUD_URL)
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit.create(serviceClass);
    }


    private static Gson registerTypeFromMapper(){

        GsonBuilder result = new GsonBuilder();

//		for ( JsonClassMap item : JsonClassMap.getRegisteredClassMaps()) {
//			result.registerTypeAdapter(item.getClassType(), JsonMapConverter.class);
//		}

        result.setDateFormat(DATE_FORMAT);
        return result.create();
    }

    private static Gson gsonTmp = new GsonBuilder().setPrettyPrinting().create();
    private static JsonParser jp = new JsonParser();
    private static String prettyJson(String jsonResponse) {
        JsonElement je = jp.parse(jsonResponse);
        String prettyJsonString = gsonTmp.toJson(je);
        return prettyJsonString;
    }
}
