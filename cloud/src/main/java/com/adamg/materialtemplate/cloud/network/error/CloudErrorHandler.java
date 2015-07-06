package com.adamg.materialtemplate.cloud.network.error;

import android.util.Log;

import org.apache.http.HttpStatus;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Class that implements {@link ErrorHandler} interface to allow us to deal with all non
 * network connectivity errors in a single place. This class will hook allowing
 * clients to customize response exceptions, thus enabling us to interpret the error, react accordingly,
 * and if possible/necessary create/kickoff a new request.
 * <br>
 * @author Adam Greenberg
 * @version 1 on 6/27/15
 * All code under The MIT License (MIT) unless otherwise noted
 */
public class CloudErrorHandler implements ErrorHandler {

    private static final String TAG = CloudErrorHandler.class.getSimpleName();

    @Override
    public Throwable handleError(final RetrofitError error) {
        // Log it
        Log.e(TAG, "Error on API call: \n" + error.getMessage());

        // Switch on the the Kind of error
        final RetrofitError.Kind kind = error.getKind();
        switch (kind) {
            // Issue with an HTTP response error
            case HTTP:
                handleHttpError(error);
                break;

            // An issue with the network
            case NETWORK:
                handleNetworkError(error);
                break;

            // There was an error in interpreting the response
            case CONVERSION:
                handleConversionError(error);
                break;

            case UNEXPECTED:
            default:
                handleUnexpectedError(error);
        }

        // Return the exception
        return error;
    }

    /**
     * Handles all of the non-200 level exceptions that are received from the server. First it will
     * switch on their http response level and if applicable on their application level response
     *
     * @param error the error that is thrown when the server reported an exception
     */
    private void handleHttpError(final RetrofitError error) {
        final Response response = error.getResponse();

        // Ensure a non-null response
        if (response != null) {
            // Pull out the response code
            final int status = response.getStatus();
            Log.e(TAG, "Http Error Status: " + status);

            // Switch on the status code
            switch (status) {
                case HttpStatus.SC_UNAUTHORIZED:
                case HttpStatus.SC_NOT_FOUND:
                    handleAuthError(error);
                    break;

                case HttpStatus.SC_CONFLICT:
                case HttpStatus.SC_FORBIDDEN:
                case HttpStatus.SC_LENGTH_REQUIRED:
                case HttpStatus.SC_LOCKED:
                case HttpStatus.SC_METHOD_FAILURE:
                case HttpStatus.SC_METHOD_NOT_ALLOWED:

                    // FIXME add more cases?

                default:
                    // Do nothing, let the other listeners handle it
            }
        }
    }

    /**
     * Handles all of the errors that occur due to network connectivity issues, eg, the do not return
     * because of a 400-level error
     *
     * @param throwable the error that is thrown when the transport layer reports an exception
     */
    private void handleNetworkError(final RetrofitError throwable) {
        // FIXME Do more stuff after we decide what to do
    }

    /**
     * Handles all of the errors that occur due to network connectivity issues, eg, the do not return
     * because of a 400-level error
     *
     * @param error the error that is thrown when the transport layer reports an exception
     */
    private void handleUnexpectedError(final RetrofitError error) {
        // FIXME Do more stuff after we decide what to do
    }

    /**
     * Handles all of the errors that occur due to network connectivity issues, eg, the do not return
     * because of a 400-level error
     *
     * @param error the error that is thrown when the transport layer reports an exception
     */
    private void handleConversionError(final RetrofitError error) {
        // FIXME Do more stuff after we decide what to do
    }

    /**
     * Handles cases when we get an Auth challenge
     */
    private void handleAuthError(final RetrofitError error) {
        // FIXME Do more stuff after we decide what to do
    }
}
