const UNSPLASH_API_URL = "https://api.unsplash.com/photos/random";
const UNSPLASH_ACCESS_KEY = "_lhT9Mal4HscPSY3XGJwBICemc4v-0_M-CehW9tFWWk";

export const fetchPhotoFromUnsplash = async () => {
    try {
        const response = await fetch(`${UNSPLASH_API_URL}?client_id=${UNSPLASH_ACCESS_KEY}`);
        const data = await response.json();
        return data.urls.regular;
    } catch (error) {
        console.error("Error fetching photo from Unsplash: ", error);
        return null;
    }
};
