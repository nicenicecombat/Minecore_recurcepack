document.addEventListener("DOMContentLoaded", () => {
  const toggle = document.querySelector(".nav-toggle");
  const links = document.querySelector(".nav-links");
  if (toggle && links) {
    toggle.addEventListener("click", () => {
      const isOpen = links.classList.toggle("open");
      toggle.setAttribute("aria-expanded", String(isOpen));
    });
  }

  document.querySelectorAll("[data-copy]").forEach((btn) => {
    btn.addEventListener("click", async () => {
      const value = btn.getAttribute("data-copy");
      const original = btn.textContent;
      try {
        await navigator.clipboard.writeText(value);
        btn.textContent = "Kopiert!";
      } catch (err) {
        btn.textContent = "Kopieren fehlgeschlagen";
      }
      setTimeout(() => {
        btn.textContent = original;
      }, 1800);
    });
  });
});
