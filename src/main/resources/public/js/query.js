let searchParams = new URLSearchParams(window.location.search);

var venues;

if (searchParams.has("query")) {
  document.getElementById('content_list').style.display = "block";
  document.getElementById('query').value = searchParams.get("query");

  $.getJSON("/venues?location=" + searchParams.get("query"), function( data ) {
    venues = data;
    var content = document.getElementById('content_list');

    for (var i = 0; i < data.length; i++) {
      var venue = data[i];
      console.log(venue);

      li = document.createElement("li");
      content.appendChild(li);

      itemContainer = document.createElement("DIV");
      itemContainer.setAttribute("class", "collapsible-header");
      li.appendChild(itemContainer);

      mainPhoto = document.createElement("img");
      mainPhoto.setAttribute("class", "thumbnail");
      itemContainer.appendChild(mainPhoto);

      info = document.createElement("div");
      info.setAttribute("class", "info");
      itemContainer.appendChild(info);

      venueTitle = document.createElement("p");
      venueTitle.setAttribute("class", "venueTitle");
      venueTitle.innerHTML = venue.name;
      info.appendChild(venueTitle);

      venueInfo = document.createElement("p");
      var address = "";
      if (venue.location.address != null) {
        address += venue.location.address;
        if (venue.location.city != null)
          address += ", " + venue.location.city;
      }
      venueInfo.innerHTML = address;
      info.appendChild(venueInfo);

      collapsibleContainer = document.createElement("DIV");
      collapsibleContainer.setAttribute("class", "collapsible-body");
      li.appendChild(collapsibleContainer);

      $.ajax({
              type: 'GET',
              url: "/venues/images/" + venue.id,
              dataType: 'json',
              success: function(imageData) {
                  console.log(imageData);
                  if (imageData.urlWithBurger != null) {
                      mainPhoto.setAttribute("src", imageData.urlWithBurger);
                      mainPhoto.setAttribute("class", "thumbnail highlighted");
                  } else if (imageData.images.length > 0) {
                      mainPhoto.setAttribute("src", imageData.images[0]);
                  }

                  if (imageData.images.length > 0) {
                    collapsibleContainer = document.createElement("DIV");
                    collapsibleContainer.setAttribute("class", "collapsible-body");
                    li.appendChild(collapsibleContainer);
                    for (var i = 0; i < imageData.images.length; i++) {
                      var image = imageData.images[i];
                      if (image == imageData.urlWithBurger)
                        continue;

                      photo = document.createElement("img");
                      photo.setAttribute("src", image);
                      photo.setAttribute("class", "thumbnail");
                      collapsibleContainer.appendChild(photo);
                    }
                  }
              },
              timeout: 10000
          });
    }
  });
} else {
  document.getElementById('background').style.display = "block";
}

function query() {
  window.location.href = '?query=' + document.getElementById('query').value;
}
