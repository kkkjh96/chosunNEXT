document.addEventListener('DOMContentLoaded', function () {
    const surveyContent = document.getElementById('surveyContent');
    const path = window.location.pathname; // "/survey/view/1"
    const surveyId = path.split('/').pop(); // "1"
    console.log(surveyId);

    api.get(`/api/survey/${surveyId}`)
        .then(response => {
            renderSurvey(response);
            console.log(response);
        })
        .catch(error => {
            console.error('설문조사 데이터를 가져오는데 실패했습니다.', error);
            surveyContent.innerHTML = '<p>데이터를 불러오는 중 오류가 발생했습니다.</p>';
        });

    function renderSurvey(survey) {
        const titleElement = document.createElement('h2');
        titleElement.textContent = survey.title;
        surveyContent.appendChild(titleElement);

        const descriptionElement = document.createElement('p');
        descriptionElement.textContent = survey.description;
        surveyContent.appendChild(descriptionElement);

        survey.questions.forEach((question, index) => {
            const questionElement = document.createElement('div');
            questionElement.classList.add('question');

            questionElement.innerHTML = `
<!--                <p class="question-title">질문 ${index + 1}: ${question.content} (${question.type})</p>-->
                <p class="question-title">질문 ${index + 1}: ${question.content}</p>
            `;

            question.options.forEach((option, idx) => {
                const optionElement = document.createElement('p');
                optionElement.classList.add('option');
                optionElement.textContent = `${idx + 1}. ${option.content}`;
                questionElement.appendChild(optionElement);
            });

            surveyContent.appendChild(questionElement);
        });
    }
    // 목록으로 돌아가기 버튼 이벤트
    document.getElementById('backButton').addEventListener('click', function () {
        window.location.href = '/survey/list';
    });
});
