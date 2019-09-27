
package com.othman.go4lunch.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class GooglePlaces {

    @SerializedName("html_attributions")
    private List<Object> mHtmlAttributions;
    @SerializedName("results")
    private List<Result> mResults;
    @SerializedName("status")
    private String mStatus;

    public List<Object> getHtmlAttributions() {
        return mHtmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        mHtmlAttributions = htmlAttributions;
    }

    public List<Result> getResults() {
        return mResults;
    }

    public void setResults(List<Result> results) {
        mResults = results;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    @SuppressWarnings("unused")
    public static class Geometry {

        @SerializedName("location")
        private Location mLocation;

        public Location getLocation() {
            return mLocation;
        }

        public void setLocation(Location location) {
            mLocation = location;
        }

    }

    @SuppressWarnings("unused")
    public static class Location {

        @SerializedName("lat")
        private Double mLat;
        @SerializedName("lng")
        private Double mLng;

        public Double getLat() {
            return mLat;
        }

        public void setLat(Double lat) {
            mLat = lat;
        }

        public Double getLng() {
            return mLng;
        }

        public void setLng(Double lng) {
            mLng = lng;
        }

    }

    @SuppressWarnings("unused")
    public static class OpeningHours {

        @SerializedName("open_now")
        private Boolean mOpenNow;

        public Boolean getOpenNow() {
            return mOpenNow;
        }

        public void setOpenNow(Boolean openNow) {
            mOpenNow = openNow;
        }

    }
    @SuppressWarnings("unused")
    public static class Photo {

        @SerializedName("height")
        private Long mHeight;
        @SerializedName("html_attributions")
        private List<Object> mHtmlAttributions;
        @SerializedName("photo_reference")
        private String mPhotoReference;
        @SerializedName("width")
        private Long mWidth;

        public Long getHeight() {
            return mHeight;
        }

        public void setHeight(Long height) {
            mHeight = height;
        }

        public List<Object> getHtmlAttributions() {
            return mHtmlAttributions;
        }

        public void setHtmlAttributions(List<Object> htmlAttributions) {
            mHtmlAttributions = htmlAttributions;
        }

        public String getPhotoReference() {
            return mPhotoReference;
        }

        public void setPhotoReference(String photoReference) {
            mPhotoReference = photoReference;
        }

        public Long getWidth() {
            return mWidth;
        }

        public void setWidth(Long width) {
            mWidth = width;
        }

    }

    @SuppressWarnings("unused")
    public static class Result {

        @SerializedName("geometry")
        private Geometry mGeometry;
        @SerializedName("icon")
        private String mIcon;
        @SerializedName("id")
        private String mId;
        @SerializedName("name")
        private String mName;
        @SerializedName("opening_hours")
        private OpeningHours mOpeningHours;
        @SerializedName("photos")
        private List<Photo> mPhotos;
        @SerializedName("place_id")
        private String mPlaceId;
        @SerializedName("reference")
        private String mReference;
        @SerializedName("types")
        private List<String> mTypes;
        @SerializedName("vicinity")
        private String mVicinity;

        public Geometry getGeometry() {
            return mGeometry;
        }

        public void setGeometry(Geometry geometry) {
            mGeometry = geometry;
        }

        public String getIcon() {
            return mIcon;
        }

        public void setIcon(String icon) {
            mIcon = icon;
        }

        public String getId() {
            return mId;
        }

        public void setId(String id) {
            mId = id;
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }

        public OpeningHours getOpeningHours() {
            return mOpeningHours;
        }

        public void setOpeningHours(OpeningHours openingHours) {
            mOpeningHours = openingHours;
        }

        public List<Photo> getPhotos() {
            return mPhotos;
        }

        public void setPhotos(List<Photo> photos) {
            mPhotos = photos;
        }

        public String getPlaceId() {
            return mPlaceId;
        }

        public void setPlaceId(String placeId) {
            mPlaceId = placeId;
        }

        public String getReference() {
            return mReference;
        }

        public void setReference(String reference) {
            mReference = reference;
        }

        public List<String> getTypes() {
            return mTypes;
        }

        public void setTypes(List<String> types) {
            mTypes = types;
        }

        public String getVicinity() {
            return mVicinity;
        }

        public void setVicinity(String vicinity) {
            mVicinity = vicinity;
        }

    }
}
