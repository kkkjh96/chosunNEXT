document.addEventListener("DOMContentLoaded", function () {
    const menuItems = document.querySelectorAll(".menu-item > a");

    menuItems.forEach(item => {
        item.addEventListener("click", function (event) {
            event.preventDefault(); // 링크 기본 동작 방지

            let parent = this.parentElement;
            parent.classList.toggle("active"); // 클릭된 메뉴의 active 상태 변경
        });
    });
});
