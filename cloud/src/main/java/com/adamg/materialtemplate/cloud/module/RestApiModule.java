package com.adamg.materialtemplate.cloud.module;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.adamg.materialtemplate.cloud.R;
import com.adamg.materialtemplate.cloud.network.converter.JacksonConverter;
import com.adamg.materialtemplate.cloud.network.error.CloudErrorHandler;
import com.squareup.okhttp.OkHttpClient;

import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;

/**
 * Class that provides the Retrofit API client. It is here that it is configured and made to run
 * making the appropriate calls as need be.
 *
 * @author Adam Greenberg
 * @version 1 on 6/27/15
 *          All code under The MIT License (MIT) unless otherwise noted.
 */
@Module(
        includes = {
                ApiServiceModule.class
        },
        complete = false,
        library = true
)
public class RestApiModule {

    private static final String TAG = RestApiModule.class.getSimpleName();

    /**
     * Integer that defines the basic server
     */
    private static final int DEFAULT_SERVER = 0;

    /**
     * Injectable Client which will supply the {@link OkHttpClient}.
     *
     * @param client the {@link OkHttpClient} defined in
     *               {@link OkHttpModule} or a testable module.
     * @return an instance of the transportation layer used for REST calls.
     */
    @Provides
    Client provideClient(final OkHttpClient client) {
        return new OkClient(client);
    }

    /**
     * Module that provides the basic patterned {@link RestAdapter} for use in our REST
     * requests.
     * This module will weave together the basic pieces that allow us to make a signed call, handle
     * default
     * errors, and append the correct endpoint for each call.
     *
     * @param endpoint     URL endpoint of each call being made
     * @param client       the REST client
     * @param converter    the Jackson converter that manages the JSON conversion
     * @param errorHandler common error handler piece
     * @return a {@link RestAdapter} that has been stitched together via the pieces passed
     * in.
     */
    @Provides
    RestAdapter provideRestAdapter(final Endpoint endpoint, final Client client, final JacksonConverter converter,
                                   final CloudErrorHandler errorHandler) {
        return new RestAdapter.Builder()
                // Add OkHttp as the client
                .setClient(client)
                        // Set the Jackson converter
                .setConverter(converter)
                        // Set the endpoints for the call
                .setEndpoint(endpoint)
                        // Set the error handler
                .setErrorHandler(errorHandler)
                        // Set the Logger to show all pieces of the request and response bodies
                .setLogLevel(RestAdapter.LogLevel.FULL)
                        // Build the client
                .build();
    }

    /**
     * Provides the default implementation for the Jackson converter
     *
     * @return the Jackson converter for the JSON conversion
     */
    @Provides
    JacksonConverter provideJacksonConverter() {
        return new JacksonConverter();
    }

    /**
     * Provides the default implementation for the {@link retrofit.ErrorHandler} for handling the errors
     * that occur on the network
     *
     * @return a new implemented class that allows us to have a coomon error handler
     */
    @Provides
    @Singleton
    CloudErrorHandler provideCloudErrorHandler() {
        return new CloudErrorHandler();
    }

    /**
     * Module which provides the base URL for APIs
     *
     * @param ctx the client's Context
     * @return an Endpoint constructed for the base URL in use with Retrofit
     */
    @Provides
    Endpoint providesEndpoint(@CloudLibModule.ClientApplication Context ctx) {
        // Get the current server
        final int server = ctx.getResources().getInteger(R.integer.current_server);

        // Server protocol
        final String protocol = getProtocol(ctx, server);

        // Server host
        final String serverHost = getHost(ctx, server);

        // Server port
        final int serverPort = getPort(ctx, server);

        return getEndpoint(protocol, serverHost, serverPort);
    }

    /**
     * Obtains the protocol piece of the endpoint based on the current server selected
     */
    private String getProtocol(final Context ctx, final int server) {
        String protocol = null;

        // Switch on the Server type
        switch (server) {
            case DEFAULT_SERVER:
                protocol = ctx.getString(R.string.default_server_protocol).trim();
                break;

            default:
                throwIfNullOrEmpty(null, "default_server_protocol");
        }

        // Ensure valid
        throwIfNullOrEmpty(protocol, "default_server_protocol");

        return protocol;
    }

    /**
     * Obtains the host piece of the endpoint based on the current server selected
     */
    private String getHost(Context ctx, final int server) {
        String serverHost = null;

        // Switch on the Server type
        switch (server) {
            case DEFAULT_SERVER:
                serverHost = ctx.getString(R.string.default_server_host_develop).trim();
                break;

            default:
                throwIfNullOrEmpty(null, "default_server_host_develop");
        }

        // Ensure valid
        throwIfNullOrEmpty(serverHost, "default_server_host_develop");

        return serverHost;
    }

    /**
     * Obtains the port piece of the endpoint based on the current server selected
     */
    private int getPort(final Context ctx, final int server) {
        int serverPort = 0;

        // Switch on the Server type
        switch (server) {
            case DEFAULT_SERVER:
                serverPort = ctx.getResources().getInteger(R.integer.default_server_port);
                break;

            default:
                throw new IllegalStateException(
                        "You must define a integer resource for default_server_port that represents a valid port address.");
        }


        if (serverPort < 0) {
            throw new IllegalStateException(
                    "You must define a integer resource for default_server_port that represents a valid port address.");
        }

        return serverPort;
    }

    /**
     * Constructs a new {@link Endpoint} from the config.xml parameters
     *
     * @param protocol   the name of the protocol to use.
     * @param serverHost the name of the host.
     * @param serverPort the port number on the host.
     * @return new constructed {@link Endpoint}
     */
    private Endpoint getEndpoint(final String protocol, final String serverHost, final int serverPort) {
        // Build the endpoint using URL so we catch any malformed exceptions, etc
        try {
            final URL url = new URL(protocol, serverHost, serverPort, "");
            Log.v(TAG, "Base URl is: " + url.toString());
            return Endpoints.newFixedEndpoint(url.toString());
        } catch (final MalformedURLException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }

        throw new IllegalArgumentException("Could not construct the base endpoint.");
    }

    /**
     * Check to see if the resource we obtain at initialization are valid and
     * contain data
     *
     * @param reference the string from the XML config resource
     * @param name      the name of the resource
     */
    public static void throwIfNullOrEmpty(final String reference, final String name) {
        if (TextUtils.isEmpty(reference)) {
            throw new IllegalStateException("The user must define a value in the config.xml file for: " + name);
        }
    }
}
