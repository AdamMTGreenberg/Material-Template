package com.adamg.materialtemplate.cloud.module;

/**
 * Module that provides the API services as needed and built ready to make a request.
 *
 * @author Adam Greenberg
 * @version 1 on 6/28/15
 * All code under The MIT License (MIT) unless otherwise noted.
 */


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

@Module(
        complete = false,
        library = true
)
public class ApiServiceModule {

//    /**
//     * Module that provides the Service that calls an API
//     *
//     * @param restAdapter client provided by
//     *                    {@link RestApiModule
//     *                    #provideRestAdapter(retrofit.Endpoint, retrofit.client.Client,
//     *                    BaseApiHeaders, JacksonConverter)}
//     * @return the implemented Retrofit Service
//     */
//    @Provides
//    @Singleton
//    ApiService provideApiService(final RestAdapter restAdapter) {
//        return restAdapter.create(ApiService.class);
//    }
}
