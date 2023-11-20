

function addHobby() {
    var hobbyToAdd = document.getElementById("addbox").value;

    if (hobbyToAdd) {

        $.ajax({
            url: "/api/find-hobby",
            type: "POST",
            data: hobbyToAdd,
            success: function(response) {

                const jsonResponse = JSON.parse(response);

                // Extract the necessary data
                const trackMatches = jsonResponse.results.trackmatches;

                if (trackMatches) {
                const tracks = trackMatches.track;

                var counter = 0;
                const songList = document.getElementById("songList");
                songList.innerHTML = "";

                if (Array.isArray(tracks)) {
                    tracks.forEach((track) => {


                    // Create the list items and add click event listeners

                    if (counter > 5) {
                        return true;
                    }
                    counter++;

                    const listItem = document.createElement("li");
                    const songTitle = track.name;
                    const artistName = track.artist;

                    const songDiv = document.createElement('div');
                    songDiv.textContent = songTitle;
                    songDiv.className = 'songNameListDiv';
                    listItem.appendChild(songDiv);
                    const artistDiv = document.createElement('div');
                    artistDiv.textContent = "by " + artistName;
                    artistDiv.className = 'artistNameListDiv';
                    listItem.appendChild(artistDiv);

                    listItem.addEventListener("click", () => {
                        addHobbyAllInfo(songTitle, artistName);
                    });

                    songList.appendChild(listItem);

                    });

                } else {
                    const artistName = tracks.artist;
                    console.log("Artist: " + artistName);
                }
                } else {
                console.log("No tracks found for the given title.");
                }

            },
            error: function(error) {
                console.error("Error calling Java method:", error);
            }



        });
    }
} // addHobby

function deleteHobby(title, artist) {
    $.ajax({
        url: "/api/delete-hobby",
        method: "POST",
        data: {
            param1: title,
            param2: artist
        },
        success: function (response) {
            updateMyMusicList();
        },
        error: function(error) {
            console.error("Failed to remove song: " + title + " - " + artist);
        }
    });
}


function addHobbyAllInfo(title, artist) {



        const songList = document.getElementById("songList");
        songList.innerHTML = "";

        $.ajax({
            url: "/api/add-hobby",
            method: "POST",
            data: {
                param1: title,
                param2: artist
            },
            success: function(response) {
                updateMyMusicList();
            },
            error: function(error) {
                console.error("Error sending hobby to java: ", error);
            }
        });

} // addHobbyAllInfo

document.getElementById("similarMusicButton").addEventListener("click", () => {
    findSimilarMusic();
});

function findSimilarMusic() {

        const similarityWeightElement = document.getElementById("similarity-weight");

        const similarityWeight = similarityWeightElement.value;

        console.log("similarity weight: " + similarityWeight);

        var songWeightsArray = "";

        var songsToFindWeights = document.getElementById("mySongsList");
        for (i = 0; i < songsToFindWeights.children.length; i++) {
            songWeightsArray += document.getElementsByClassName('songSlider')[i].value;
            if (i < songsToFindWeights.children.length - 1) {
                songWeightsArray += ',';
            }
        } // for
        for (i = 0; i < songsToFindWeights.children.length; i++) {
            console.log("WEIGHTS [" + i + "] = " + songWeightsArray);
        } // for

        const similarMusicResult = document.getElementById("similarMusicResult");
        similarMusicResult.innerHTML = "";
        $.ajax({
            url: "/api/find-similar",
            type: "GET",
            data: {
                param1: similarityWeight, param2: songWeightsArray,
            },
            success: function(response) {

                if (response == null) {
                    console.log("NO RESULTS FROM JSON");
                    similarMusicResult.innerHTML = "No Results";
                }

                for (i = 0; i < response.length; i++) {
                    var newSimilarElement = document.createElement("li");
                    var song = JSON.parse(response[i]);
                    console.log(response[i]);

                    const songDiv = document.createElement('div');
                    songDiv.textContent = song.name;
                    songDiv.className = 'songNameListDiv';
                    newSimilarElement.appendChild(songDiv);
                    const artistDiv = document.createElement('div');
                    artistDiv.textContent = "by " + song.artist;
                    artistDiv.className = 'artistNameListDiv';
                    newSimilarElement.appendChild(artistDiv);


                    const songName = song.name;
                    const artistName = song.artist;

                    newSimilarElement.addEventListener('click', () => {
                        addHobbyAllInfo(songName, artistName);
                    });

                    similarMusicResult.appendChild(newSimilarElement);
                }

            },

            error: function(error) {
                console.error("Error getSimilar Java method:", error);
            }

        });

}

function showMyMusicButton() {
    const myMusicButton = document.getElementById("showMyMusicButton");
    const myMusicList = document.getElementById("mySongsList");
    if (!myMusicList.style.display || myMusicList.style.display == 'none') {
        updateMyMusicList();
        myMusicList.style.display = 'block';
        myMusicButton.innerHTML = "Hide";
    } else {
        myMusicList.style.display = 'none';
        myMusicButton.innerHTML = "My Music";
    }
}

function findSimilarMusicButton() {
    const similarMusicButton = document.getElementById("findSimilarMusicButton");
    const similarMusicList = document.getElementById("similarMusicResult");
    if (!similarMusicList.style.display || similarMusicList.style.display == 'none') {
        findSimilarMusic();
        document.getElementById("newSimilarMusicButton").style.display = 'block';
        similarMusicList.style.display = 'block';
        similarMusicButton.innerHTML = 'Hide';
    } else {
        document.getElementById("newSimilarMusicButton").style.display = 'none';
        similarMusicList.style.display = 'none';
        similarMusicButton.innerHTML = "Find Similar Music"
    }
}

function updateMyMusicList() {

    const mySongsList = document.getElementById("mySongsList");
    mySongsList.innerHTML = "";

        $.ajax({
            url: "/api/get-music-list",
            type: "GET",
            success: function(response) {

                if (response == null) {
                    console.log("NO SONGS IN SONG LIBRARY");
                    mySongsList.innerHTML = "No Results";
                }

                for (i = 0; i < response.length; i++) {
                    var newSongElement = document.createElement("li");
                    var song = JSON.parse(response[i]);

                    const currName = song.name;
                    const currArtist = song.artist;

                    const songDiv = document.createElement('div');
                    songDiv.textContent = currName;
                    songDiv.className = 'songNameListDiv';
                    newSongElement.appendChild(songDiv);
                    const sliderDiv = document.createElement('div');
                    sliderDiv.setAttribute("class", "considerationSlider");
                    const sliderElement = document.createElement('input');
                    sliderElement.setAttribute("class", "songSlider")
                    sliderElement.setAttribute("type", "range");
                    sliderElement.setAttribute("min", "-50");
                    sliderElement.setAttribute("max", "100");
                    sliderElement.setAttribute("value", "50");
                    sliderDiv.appendChild(sliderElement);
                    newSongElement.appendChild(sliderDiv);

                    const artistDiv = document.createElement('div');
                    artistDiv.textContent = "by " + currArtist;
                    artistDiv.className = 'artistNameListDiv';
                    newSongElement.appendChild(artistDiv);

                    const deleteButton = document.createElement('span');
                    deleteButton.setAttribute("class", "material-symbols-outlined mySongsDeleteButton");
                    deleteButton.textContent = 'delete';
                    newSongElement.appendChild(deleteButton);

                    deleteButton.addEventListener('click', () => {
                        deleteHobby(currName, currArtist);
                    });

                    mySongsList.appendChild(newSongElement);
                }

            },

            error: function(error) {
                console.error("Error getSimilar Java method:", error);
            }

        });


}





