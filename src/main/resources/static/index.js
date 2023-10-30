

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

                if (Array.isArray(tracks)) {
                    tracks.forEach((track) => {

                    const songList = document.getElementById("songList");

                    // Create the list items and add click event listeners

                    if (counter > 5) {
                        return true;
                    }
                    counter++;

                    const listItem = document.createElement("li");
                    const songTitle = track.name;
                    const artistName = track.artist;

                    listItem.textContent = songTitle + " - " + artistName;
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
            success: function() {
                console.log("Hobby Added");
                console.log("JAVASCRIPT TITLE: " + title);
                console.log("JAVASCRIPT ARTIST: " + artist);
            },
            error: function(error) {
                console.error("Error sending hobby to java: ", error);
            }
        });

        var newListElement = document.createElement("li");
        newListElement.textContent = title + " - " + artist;
        document.getElementById("mySongsList").appendChild(newListElement);
        
} // addHobbyAllInfo

document.getElementById("similarMusicButton").addEventListener("click", () => {
    findSimilarMusic();
});

function findSimilarMusic() {
        const similarMusicResult = document.getElementById("similarMusicResult");
        similarMusicResult.innerHTML = "";
        $.ajax({
            url: "/api/find-similar",
            type: "GET",
            success: function(response) {

                for (i = 0; i < response.length; i++) {
                    var newSimilarElement = document.createElement("li");
                    var song = JSON.parse(response[i]);
                    console.log(response[i]);
                    newSimilarElement.textContent = song.name + " - " + song.artist;

                    const songName = song.name;
                    const artistName = song.artist;

                    newSimilarElement.addEventListener('click', () => {
                        console.log("Event Listener name and artist : " + songName + " " + artistName);
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





