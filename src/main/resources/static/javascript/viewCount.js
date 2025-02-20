window.onload = function () {
    const newsId = window.location.pathname.split('/').pop();

    if(newsId){
        api.put(`/api/news/${newsId}/view`)
        .then(response => {
            console.log(response);
        })
        .catch(error => {
            console.error("조회수 증가 실패" + error);
        });
    }
}