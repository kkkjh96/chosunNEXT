document.addEventListener('DOMContentLoaded', function () {
    const surveyList = document.getElementById('surveyList');

    // 설문조사 목록 데이터 가져오기
    api.get('/api/survey/list')
        .then(response => {
            const surveys = response;

            surveys.forEach(survey => {
                const card = document.createElement('div');
                card.classList.add('survey-card');

                card.innerHTML = `
                    <div class="survey-info">
                        <h3 class="survey-title">${survey.title}</h3>
                        <p class="survey-description">${survey.content}</p>
                    </div>
                    <div class="survey-actions">
                        <button class="button view-button" data-id="${survey.titleId}">보기</button>
                        <button class="button delete-button" data-id="${survey.titleId}">삭제</button>
                    </div>
                `;

                surveyList.appendChild(card);
            });

            // 보기 버튼 이벤트 등록
            document.querySelectorAll('.view-button').forEach(button => {
                button.addEventListener('click', function () {
                    const surveyId = button.getAttribute('data-id');
                    window.location.href = `/survey/view/${surveyId}`;
                });
            });

            // 삭제 버튼 이벤트 등록
            document.querySelectorAll('.delete-button').forEach(button => {
                button.addEventListener('click', function () {
                    const surveyId = button.getAttribute('data-id');
                    if (confirm('이 설문조사를 삭제하시겠습니까?')) {
                        api.delete(`/api/survey/${surveyId}`)
                            .then(() => {
                                alert('설문조사가 삭제되었습니다.');
                                window.location.reload();
                            })
                            .catch(error => {
                                console.error(error);
                                alert('삭제에 실패했습니다.');
                            });
                    }
                });
            });
        })
        .catch(error => {
            console.error(error);
            alert('설문조사 목록을 가져오는 데 실패했습니다.');
        });

    // 설문조사 등록 페이지 이동
    document.getElementById('createSurveyBtn').addEventListener('click', function () {
        window.location.href = '/survey/create';
    });
});
