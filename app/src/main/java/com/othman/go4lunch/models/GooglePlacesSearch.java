
package com.othman.go4lunch.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class GooglePlacesSearch {

    @Expose
    private List<Candidate> candidates;
    @SerializedName("debug_log")
    private DebugLog debugLog;
    @Expose
    private String status;

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public DebugLog getDebugLog() {
        return debugLog;
    }

    public void setDebugLog(DebugLog debugLog) {
        this.debugLog = debugLog;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SuppressWarnings("unused")
    public static class Location {

        @Expose
        private Double lat;
        @Expose
        private Double lng;

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLng() {
            return lng;
        }

        public void setLng(Double lng) {
            this.lng = lng;
        }

    }

    @SuppressWarnings("unused")
    public static class Northeast {

        @Expose
        private Double lat;
        @Expose
        private Double lng;

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLng() {
            return lng;
        }

        public void setLng(Double lng) {
            this.lng = lng;
        }

    }

    @SuppressWarnings("unused")
    public static class OpeningHours {

        @SerializedName("open_now")
        private Boolean openNow;
        @SerializedName("weekday_text")
        private List<Object> weekdayText;

        public Boolean getOpenNow() {
            return openNow;
        }

        public void setOpenNow(Boolean openNow) {
            this.openNow = openNow;
        }

        public List<Object> getWeekdayText() {
            return weekdayText;
        }

        public void setWeekdayText(List<Object> weekdayText) {
            this.weekdayText = weekdayText;
        }

    }

    @SuppressWarnings("unused")
    public static class Photo {

        @Expose
        private Long height;
        @SerializedName("html_attributions")
        private List<String> htmlAttributions;
        @SerializedName("photo_reference")
        private String photoReference;
        @Expose
        private Long width;

        public Long getHeight() {
            return height;
        }

        public void setHeight(Long height) {
            this.height = height;
        }

        public List<String> getHtmlAttributions() {
            return htmlAttributions;
        }

        public void setHtmlAttributions(List<String> htmlAttributions) {
            this.htmlAttributions = htmlAttributions;
        }

        public String getPhotoReference() {
            return photoReference;
        }

        public void setPhotoReference(String photoReference) {
            this.photoReference = photoReference;
        }

        public Long getWidth() {
            return width;
        }

        public void setWidth(Long width) {
            this.width = width;
        }

    }

    @SuppressWarnings("unused")
    public static class Southwest {

        @Expose
        private Double lat;
        @Expose
        private Double lng;

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLng() {
            return lng;
        }

        public void setLng(Double lng) {
            this.lng = lng;
        }

    }

    @SuppressWarnings("unused")
    public static class Viewport {

        @Expose
        private Northeast northeast;
        @Expose
        private Southwest southwest;

        public Northeast getNortheast() {
            return northeast;
        }

        public void setNortheast(Northeast northeast) {
            this.northeast = northeast;
        }

        public Southwest getSouthwest() {
            return southwest;
        }

        public void setSouthwest(Southwest southwest) {
            this.southwest = southwest;
        }

    }

    @SuppressWarnings("unused")
    public static class Candidate {

        @SerializedName("formatted_address")
        private String formattedAddress;
        @Expose
        private Geometry geometry;
        @Expose
        private String name;
        @SerializedName("opening_hours")
        private OpeningHours openingHours;
        @Expose
        private List<Photo> photos;
        @Expose
        private Double rating;

        public String getFormattedAddress() {
            return formattedAddress;
        }

        public void setFormattedAddress(String formattedAddress) {
            this.formattedAddress = formattedAddress;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public OpeningHours getOpeningHours() {
            return openingHours;
        }

        public void setOpeningHours(OpeningHours openingHours) {
            this.openingHours = openingHours;
        }

        public List<Photo> getPhotos() {
            return photos;
        }

        public void setPhotos(List<Photo> photos) {
            this.photos = photos;
        }

        public Double getRating() {
            return rating;
        }

        public void setRating(Double rating) {
            this.rating = rating;
        }

    }

    @SuppressWarnings("unused")
    public static class DebugLog {

        @Expose
        private List<Object> line;

        public List<Object> getLine() {
            return line;
        }

        public void setLine(List<Object> line) {
            this.line = line;
        }

    }

    @SuppressWarnings("unused")
    public static class Geometry {

        @Expose
        private Location location;
        @Expose
        private Viewport viewport;

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Viewport getViewport() {
            return viewport;
        }

        public void setViewport(Viewport viewport) {
            this.viewport = viewport;
        }

    }
}
