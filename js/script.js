(function () {
  "use strict";

  // Mobile nav toggle
  var toggle = document.getElementById("nav-toggle");
  var nav = document.getElementById("main-nav");
  if (toggle && nav) {
    toggle.addEventListener("click", function () {
      var open = nav.classList.toggle("is-open");
      toggle.setAttribute("aria-expanded", open ? "true" : "false");
    });
    nav.querySelectorAll("a").forEach(function (link) {
      link.addEventListener("click", function () {
        nav.classList.remove("is-open");
        toggle.setAttribute("aria-expanded", "false");
      });
    });
  }

  // Speisekarte category filter
  var tabsWrap = document.getElementById("menu-tabs");
  if (tabsWrap) {
    var tabs = Array.prototype.slice.call(tabsWrap.querySelectorAll(".menu-tab"));
    var cats = Array.prototype.slice.call(document.querySelectorAll(".menu-cat"));
    var items = Array.prototype.slice.call(document.querySelectorAll(".menu-item"));

    tabs.forEach(function (tab) {
      tab.addEventListener("click", function () {
        var filter = tab.getAttribute("data-filter");

        tabs.forEach(function (t) { t.classList.remove("is-active"); });
        tab.classList.add("is-active");

        items.forEach(function (item) {
          var match = filter === "all" || item.getAttribute("data-cat") === filter;
          item.classList.toggle("is-hidden", !match);
        });

        cats.forEach(function (cat) {
          var match = filter === "all" || cat.getAttribute("data-cat") === filter;
          cat.style.display = match ? "" : "none";
        });
      });
    });
  }

  // Rundgang (virtual tour) gallery
  var stageImg = document.getElementById("tour-image");
  var thumbsWrap = document.getElementById("tour-thumbs");
  var prevBtn = document.getElementById("tour-prev");
  var nextBtn = document.getElementById("tour-next");
  var fullscreenBtn = document.getElementById("tour-fullscreen");

  var thumbs = [];
  var current = 0;

  function showSlide(index) {
    if (!thumbs.length) return;
    if (index < 0) index = thumbs.length - 1;
    if (index >= thumbs.length) index = 0;
    current = index;
    var thumb = thumbs[current];

    stageImg.style.opacity = 0;
    window.setTimeout(function () {
      stageImg.src = thumb.getAttribute("data-src");
      stageImg.alt = thumb.getAttribute("data-caption") || "";
      stageImg.style.opacity = 1;
    }, 120);

    thumbs.forEach(function (t) { t.classList.remove("is-active"); });
    thumb.classList.add("is-active");
  }

  if (stageImg && thumbsWrap) {
    thumbs = Array.prototype.slice.call(thumbsWrap.querySelectorAll(".tour-thumb"));

    thumbs.forEach(function (thumb, i) {
      thumb.addEventListener("click", function () { showSlide(i); });
    });

    if (prevBtn) prevBtn.addEventListener("click", function () { showSlide(current - 1); });
    if (nextBtn) nextBtn.addEventListener("click", function () { showSlide(current + 1); });

    var tourSection = document.getElementById("tour");
    if (tourSection) {
      tourSection.addEventListener("keydown", function (e) {
        if (e.key === "ArrowLeft") showSlide(current - 1);
        if (e.key === "ArrowRight") showSlide(current + 1);
      });
    }
  }

  // Fullscreen lightbox
  var lightbox = document.getElementById("lightbox");
  var lightboxImg = document.getElementById("lightbox-image");
  var lightboxClose = document.getElementById("lightbox-close");

  function openLightbox() {
    if (!lightbox || !lightboxImg) return;
    lightboxImg.src = stageImg.src;
    lightboxImg.alt = stageImg.alt;
    lightbox.classList.add("is-open");
  }
  function closeLightbox() {
    if (!lightbox) return;
    lightbox.classList.remove("is-open");
  }

  if (fullscreenBtn) fullscreenBtn.addEventListener("click", openLightbox);
  if (lightboxClose) lightboxClose.addEventListener("click", closeLightbox);
  if (lightbox) {
    lightbox.addEventListener("click", function (e) {
      if (e.target === lightbox) closeLightbox();
    });
  }
  document.addEventListener("keydown", function (e) {
    if (e.key === "Escape") closeLightbox();
  });
})();
