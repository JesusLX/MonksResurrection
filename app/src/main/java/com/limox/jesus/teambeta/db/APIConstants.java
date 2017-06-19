package com.limox.jesus.teambeta.db;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Class with API connections methods
 * Created by Jesus on 20/05/2017.
 */
public class APIConstants {
    public static class Forums {
        private static final String API_URL = "https://jesuslx.ncatz.com/API/TeamBetaAPI/";

        public static final String TABLE_NAME = "forum";
        public static final String FORUM_KEY = "id_key";
        public static final String FORUM_CREATION_D = "creationDate";
        public static final String FORUM_IMG_URL = "imgUrl";
        public static final String FORUM_NAME = "name";
        public static final String FORUM_OWNER_ID = "ownerId";
        public static final String FORUM_ADM_KEY = "adminsKey";
        public static final String FORUM_USR_KEY = "usersKey";
        public static final String FORUM_DELETED = "deleted";

        private static final String FORUM_SEARCH = "search";
        private static final String FORUM_POST = "post";
        private static final String FORUM_EXISTS = "exists";

        public static void getNames(Context context, final String name, Response.Listener<String> stringListener, Response.ErrorListener errorListener) {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(context);
            final HashMap<String, String> params = new HashMap<>();
            params.put(FORUM_SEARCH, FORUM_SEARCH);
            params.put(FORUM_NAME, name);

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL + TABLE_NAME + "/1" + getParams(params), stringListener, errorListener);
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }

        public static void existsForum(Context context, final String name, Response.Listener<String> stringListener, Response.ErrorListener errorListener) {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(context);
            final HashMap<String, String> params = new HashMap<>();
            params.put(FORUM_EXISTS, FORUM_EXISTS);
            params.put(FORUM_NAME, name);

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL + TABLE_NAME + "/1" + getParams(params), stringListener, errorListener);
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }

        private static String getParams(Map<String, String> params) {
            String param = "";
            if (params != null) {
                for (String key :
                        params.keySet()) {
                    if (param.isEmpty()) {
                        param = "?";
                    } else {
                        param += "&";
                    }
                    param += String.format("%s=%s", key, params.get(key).replace(" ", "%20"));
                }
            }
            return param;
        }

        public static void getForum(Context context, String name, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            RequestQueue queue = Volley.newRequestQueue(context);
            final HashMap<String, String> params = new HashMap<>();
            params.put(FORUM_NAME, name);

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL + TABLE_NAME + "/1" + getParams(params), listener, errorListener);
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }

        public static void postForum(Context context, final HashMap<String, String> params, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {

            // Request a string response from the provided URL.
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, API_URL + TABLE_NAME, toJSONObject(params), listener, errorListener);
            // Add the request to the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(jsonObjectRequest);
        }

        public static void updateForum(Context context, String forumKey, final HashMap<String, String> params, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {

            // Request a string response from the provided URL.
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, API_URL + TABLE_NAME + "/1?" + APIConstants.Forums.FORUM_KEY + "=" + forumKey, toJSONObject(params), listener, errorListener);
            // Add the request to the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(jsonObjectRequest);
        }

        public static void getForumByKey(Context context, String key, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
            RequestQueue queue = Volley.newRequestQueue(context);
            final HashMap<String, String> params = new HashMap<>();
            params.put(FORUM_KEY, key);
            // Request a string response from the provided URL.
            JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, API_URL + TABLE_NAME + getParams(params), null, listener, errorListener);
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }

    private static JSONObject toJSONObject(HashMap<String, String> params) {
        JSONObject param = new JSONObject();
        for (String key :
                params.keySet()) {
            try {
                param.put(key, params.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return param;
    }
}
