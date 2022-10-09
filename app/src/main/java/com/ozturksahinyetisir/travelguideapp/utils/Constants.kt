package com.ozturksahinyetisir.travelguideapp.utils

class Constants {
    companion object{
        /**
         * Constants can use save BASE_URL and API KEY from team members.
         * If working with gitHub and this contants upload with .gitignore
         * so team members can't reach it without reverse engineering.
         * But attackers can reach [BASE_URL] & [API_KEY] with reverse engineering.
         * Best way to hide [API_KEY] using SHA-256 security system.
         * Encryption [API_KEY] possible inside of Android Studio coding.
         */
        const val BASE_URL = "https://633800d85327df4c43db5614.mockapi.io/"
        // const val API_KEY = "test"
    }
}