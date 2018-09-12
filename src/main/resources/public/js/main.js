$( document ).ready(function() {

  $('.collapsible').collapsible();

  $.getJSON("/venues", function( data ) {
    var content = document.getElementById('content_list');
    if (data.length > 0) {
      $('#background').remove();
      content_list.setAttribute("style", "display:block");
    }

    for (var venueIndex = 0; venueIndex < data.length; venueIndex++) {
      var venue = data[venueIndex];

      var li = document.createElement("li");
      content.appendChild(li);

      var itemContainer = document.createElement("div");
      itemContainer.setAttribute("class", "collapsible-header");
      li.appendChild(itemContainer);

      var mainPhoto = document.createElement("img");
      mainPhoto.setAttribute("class", "thumbnail");
      itemContainer.appendChild(mainPhoto);

      var info = document.createElement("div");
      info.setAttribute("class", "info");
      itemContainer.appendChild(info);

      var venueTitle = document.createElement("p");
      venueTitle.setAttribute("class", "venueTitle");
      venueTitle.innerHTML = venue.name;
      info.appendChild(venueTitle);

      var venueInfo = document.createElement("p");
      venueInfo.setAttribute("class", "infoText");
      venueInfo.innerHTML = venue.locationString;
      info.appendChild(venueInfo);

      var images = venue.images;

      if (images.length > 0) {
        mainPhoto.setAttribute("src", images[0].url);
        if (images[0].burger) {
          mainPhoto.setAttribute("class", "thumbnail highlighted");
        }

        var collapsibleContainer = document.createElement("div");
        collapsibleContainer.setAttribute("class", "collapsible-body z-depth-0");
        li.appendChild(collapsibleContainer);

        for (var imgIndex = 1; imgIndex < images.length; imgIndex++) {
          var photo = document.createElement("img");
          if (images[imgIndex].burger) {
            photo.setAttribute("class", "thumbnail highlighted");
          } else {
            photo.setAttribute("class", "thumbnail");
          }
          photo.setAttribute("src", images[imgIndex].url);
          collapsibleContainer.appendChild(photo);
        }

      } else {
        mainPhoto.setAttribute("src", "favicon.png");
      }

    }
  });
});
