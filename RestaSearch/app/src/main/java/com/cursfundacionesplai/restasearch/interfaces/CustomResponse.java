package com.cursfundacionesplai.restasearch.interfaces;

import com.cursfundacionesplai.restasearch.models.RestaurantModel;

public interface CustomResponse {
    interface EstablimentDetail {
        void onEstablimentResponse(RestaurantModel r);
    }
}